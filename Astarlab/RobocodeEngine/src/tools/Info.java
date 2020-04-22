package tools;

public class Info implements Comparable<Info> {
	
public enum Directions {UP, RIGHT, DOWN, LEFT }
	
	private State s;
	private int g, f;
	private Info parent;
	private boolean closed;
	private Directions direction;
	
	public Info(State s1, int gValue, Info p) {
		s = new State (s1);
		g = gValue;
		f = gValue + s.h();
		parent = p;
		closed = false;
		if (parent != null) {
			direction = setDir(s, parent.getState());
		} else {
			direction = null;
		}	
	}
	
	public Directions getDirection() {
		return direction;
	}
	
	
	public int getValueG() {
		return g;
	}
	
	public void setClosed() {
		closed = true;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public State getState() {
		return s;
	}
	
	public Info getParent() {
		return parent;
	}
	
	@Override
    public boolean equals(Object o) {
		boolean res = false;
        if (o instanceof Info) {
            Info s = (Info) o;
            res = this.getState().getRow() == s.getState().getRow() &&
                     this.getState().getCol() == s.getState().getCol();
        }
        return res;
    }

	@Override
	public int hashCode() {
		return s.hashCode();
	}


	@Override
	public int compareTo(Info s) {
		return f - s.f;
	}

	private Directions setDir(State s1, State s2) {
		int r1 = s1.getRow();
		int c1 = s1.getCol();
		int r2 = s2.getRow();
		int c2 = s2.getCol();
		Directions dir;
		if (r1 == r2 && c1 == c2+1) {
			dir = Directions.RIGHT;
		} else if (r1 == r2) {
			dir = Directions.LEFT;
		} else if (r1 == r2+1) {
			dir = Directions.DOWN;
		} else {
			dir = Directions.UP;
		}
		return dir;
	}
	
}