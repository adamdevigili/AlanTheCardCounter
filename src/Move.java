import java.util.Comparator;


public class Move implements Comparable {

	int[] location;
	int potential;
	
	public Move(int[] location, int potential)
	{
		this.location = location;
		this.potential = potential;
	}


	@Override
	public int compareTo(Object arg0) {
		return ((Move)arg0).potential - potential;
	}

}
