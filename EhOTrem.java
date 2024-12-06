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
	   
		double enemyBearing = e.getBearing();
        double enemyDistance = e.getDistance();

		double absoluteBearing = getHeading() + e.getBearing(); // Calcula o ângulo absoluto 
		double gunTurn = absoluteBearing - getGunHeading(); // Calcula o quanto a arma deve girar 
		turnGunRight(normalizeBearing(gunTurn)); // Ajusta a arma para apontar para o inimigo 

		// Atira dependendo da distância (força ajustada) 
		if (enemyDistance < 150) {
            fire(3);  // Maior força de disparo para inimigos próximos
        } else if (enemyDistance < 400) {
            fire(2);  // Força média para inimigos a distâncias médias
        } else {
            fire(1);  // Menor força para inimigos distantes
        }

		// Continua rastreando o inimigo com o radar 
		double radarTurn = absoluteBearing - getRadarHeading(); turnRadarRight(normalizeBearing(radarTurn));
		
	} 

	// Função para normalizar o ângulo entre -180 e 180 graus 
	private double normalizeBearing(double angle) { 
		while (angle > 180) angle -= 360; 
		while (angle < -180) angle += 360; 
		return angle;
	}

   public void setTremColor(){
       setBodyColor(new java.awt.Color(255, 1, 255));
       setGunColor(new java.awt.Color(205, 148, 25));
       setBulletColor(new java.awt.Color(255, 1, 213, 255));
       setScanColor(new java.awt.Color(205, 148, 25));
   }
}