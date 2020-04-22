package tools;

import java.util.Random;

public class Problem {
	
	public final static int PIXELSPERTILE = 64;
	public final static int NUMTILEROWS = 13;
	public final static int NUMTILECOLS = 10;
	public final static double OBSTACLEFRACTION = 0.3;
	
	private boolean battleMatrix[][];
	private int startRow;
	private int startCol;
	private int goalRow;
	private int goalCol;
	private int[] obstaclesRows;
	private int[] obstaclesCols;
		
	
	public Problem() {
		
		long seed = 10;
		
		Random rnd = new Random(seed);
		Random noSeedRnd = new Random(seed);
		
		battleMatrix = new boolean[NUMTILEROWS][NUMTILECOLS];
		
		int NumObstacles = (int) (NUMTILEROWS*NUMTILECOLS*OBSTACLEFRACTION);
		
		obstaclesRows = new int[NumObstacles];
		obstaclesCols = new int[NumObstacles];
		
		int row, col;
		
		/** Stablishing the start **/
		startRow = noSeedRnd.nextInt(NUMTILEROWS);
		startCol = noSeedRnd.nextInt(NUMTILECOLS);
		battleMatrix[startRow][startCol] = true;
		
		/** Stablishing obstacles **/
		for(int i = 0; i < NumObstacles; i++){   
			do {
				row = rnd.nextInt(NUMTILEROWS);
				col = rnd.nextInt(NUMTILECOLS);
			} while (battleMatrix[row][col]);
			battleMatrix[row][col] = true;
			obstaclesRows[i] = row;
			obstaclesCols[i] = col;
		}
		
		/** Stablishing the goal **/
		do {
			row = noSeedRnd.nextInt(NUMTILEROWS);
			col = noSeedRnd.nextInt(NUMTILECOLS);
		} while (battleMatrix[row][col]);
		goalRow = row;
		goalCol = col;
	}
	
	public boolean[][] getMatrix (){
		return battleMatrix;
	}
	
	public int getStartRow (){
		return startRow;
	}
	
	public int getStartCol (){
		return startCol;
	}
	
	public int getGoalRow (){
		return goalRow;
	}
	
	public int getGoalCol (){
		return goalCol;
	}
	
	public int[] getObstaclesRows() {
		return obstaclesRows;
	}
	
	public int[] getObstaclesCols() {
		return obstaclesCols;
	}

}