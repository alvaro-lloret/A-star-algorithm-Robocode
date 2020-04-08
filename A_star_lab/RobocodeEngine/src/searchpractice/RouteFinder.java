package searchpractice;

import java.util.Random;

//import robocode.*;
//import robocode.Robot;
//import robocode.ScannedRobotEvent;

import robocode.control.*;
import robocode.control.events.*;

public class RouteFinder {
	
	/*
	final static int PIXELSPERTILE = 64;
	final static int NUMTILEROWS = 19;
	final static int NUMTILECOLS = 19;
	final static double OBSTACLEFRACTION = 0.3;
	*/
	
	static int battleMatrix [][] = null;
	
	static int startX = 0;
	static int startY = 0;
	static int goalX = 0;
	static int goalY = 0;
	
	static public int[][] getMatrix (){
		return battleMatrix;
	}
	static public int getStartX (){
		return startX;
	}
	static public int getStartY (){
		return startY;
	}
	static public int getGoalX (){
		return goalX;
	}
	static public int getGoalY (){
		return goalY;
	}
	public static void main(String[] args) {
		
		// Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
								// Run from C:/Robocode 
		 
		// Show the Robocode battle view          
		engine.setVisible(true); 
		 
		// Create the battlefield
		int NumPixelRows = 832; // 768 o 832
		int NumPixelCols = 640; // 576 o 640
		int PixelPerTile = 64;
		
		int NumTileRows = NumPixelRows / PixelPerTile; // 12 o 13
		int NumTileCols = NumPixelCols / PixelPerTile; // 9 o 10
		
		BattlefieldSpecification battlefield = new BattlefieldSpecification(NumPixelRows, NumPixelCols); 
													// 800x600 
		battleMatrix = new int[NumTileRows][NumTileCols];
		 
		// Setup battle parameters
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		
		double ObstacleFraction = 0.3;
		
		int NumObstacles = (int) (NumTileRows*NumTileCols*ObstacleFraction);
		
		/** Create obstacles and place them at random so that no pair of obstacles are at the same position **/
		RobotSpecification[] modelRobots = engine.getLocalRepository ("sample.SittingDuck, robotastar.RouteRobot*");
		RobotSpecification[] existingRobots = new RobotSpecification[NumObstacles+1];
		RobotSetup[] robotSetups = new RobotSetup[NumObstacles+1];
		
		Random rnd = new Random();
        long seed = 0;
        rnd.setSeed(seed);
        
        Random noSeedRnd = new Random();
        
        
		double InitialObstacleRow, InitialObstacleCol;
		int i, auxRow, auxCol, matrixRow, matrixCol;
		
		for(int NdxObstacle=0; NdxObstacle<NumObstacles; NdxObstacle++){   
			do {
				matrixRow = rnd.nextInt(NumTileRows);
				matrixCol = rnd.nextInt(NumTileCols);
				auxRow = matrixRow*PixelPerTile + (PixelPerTile/2);
				auxCol = matrixCol*PixelPerTile + (PixelPerTile/2);
				i = 0;
				while (i < NdxObstacle && !(robotSetups[i].getX() == auxRow && robotSetups[i].getY() == auxCol)) {
					i++;
				}
			} while (i < NdxObstacle);
			
			InitialObstacleRow = auxRow;
			InitialObstacleCol = auxCol;
			existingRobots[NdxObstacle] = modelRobots[0];
			battleMatrix[matrixRow][matrixCol] = 1;
			robotSetups[NdxObstacle]= new RobotSetup(InitialObstacleRow, InitialObstacleCol, 0.0);
		}
		
		/** Create the agent and place it in a random position without obstacle **/
		existingRobots[NumObstacles] = modelRobots[1];
		do {
			matrixRow = noSeedRnd.nextInt(NumTileRows);
			matrixCol = noSeedRnd.nextInt(NumTileCols);
			auxRow = matrixRow*PixelPerTile + (PixelPerTile/2);
			auxCol = matrixCol*PixelPerTile + (PixelPerTile/2);
			i = 0;
			while (i < NumObstacles && !(robotSetups[i].getX() == auxRow && robotSetups[i].getY() == auxCol)) {
				i++;
			}
		} while (i < NumObstacles);
		
		double InitialAgentRow = auxRow;   
		double InitialAgentCol = auxCol; 
		battleMatrix[matrixRow][matrixCol] = 2;
		startX = matrixRow;
		startY = matrixCol;
		robotSetups[NumObstacles] = new RobotSetup(InitialAgentRow, InitialAgentCol, 0.0); 
		
		do {
			matrixRow = noSeedRnd.nextInt(NumTileRows);
			matrixCol = noSeedRnd.nextInt(NumTileCols);
			auxRow = matrixRow*PixelPerTile + (PixelPerTile/2);
			auxCol = matrixCol*PixelPerTile + (PixelPerTile/2);
			i = 0;
			while (i < NumObstacles && !(robotSetups[i].getX() == auxRow && robotSetups[i].getY() == auxCol)) {
				i++;
			}
		} while (i < NumObstacles);
		battleMatrix[matrixRow][matrixCol] = 3;
		goalX = matrixRow;
		goalY = matrixCol;
		
		 
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
