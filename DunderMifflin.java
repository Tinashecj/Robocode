package DunderMifflin;
import robocode.*;
import java.awt.Color;
import robocode.util.*;

import robocode.*;

public class DunderMifflin extends AdvancedRobot {

	public void run() {
		setColors(Color.black,Color.red,Color.black); // body,gun,radar
		setBulletColor(Color.white);
		setScanColor(Color.cyan);
		setAdjustRadarForGunTurn(true);
		setTurnRadarRight(100000); // initial scan
		execute();
		while (true) {

			// if we stopped moving the radar, move it a tiny little bit
			// so we keep generating scan events
			if (getRadarTurnRemaining() == 0)
				setTurnRadarRight(5);

			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		//out.println("scanned: " + e.getName() + " at " + getTime());

		// turn toward the robot we scanned
		setTurnRight(e.getBearing());

		// if we've turned toward our enemy...
		if (Math.abs(getTurnRemaining()) < 10) {
			// move a little closer
			if (e.getDistance() > 200) {
				setAhead(e.getDistance() / 2);
			}
			// but not too close
			if (e.getDistance() < 100) {
				setBack(e.getDistance() * 2);
			}
			if (getEnergy() < 5 && e.getDistance() > 50){
				//do nothing
			}
			else if (e.getDistance() < 25){
				fire(10);
			}
			else if (e.getDistance() < 75){
				fire(5);
			}
			else if (e.getDistance() > 75 && e.getDistance() < 150){
				fire(3);
			}
			else if (e.getDistance() > 150 && e.getDistance() < 300){
				fire(2);
			}
			else if (e.getDistance() > 300 && e.getDistance() < 400){
				fire(1);

			}
		}

		// lock our radar onto our target
		setTurnRadarRight(getHeading() - getRadarHeading() + e.getBearing());
	}
	
	// if the robot we were shooting at died, scan around again
	public void onRobotDeath(RobotDeathEvent e) { setTurnRadarRight(1000); }
}