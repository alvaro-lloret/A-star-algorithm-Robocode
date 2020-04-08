package searchpractice;

import java.util.Random;

//import robocode.*;
//import robocode.Robot;
//import robocode.ScannedRobotEvent;

import robocode.control.*;
import robocode.control.events.*;

public class RouteFinder {
	
	final static int PIXELSPERTILE = 64;
	final static int NUMTILEROWS = 19;
	final static int NUMTILECOLS = 19;
	final static double OBSTACLEFRACTION = 0.3;
	
	public static void main(String[] args) {
		
		// Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
								// Run from C:/Robocode 
		 
		// Show the Robocode battle view          
		engine.setVisible(true); 
		 
		// Create the battlefield
		int NumPixelRows = NUMTILEROWS*PIXELSPERTILE;
		int NumPixelCols = NUMTILECOLS*PIXELSPERTILE;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(NumPixelRows, NumPixelCols); 
													// 800x600 
		 
		// Setup battle parameters
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		
		int NumObstacles = (int) (NUMTILEROWS*NUMTILECOLS*OBSTACLEFRACTION);
		
		/** Create obstacles and place them at random so that no pair of obstacles are at the same position **/
		RobotSpecification[] modelRobots = engine.getLocalRepository ("sample.SittingDuck,robotastar.RouteRobot*");
		RobotSpecification[] existingRobots = new RobotSpecification[NumObstacles+1];
		RobotSetup[] robotSetups = new RobotSetup[NumObstacles+1];
		
		Random rnd = new Random();
        	long seed = (PIXELSPERTILE*NUMTILEROWS*NUMTILECOLS);
        	rnd.setSeed(seed);
        
		double InitialObstacleRow, InitialObstacleCol;
		int i, auxRow, auxCol;
		
		for(int NdxObstacle=0; NdxObstacle<NumObstacles; NdxObstacle++){   
			do {
				auxRow = rnd.nextInt(NUMTILEROWS);
				auxCol = rnd.nextInt(NUMTILECOLS);
				i = 0;
				while (i < NdxObstacle && !(robotSetups[i].getX() == auxRow && robotSetups[i].getY() == auxCol)) {
					i++;
				}
			} while (i < NdxObstacle);
			
			InitialObstacleRow = auxRow;
			InitialObstacleCol = auxCol;
			existingRobots[NdxObstacle] = modelRobots[0];
			robotSetups[NdxObstacle]= new RobotSetup(InitialObstacleRow, InitialObstacleCol, 0.0);                 
		}
		
		/** Create the agent and place it in a random position without obstacle **/
		existingRobots[NumObstacles] = modelRobots[1];
		do {
			auxRow = rnd.nextInt(NUMTILEROWS);
			auxCol = rnd.nextInt(NUMTILECOLS);
			i = 0;
			while (i < NumObstacles && !(robotSetups[i].getX() == auxRow && robotSetups[i].getY() == auxCol)) {
				i++;
			}
		} while (i < NumObstacles);
		
		double InitialAgentRow = auxRow;   
		double InitialAgentCol = auxCol;   
		robotSetups[NumObstacles] = new RobotSetup(InitialAgentRow, InitialAgentCol, 0.0); 
		 
		/** Create and run the battle **/ 
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numberOfRounds,
																	inactivityTime, gunCoolingRate,
																		sentryBorderSize, hideEnemyNames,
																			existingRobots, robotSetups);
		 
		// Run our specified battle and let it run till it is over 
		engine.runBattle(battleSpec, true); // waits till the battle finishes 
		 
		// Cleanup our RobocodeEngine
		engine.close();
		 
		// Make sure that the Java VM is shut down properly 
		System.exit(0); 
	}
	
}