package robotastar;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;




public class RouteRobot extends Robot{

	public enum Directions { UP, RIGHT, DOWN, LEFT }
	
	int goalX = 3;
    int goalY = 2;
	
	//private static class
    private class State {
        int x;
        int y;
        int g;
        int h;
        State parent;

        public State(int posX, int posY) {
            x = posX;
            y = posY;
            h = Math.abs(x - goalX) + Math.abs(y - goalY);
        }       
    }
    
    private int distance(State a, State b) {
    	return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    
    private int functionF(State s) {
    	return s.g + s.h;
    }
	
    public void run() {
    	

        System.out.println("Iniciando ejecución del robot");

        // Color of our robot
        setAllColors(Color.pink);

        //Data that must be equal to the data in the Main class RouteFinder
		int PixelPerTile = 64;
		int NumTileRows = 13;
		int NumTileCols = 10;
        long seed = 0;
		int NumObstacles = 39;
        

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
        int startX = 4;
        int startY = 4;
        
        
        Set<State> closedset = new HashSet<>(); // The set of nodes already evaluated
        Set<State> openset = new HashSet<>(); // The set of tentative nodes to be evaluated, el conjunto de start
        State start = new State(startX, startY);
        start.g = 0; // Cost from start along best known path
        openset.add(start);
        // PAPAPAAP Set<Directions> parent = new HashSet<>(); // The map of navigated nodes
        int f;
        f = functionF(start); // Estimated total cost from start to nearest goal through y
        int minf;
        int tentativeG;
        State current;
        while (!openset.isEmpty()) {
        	
        	minf = Integer.MAX_VALUE;
        	for (State c : openset) {
        		if (functionF(c) < minf) {
        			current = c;	// the node in openset having the lowest f[] value
        		}
        	}
        	
        	if (current.x == goalX && current.y == goalY) {
        		return reconstruct_path(parent, current);
        	}
        	openset.remove(current);
        	closedset.add(current);
        	
        	for (State neighbor : neighbor_nodes(current)) {
        		if (closedset.contains(neighbor)) {
        			continue;
        		}
        		tentativeG = current.g + distance(current, neighbor);
        		if (openset.contains(neighbor) || tentativeG < neighbor.g) {
        			neighbor.parent = current;
        			neighbor.g= tentativeG;
        			f = functionF(neighbor);
        			if (!openset.contains(neighbor)) {
        				openset.add(neighbor);
        				return failure;
        			}
        		}
        	}
        }
        
        
        
        //  2. Find the solution with a search algorithm
        //  3. Execute the solution found

    }

}
