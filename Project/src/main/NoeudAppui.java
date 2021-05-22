package main;

public class NoeudAppui extends Noeud {
	
	private SegmentTerrain seg;
	private boolean simple;
	private double pos;
	
	// Constructeurs 
	
	public NoeudAppui(Treilli treilli, SegmentTerrain seg, double pos) { // Appui double de base.
		this(treilli,seg,pos,false);
	}
	
	public NoeudAppui(Treilli treilli, SegmentTerrain seg, double pos, boolean simple) {
		super(treilli);
		/* On n'a pas besoin de vérifier si le noeud est dans la limite du terrain car il est sur un objet qui
		 * est dedans.
		 */
		this.seg = seg;
		this.simple = simple;
		// On vérifie que la position est entre 0 et 1
		if(pos < 0 || pos > 1) {throw new Error("Position mismatch");}
		this.pos = pos;
	}

	// Get & Set
	
	public SegmentTerrain getSeg() {
		return seg;
	}

	public double getPos() {
		return pos;
	}

	public void setPos(double pos) {
		this.pos = pos;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	public double getAbs() {
		double Abs1, Abs2;
		Abs1 = this.getSeg().getStart().getAbs();
		Abs2 = this.getSeg().getEnd().getAbs();
		
		return this.getPos()*Abs2 + (1-this.getPos())*Abs1;
		// Position = 0 : point sur P1. Position = 1 : point sur P2
	}
	
	public double getOrd() {
		double Ord1, Ord2;
		Ord1 = this.getSeg().getStart().getOrd();
		Ord2 = this.getSeg().getEnd().getOrd();
		
		return this.getPos()*Ord2 + (1-this.getPos())*Ord1;
		// Position = 0 : point sur P1. Position = 1 : point sur P2
	}
	
	// Affichage
	
	@Override
	public String toString() {
		if(simple == true) { return "AppuiSimple;"+this.getId()+ ";" +super.getTreilli().getId()+ ";"+this.getSeg()+";"+this.getPos();}
		else { return "AppuiDouble;"+this.getId()+ ";" +super.getTreilli().getId()+ ";"+this.getSeg()+";"+this.getPos();}
	}
	
	// Fonctions et Procedures
	
	public double beta() { // Angle ABC ou AB est l'axe des abscisse et BC est le segment de this
		double vx,vy;
		// Vecteur BA est l'axe des abscisse.
		//vx = x-S.getAbs(); vy = y-S.getOrd(); // Vecteur BC.
		vx = this.getSeg().getStart().getAbs() - this.getSeg().getEnd().getAbs();
		vy = this.getSeg().getStart().getOrd() - this.getSeg().getEnd().getOrd();
		return Math.atan2(vy, vx) + Math.PI/2; //angle ABC.
	}
	
}
