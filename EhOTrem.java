package EhOTrem;

import java.awt.geom.*;
import robocode.*;
import robocode.util.Utils;
import java.util.ArrayList;
// Ponto-futuro
// Por: Antonio e Bernardo
public class EhOTrem extends AdvancedRobot {

    // Variáveis compartilhadas
    private Point2D.Double myLocation;
    private Point2D.Double enemyLocation;

    private ArrayList<EnemyWave> enemyWaves;
    private ArrayList<Integer> surfDirections;
    private ArrayList<Double> surfAbsBearings;

    private static final int BINS = 47;
    private static final double[] surfStats = new double[BINS];
    private static final double WALL_STICK = 160;
    private double opponentEnergy = 100.0;
    private Rectangle2D.Double fieldRect;

    public void run() {
        // Inicializar o campo de batalha dinamicamente
        double fieldWidth = getBattleFieldWidth();
        double fieldHeight = getBattleFieldHeight();
        fieldRect = new Rectangle2D.Double(18, 18, fieldWidth - 36, fieldHeight - 36);

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

    public void setTremColor() {
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

        // Disparo de bala com mira adaptativa
        fireAtEnemy(e);
    }

    public void onHitRobot(HitRobotEvent e) {
        // Ajustar o robô para o lado contrário do inimigo
        adjustBearingAwayFromEnemy(e);
        
        // Se o robô tiver energia suficiente, atirar no inimigo
        if (getEnergy() > 50) {
            setFire(3);
        } else {
            setFire(1);
        }
    
        // Se o robô estiver muito próximo do inimigo, mover-se para trás
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            setBack(50);
        } else {
            setAhead(50);
        }
    
        // Mudar o alvo do radar para o robô que bateu
        double bearing = e.getBearing();
        double heading = getHeading();
        double radarTurn = Utils.normalRelativeAngleDegrees(heading + bearing - getRadarHeading());
        setTurnRadarRight(radarTurn);
        execute();
    }
    
    public void onHitByBullet(HitByBulletEvent e) {
        // Lógica para lidar com tiros recebidos
        // Você pode adicionar movimentação evasiva aqui
        double bearing = e.getBearing();
        double heading = getHeading();
        double angle = Math.toRadians((heading + bearing) % 360);
    
        // Calcular a posição do inimigo que disparou o tiro
        double enemyX = getX() + Math.sin(angle) * e.getVelocity();
        double enemyY = getY() + Math.cos(angle) * e.getVelocity();
    
        // Adicionar lógica para reagir ao tiro
        // Por exemplo, mover-se para uma posição diferente
        setTurnRight(90 - bearing);
        setAhead(150);
        execute();
    
        // Verificar se o robô conseguiu se mover
        if (getDistanceRemaining() == 0) {
            // Se não conseguiu se mover, tentar na direção oposta
            setTurnRight(90 - bearing);
            setBack(150);
            execute();
        }
    
        // Mudar o alvo do radar para o robô que está atingindo
        double radarTurn = Utils.normalRelativeAngleDegrees(heading + bearing - getRadarHeading());
        setTurnRadarRight(radarTurn);
        execute();
    }
    
    private Point2D.Double predictEnemyPosition(ScannedRobotEvent e, long time) {
        double enemyHeading = e.getHeadingRadians();
        double enemyVelocity = e.getVelocity();
        double enemyAcceleration = 0;

        double deltaTime = 0;
        double predictedX = enemyLocation.x;
        double predictedY = enemyLocation.y;

        while ((++deltaTime) * bulletVelocity(3.0) < Point2D.distance(myLocation.x, myLocation.y, predictedX, predictedY)) {
            enemyAcceleration = (enemyVelocity - e.getVelocity()) / deltaTime;
            enemyVelocity += enemyAcceleration;
            predictedX += Math.sin(enemyHeading) * enemyVelocity;
            predictedY += Math.cos(enemyHeading) * enemyVelocity;

            if (predictedX < 18.0 || predictedY < 18.0 || predictedX > getBattleFieldWidth() - 18.0 || predictedY > getBattleFieldHeight() - 18.0) {
                predictedX = Math.min(Math.max(18.0, predictedX), getBattleFieldWidth() - 18.0);
                predictedY = Math.min(Math.max(18.0, predictedY), getBattleFieldHeight() - 18.0);
                break;
            }
        }

        return new Point2D.Double(predictedX, predictedY);
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

    private void adjustBearingAwayFromEnemy(HitRobotEvent e) {
        // Determinar o ângulo para se afastar do robô
        double angleToMove = e.getBearingRadians() + Math.PI;

        // Ajustar a direção do robô para evitar travamento
        setTurnRightRadians(Utils.normalRelativeAngle(angleToMove - getHeadingRadians()));
        setAhead(100); // Move-se para frente na direção ajustada
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

    private Point2D.Double predictEnemyPosition(ScannedRobotEvent e, double bulletSpeed) {
        Point2D.Double predictedPosition = (Point2D.Double) enemyLocation.clone();
        double enemyHeading = e.getHeadingRadians();
        double enemyVelocity = e.getVelocity();
        double deltaTime = 0;

        // Iterar para prever a posição do inimigo no futuro
        while ((++deltaTime) * bulletSpeed < myLocation.distance(predictedPosition)) {
            // Atualizar a posição do inimigo com base no movimento atual
            predictedPosition = project(predictedPosition, enemyHeading, enemyVelocity);

            // Prever a mudança de direção se o inimigo atingir as bordas do campo
            if (!fieldRect.contains(predictedPosition)) {
                enemyHeading += Math.PI; // Inverte a direção ao atingir a parede
            }
        }

        return predictedPosition;
    }

    private int getFactorIndex(EnemyWave ew, Point2D.Double targetLocation) {
        double offsetAngle = absoluteBearing(ew.fireLocation, targetLocation) - ew.directAngle;
        double factor = Utils.normalRelativeAngle(offsetAngle) / maxEscapeAngle(ew.bulletVelocity) * ew.direction;

        return (int) limit(0, (factor * ((BINS - 1) / 2)) + ((BINS - 1) / 2), BINS - 1);
    }

    private double wallSmoothing(Point2D.Double botLocation, double angle, int orientation) {
        while (!fieldRect.contains(project(botLocation, angle, WALL_STICK))) {
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
        double bulletPower = Math.min(3.0, Math.max(1.0, getEnergy() / 10)); // Dispara com base na energia disponível
        Point2D.Double predictedPosition = predictEnemyPosition(e, getTime());

        double absBearing = absoluteBearing(myLocation, predictedPosition);
        double gunTurn = Utils.normalRelativeAngle(absBearing - getGunHeadingRadians());

        setTurnGunRightRadians(gunTurn);
        if (getGunHeat() == 0 && Math.abs(gunTurn) < Math.PI / 18) { // Dispara se a arma estiver pronta e quase alinhada
            setFire(bulletPower);
        }
    }

    class EnemyWave {
        Point2D.Double fireLocation;
        long fireTime;
        double bulletVelocity, directAngle, distanceTraveled;
        int direction;
    }
}