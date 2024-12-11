package EhOTrem;

import robocode.*;

public class EhOTrem extends AdvancedRobot {
    private Boolean mode1V1 = false;
    private Arena arena;
    private Vector2 position;
    private long printInterval = 1000;

    public void run(){
        setTremColor();
        arena = new Arena(getBattleFieldHeight(), getBattleFieldWidth());
        System.out.println("Dimensões da arena: " + arena.length + " x " + arena.width);
        moveToClosestWall();

        while(true) {
            // controlMovement();
        }
    }

    public void moveToClosestWall() {
        updatePosition();

        // Calcula as distâncias até as paredes
        double distanceToNorth = position.y; // Distância para a parede do Norte
        double distanceToSouth = arena.length - position.y; // Distância para a parede do Sul
        double distanceToWest = position.x; // Distância para a parede do Oeste
        double distanceToEast = arena.width - position.x; // Distância para a parede do Leste

        // Exibe as distâncias até as paredes
        System.out.println("Distância até o Norte: " + distanceToNorth);
        System.out.println("Distância até o Sul: " + distanceToSouth);
        System.out.println("Distância até o Oeste: " + distanceToWest);
        System.out.println("Distância até o Leste: " + distanceToEast);

        // Determina a parede mais próxima
        double minDistance = Math.min(Math.min(distanceToNorth, distanceToSouth), Math.min(distanceToWest, distanceToEast));

        // Define a direção para a parede mais próxima
        if (minDistance == distanceToNorth) {
            // Movimenta o robô para o Norte
            setTurnRight(90);  // Gira o robô para a direção norte (perpendicular)
            setAhead(distanceToNorth);  // Move até a parede norte
        } else if (minDistance == distanceToSouth) {
            // Movimenta o robô para o Sul
            setTurnLeft(90);   // Gira o robô para a direção sul (perpendicular)
            setAhead(distanceToSouth);  // Move até a parede sul
        } else if (minDistance == distanceToWest) {
            // Movimenta o robô para o Oeste
            setTurnLeft(90);   // Gira o robô para a direção oeste (perpendicular)
            setAhead(distanceToWest);  // Move até a parede oeste
        } else if (minDistance == distanceToEast) {
            // Movimenta o robô para o Leste
            setTurnRight(90);  // Gira o robô para a direção leste (perpendicular)
            setAhead(distanceToEast);  // Move até a parede leste
        }

        // Aguarde o movimento ser concluído
        execute();

        // Mantenha o radar e o canhão girando
        setTurnRadarRight(360);
        setTurnGunRight(360);
    }

    public void controlMovement() {
        for(int directionTurns = 0; directionTurns < 25; directionTurns++) {
            basicMovement(true);

            updatePosition();
            System.out.println("Coordenadas atuais: (" + position.x + ", " + position.y + ")");

            try {
                Thread.sleep(printInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int directionTurns = 25; directionTurns > 0; directionTurns--) {
            basicMovement(false);

            updatePosition();
            System.out.println("Coordenadas atuais: (" + position.x + ", " + position.y + ")");

            try {
                Thread.sleep(printInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void basicMovement(boolean right){
        if (right) {
            setAhead(100);
            setTurnRight(100);
        } else {
            setAhead(100);
            setTurnLeft(100);
        }
        setTurnRadarRight(360);
        setTurnGunRight(360);
        //execute();
    }

    private void updatePosition() {
        position = new Vector2(getX(), getY());
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        //fire(1);
    }

    public void setTremColor(){
        setBodyColor(new java.awt.Color(255, 1, 255));
        setGunColor(new java.awt.Color(205, 148, 25));
        setBulletColor(new java.awt.Color(255, 1, 213, 255));
        setScanColor(new java.awt.Color(205, 148, 25));
    }
}

class Vector2 {
    public double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Arena {
    public double length, width;

    public Arena(double length, double width) {
        this.length = length;
        this.width = width;
    }
}

class Enemy {
    public String name;
    public double health, speed, energy;
    public Vector2 position;

    public Enemy(String name, double health, double speed, double energy, Vector2 position) {
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.energy = energy;
        this.position = position;
    }
}