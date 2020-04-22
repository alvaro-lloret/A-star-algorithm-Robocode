package robotastar;

import robocode.Robot;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import searchpractice.RouteFinder;
import tools.*;


public class RouteRobot extends Robot{
	static State current;
	static ArrayList<Info.Directions> path;
	static State start = new State(RouteFinder.getStartRow(), RouteFinder.getStartCol());
	static boolean[][] battleMatrix = RouteFinder.getMatrix();
	
    public static ArrayList<Info.Directions> getPath(){
    	ArrayList<Info.Directions> getpath = aStar();
    	return getpath;
    }
    
    public void run() {
    	
        System.out.println("Starting the execution of the robot...");

        /** Color of our robot **/
        setAllColors(Color.red);

        
        /** Execution of the solution found **/
        
        ArrayList<Info.Directions> path1 = getPath();
        
        turnRight(normalRelativeAngleDegrees(0 - getHeading()));
        if (path1!=null && !path1.isEmpty()) {
        	for (Info.Directions dir : path1) {
        		switch (dir) {
        		 	case RIGHT :	turnRight(up(getHeading()));    break;
        		 	case DOWN :		turnRight(right(getHeading())); break;
        			case LEFT :		turnRight(down(getHeading()));  break;
        			case UP :		turnRight(left(getHeading()));  break;
        			default :
        				
        		}
        		ahead(Problem.PIXELSPERTILE);
        	}
        } else {	// No solution
        	System.out.println("ERROR: Solution Not Found");
        }
        
        //PRINT IN THE ROBOT
        System.out.println("START: ("+ start.getRow() + ","+ start.getCol() +")");
        System.out.println("GOAL: ("+ current.getRow() +","+ current.getCol() +")");
        
        for (int i = 0; i < Problem.NUMTILEROWS; i++) {
            for (int j = 0; j < Problem.NUMTILECOLS; j++) {
                System.out.print(battleMatrix[i][j] + "  ");
            }
            System.out.println();
        }
        
        System.out.println("\nPath: ");
        for (Info.Directions dir : path) {
            System.out.print(dir + ", ");
        }
        
    }
    
    
    private static ArrayList<Info.Directions> aStar() {
    	HashMap<State, Info> searchTree = new HashMap<>();
        PriorityQueue<Info> openset = new PriorityQueue<>();
        Set<State> neighborStates = new HashSet<>();
        path = new ArrayList<>();
        Info c, n;
        int tentativeG;
        Info s = new Info(start, 0, null);
        openset.add(s);
        searchTree.put(start, s);     
                
        
        while (!openset.isEmpty()) {
        	c = openset.element();
        	current = c.getState();
        	
        	if (current.isGoal()) {		
        		path = reconstructPath(c);
        		return path;
        	}
        
        	c.setClosed();
        	neighborStates = current.neighbors();
        	
        	
        	for (State neighbor : neighborStates) {
        		if (!searchTree.containsKey(neighbor) || !searchTree.get(neighbor).isClosed()) {	
        			tentativeG = c.getValueG() + 1;		
            		if (!searchTree.containsKey(neighbor) || tentativeG < searchTree.get(neighbor).getValueG()) {
            			n = new Info(neighbor, tentativeG, c);
            			searchTree.put(neighbor, n);
            			if (openset.contains(n)) {
            				openset.remove(n);            				
            			}
            			openset.add(n);
            		}
        		}
        	}
        	openset.remove(c);
        }
        
        return path;
    }
    
    
    private static ArrayList<Info.Directions> reconstructPath(Info inf) {
    	ArrayList<Info.Directions> path = new ArrayList<>();
    	Info c = inf;
    	while (c.getParent() != null) {
    		path.add(c.getDirection());
    		c = c.getParent();
    	}
    	Collections.reverse(path);
    	return path;
    }
    
    
    private double up(double heading) {
		int facing = (int) heading;
		double up=0.0;
		if(facing==90) {
			up = 270.0;
		}else if(facing==180) {
			up = 180.0;
		}else if(facing==270) {
			up=90.0;
		}
		return up;
	}

	private double left(double heading) {
		int facing = (int) heading;
		double left=0.0;
		if(facing==0) {
			left = 270.0;
		}else if(facing==90) {
			left = 180.0;
		}else if(facing==180) {
			left=90.0;
		}
		return left;
	}

	private double down(double heading) {
		int facing = (int) heading;
		double down=0.0;
		if(facing==0) {
			down = 180.0;
		}else if(facing==90) {
			down = 90.0;
		}else if(facing==270) {
			down=270.0;
		}
		return down;
	}

	private double right(double heading) {
		int facing = (int) heading;
		double right=0.0;
		if(facing==0) {
			right = 90.0;
		}else if(facing==180) {
			right = 270.0;
		}else if(facing==270) {
			right=180.0;
		}
		return right;
	}
    
}