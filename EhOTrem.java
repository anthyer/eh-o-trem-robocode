package EhOTrem;

import robocode.*; //lele 33

public class EhOTrem extends AdvancedRobot { // Alterado para AdvancedRobot para usar métodos avançados
    public void run() {
        while (true) {
            setTurnRadarRight(360); // Mantém o radar girando
            moveRandomly(); // Chamando movimentação aleatória
        }
    }

    private void moveRandomly() {
        double moveDistance = Math.random() * 200 + 50; // Movimenta-se entre 50 e 250 unidades
        double turnAngle = Math.random() * 180 - 90;    // Gira entre -90 e 90 graus

        setTurnRight(turnAngle);
        setAhead(moveDistance);

        execute();
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double bearing = e.getBearing();
        double enemyDistance = e.getDistance();

        // Mova-se perpendicularmente ao inimigo
        if (enemyDistance < 300) {
            setTurnRight(bearing + 90); 
            setAhead(150);
        } else {
            setTurnRight(bearing - 90); 
            setBack(150);
        }

        fire(Math.min(400 / enemyDistance, 3)); // Ajusta a potência do tiro com base na distância

        execute();
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        back(50); // Reage recuando mais ao ser atingido
        turnRight(90); // Tenta desviar para outro ângulo
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        back(50); // Recuar para uma distância segura
        turnRight(90); // Gira para evitar voltar ao mesmo lugar
    }
}
