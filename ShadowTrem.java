package EhOTrem;


import java.awt.geom.*;
import robocode.*;
import robocode.util.Utils;
import java.util.ArrayList;
// Ponto-futuro
// Por: Antonio e Bernardo
public class ShadowTrem extends AdvancedRobot {


    // Variáveis compartilhadas
    private Point2D.Double myLocation;
    private Point2D.Double enemyLocation;


    private ArrayList<EnemyWave> enemyWaves;
    private ArrayList<Integer> surfDirections;
    private ArrayList<Double> surfAbsBearings;


    private static final int BINS = 47;
    private static final double[] surfStats = new double[BINS];
    private static final Rectangle2D.Double FIELD_RECT = new Rectangle2D.Double(18, 18, 764, 564);
    private static final double WALL_STICK = 160;
    private double opponentEnergy = 100.0;


    public void run() {
        enemyWaves = new ArrayList<>();
        surfDirections = new ArrayList<>();
        surfAbsBearings = new ArrayList<>();
        setTremColor();


        setAdjustGunForRobotTurn(true);  // Ajustar a arma para virar com o robô
        setAdjustRadarForGunTurn(true); // Ajustar o radar para seguir a arma


        do {
            turnRadarRightRadians(Double.POSITIVE_INFINITY);  // Rastrear o inimigo indefinidamente
        } while (true);
    }


    public void setTremColor(){
        setBodyColor(new java.awt.Color(255, 1, 255));
        setGunColor(new java.awt.Color(205, 148, 25));
        setBulletColor(new java.awt.Color(255, 1, 213, 255));
        setScanColor(new java.awt.Color(205, 148, 25));
    }


    public void onScannedRobot(ScannedRobotEvent e) {
        myLocation = new Point2D.Double(getX(), getY());


        double lateralVelocity = getVelocity() * Math.sin(e.getBearingRadians());
        double absBearing = e.getBearingRadians() + getHeadingRadians();


        setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing - getRadarHeadingRadians()) * 2);


        surfDirections.add(0, lateralVelocity >= 0 ? 1 : -1);
        surfAbsBearings.add(0, absBearing + Math.PI);


        double bulletPower = opponentEnergy - e.getEnergy();
        if (bulletPower < 3.01 && bulletPower > 0.09 && surfDirections.size() > 2) {
            EnemyWave ew = new EnemyWave();
            ew.fireTime = getTime() - 1;
            ew.bulletVelocity = bulletVelocity(bulletPower);
            ew.distanceTraveled = bulletVelocity(bulletPower);
            ew.direction = surfDirections.get(2);
            ew.directAngle = surfAbsBearings.get(2);
            ew.fireLocation = (Point2D.Double) enemyLocation.clone();


            enemyWaves.add(ew);
        }


        opponentEnergy = e.getEnergy();
        enemyLocation = project(myLocation, absBearing, e.getDistance());


        updateWaves();
        doSurfing();


        // Disparo de bala
        fireAtEnemy(e);
    }


    private void updateWaves() {
        for (int i = 0; i < enemyWaves.size(); i++) {
            EnemyWave ew = enemyWaves.get(i);
            ew.distanceTraveled = (getTime() - ew.fireTime) * ew.bulletVelocity;


            if (ew.distanceTraveled > myLocation.distance(ew.fireLocation) + 50) {
                enemyWaves.remove(i);
                i--;
            }
        }
    }


    private EnemyWave getClosestSurfableWave() {
        double closestDistance = Double.POSITIVE_INFINITY;
        EnemyWave surfWave = null;


        for (EnemyWave ew : enemyWaves) {
            double distance = myLocation.distance(ew.fireLocation) - ew.distanceTraveled;


            if (distance > ew.bulletVelocity && distance < closestDistance) {
                surfWave = ew;
                closestDistance = distance;
            }
        }


        return surfWave;
    }


    private void doSurfing() {
        EnemyWave surfWave = getClosestSurfableWave();


        if (surfWave == null) return;


        double dangerLeft = checkDanger(surfWave, -1);
        double dangerRight = checkDanger(surfWave, 1);


        double goAngle = absoluteBearing(surfWave.fireLocation, myLocation);
        if (dangerLeft < dangerRight) {
            goAngle = wallSmoothing(myLocation, goAngle - (Math.PI / 2), -1);
        } else {
            goAngle = wallSmoothing(myLocation, goAngle + (Math.PI / 2), 1);
        }


        setBackAsFront(this, goAngle);
    }


    private double checkDanger(EnemyWave surfWave, int direction) {
        int index = getFactorIndex(surfWave, predictPosition(surfWave, direction));
        return surfStats[index];
    }


    private Point2D.Double predictPosition(EnemyWave surfWave, int direction) {
        Point2D.Double predictedPosition = (Point2D.Double) myLocation.clone();
        double predictedVelocity = getVelocity();
        double predictedHeading = getHeadingRadians();
        double maxTurning, moveAngle, moveDir;


        int counter = 0;
        boolean intercepted = false;


        do {
            moveAngle = wallSmoothing(predictedPosition, absoluteBearing(surfWave.fireLocation, predictedPosition) + (direction * (Math.PI / 2)), direction) - predictedHeading;
            moveDir = 1;


            if (Math.cos(moveAngle) < 0) {
                moveAngle += Math.PI;
                moveDir = -1;
            }


            moveAngle = Utils.normalRelativeAngle(moveAngle);
            maxTurning = Math.PI / 720d * (40d - 3d * Math.abs(predictedVelocity));
            predictedHeading = Utils.normalRelativeAngle(predictedHeading + limit(-maxTurning, moveAngle, maxTurning));


            predictedVelocity += (predictedVelocity * moveDir < 0 ? 2 * moveDir : moveDir);
            predictedVelocity = limit(-8, predictedVelocity, 8);


            predictedPosition = project(predictedPosition, predictedHeading, predictedVelocity);


            counter++;
            if (predictedPosition.distance(surfWave.fireLocation) < surfWave.distanceTraveled + (counter * surfWave.bulletVelocity) + surfWave.bulletVelocity) {
                intercepted = true;
            }
        } while (!intercepted && counter < 500);


        return predictedPosition;
    }


    private int getFactorIndex(EnemyWave ew, Point2D.Double targetLocation) {
        double offsetAngle = absoluteBearing(ew.fireLocation, targetLocation) - ew.directAngle;
        double factor = Utils.normalRelativeAngle(offsetAngle) / maxEscapeAngle(ew.bulletVelocity) * ew.direction;


        return (int) limit(0, (factor * ((BINS - 1) / 2)) + ((BINS - 1) / 2), BINS - 1);
    }


    private double wallSmoothing(Point2D.Double botLocation, double angle, int orientation) {
        while (!FIELD_RECT.contains(project(botLocation, angle, WALL_STICK))) {
            angle += orientation * 0.05;
        }
        return angle;
    }


    private static Point2D.Double project(Point2D.Double sourceLocation, double angle, double length) {
        return new Point2D.Double(sourceLocation.x + Math.sin(angle) * length, sourceLocation.y + Math.cos(angle) * length);
    }


    private static double absoluteBearing(Point2D.Double source, Point2D.Double target) {
        return Math.atan2(target.x - source.x, target.y - source.y);
    }


    private static double limit(double min, double value, double max) {
        return Math.max(min, Math.min(value, max));
    }


    private static double bulletVelocity(double power) {
        return 20.0 - (3.0 * power);
    }


    private static double maxEscapeAngle(double velocity) {
        return Math.asin(8.0 / velocity);
    }


    private static void setBackAsFront(AdvancedRobot robot, double goAngle) {
        double angle = Utils.normalRelativeAngle(goAngle - robot.getHeadingRadians());
        if (Math.abs(angle) > (Math.PI / 2)) {
            if (angle < 0) {
                robot.setTurnRightRadians(Math.PI + angle);
            } else {
                robot.setTurnLeftRadians(Math.PI - angle);
            }
            robot.setBack(100);
        } else {
            if (angle < 0) {
                robot.setTurnLeftRadians(-angle);
            } else {
                robot.setTurnRightRadians(angle);
            }
            robot.setAhead(100);
        }
    }


    // Função para disparar contra o inimigo
    private void fireAtEnemy(ScannedRobotEvent e) {
        double distance = e.getDistance();
        double firePower = Math.min(3.0, Math.max(1.0, getEnergy() / 10)); // Dispara com base na energia disponível


        if (distance < 100) { // Se o inimigo está muito perto, usa potência máxima
            firePower = 3.0;
        }


        double absBearing = e.getBearingRadians() + getHeadingRadians();
        setTurnGunRightRadians(Utils.normalRelativeAngle(absBearing - getGunHeadingRadians()));
        setFire(firePower);
    }


    class EnemyWave {
        Point2D.Double fireLocation;
        long fireTime;
        double bulletVelocity, directAngle, distanceTraveled;
        int direction;
    }
}