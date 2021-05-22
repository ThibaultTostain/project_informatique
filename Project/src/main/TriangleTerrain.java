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
		/* On associe � un triangle 3 segments et � un segment son triangle associ�.
		 * De cette mani�re on peut retrouver un triangle � partir d'un segment.
		 */
		
		this.id = "TT"+newId(); // Identificateur : "TTN" o� N est un entier
		this.terrain.getSol().add(this); // On ajout le triangle � la liste du terrain
		/* L'ajout se fait directement � la cr�ation car un triangle doit �tre associ� � un terrain. Ainsi, on � pas besoin de
		 * m�thode add dans la classe terrain et on a besoin de rien v�rifier, l'attribut terrain est forc�ment null au d�part.
		 */
	}
	
	// Get & Set
	
	public Terrain getTerrain() { // Permet de v�rifier si un triangle appartient d�ja dans un terrain ou non
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
