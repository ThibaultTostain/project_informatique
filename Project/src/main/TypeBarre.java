package main;

import java.util.ArrayList;
import java.util.List;

public class TypeBarre {
	
	private float cout;
	private double longMin, longMax;
	private double resTension, resCompression;
	private String id;
	public static List<TypeBarre> list = new ArrayList<TypeBarre> ();
	public static long counter = 0;
	// Types prédéfinis sous forme de constantes : 
	public static TypeBarre ACIER = new TypeBarre(1, 5, 1000, 600, 200);
	public static TypeBarre BOIS = new TypeBarre(0.5, 2, 100, 200, 25);
	public static TypeBarre BETON = new TypeBarre(0.5, 4, 600, 400, 100);
	
	// Constructeur
	
	public TypeBarre(double longMin, double longMax, double resTension, double resCompression, float cout) {
		super();
		this.cout = cout;
		this.longMin = longMin;
		this.longMax = longMax;
		this.resTension = resTension;
		this.resCompression = resCompression;
		this.id = "TB"+newId(); // Identificateur : "TBN" où N est un entier
		list.add(this);
	}

	// Get & Set
	
	
	
	// Fin
	
	public float getCout() {
		return cout;
	}
	
	public void setCout(float i) {
		this.cout = i;
	}

	public double getLongMin() {
		return longMin;
	}
	
	public void setLongMin(double i) {
		this.longMin = i;
	}

	public double getLongMax() {
		return longMax;
	}
	
	public void setLongMax(double i) {
		this.longMax = i;
	}

	public double getResTension() {
		return resTension;
	}
	
	public void setResTension(double i) {
		this.resTension = i;
	}

	public double getResCompression() {
		return resCompression;
	}
	
	public void setResCompression(double i) {
		this.resCompression = i;
	}

	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
	// Fonctions et Procedures
	
	public static synchronized String newId() {
	    return String.valueOf(counter ++);
	}
	
	public boolean equals(TypeBarre S) {
		if(this.getId().equals(S.getId())) {return true;}
		else {return false;}
	}

	/*
	public boolean delete() {
		return list.remove(this);
	}
	*/
}
