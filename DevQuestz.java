package devquestz;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.*;
/**
 * DevQuestz - a class by (Filipe da Silva Oliveira)
 */
public class DevQuestz extends Robot {

	boolean peek; // Não vire se houver um robô lá
	double moveAmount; // Quanto mover

	/**
	 * run: Movimentação pela parede
	 */
	public void run() {
		// Cores do Robô
		setBodyColor(Color.orange);
		setGunColor(Color.red);
		setRadarColor(Color.white);
		setBulletColor(Color.cyan);
		setScanColor(Color.cyan);

		// inicialize moveAmount com o máximo possível para este campo de batalha.
		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		// Inicializar peek para falso
		peek = false;

		// turnLeft para ficar de frente para a parede.
		// getHeading() % 90 significa o restante
		// getHeading() dividido por 90.
		turnLeft(getHeading() % 90);
		ahead(moveAmount);
		// Gire a arma para virar 90 graus para a direita.
		peek = true;
		turnGunRight(90);
		turnRight(90);

		while (true) {
			// Olhe antes de virar quando ahead() for concluído.
			peek = true;
			// Suba a parede
			ahead(moveAmount);
			// Não olhe agora
			peek = false;
			// Vire para a próxima parede
			turnRight(90);
		}
	}

	/**
	 * onHitRobot: Afaste-se um pouco.
	 */
	public void onHitRobot(HitRobotEvent e) {
		// If he's in front of us, set back up a bit.
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} // else he's in back of us, so set ahead a bit.
		else {
			ahead(100);
		}
	}

	/**
	 * onScannedRobot:  FOGO!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);
	// Note que o scan é chamado automaticamente quando o robô está se movendo.
	// Ao chamá-lo manualmente aqui, garantimos que geramos outro evento scan se houver um robô na próxima
	// parede, para que não comecemos a subir até que ele desapareça.
		if (peek) {
			scan();
		}
	}
}