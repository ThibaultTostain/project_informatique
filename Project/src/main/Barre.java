package main;

import java.awt.Color;

public class Barre {
	
	private Treilli treilli;
	private Noeud start, end;
	private TypeBarre type;
	private Color color;
	private String id;
	
	// Constructeurs
	
	public Barre(Noeud start, Noeud end, TypeBarre type) { // Noir comme couleur de base.
		this(start, end, type, Color.black);
	}
	
	public Barre(Noeud start, Noeud end, TypeBarre type, Color color) {
		super();
		if(start.getTreilli() != end.getTreilli()) {throw new Error("Treilli mismatch");}
		this.treilli = start.getTreilli();
		this.start = start;
		this.end = end;
		this.type = type;
		this.color = color;
		this.id = "B"+this.newId(); // Identificateur : "BN,M" où N et M sont les numéros d'identification des deux noeuds de la barre.
		this.control();
		
		treilli.getBarreList().add(this);
		if(!treilli.getTypeList().contains(type)) {treilli.getTypeList().add(type);}
		// On ajoute le type de cette barre à la liste du treillis ssi elle n'est pas déjà dedans.
		
		start.getRamifi().add(this); // On ajoute la barre aux ramifications des deux noeuds.
		end.getRamifi().add(this);
	}
	
	// Get & Set
	
	public Treilli getTreilli() {
		return treilli;
	}

	public Noeud getStart() {
		return start;
	}

	public void setStart(Noeud start) {
		this.start = start;
	}

	public Noeud getEnd() {
		return end;
	}

	public void setEnd(Noeud end) {
		this.end = end;
	}

	public TypeBarre getType() {
		return type;
	}

	public void setType(TypeBarre type) {
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getId() {
		return id;
	}
	
	// Affichage
	
	@Override
	public String toString() {
		return "Barre;"+id+";"+type.getId()+";"+start.getId()+";"+end.getId();
	}
	
	// Fonctions et Procedures
	
	public boolean equals(Barre B) {
		if(this.getId().equals(B.getId())) {return true;}
		else {return false;}
	}
	
	public double alpha (Noeud S) { // angle ABC ou AB est // à l'axe des abscisse et passe par S, et BC est this.
		if(!this.getStart().equals(S) && !this.getEnd().equals(S)) {throw new Error("Noeud is not part of the Barre");}
		
		double x,y,vx,vy;
		if(this.getStart().equals(S)) {
			x = this.getEnd().getAbs(); y = this.getEnd().getOrd(); // (x,y) est le Noeud opposé à S sur la Barre this.
		}
		else { x = this.getStart().getAbs(); y = this.getStart().getOrd();}
		
		// Vecteur BA est l'axe des abscisse.
		vx = x-S.getAbs(); vy = y-S.getOrd(); // Vecteur BC.
		return Math.atan2(vy, vx); //angle ABC.
	}
	
	public boolean delete() {
		boolean res, delType=true;
		res = this.getTreilli().getBarreList().remove(this);
		/* Si il y a une barre qui à le même type que celle supprimée, on garde son type.
		 * Sinon, on le supprime également.
		 */
		for(Barre i : this.getTreilli().getBarreList()) {
			if(i.getType().equals(this.getType())) {delType = false;}
		}
		if(delType) {this.getTreilli().getTypeList().remove(this.getType());}
		return res;
	}

	public void control() {
		if(this.getStart().equals(this.getEnd())) {throw new Error("Start and end must be different");}
		for(Barre i : this.getTreilli().getBarreList()) {
			if(this.equals(i)) {throw new Error("Barre already existing");}
		}
	}

	public synchronized String newId() {
		int max = Math.max(Character.getNumericValue(this.getStart().getId().charAt(1)),Character.getNumericValue(this.getEnd().getId().charAt(1)));
		int min = Math.min(Character.getNumericValue(this.getStart().getId().charAt(1)),Character.getNumericValue(this.getEnd().getId().charAt(1)));
	    return String.valueOf(min) +","+ String.valueOf(max);
	}
	
}

