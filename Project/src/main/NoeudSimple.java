package main;

import java.util.List;

public class NoeudSimple extends Noeud
{
	
	private double abs;
	private double ord;
	
	// Constructeurs
	
	public NoeudSimple(Treilli treilli, double abs, double ord) {
		super(treilli);
		this.abs = abs;
		this.ord = ord;
		this.control();
	}
	
	// Get & Set
	
	public double getAbs() {
		return abs;
	}
	
	public void setAbs(double abs) {
		this.abs = abs;
		this.control();
	}

	public double getOrd() {
		return ord;
	}

	public void setOrd(double ord) {
		this.ord = ord;
		this.control();
	}
	
	// Affichage
	
	@Override
	public String toString() {
		return "NoeudSimple;"+this.getId() + ";(" + this.getAbs() + "," + this.getOrd() + ")";
	}

	// Fonctions et Procedures
	
	public void control() {
		if(this.getAbs() > this.getTreilli().getTerrain().getAbsMax() || this.getAbs() < this.getTreilli().getTerrain().getAbsMin()) {
			throw new Error("Noeud out of bounds");
		}
		if(this.getOrd() > this.getTreilli().getTerrain().getOrdMax() || this.getOrd() < this.getTreilli().getTerrain().getOrdMin()) {
			throw new Error("Noeud out of bounds");
		}
		for(TriangleTerrain i : this.getTreilli().getTerrain().getSol()) {
			if(this.inside(i)) {throw new Error("Noeud in TriangleTerrain");}
		}
	}
	
	public double distance(NoeudSimple S) {
		double distAbs = this.getAbs() - S.getAbs();
		double distOrd = this.getOrd() - S.getOrd();
		
		return Math.sqrt(distAbs*distAbs + distOrd*distOrd);
	}
	
	public double distance(PointTerrain P) {
		double distAbs = this.getAbs() - P.getAbs();
		double distOrd = this.getOrd() - P.getOrd();
		
		return Math.sqrt(distAbs*distAbs + distOrd*distOrd);
	}
	
	//________________________________________
	
	/*
	
	public static NoeudSimple max(List <NoeudSimple> list) {
		double absMax = list.get(0).getAbs();
		double ordMax = list.get(0).getOrd();
		
		for(int i = 1; i < list.size(); i++)
		{
			if(list.get(i).getAbs() > absMax)
			{
				absMax = list.get(i).getAbs();
			}
			if(list.get(i).getOrd() > ordMax)
			{
				ordMax = list.get(i).getOrd();
			}
		}
		return new NoeudSimple(absMax, ordMax);
	}

	public static NoeudSimple min(List <NoeudSimple> list) {
		double absMin = list.get(0).getAbs();
		double ordMin = list.get(0).getOrd();
		
		for(int i = 1; i < list.size(); i++)
		{
			if(list.get(i).getAbs() < absMin)
			{
				absMin = list.get(i).getAbs();
			}
			if(list.get(i).getOrd() < ordMin)
			{
				ordMin = list.get(i).getOrd();
			}
		}
		return new NoeudSimple(absMin, ordMin);
	}
	//________________________________________
	 */
}
