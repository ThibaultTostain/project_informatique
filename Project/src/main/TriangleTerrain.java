package main;

public class TriangleTerrain {
	
	private Terrain terrain;
	private PointTerrain start, middle, end;
	private SegmentTerrain first, second, third;
	private String id;
	private static long counter = 0;
	
	// Constructeurs
	
	public TriangleTerrain(PointTerrain start, PointTerrain middle, PointTerrain end) {
		super();
		if(start.getTerrain() != middle.getTerrain() || middle.getTerrain() != end.getTerrain()) {throw new Error("");}
		this.terrain = start.getTerrain();
		
		this.start = start;
		this.middle = middle;
		this.end = end;
		
		this.first = new SegmentTerrain (start,middle);
		this.second = new SegmentTerrain (middle,end);
		this.third = new SegmentTerrain (end,start);
		/* On associe à un triangle 3 segments et à un segment son triangle associé.
		 * De cette manière on peut retrouver un triangle à partir d'un segment.
		 */
		
		this.id = "TT"+newId(); // Identificateur : "TTN" où N est un entier
		this.terrain.getSol().add(this); // On ajout le triangle à la liste du terrain
		/* L'ajout se fait directement à la création car un triangle doit être associé à un terrain. Ainsi, on à pas besoin de
		 * méthode add dans la classe terrain et on a besoin de rien vérifier, l'attribut terrain est forcément null au départ.
		 */
	}
	
	// Get & Set
	
	public Terrain getTerrain() { // Permet de vérifier si un triangle appartient déja dans un terrain ou non
		return terrain;
	}

	public PointTerrain getStart() {
		return start;
	}
	
	public PointTerrain getMiddle() {
		return middle;
	}
	
	public PointTerrain getEnd() {
		return end;
	}

	public SegmentTerrain getFirst() {
		return first;
	}
	
	public SegmentTerrain getSecond() {
		return second;
	}
	
	public SegmentTerrain getThird() {
		return third;
	}

	public String getId() {
		return id;
	}
	
	// Affichage
	
	@Override
	public String toString() {
		return id +" [" + start + ", " + middle + ", " + end + "]";
	}

	// Fonctions & Procedures

	public boolean delete() {
		return this.getTerrain().getSol().remove(this);
	}
	
	public static synchronized String newId() {
	    return String.valueOf(counter ++);
	}
	
}
