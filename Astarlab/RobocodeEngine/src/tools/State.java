package tools;

import java.util.HashSet;
import java.util.Set;

import searchpractice.RouteFinder;

public class State {
	
	private int row;
	private int col;
	private static boolean[][] matrix = RouteFinder.getMatrix();
	private static int goalRow = RouteFinder.getGoalRow();
	private static int goalCol = RouteFinder.getGoalCol();
	
	public State(int r, int c) {
		row = r;
		col = c;
	}
	
	public State(State s) {
		row = s.row;
		col = s.col;
	}
	
	public boolean isGoal() {
		return row == goalRow && col == goalCol;
	}
	
	public Set<State> neighbors () {
    	Set<State> neighbors = new HashSet<>();
    	    	
    	if (validPosition(row, (col+1)) && !matrix[row][col+1]) {
    		State statR = new State(row, col+1);
    		neighbors.add(statR);
    	}
    	if (validPosition((row+1), col) && !matrix[row+1][col]) {
    		State statD = new State(row+1, col);
    		neighbors.add(statD);
    	}
    	if (validPosition(row, (col-1)) && !matrix[row][col-1]) {
    		State statL = new State(row, col-1);
    		neighbors.add(statL);
    	}
    	if (validPosition((row-1), col) && !matrix[row-1][col]) {
    		State statU = new State(row-1, col);
    		neighbors.add(statU);
    	}
    	return neighbors;
    }
	
	public int h() {
		return Math.abs(row - goalRow) + Math.abs(col - goalCol);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	@Override
    public boolean equals(Object o) {
    	boolean res = false;
    	if (o instanceof State) {
    		State s = (State) o;
    		res = row == s.row && col == s.col;
    	}
    	return res;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}
	
	private boolean validPosition(int r, int c) {
    	return r >= 0 && c >= 0 && r < Problem.NUMTILEROWS && c < Problem.NUMTILECOLS;
    }

}