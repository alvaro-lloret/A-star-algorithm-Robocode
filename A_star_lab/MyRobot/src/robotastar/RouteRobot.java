package robotastar;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;

public class RouteRobot extends Robot{
	
	public void run() {
		
		
		// Color of our robot
		setAllColors(Color.red);

		//Data that must be equal to the data in the Main class RouteFinder
		long seed = 0;
		int NumTileRows = 13;
		int NumTileCols = 10;
		int NumObstacles = 39;
		int PixelPerTile = 64;
		
		
		//robot is initially looking at the top
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));
		
		//Our robot will turn around itself a little bit		
		int k = 0;
		while(k < 20){
			turnRight(90);
			k++;
		}
		
		
		// We must:
		//  1. Generate the search problem
		//  2. Find the solution with a search algorithm
		//  3. Execute the solution found
			
	}
	 

}
