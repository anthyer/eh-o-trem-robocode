package EhOTrem;

import robocode.*;

public class EhOTrem extends AdvancedRobot {
    Boolean mode1V1 = false;

    public void run(){
        setTremColor();

        while(true) {
            for(int directionTurns = 0; directionTurns < 100; directionTurns++) {
                basicMovement(true);
            }
            for(int directionTurns = 100; directionTurns > 0; directionTurns--) {
                basicMovement(false);
            }
        }
    }

    public void basicMovement(boolean right){
        if (right) {
            setAhead(100);
            setTurnRight(100);
        } else {
            setAhead(-100);
            setTurnLeft(100);
        }
        setTurnRadarRight(360);
        setTurnGunRight(360);
        execute();
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }

    public void setTremColor(){
        setBodyColor(new java.awt.Color(255, 1, 255));
        setGunColor(new java.awt.Color(205, 148, 25));
        setBulletColor(new java.awt.Color(255, 1, 213, 255));
        setScanColor(new java.awt.Color(205, 148, 25));
    }
}