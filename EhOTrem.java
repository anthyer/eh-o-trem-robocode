package EhOTrem;

import java.awt.geom.*;
import robocode.*;
import robocode.util.Utils;
import java.util.ArrayList;

public class EhOTrem extends AdvancedRobot {

    // Variáveis compartilhadas
    private Point2D.Double myLocation; // Localização atual do robô
    private Point2D.Double enemyLocation; // Localização atual do inimigo

    private ArrayList<EnemyWave> enemyWaves; // Lista para armazenar ondas de tiro do inimigo
    private ArrayList<Integer> surfDirections; // Direções de surf para evitar tiros
    private ArrayList<Double> surfAbsBearings; // Ângulos absolutos das direções de surf

    private static final int BINS = 47; // Número de divisões para o sistema de perigo
    private static final double[] surfStats = new double[BINS]; // Estatísticas de perigo
    private static final Rectangle2D.Double FIELD_RECT = new Rectangle2D.Double(18, 18, 764, 564); // Limites do campo
    private static final double WALL_STICK = 160; // Distância mínima da parede
    private double opponentEnergy = 100.0; // Energia inicial do oponente

    public void run() {
        enemyWaves = new ArrayList<>(); // Inicializa a lista de ondas
        surfDirections = new ArrayList<>(); // Inicializa a lista de direções de surf
        surfAbsBearings = new ArrayList<>(); // Inicializa a lista de ângulos absolutos de surf

        // Configurações iniciais do robô
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        // Loop principal
        do {
            turnRadarRightRadians(Double.POSITIVE_INFINITY); // Mantém o radar girando continuamente
        } while (true);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        myLocation = new Point2D.Double(getX(), getY()); // Atualiza a localização do robô

        double lateralVelocity = getVelocity() * Math.sin(e.getBearingRadians()); // Calcula a velocidade lateral
        double absBearing = e.getBearingRadians() + getHeadingRadians(); // Calcula o ângulo absoluto do inimigo

        setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing - getRadarHeadingRadians()) * 2); // Ajusta o radar

        surfDirections.add(0, lateralVelocity >= 0 ? 1 : -1); // Determina a direção de surf
        surfAbsBearings.add(0, absBearing + Math.PI); // Armazena o ângulo absoluto para surf

        double bulletPower = opponentEnergy - e.getEnergy(); // Calcula a potência do tiro do inimigo
        if (bulletPower < 3.01 && bulletPower > 0.09 && surfDirections.size() > 2) {
            // Cria uma nova onda de tiro
            EnemyWave ew = new EnemyWave();
            ew.fireTime = getTime() - 1; // Define o tempo do disparo
            ew.bulletVelocity = bulletVelocity(bulletPower); // Velocidade do projétil
            ew.distanceTraveled = bulletVelocity(bulletPower); // Distância inicial
            ew.direction = surfDirections.get(2); // Direção da onda
            ew.directAngle = surfAbsBearings.get(2); // Ângulo direto da onda
            ew.fireLocation = (Point2D.Double) enemyLocation.clone(); // Localização de origem da onda

            enemyWaves.add(ew); // Adiciona a onda à lista
        }

        opponentEnergy = e.getEnergy(); // Atualiza a energia do oponente
        enemyLocation = project(myLocation, absBearing, e.getDistance()); // Calcula a localização do inimigo

        updateWaves(); // Atualiza as ondas existentes
        doSurfing(); // Executa a lógica de surf

        // Lógica de disparo (não implementada)
    }

    private void updateWaves() {
        for (int i = 0; i < enemyWaves.size(); i++) {
            EnemyWave ew = enemyWaves.get(i);
            ew.distanceTraveled = (getTime() - ew.fireTime) * ew.bulletVelocity; // Calcula a distância percorrida pela onda

            if (ew.distanceTraveled > myLocation.distance(ew.fireLocation) + 50) {
                enemyWaves.remove(i); // Remove ondas que já passaram do robô
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
                surfWave = ew; // Define a onda mais próxima
                closestDistance = distance;
            }
        }

        return surfWave; // Retorna a onda mais próxima
    }

    private void doSurfing() {
        EnemyWave surfWave = getClosestSurfableWave(); // Obtém a onda mais próxima

        if (surfWave == null) return;

        double dangerLeft = checkDanger(surfWave, -1); // Calcula o perigo à esquerda
        double dangerRight = checkDanger(surfWave, 1); // Calcula o perigo à direita

        double goAngle = absoluteBearing(surfWave.fireLocation, myLocation); // Calcula o ângulo para se mover
        if (dangerLeft < dangerRight) {
            goAngle = wallSmoothing(myLocation, goAngle - (Math.PI / 2), -1); // Move-se para a esquerda
        } else {
            goAngle = wallSmoothing(myLocation, goAngle + (Math.PI / 2), 1); // Move-se para a direita
        }

        setBackAsFront(this, goAngle); // Move o robô para a direção calculada
    }

    private double checkDanger(EnemyWave surfWave, int direction) {
        int index = getFactorIndex(surfWave, predictPosition(surfWave, direction)); // Calcula o índice do fator de perigo
        return surfStats[index]; // Retorna o perigo associado
    }

    private Point2D.Double predictPosition(EnemyWave surfWave, int direction) {
        Point2D.Double predictedPosition = (Point2D.Double) myLocation.clone(); // Clona a localização atual
        double predictedVelocity = getVelocity(); // Velocidade prevista
        double predictedHeading = getHeadingRadians(); // Direção prevista
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

            predictedPosition = project(predictedPosition, predictedHeading, predictedVelocity); // Calcula a nova posição prevista

            counter++;
            if (predictedPosition.distance(surfWave.fireLocation) < surfWave.distanceTraveled + (counter * surfWave.bulletVelocity) + surfWave.bulletVelocity) {
                intercepted = true; // Verifica se o projétil atinge a posição prevista
            }
        } while (!intercepted && counter < 500);

        return predictedPosition; // Retorna a posição prevista
    }

    private int getFactorIndex(EnemyWave ew, Point2D.Double targetLocation) {
        double offsetAngle = absoluteBearing(ew.fireLocation, targetLocation) - ew.directAngle;
        double factor = Utils.normalRelativeAngle(offsetAngle) / maxEscapeAngle(ew.bulletVelocity) * ew.direction;

        return (int) limit(0, (factor * ((BINS - 1) / 2)) + ((BINS - 1) / 2), BINS - 1); // Calcula o índice do fator de perigo
    }

    private double wallSmoothing(Point2D.Double botLocation, double angle, int orientation) {
        while (!FIELD_RECT.contains(project(botLocation, angle, WALL_STICK))) {
            angle += orientation * 0.05; // Ajusta o ângulo para evitar colisão com a parede
        }
        return angle;
    }

    private static Point2D.Double project(Point2D.Double sourceLocation, double angle, double length) {
        return new Point2D.Double(sourceLocation.x + Math.sin(angle) * length, sourceLocation.y + Math.cos(angle) * length); // Calcula uma nova posição baseada em um ângulo e distância
    }

    private static double absoluteBearing(Point2D.Double source, Point2D.Double target) {
        return Math.atan2(target.x - source.x, target.y - source.y); // Calcula o ângulo absoluto entre duas posições
    }

    private static double limit(double min, double value, double max) {
        return Math.max(min, Math.min(value, max)); // Restringe um valor entre um mínimo e um máximo
    }

    private static double bulletVelocity(double power) {
        return 20.0 - (3.0 * power); // Calcula a velocidade de um projétil com base na potência
    }

    private static double maxEscapeAngle(double velocity) {
        return Math.asin(8.0 / velocity); // Calcula o ângulo máximo de fuga baseado na velocidade do projétil
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

    class EnemyWave {
        Point2D.Double fireLocation; // Local de origem da onda
        long fireTime; // Momento do disparo
        double bulletVelocity, directAngle, distanceTraveled; // Propriedades da onda
        int direction; // Direção da onda
    }
}
