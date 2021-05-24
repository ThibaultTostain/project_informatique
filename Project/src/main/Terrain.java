package main;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
	
	private double absMin, absMax, ordMin, ordMax;
	private List <TriangleTerrain> sol;
	
	// Constructeurs
	
	public Terrain(double absMin, double absMax, double ordMin, double ordMax) {
		super();
		this.absMin = absMin;
		this.absMax = absMax;
		this.ordMin = ordMin;
		this.ordMax = ordMax;
		this.sol = new ArrayList<TriangleTerrain> (); // Le terrain est vide à sa création
	}
	
	// Get & Set
	
	public List<TriangleTerrain> getSol() {
		return sol;
	}

	public void addSol(TriangleTerrain Tri) {
		this.sol.add(Tri);
	}
	
	public double getAbsMin() {
		return absMin;
	}

	public void setAbsMin(double absMin) {
		this.absMin = absMin;
	}

	public double getAbsMax() {
		return absMax;
	}

	public void setAbsMax(double absMax) {
		this.absMax = absMax;
	}

	public double getOrdMin() {
		return ordMin;
	}

	public void setOrdMin(double ordMin) {
		this.ordMin = ordMin;
	}

	public double getOrdMax() {
		return ordMax;
	}

	public void setOrdMax(double ordMax) {
		this.ordMax = ordMax;
	}
}