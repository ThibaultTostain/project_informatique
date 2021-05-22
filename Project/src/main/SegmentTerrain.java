package main;

public class SegmentTerrain {
	
	private PointTerrain start, end; // Un segment est défini par un point de départ et un d'arriver

	// Constructeurs
	
	public SegmentTerrain(PointTerrain start, PointTerrain end) {
		if(start.getTerrain() != end.getTerrain()) {throw new Error("");}
		this.start = start;
		this.end = end;
	}

	// Get & Set
	
	public PointTerrain getStart() {
		return start;
	}
	
	public void setStart(PointTerrain start) {
		this.start = start;
	}
	
	public PointTerrain getEnd() {
		return end;
	}
	
	public void setEnd(PointTerrain end) {
		this.end = end;
	}

	// Affichage
	
	@Override
	public String toString() {
		return "[" + start + ", " + end + "]";
	}
	
}
