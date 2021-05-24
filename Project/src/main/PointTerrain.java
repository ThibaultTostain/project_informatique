package main;

public class PointTerrain {
	
	private Terrain terrain;
	private double abs, ord;

	// Constructeurs
	
	public PointTerrain(Terrain terrain, double abs, double ord) {
		super();
		/* On vérifie à la création d'un point s'il appartient bien à la limite du terrain. Cela assure que les segments, 
		 * les triangles et les points d'appuis sont dans cette limite.
		 */
		this.terrain = terrain;
		this.abs = abs;
		this.ord = ord;
		this.control();
	}
	
	// Get & Set
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public double getAbs() {
		return abs;
	}

	public void setAbs(double abs) {
		this.abs = abs;
	}

	public double getOrd() {
		return ord;
	}

	public void setOrd(double ord) {
		this.ord = ord;
	}
	
	// Affichages
	
	@Override
	public String toString() {
		return this.getAbs() + ";" + this.getOrd();
	}
	
	// Fonctions et Procedures

	public void control() {
		if(this.getAbs() > this.getTerrain().getAbsMax() || this.getAbs() < this.getTerrain().getAbsMin()) {
			throw new Error("Noeud out of bounds");
		}
		if(this.getOrd() > this.getTerrain().getOrdMax() || this.getOrd() < this.getTerrain().getOrdMin()) {
			throw new Error("Noeud out of bounds");
		}
	}
	
	
}
