package searchpractice;

import java.util.ArrayList;
import robotastar.RouteRobot;
import tools.Info;

import robocode.control.*;

import tools.Problem;

public class RouteFinder {
	
	static Problem p = new Problem();
	final static int startRow = p.getStartRow();
	final static int startCol = p.getStartCol();
	final static int goalRow = p.getGoalRow();
	final static int goalCol = p.getGoalCol();
	final static boolean[][] battleMatrix = p.getMatrix();
	final static int[] obstaclesRows = p.getObstaclesRows();
	final static int[] obstaclesCols = p.getObstaclesCols();
	
	public static boolean[][] getMatrix (){
        return battleMatrix;
    }

    public static int getStartRow (){
        return startRow;
    }

    public static int getStartCol (){
        return startCol;
    }

    public static int getGoalRow (){
        return goalRow;
    }

    public static int getGoalCol (){
        return goalCol;
    }
		
	public static void main(String[] args) {
		
		// Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
																	// Run from C:/Robocode 
		 
		// Show the Robocode battle view          
		engine.setVisible(true); 
		 
		// Create the battlefield
		int NumPixelRows = Problem.NUMTILEROWS * Problem.PIXELSPERTILE;
		int NumPixelCols = Problem.NUMTILECOLS * Problem.PIXELSPERTILE;		 
		
		BattlefieldSpecification battlefield = new BattlefieldSpecification(NumPixelRows, NumPixelCols); 
																				// 832x640 
		 
		// Setup battle parameters
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		
		int NumObstacles = (int) (Problem.NUMTILEROWS*Problem.NUMTILECOLS*Problem.OBSTACLEFRACTION);
		
		
		/** Create obstacles and place them at random so that no pair of obstacles are at the same position **/
		RobotSpecification[] modelRobots = engine.getLocalRepository ("sample.SittingDuck, robotastar.RouteRobot*");
		RobotSpecification[] existingRobots = new RobotSpecification[NumObstacles+1];
		RobotSetup[] robotSetups = new RobotSetup[NumObstacles+1];
				
		double InitialObstacleRow, InitialObstacleCol;
		
		for(int NdxObstacle=0; NdxObstacle<NumObstacles; NdxObstacle++){   
			InitialObstacleRow = obstaclesRows[NdxObstacle] * Problem.PIXELSPERTILE + (Problem.PIXELSPERTILE/2);
			InitialObstacleCol = obstaclesCols[NdxObstacle] * Problem.PIXELSPERTILE + (Problem.PIXELSPERTILE/2);
			existingRobots[NdxObstacle] = modelRobots[0];
			robotSetups[NdxObstacle]= new RobotSetup(InitialObstacleRow, InitialObstacleCol, 0.0);
		}
		
		/** Create the agent and place it in a random position without obstacle **/
		existingRobots[NumObstacles] = modelRobots[1];
		double InitialAgentRow = startRow * Problem.PIXELSPERTILE + (Problem.PIXELSPERTILE/2);   
		double InitialAgentCol = startCol * Problem.PIXELSPERTILE + (Problem.PIXELSPERTILE/2);   
		robotSetups[NumObstacles] = new RobotSetup(InitialAgentRow, InitialAgentCol, 0.0); 
				
		
		/** Create and run the battle **/ 
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numberOfRounds,
																	inactivityTime, gunCoolingRate,
																		sentryBorderSize, hideEnemyNames,
																			existingRobots, robotSetups);
		 
		System.out.println("START: ("+ startRow +","+ startCol+")"); 
		System.out.println("GOAL: ("+ goalRow +","+ goalCol+")");
		
		for (int i = 0; i < Problem.NUMTILEROWS; i++) {
            for (int j = 0; j < Problem.NUMTILECOLS; j++) {
                System.out.print(battleMatrix[i][j] + "  ");
            }
            System.out.println();
        }
		
		ArrayList<Info.Directions> path1 = RouteRobot.getPath();
		System.out.println("\nPath: ");
        for (Info.Directions dir : path1) {
            System.out.print(dir + ", ");
        }
        
		// Run our specified battle and let it run till it is over 
		engine.runBattle(battleSpec, true); // waits till the battle finishes 
		 
		// Cleanup our RobocodeEngine
		engine.close();
		 
		// Make sure that the Java VM is shut down properly 
		System.exit(0); 
	}
	
}