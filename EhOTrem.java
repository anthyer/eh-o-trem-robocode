package EhOTrem;
import java.awt.geom.*;
import robocode.*;
import java.lang.Math;

public class EhOTrem extends Robot {
    static Point2D.Double[] enemyPoints = new Point2D.Double[10];
    int count;

    public void run() {
        while(true) {
            ahead(100);
            turnGunRight(360);
            back(100);
            turnGunRight(360);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
        double absBearing = e.getBearingRadians() + getHeadingRadians(); // Método correto para obter o ângulo do robô
        enemyPoints[count] = new Point2D.Double(getX() + e.getDistance() * Math.cos(absBearing),
                                                getY() + e.getDistance() * Math.sin(absBearing));
    
        if (++count >= 10) {
            count = 0;
        }
    }
    

    public void onHitByBullet(HitByBulletEvent e) {
        back(10);
    }

    public void onHitWall(HitWallEvent e) {
        back(20);
    }
}
