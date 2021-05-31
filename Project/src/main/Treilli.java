package main;

import java.util.ArrayList;
import java.util.List;

public class Treilli {
	
	private Terrain terrain;
	private List<Noeud> noeudList;
	private List<Barre> barreList;
	private List<TypeBarre> typeList;
	private String id;
	public static long counter = 0;
	
	// Constructeur
	
	public Treilli(Terrain terrain) {
		super();
		this.terrain = terrain;
		/* Comme le terrain, le treillis est initialement vide. On vient rajouter petit à petit des éléments au fure et à
		 * mesure qu'on les crés.
		 */
		this.noeudList = new ArrayList<Noeud> ();
		this.barreList = new ArrayList<Barre> ();
		this.typeList = new ArrayList<TypeBarre> ();
		this.id = "TR"+newId();
	}

	// Get & Set
	
	public long getCount() {
		return this.counter;
	}
	
	public void setCount(long n) {
		this.counter = n;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String c) {
		this.id = c ;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public List<Noeud> getNoeudList() {
		return noeudList;
	}
	
	public List<Barre> getBarreList() {
		return barreList;
	}
	
	public List<TypeBarre> getTypeList() {
		return typeList;
	}
	
	public void addNoeudList(Noeud e) {
		this.noeudList.add(e);
	}
	
	public void addBarredList(Barre e) {
		this.barreList.add(e);
	}
	
	// Fonctions et Procedures
	
	public Noeud searchNoeud(String id) {
		for(Noeud i : this.getNoeudList()) {
			if(i.getId() == id) {return i;}
		}
		return null;
	}
	
	public Barre searchBarre(String id) {
		for(Barre i : this.getBarreList()) {
			if(i.getId() == id) {return i;}
		}
		return null;
	}
	
	public TypeBarre searchType(String id) {
		for(TypeBarre i : this.getTypeList()) {
			if(i.getId() == id) {return i;}
		}
		return null;
	}
	
	public static synchronized String newId() {
	    return String.valueOf(counter ++);
	}
	
}
