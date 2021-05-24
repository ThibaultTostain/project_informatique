package other;

import java.io.*;
import java.util.*;
import main.*; //import tout le package main

public class SimulIG
{

	public List<NoeudAppui> listNoeudAppui = new ArrayList<NoeudAppui> ();
	public List<NoeudSimple> listNoeudSimple = new ArrayList<NoeudSimple> ();
	public List<Barre> listBarre = new ArrayList<Barre> ();
	
	public static void affich(List list) {
		for(Object i : list) {
			System.out.print(i+"  ");
		}
	}
	
	public static void ouvrirFichier(String nomDocument, Terrain terrain, Treilli treilli ) {
		try {
			BufferedReader sauv=new BufferedReader(new FileReader(nomDocument+".txt"));
			String ligne;
			String motSauv;
				while((ligne=sauv.readLine())!=null) {
					System.out.println(ligne);
					StringTokenizer mot=new StringTokenizer(ligne, ";"); 
					motSauv = mot.nextToken();
					if(motSauv.equals("Terrain")) {
						terrain.setAbsMin(Double.parseDouble(mot.nextToken()));
						terrain.setAbsMax(Double.parseDouble(mot.nextToken()));
						terrain.setOrdMin(Double.parseDouble(mot.nextToken()));
						terrain.setOrdMax(Double.parseDouble(mot.nextToken()));
					}
					if(motSauv.equals("Triangle")) {
						long c = Long.parseLong(mot.nextToken());
						TriangleTerrain T1 = new TriangleTerrain(mot.nextToken(),new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())),new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())),new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())));
						T1.setCounter(c);
						terrain.addSol(T1);
					}
					if(motSauv.equals("Treilli")) {
						treilli.setId(mot.nextToken());
					}
					if(motSauv.equals("TypeBarre")) {
						
					}
					if(motSauv.equals("AppuiDouble")) {
						
					}
					if(motSauv.equals("AppuiSimple")) {
						
					}
					if(motSauv.equals("NoeudSimple")) {
						
					}
					if(motSauv.equals("Barre")) {
						
					}
				}
			sauv.close();
		}
		catch (FileNotFoundException err) {System.out.println("Main : sauvgarder() : Fichier introuvable");} //erreur en cas de non lecture de fichier
		catch (IOException err) {System.out.println("Main : sauvgarder() : Fichier illisible");} // Erreur pour la lecture ou la suppression du fichier
	}
	
	public static void ecrireFichier (String nomDocument, Terrain terrain, Treilli treilli ) {
		try {
			BufferedWriter sauv = new BufferedWriter(new FileWriter(nomDocument+".txt",false));
			List<Noeud> noeudList = treilli.getNoeudList();
			List<Barre> barreList = treilli.getBarreList();
			List<TypeBarre> typeList = treilli.getTypeList();
			List<TriangleTerrain> sol = terrain.getSol();
			sauv.write("Terrain;"+terrain.getAbsMin()+";"+terrain.getAbsMax()+";"+terrain.getOrdMin()+";"+terrain.getOrdMax()+";");
			sauv.newLine();
			for(int i =0 ; i < sol.size() ; i++) {
				sauv.write("Triangle;"+sol.get(i).getCounter()+";"+sol.get(i).getId()+";"+sol.get(i).getStart()+";"+sol.get(i).getMiddle()+";"+sol.get(i).getEnd()+";");
				sauv.newLine();
			}
			sauv.write("Treilli;"+treilli.getId());
			sauv.newLine();
			for(int i = 0; i < typeList.size() ; i++) {
				sauv.write("TypeBarre;"+typeList.get(i).getId()+";"+typeList.get(i).getCout()+";"+typeList.get(i).getLongMin()+";"+typeList.get(i).getLongMax()+";"+typeList.get(i).getResTension()+";"+typeList.get(i).getResCompression()+";");
				sauv.newLine();
			}
			for(int i = 0 ; i < noeudList.size(); i++) {
				sauv.write("" + noeudList.get(i));
				sauv.newLine();
			}
			for(int i = 0 ; i < barreList.size(); i++) {
				sauv.write(""+barreList.get(i));
				sauv.newLine();
			}
			sauv.close();
		}
		catch (IOException err) {System.out.println("Main : ecricreFichier : impossible de créer le fichier");}
	}
	
	public static void affichTrei(Treilli trei) {
		System.out.println("Liste noeud");
		affich(trei.getNoeudList());
		
		System.out.println("\nListe barre");
		affich(trei.getBarreList());
		
		System.out.println("\nListe type");
		affich(trei.getTypeList());
	}
	public static void affichTer(Terrain ter) {
		System.out.println("Limite du terrain");
		System.out.println("  Abs min : "+ter.getAbsMin());
		System.out.println("  Abs max : "+ter.getAbsMax());
		System.out.println("  Ord min : "+ter.getOrdMin());
		System.out.println("  Ord max : "+ter.getOrdMax());
		
		System.out.println("\nListe triangle de terrain");
		affich(ter.getSol());
	}
	
	public static PointTerrain newPoint(Terrain ter) {
		double x,y;
		System.out.println("\nNouveau point");
		System.out.print("Abscisse : "); x = Lire.d();
		System.out.print("Ordonnée : "); y = Lire.d();
		return new PointTerrain(ter,x,y);
	}
	public static void modifyPoint(PointTerrain S) {
		double x,y;
		System.out.print("\nNouvelle abscisse : "); x = Lire.d();
		System.out.print("Nouvelle ordonnée : "); y = Lire.d();
		S.setAbs(x); S.setOrd(y);
	}
	public static PointTerrain choosePoint(TriangleTerrain tri) {
		int num, k=0;
		System.out.println("\nChoisir un point");
		System.out.println("1 : "+ tri.getStart());
		System.out.println("2 : "+ tri.getMiddle());
		System.out.println("3 : "+ tri.getEnd());
		System.out.print("Votre choix : "); num = Lire.i();
		
		switch(num) {
		default:
			return tri.getStart();
		case 2:
			return tri.getMiddle();
		case 3:
			return tri.getEnd();
		}
	}
	
	
	public static NoeudSimple newNoeudSimple(Treilli trei) {
		double x,y;
		System.out.println("\nNouveau noeud");
		System.out.print("Abscisse : "); x = Lire.d();
		System.out.print("Ordonnée : "); y = Lire.d();
		return new NoeudSimple(trei,x,y);
	}
	public static void modifyNoeudSimple(NoeudSimple S) {
		double x,y;
		System.out.print("\nNouvelle abscisse : "); x = Lire.d();
		System.out.print("Nouvelle ordonnée : "); y = Lire.d();
		S.setAbs(x); S.setOrd(y);
	}
	public static NoeudSimple chooseNoeudSimple(Treilli trei) {
		int num, k=0;
		System.out.println("\nChoisir un noeud");
		for(Noeud i : trei.getNoeudList()) {
			k++;
			if(i instanceof NoeudSimple) {
				System.out.println(k+" : "+i);
			}
		}
		System.out.print("Votre choix : "); num = Lire.i();
		return (NoeudSimple)trei.getNoeudList().get(num-1);
	}
	
	public static void modifyForce(Treilli trei) {
		double x,y;
		System.out.print("\nEffort abscisse : "); x = Lire.d();
		System.out.print("Effort ordonnée : "); y = Lire.d();
		chooseNoeud(trei).setForce(new Force(x,y));
	}
	public static void supprForce(Treilli trei) {
		chooseNoeud(trei).setForce(new Force(0,0));
	}
	
	
	public static NoeudAppui newAppui(Treilli trei) {
		int num;
		double pos;
		int bin;
		boolean simple = false;
		
		TriangleTerrain tri = chooseTriangle(trei.getTerrain());
		
		System.out.println("\nChoisir un segment");
		System.out.println("1 : "+ tri.getFirst());
		System.out.println("2 : "+ tri.getSecond());
		System.out.println("3 : "+ tri.getThird());
		System.out.print("Votre choix : "); num = Lire.i();
		System.out.print("Position : "); pos = Lire.d();
		System.out.println("0 : double");
		System.out.println("1 : simple");
		System.out.print("Votre choix : "); bin = Lire.i();
		
		if(bin == 1) {simple = true;}
		
		switch(num) {
		default:
			return new NoeudAppui(trei,tri.getFirst(),pos,simple);
		case 2:
			return new NoeudAppui(trei,tri.getSecond(),pos,simple);
		case 3:
			return new NoeudAppui(trei,tri.getThird(),pos,simple);
		}
	}
	public static void modifyAppui(NoeudAppui A) {
		double pos;
		System.out.println("\nNouvelle position : "); pos = Lire.d();
		A.setPos(pos);
	}
	public static NoeudAppui chooseAppui(Treilli trei) {
		int num, k=0;
		System.out.println("\nChoisir un appui");
		for(Noeud i : trei.getNoeudList()) {
			k++;
			if(i instanceof NoeudAppui) {
				System.out.println(k+" : "+i);
			}
		}
		System.out.print("Votre choix : "); num = Lire.i();
		return (NoeudAppui)trei.getNoeudList().get(num-1);
	}
	
	
	public static TriangleTerrain chooseTriangle(Terrain ter) {
		int num, k=0;
		System.out.println("\nChoisir un triangle");
		for(TriangleTerrain i : ter.getSol()) {
			k++;
			System.out.println(k+" : "+i);
		}
		System.out.print("Votre choix : "); num = Lire.i();
		return ter.getSol().get(num-1);
	}
	
	public static Noeud chooseNoeud(Treilli trei) {
		int num, k=0;
		System.out.println("\nChoisir un noeud");
		for(Noeud i : trei.getNoeudList()) {
			k++;
			System.out.println(k+" : "+i);
		}
		System.out.print("Votre choix : "); num = Lire.i();
		return trei.getNoeudList().get(num-1);
	}
	
	public static TypeBarre newType() {
		double lMin, lMax, tens, comp;
		float cout;
		
		System.out.println("\nLongueure min : "); lMin = Lire.d();
		System.out.println("Longueure max : "); lMax = Lire.d();
		System.out.println("Tension max : "); tens = Lire.d();
		System.out.println("Compression max : "); comp = Lire.d();
		System.out.println("Cout au metre : "); cout = Lire.f();
		
		return new TypeBarre (lMin,lMax,tens,comp,cout);
	}
	
	public static TypeBarre chooseType() {
		int num, k=0;
		System.out.println("\nChoisir un type");
		for(TypeBarre i : TypeBarre.list) {
			k++;
			System.out.println(k+" : "+i);
		}
		System.out.print("Votre choix : "); num = Lire.i();
		return TypeBarre.list.get(num-1);
	}
	
	public static Barre chooseBarre(Treilli trei) {
		int num, k=0;
		System.out.println("\nChoisir un type");
		for(Barre i : trei.getBarreList()) {
			k++;
			System.out.println(k+" : "+i);
		}
		System.out.print("Votre choix : "); num = Lire.i();
		return trei.getBarreList().get(num-1);
	}
	
	//________________________________________________________________
	
	public static void SimIG() {
		int t;
		boolean stop = false;
		
		Terrain ter = new Terrain(-10, 10, -10, 10);
		Treilli trei = new Treilli(ter);
		
		
		new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,2,0),new PointTerrain(ter,1,-1));
		new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,1,-1),new PointTerrain(ter,-1,-1));
		new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,1,-1),new PointTerrain(ter,-2,0));
		
		
		while(!stop) {
			System.out.println("\n____________________MENU____________________");
			System.out.print("\n");
			System.out.println("1 : Afficher les éléments du treillis");
			System.out.println("2 : Afficher les éléments du terrain");
			System.out.println("3 : Triangle de terrain");
			System.out.println("4 : Noeud");
			System.out.println("5 : Barre");
			System.out.println("6 : Force");
			System.out.println("7 : Calculer");
			System.out.println("0 : Quitter");
			System.out.print("Votre Choix : ");
			t = Lire.i();
			
			if(t == 0) {stop = true;}
		
			switch(t) {
			case 0:
				break;
			case 1:
				System.out.println("\n___________________TREILLI___________________");
				System.out.print("\n");
				affichTrei(trei);
				break;
			case 2:
				System.out.println("\n___________________TERRAIN___________________");
				System.out.print("\n");
				affichTer(ter);
				break;
			case 3:
				System.out.println("\n__________________TRIANGLE__________________");
				System.out.print("\n");
				System.out.println("1 : Créer un nouveau triangle");
				System.out.println("2 : Modifier un triangle existant");
				System.out.println("3 : Supprimer un triangle");
				System.out.println("0 : Revenir au menu");
				System.out.print("Votre Choix : ");
				t = Lire.i();
				
				switch(t) {
				case 0:
					break;
				case 1:
					new TriangleTerrain(newPoint(ter),newPoint(ter),newPoint(ter));
					break;
				case 2:
					modifyPoint(choosePoint(chooseTriangle(ter)));
					break;
				case 3:
					
					break;
				}
				break;
			case 4:
				System.out.println("\n____________________NOEUD____________________");
				System.out.print("\n");
				System.out.println("1 : Créer un nouveau noeud d'appui");
				System.out.println("2 : Modifier un noeud simple existant");
				System.out.println("3 : Modifier un appui existant");
				System.out.println("4 : Supprimer un noeud");
				System.out.println("0 : Revenir au menu");
				System.out.print("Votre Choix : ");
				t = Lire.i();
				
				switch(t) {
				case 0:
					break;
				case 1:
					newAppui(trei);
					break;
				case 2:
					modifyNoeudSimple(chooseNoeudSimple(trei));
					break;
				case 3:
					modifyAppui(chooseAppui(trei));
					break;
				case 4:
					chooseNoeud(trei).delete();
					break;
				}
				break;
			case 5:
				System.out.println("\n____________________BARRE____________________");
				System.out.print("\n");
				System.out.println("1 : Créer une nouvelle barre");
				System.out.println("2 : Créer une barre entre deux noeuds existants");
				System.out.println("3 : Supprimer une barre");
				System.out.println("4 : Créer un nouveau type");
				System.out.println("0 : Revenir au menu");
				System.out.print("Votre Choix : ");
				t = Lire.i();
				
				switch(t) {
				case 0:
					break;
				case 1:
					if(trei.getNoeudList().size() == 0) {System.out.println("\nVous devez d'abord construire un appui");}
					else {new Barre(chooseNoeud(trei),newNoeudSimple(trei), chooseType());}
					break;
				case 2:
					new Barre(chooseNoeud(trei), chooseNoeud(trei), chooseType());
					break;
				case 3:
					chooseBarre(trei).delete();
					break;
				case 4:
					newType();
					break;
				}
				break;
			case 6:
				System.out.println("\n____________________FORCE____________________");
				System.out.print("\n");
				System.out.println("1 : Appliquer une force sur un noeud");
				System.out.println("2 : Supprimer une force sur un noeud");
				System.out.println("0 : Retour au menu");
				System.out.print("Votre Choix : ");
				t = Lire.i();
				
				switch(t) {
				case 0:
					break;
				case 1:
					modifyForce(trei);
					break;
				case 2:
					supprForce(trei);
					break;
				}
			break;
			case 7:
				calculs(trei);
				break;
			}
		}
	}
	
	public static void calculs (Treilli trei) {
		int ns,nb,nas=0,nad=0;
		
		ns = trei.getNoeudList().size();
		nb = trei.getBarreList().size();
		for(Noeud S : trei.getNoeudList()) {
			if(S instanceof NoeudAppui) {
				if (((NoeudAppui)S).isSimple()) {nas++;}
				else {nad++;}
			}
		}
		System.out.println("ns = "+ns+"; nas = "+nas+"; nad = "+nad+"; nb = "+nb);
		
		if(2*ns != nb+nas+2*nad) {
			throw new Error("Treilli non isostatique");
		}
		
		Matrice coef = new Matrice(2*ns); // Matrice carrée
		List<String> I = new ArrayList<String> ();
		List<Double> V = new ArrayList<Double> ();
		
		int ligne = 0;
		
		for(Noeud S : trei.getNoeudList()) {
			if(S instanceof NoeudSimple) { // Noeud simple
				for(Barre B : S.getRamifi()) {
					if(!I.contains(B.getId())) {
						I.add(B.getId()); // T(barre)
					}
					coef.set(ligne, I.indexOf(B.getId()), Math.cos(B.alpha(S))); // ligne, colonne, donnée
					coef.set(ligne+1, I.indexOf(B.getId()), Math.sin(B.alpha(S)));
				}
				V.add(S.getForce().getX());
				V.add(S.getForce().getY());
				
			}
			else if(!((NoeudAppui)S).isSimple()) { // Noeud appui double
				for(Barre B : S.getRamifi()) {
					if(!I.contains(B.getId())) {
						I.add(B.getId()); // T(barre)
					}
					coef.set(ligne, I.indexOf(B.getId()), Math.cos(B.alpha(S))); // ligne, colonne, donnée
					coef.set(ligne+1, I.indexOf(B.getId()), Math.sin(B.alpha(S)));
				}
				if(!I.contains(S.getId()+"x")) {
					I.add(S.getId()+"x"); // R(noeud)x
					I.add(S.getId()+"y"); // R(noeud)y
				}
				coef.set(ligne, I.indexOf(S.getId()+"x"), 1); // ligne, colonne, donnée
				coef.set(ligne+1, I.indexOf(S.getId()+"y"), 1);
				
				V.add(S.getForce().getX());
				V.add(S.getForce().getY());
			}
			else { // Noeud appui simple
				for(Barre B : S.getRamifi()) {
					if(!I.contains(B.getId())) {
						I.add(B.getId()); // T(barre)
					}
					coef.set(ligne, I.indexOf(B.getId()), Math.cos(B.alpha(S))); // ligne, colonne, donnée
					coef.set(ligne+1, I.indexOf(B.getId()), Math.sin(B.alpha(S)));
				}
				if(!I.contains(S.getId())){
					I.add(S.getId()); // R(noeud)
				}
				coef.set(ligne, I.indexOf(S.getId()), Math.cos(((NoeudAppui)S).beta())); // ligne, colonne, donnée
				coef.set(ligne+1, I.indexOf(S.getId()), Math.sin(((NoeudAppui)S).beta()));
				
				V.add(S.getForce().getX());
				V.add(S.getForce().getY());
			}
			ligne += 2; // Un noeud engendre deux equations donc deux lignes
		}
		
		Matrice val = Matrice.vecteur(V);
		
		coef.diagonalisation();
		coef = coef.mult(val);
		
		System.out.println(coef);
		System.out.println(I);
	}
	
	public static void main (String [] args) {
		
		/*
		List<Integer> l = new ArrayList<Integer>();
		l.contains(null);
		Matrice m = new Matrice(l.toArray());
		*/
		//SimIG();
		
		Terrain ter = new Terrain(-10, 10, -10, 10);
		Treilli trei = new Treilli(ter);
		
		
		TriangleTerrain T1 = new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,0,2),new PointTerrain(ter,-1,1));
		
		
		
		NoeudAppui AD = new NoeudAppui(trei,T1.getFirst(),1);
		NoeudAppui AS = new NoeudAppui(trei,T1.getFirst(),0,true);
		NoeudSimple S = new NoeudSimple(trei,1,1);
		
		System.out.println(AD);
		System.out.println(AS);
		System.out.println(S);
		
		Barre B02 = new Barre(AD,S,TypeBarre.ACIER);
		Barre B12 = new Barre(S,AS,TypeBarre.ACIER);
		Barre B01 = new Barre(AS,AD,TypeBarre.ACIER);
		
		System.out.println(B01);
		System.out.println(B12);
		System.out.println(B02);
		
		S.setForce(new Force(0,1000));
		
		//System.out.println(AS.beta());
		
		//calculs(trei);
		

		System.out.println("\n \n \n");
		
		String doc = "document_test";
		ecrireFichier(doc, ter, trei);
		ouvrirFichier(doc,ter,trei);
		/*
		TriangleTerrain T1 = new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,2,0),new PointTerrain(ter,1,-1));
		TriangleTerrain T2 = new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,1,-1),new PointTerrain(ter,-1,-1));
		TriangleTerrain T3 = new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,1,-1),new PointTerrain(ter,-2,0));
		
		NoeudSimple S1 = new NoeudSimple(trei,0,2);
		NoeudSimple S2 = new NoeudSimple(trei,0,0);
		NoeudSimple S3 = new NoeudSimple(trei,1,1);
		
		
		
		Barre B1 = new Barre(S1,S3,TypeBarre.ACIER);
		Barre B2 = new Barre(S3,S2,TypeBarre.ACIER);
		Barre B3 = new Barre(S2,S1,TypeBarre.BOIS);
		
		
		//System.out.println(B3.angle(S2));
		
		
		
		/*
		
		
		
		
		//System.out.println(NoeudSimple.angle(S, a, c));
		

		//System.out.println(S.pos(new SegmentTerrain(a,b)));
		//System.out.println(S.pos(new SegmentTerrain(b,c)));
		//System.out.println(S.pos(new SegmentTerrain(c,d)));
		
		double alpha = 0.5;
		//Noeud A = new NoeudAppui();
		
		//System.out.println(A);
		
		/*
		
		SegmentTerrain seg = new SegmentTerrain (b, c);
		
		PointTerrain p1, p2, p3, p4;
		
		p1 = new PointTerrain (0, 0);
		p2 = new PointTerrain (2, 1);
		p3 = new PointTerrain (3, 4);
		p4 = new PointTerrain (-2, 1);

		
		System.out.println(p1.getNum());
		System.out.println(p2.getNum());
		System.out.println(p3.getNum());
		System.out.println(p4.getNum());
		*/
		
		
	}
}
