package other;

import java.awt.Color;
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
		// Systeme d'ouverture de fichier
		try {
			BufferedReader sauv=new BufferedReader(new FileReader(nomDocument+".txt"));
			String ligne;
			long l=0; // variable pour le counter des noeuds
			// On vide l'environnement de travail pour éviter d'avoir des traces de l'ancien treilli.
			treilli.getTypeList().clear();
			treilli.getBarreList().clear();
			treilli.getNoeudList().clear();
			terrain.getSol().clear();
			// Le reste va de toute façon être écrasé donc pas besoin de le supprimer
			// On lit ligne après ligne notre fichier tampon
				while((ligne=sauv.readLine())!=null) {
					System.out.println(ligne); // Affiche ce qui est lu, l'utilisateur peut ainsi se rendre compte d'une erreur si le programme ne l'a pas vu.
					StringTokenizer mot=new StringTokenizer(ligne, ";"); // définit notre élément de s'éparation
					switch(mot.nextToken()) {
						case ("Terrain"):
							// set les coordonnées du terrain
							terrain.setAbsMin(Double.parseDouble(mot.nextToken()));
							terrain.setAbsMax(Double.parseDouble(mot.nextToken()));
							terrain.setOrdMin(Double.parseDouble(mot.nextToken()));
							terrain.setOrdMax(Double.parseDouble(mot.nextToken()));
							break; // permet de quitter tout le switch comme une ligne ne remplie qu'un élément
						case ("Triangle"):
							// créer le triangle terrain
							// affecter le counter à la valeur du counter lors de la sauvegarde
							//Ajouter le nouveau triangle au terrain déjà créé
							TriangleTerrain T1 = new TriangleTerrain(mot.nextToken(),new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())),new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())),new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())));
							T1.setCounter(Long.parseLong(mot.nextToken()));
							break;
						case ("Treilli"):
							// set l'id du treilli puis le count dans son état lors de la sauvegarde
							treilli.setId(mot.nextToken());
							treilli.setCount(Long.parseLong(mot.nextToken()));
							break;
						case ("TypeBarre"):
							// Créer un nouveau type de barre et le mettre dans la liste des types de barres du treilli
							// Mettre le counter id des types de barres au dernier chiffre sauvegardé
							treilli.getTypeList().add(new TypeBarre(Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken()), Float.parseFloat(mot.nextToken())));
							treilli.getTypeList().get(0).setCount(Long.parseLong(mot.nextToken()));
							break;
						case ("AppuiDouble"):
							String tamponD = mot.nextToken();
							if (Long.parseLong(tamponD.substring(1,tamponD.length())) > l) {l = Long.parseLong(tamponD.substring(1,tamponD.length()));}
							treilli.getNoeudList().add(new NoeudAppui(l,tamponD,treilli,new SegmentTerrain(new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())),new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken()))),Double.parseDouble(mot.nextToken()),false));
							treilli.getNoeudList().get(0);
							break;
						case ("AppuiSimple"):
							String tamponS = mot.nextToken();
							if (Long.parseLong(tamponS.substring(1,tamponS.length())) > l) {l = Long.parseLong(tamponS.substring(1,tamponS.length()));}
							treilli.getNoeudList().add(new NoeudAppui(l,tamponS,treilli,new SegmentTerrain(new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())),new PointTerrain(terrain,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken()))),Double.parseDouble(mot.nextToken()),true));
							break;
						case ("NoeudSimple"):
							String tamponN = mot.nextToken();
							if (Long.parseLong(tamponN.substring(1,tamponN.length())) > l) {l = Long.parseLong(tamponN.substring(1,tamponN.length()));}
							treilli.getNoeudList().add(new NoeudSimple(l,tamponN,treilli,Double.parseDouble(mot.nextToken()),Double.parseDouble(mot.nextToken())));
							break;
						case ("Barre"): 
							String noeudA = mot.nextToken();
							String noeudB = mot.nextToken();
							String typeBarre = mot.nextToken();
							int A,B,TB;
							A=B=TB=0;
							// On retrouver les noeuds qui ont servit à faire la barre
							for(int i = 0 ; i < treilli.getNoeudList().size();i++) {
								if (noeudA.equals(treilli.getNoeudList().get(i).getId())) {
									A = i;
								}
								if (noeudB.equals(treilli.getNoeudList().get(i).getId())) {
									B = i;
								}
							}
							// On retrouve le type de la barre
							for (int i = 0; i < treilli.getTypeList().size();i++) {
								if(typeBarre.equals(treilli.getTypeList().get(i))) {
									TB = i;
								}
							}
							treilli.getBarreList().add(new Barre(mot.nextToken(),treilli.getNoeudList().get(A),treilli.getNoeudList().get(B),treilli.getTypeList().get(TB), Color.black)); // parcourir la liste de noeud pour trouver quel noeud à cette id =)
							break;
						default : // optionel pour le switch mais permet de me prévenir si aucun cas c'est arrivé
							System.out.println("SimullG : ouvrirFichier() : une ligne n'a pas de correspondance");
					}
				}
			sauv.close(); // permet de fermer la mémoire tampon BufferedReader
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
			sauv.write("Terrain;"+terrain.getAbsMin()+";"+terrain.getAbsMax()+";"+terrain.getOrdMin()+";"+terrain.getOrdMax());
			sauv.newLine();
			for(int i =0 ; i < sol.size() ; i++) {
				sauv.write("Triangle;"+sol.get(i).getId()+";"+sol.get(i).getStart()+";"+sol.get(i).getMiddle()+";"+sol.get(i).getEnd()+";"+sol.get(i).getCounter());
				sauv.newLine();
				System.out.println(sol.get(i).getId());
			}
			sauv.write("Treilli;"+treilli.getId()+";"+treilli.getCount());
			sauv.newLine();
			for(int i = 0; i < typeList.size() ; i++) {
				sauv.write("TypeBarre;"+typeList.get(i).getLongMin()+";"+typeList.get(i).getLongMax()+";"+typeList.get(i).getResTension()+";"+typeList.get(i).getResCompression()+";"+typeList.get(i).getCout()+";"+typeList.get(i).getCount()+";"+typeList.get(i).getId());
				sauv.newLine();
			}
			for(int i = 0 ; i < noeudList.size(); i++) {
				sauv.write(""+noeudList.get(i));
				sauv.newLine();
			}
			for(int i = 0 ; i < barreList.size(); i++) {
				sauv.write(""+barreList.get(i));
				sauv.newLine();
			}
			sauv.close(); // permet de fermer la mémoire tampon BufferedWriter
		}
		catch (IOException err) {System.out.println("Main : ecricreFichier : impossible de créer le fichier");}
	}
	//_________________________________Treilli________________________________
	
	public static void afficher(Treilli trei) {
		System.out.println("Liste noeud");
		affich(trei.getNoeudList());
		
		System.out.println("\nListe barre");
		affich(trei.getBarreList());
		
		System.out.println("\nListe type");
		affich(trei.getTypeList());
	}
	//_________________________________Terrain________________________________
	
	public static void afficher(Terrain ter) {
		System.out.println("\nLimite du terrain");
		System.out.println("  Abs min : "+ter.getAbsMin());
		System.out.println("  Abs max : "+ter.getAbsMax());
		System.out.println("  Ord min : "+ter.getOrdMin());
		System.out.println("  Ord max : "+ter.getOrdMax());
		
		System.out.println("\nListe triangle de terrain");
		affich(ter.getSol());
	}
	
	public static void modifyTerrain(Terrain ter) {
		double xMin,xMax,yMin,yMax;
		System.out.println("Abs min : "); xMin = Lire.i();
		System.out.println("Abs max : "); xMax = Lire.i();
		System.out.println("Ord min : "); yMin = Lire.i();
		System.out.println("Ord max : "); yMax = Lire.i();
		
		ter.setAbsMin(xMin);
		ter.setAbsMax(xMax);
		ter.setOrdMin(yMin);
		ter.setOrdMax(yMax);
	}
	//______________________________Point_Terrain______________________________
	
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
	//_________________________________Noeud_________________________________
	
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
	
	//______________________________Noeud_Simple______________________________
	
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
		System.out.println("\nChoisir un noeud simple");
		for(Noeud i : trei.getNoeudList()) {
			k++;
			if(i instanceof NoeudSimple) {
				System.out.println(k+" : "+i);
			}
		}
		System.out.print("Votre choix : "); num = Lire.i();
		return (NoeudSimple)trei.getNoeudList().get(num-1);
	}
	//_________________________________Force_________________________________
	
	public static void modifyForce(Noeud S) {
		double x,y;
		System.out.print("\nEffort abscisse : "); x = Lire.d();
		System.out.print("Effort ordonnée : "); y = Lire.d();
		S.setForce(new Force(x,y));
	}
	
	public static void supprForce(Noeud S) {
		S.setForce(new Force(0,0));
	}
	//________________________________Noeud_Appui______________________________
	
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
	//_____________________________Triangle_Terrain____________________________
	
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
	//________________________________Type_Barre______________________________
	
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
	
	public static void modifyType(TypeBarre type) {
		double lMin, lMax, tens, comp;
		float cout;
		
		System.out.println("\nLongueure min : "); lMin = Lire.d();
		System.out.println("Longueure max : "); lMax = Lire.d();
		System.out.println("Tension max : "); tens = Lire.d();
		System.out.println("Compression max : "); comp = Lire.d();
		System.out.println("Cout au metre : "); cout = Lire.f();
		
		type.setLongMin(lMin);
		type.setLongMax(lMax);
		type.setResTension(tens);
		type.setResCompression(comp);
		type.setCout(cout);
	}
	//___________________________________Barre_________________________________
	
	public static Barre chooseBarre(Treilli trei) {
		int num, k=0;
		System.out.println("\nChoisir une barre");
		for(Barre i : trei.getBarreList()) {
			k++;
			System.out.println(k+" : "+i);
		}
		System.out.print("Votre choix : "); num = Lire.i();
		return trei.getBarreList().get(num-1);
	}
	//_________________________________________________________________________
	
	public static void SimIG() {
		int choix;
		boolean stop = false;
		
		Terrain ter = new Terrain(-10, 10, -10, 10);
		Treilli trei = new Treilli(ter);
		
		
		new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,2,0),new PointTerrain(ter,1,-1));
		new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,1,-1),new PointTerrain(ter,-1,-1));
		new TriangleTerrain(new PointTerrain(ter,0,0),new PointTerrain(ter,1,-1),new PointTerrain(ter,-2,0));
		
		
		while(!stop) {
			System.out.println("\n____________________MENU____________________");
			System.out.print("\n");
			System.out.println("1 : Afficher les éléments du treilli");
			System.out.println("2 : Terrain");
			System.out.println("3 : Noeud");
			System.out.println("4 : Barre");
			System.out.println("5 : Force");
			System.out.println("6 : Calculer les efforts");
			System.out.println("7 : Enregistrer le treilli");
			System.out.println("8 : Ouvrir un fichier");
			System.out.println();
			System.out.println("0 : Quitter");
			System.out.println();
			System.out.print("Votre Choix : ");
			choix = Lire.i();
			
			if(choix == 0) {stop = true;}
		
			switch(choix) {
			default:
				break;
			case 1:
				System.out.println("\n___________________TREILLI___________________");
				System.out.print("\n");
				afficher(trei);
				break;
			case 2:
				System.out.println("\n___________________TERRAIN___________________");
				System.out.print("\n");
				System.out.println("1 : Afficher les éléments du terrain");
				System.out.println("2 : Modifier les limites du terrain");
				System.out.println("3 : Créer un nouveau triangle");
				System.out.println("4 : Modifier un triangle existant");
				System.out.println("5 : Supprimer un triangle");
				System.out.println();
				System.out.println("0 : Revenir au menu");
				System.out.println();
				System.out.print("Votre Choix : ");
				choix = Lire.i();
				
				switch(choix) {
				default:
					break;
				case 1:
					afficher(ter);
					break;
				case 2:
					try {
						modifyTerrain(ter);
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
					break;
				case 3:
					try {
						new TriangleTerrain(newPoint(ter),newPoint(ter),newPoint(ter));
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
					break;
				case 4:
					try {
						modifyPoint(choosePoint(chooseTriangle(ter)));
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
					break;
				case 5:
					
					break;
				}
				break;
			case 3:
				System.out.println("\n____________________NOEUD____________________");
				System.out.print("\n");
				System.out.println("1 : Créer un nouveau noeud d'appui");
				System.out.println("2 : Modifier un noeud simple existant");
				System.out.println("3 : Modifier un appui existant");
				System.out.println("4 : Supprimer un noeud");
				System.out.println();
				System.out.println("0 : Revenir au menu");
				System.out.println();
				System.out.print("Votre Choix : ");
				choix = Lire.i();
				
				switch(choix) {
				default:
					break;
				case 1:
					newAppui(trei);
					break;
				case 2:
					try {
						modifyNoeudSimple(chooseNoeudSimple(trei));
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
					break;
				case 3:
					try {
						modifyAppui(chooseAppui(trei));
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
					break;
				case 4:
					try {
						chooseNoeud(trei).delete();
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
					break;
				}
				break;
			case 4:
				System.out.println("\n____________________BARRE____________________");
				System.out.print("\n");
				System.out.println("1 : Créer une nouvelle barre");
				System.out.println("2 : Créer une barre entre deux noeuds existants");
				System.out.println("3 : Supprimer une barre");
				System.out.println("4 : Créer un nouveau type");
				System.out.println("5 : Modifier un type");
				System.out.println();
				System.out.println("0 : Revenir au menu");
				System.out.println();
				System.out.print("Votre Choix : ");
				choix = Lire.i();
				
				switch(choix) {
				default:
					break;
				case 1:
					try {
						new Barre(chooseNoeud(trei),newNoeudSimple(trei), chooseType());
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					} catch(java.lang.Error e) {
						
					}
					break;
				case 2:
					try {
						new Barre(chooseNoeud(trei), chooseNoeud(trei), chooseType());
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					} catch(java.lang.Error e) {
						
					}
					break;
				case 3:
					try {
						chooseBarre(trei).delete();
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					} catch(java.lang.Error e) {
						
					}
					break;
				case 4:
					newType();
					break;
				case 5:
					try {
						modifyType(chooseType());
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
				}
				break;
			case 5:
				System.out.println("\n____________________FORCE____________________");

				System.out.print("\n");
				System.out.println("1 : Appliquer une force sur un noeud");
				System.out.println("2 : Supprimer une force sur un noeud");
				System.out.println();
				System.out.println("0 : Retour au menu");
				System.out.println();
				System.out.print("Votre Choix : ");
				choix = Lire.i();
				
				switch(choix) {
				default:
					break;
				case 1:
					try {
						modifyForce(chooseNoeud(trei));
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
					break;
				case 2:
					try {
						supprForce(chooseNoeud(trei));
					} catch(java.lang.IndexOutOfBoundsException e) {
						System.out.println("/Elément inexistant/");
					}
					break;
				}
			break;
			case 6:
				System.out.println("\n__________________EFFORTS__________________");
				System.out.print("\n");
				calculs(trei);
				break;
			case 7:
				System.out.println("\n_________________ENREGISTRER_________________");
				System.out.print("\n");
				System.out.println("Nom du fichier : ");
				String sauv = Lire.S();
				ecrireFichier(sauv, ter, trei);
				break;
			case 8:
				System.out.println("\n___________________OUVRIR___________________");
				System.out.print("\n");
				System.out.println("Nom du fichier : ");
				String ouvr = Lire.S();
				ouvrirFichier(ouvr, ter, trei);
				
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
		SimIG();
		/*
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
