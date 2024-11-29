package EhOTrem;

import robocode.*;
import java.util.ArrayList;

public class EhOTrem extends AdvancedRobot {
    private ArrayList<Wave> enemyWaves = new ArrayList<>();
    private double previousEnemyEnergy = 100;

    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        while (true) {
            turnRadarRightRadians(Double.POSITIVE_INFINITY);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double enemyEnergyDrop = previousEnemyEnergy - e.getEnergy();
        if (enemyEnergyDrop > 0 && enemyEnergyDrop <= 3) {
            double bulletVelocity = 20 - 3 * enemyEnergyDrop;
            Wave wave = new Wave(
                    getX(),
                    getY(),
                    getHeadingRadians() + e.getBearingRadians(),
                    bulletVelocity,
                    getTime()
            );
            enemyWaves.add(wave);
        }
        previousEnemyEnergy = e.getEnergy();
        doSurfing();
    }

    public void doSurfing() {
        double closestDistance = Double.MAX_VALUE;
        Wave closestWave = null;

        for (Wave wave : enemyWaves) {
            double distance = wave.getDistance(getTime()) - distanceToWave(wave);

            if (distance < closestDistance) {
                closestWave = wave;
                closestDistance = distance;
            }
        }

        if (closestWave != null) {
            double angleToSurf = Math.PI / 2 + closestWave.angle;
            setTurnRightRadians(Math.sin(angleToSurf));
            setAhead(100 * Math.cos(angleToSurf));
        }
    }

    public double distanceToWave(Wave wave) {
        double dx = getX() - wave.startX;
        double dy = getY() - wave.startY;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

class Wave {
    public double startX, startY, velocity;
    public long startTime;
    public double angle;

    public Wave(double x, double y, double angle, double velocity, long startTime) {
        this.startX = x;
        this.startY = y;
        this.angle = angle;
        this.velocity = velocity;
        this.startTime = startTime;
    }

    public double getDistance(double time) {
        return (time - startTime) * velocity;
    }
}