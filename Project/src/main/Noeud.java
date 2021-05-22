package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Noeud { // On n'utilise pas Noeud en tant que tel, cela permet de factoriser l'id de tous les noeuds.

	private Treilli treilli; // Tout neoud est lié à un Treilli
	private Force force; // La force qui s'applique sur le Noeud
	private List<Barre> ramifi; // Les Barres liées au Noeud
	private String id; // Permet d'identifier le Noeud
	public abstract double getAbs(); // Tout noeud doit être capable de donner ses coords ou
	public abstract double getOrd(); // de les calculer si elles ne font pas parti de ses attributs.
	public static long counter = 0; // Permet de créer les id
	
	// Constructeurs
	
	public Noeud(Treilli treilli) {
		super();
		this.treilli = treilli;
		this.force = new Force(0,0); // Force inexistante initialement
		this.ramifi = new ArrayList<Barre>();
		this.id = "S"+newId(); // Identificateur : "SN" où N est un entier.
		treilli.getNoeudList().add(this);
	}
	
	// Get & Set
	
	public Force getForce() {
		return force;
	}

	public void setForce(Force force) {
		this.force = force;
	}

	public Treilli getTreilli() {
		return treilli;
	}
	
	public List<Barre> getRamifi() {
		return ramifi;
	}

	public String getId() {
		return this.id;
	}
	
	
	// Affichage
	
	public abstract String toString();
	
	// Fonctions et Procedures
	
	public boolean equals(Noeud S) {
		if(this.getId().equals(S.getId())) {return true;}
		else {return false;}
	}
	
	public boolean delete() {
		for(Barre i : this.getRamifi()) {
			i.delete();
		}
		return this.getTreilli().getNoeudList().remove(this);
	}
	
	public static synchronized String newId() {
		    return String.valueOf(counter ++);
		}
	
	public static double angle (SegmentTerrain seg, Noeud S) { // angle ABS où A et B sont respectivement le début et la fin du segment
		// On peut appliquer cette méthode a tout types de noeux
		double v1x, v1y, v2x, v2y;
		v1x = seg.getStart().getAbs()-seg.getEnd().getAbs(); v1y = seg.getStart().getOrd()-seg.getEnd().getOrd(); // Vecteur BA
		v2x = S.getAbs()-seg.getEnd().getAbs(); v2y = S.getOrd()-seg.getEnd().getOrd(); // Vecteur BS
		
		double angle = Math.atan2(v2y, v2x) - Math.atan2(v1y, v1x); //angle ABS
		if (angle < 0) {angle += 2 * Math.PI;} // On fait en sorte que l'angle soit compris entre 0 et 2Pi
		
		return angle;
	}
	
	public boolean col (SegmentTerrain seg) {
		return angle(seg, this) == 0 || angle(seg, this) == Math.PI;
	}
	
	public boolean pos (SegmentTerrain seg) {
		return (0 < angle(seg, this) && angle(seg, this) < Math.PI);
	}
	
	public boolean inside (TriangleTerrain tri) {
		if(this.pos(tri.getFirst()) == this.pos(tri.getSecond()) && this.pos(tri.getSecond()) == this.pos(tri.getThird())) {
			return true;
		}
		else {return false;}
	}
	
}
