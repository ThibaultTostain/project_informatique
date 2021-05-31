package other;

import java.util.List;

public class Matrice
{
	
	// Variables
	
	private double [][] coeffs;
	
	//Get and Set
	
	public int getNbrLig () {
		//donne le nombre de ligne de la matrice
		return coeffs.length;
	}
	public int getNbrCol () {
		//donne le nombre de colonne de la matrice
		return coeffs[0].length;
	}
	
	public Matrice col(int col) {
        Matrice res = new Matrice(this.getNbrLig(), 1);
        for (int lig = 0; lig < this.getNbrLig(); lig++) {
            res.set(lig, 0, this.get(lig, col));
        }
        return res;
    }
	public Matrice lig(int lig) {
        Matrice res = new Matrice(1, this.getNbrCol());
        for (int col = 0; col < this.getNbrCol(); col++) {
            res.set(0, col, this.get(lig, col));
        }
        return res;
    }
	public void replaceLig(int lig, Matrice l) {
        for (int col = 0; col < this.getNbrCol(); col++) {
            this.set(0, col, l.get(0, col));
        }
    }
	
	public void set (int lig, int col, double x) {
		//fixe coefficient [lig][col] de la matrice à "x" 
		if (lig<0||col<0||lig>=this.getNbrLig()||col>=this.getNbrCol()){
			throw new Error("Matrice : void .set() : indices invalides");
		}
		this.coeffs[lig][col] = x;
	}
	public double get (int lig, int col) {
		//retourne le coefficient [lig][col] de la matrice
		if(lig < 0 || col < 0 || lig>this.getNbrLig()||col>this.getNbrCol()){
			throw new Error ("Matrice : double .get(lig,col) : indices incompatibles");
		}
		return this.coeffs[lig][col];
	}
		
	//Constructeurs
	
	public Matrice (double [][] tabl) {
		//Transforme un tableau en Matrice
		this.coeffs = tabl;
	}
	
	public Matrice (int nl, int nc) {
		//Matrice de taille [nl][nc] initialisée à zéro partout
		if(nl < 0 || nc < 0){
			throw new Error ("Matrice : Constructeur (nl,nc) : indice(s) < 0");
		}
		this.coeffs = new double [nl][nc];
	}
	public Matrice (int n) {
		//Matrice carré de dimension n
		this(n,n);
	}
	
	//Affichage
	
	public String toString (){
		// Affiche la Matrice sous forme de tableau
		String res = "";
		for(int i = 0; i < this.getNbrLig(); i++) {
			res += "[ ";
			for(int j = 0; j < this.getNbrCol(); j++) {
				res += String.format("%+4.2E", this.get(i,j))+" ";
				if(j == this.getNbrCol()-1) {res += "]\n";}
			}
		}
		return res;
	}
	//Oppérations sur les Matrices
	
	public static Matrice identite (int n){
		//Créer une MAtrice identité de taille "n"
		Matrice res = new Matrice(n);
		for(int i = 0; i < n ; i++)
		{
			res.set(i,i,1);
		}
		return res;
	}
	
	public static Matrice vecteur (double[] coef) {
		// Transforme un tableau en Matrice colonne
		Matrice res = new Matrice (coef.length, 1);
		for(int i = 0; i < res.getNbrLig(); i++) {res.set(i,0,coef[i]);}
		return res;
	}
	public static Matrice vecteur (List<Double> coef) {
		// Transforme une liste en Matrice colonne
		Matrice res = new Matrice (coef.size(), 1);
		for(int i = 0; i < res.getNbrLig(); i++) {res.set(i,0,coef.get(i));}
		return res;
	}
	
	public Matrice concatLig (Matrice m){
		//Concatener deux matrice l'une "au dessus" de l'autre
		if(this.getNbrCol() != m.getNbrCol()){
			throw new Error ("Matrice : .concatLig() : Le nombre de colonnes ne correspond pas");
		}
		Matrice res = new Matrice ((this.getNbrLig() + m.getNbrLig()), this.getNbrCol());
		for(int i = 0; i < res.getNbrLig(); i++){
			for(int j = 0; j < res.getNbrCol(); j++){
				if(i < this.getNbrLig()){ // on commence par remplir par la matrice de base
					res.set(i,j,this.get(i,j));
				}
				else // on finit par la matrice a concatener quand on arrive à la fin de la première
				{
					res.set(i,j,m.get((i - this.getNbrLig()),j));
				}
			}
		}
		return res;
	}
	public Matrice concatCol (Matrice m){
		//Concaténer deux Matrice l'une "à côté" de l'autre
		if(this.getNbrLig() != m.getNbrLig()){
			throw new Error ("Matrice : .concatCol() : Le nombre de ligne ne correspond pas");
		}
		Matrice res = new Matrice (this.getNbrLig(), (this.getNbrCol() + m.getNbrCol()));
		for(int j = 0; j < res.getNbrCol(); j++){
			for(int i = 0; i < res.getNbrLig(); i++){
				if(j < this.getNbrCol()){
					res.set(i,j,this.get(i,j));
				}
				else{
					res.set(i,j,m.get(i,(j - this.getNbrCol())));
				}
			}
		}
		return res;
	}
	public Matrice subLig (int nMin, int nMax){
		//Ne prendre qu'une partie de la matrice d'une ligne "nMin" à une ligne "nMax" plus grande
		if (nMin < 0 || nMax < nMin || nMax > this.getNbrLig()){
			throw new Error("Matrice : .subLig() : indices lignes invalides");
		}
		Matrice res = new Matrice (nMax - nMin + 1, this.getNbrCol());
		for(int i = 0; i < res.getNbrLig(); i++) {
			for(int j = 0; j < res.getNbrCol(); j++) {
				res.set(i, j, this.get((i + nMin),j));
			}
		}
		return res;
	}
	public Matrice subCol (int nMin, int nMax) {
		//Ne prendre qu'une partie de la matrice d'une colonne "nMin" à une colonne "nMax" plus grande
		if (nMin < 0 || nMax < nMin || nMax > this.getNbrCol()){
			throw new Error("Matrice : .subCol() : indices colonnes invalides");
		}
		Matrice res = new Matrice (this.getNbrLig(), (nMax - nMin) + 1);
		for(int j = 0; j < res.getNbrCol(); j++) {
			for(int i = 0; i < res.getNbrLig(); i++) {
				res.set(i, j, this.get(i,(j + nMin)));
			}
		}
		return res;
	}
	public Matrice transposee () {
		Matrice res = new Matrice(this.getNbrCol(), this.getNbrLig());
		for (int i = 0; i < res.getNbrLig(); i++) {
	        for (int j = 0; j < res.getNbrCol(); j++) {
	            res.set(i, j, this.get(j, i));
	        }
	    }
	    return res;
	}
	public Matrice add (Matrice m) {
		//Additionne deux Matrices de même taille
		if (this.getNbrLig() != m.getNbrLig() || this.getNbrCol() != m.getNbrCol()){
			throw new Error("Matrice : .add() : Matrices incompatibles");
		}
		Matrice res = new Matrice(this.getNbrLig(), this.getNbrCol());
		for(int i = 0; i < res.getNbrLig(); i++) {
			for(int j = 0; j < res.getNbrCol(); j++) {
				res.set(i,j,this.get(i,j) + m.get(i,j));
			}
		}
		return res;
	}
	public Matrice sub (Matrice m) {
		//Faire return = this - m
		return this.add(m.opp());
	} 
	public Matrice scal (double l) {
		//Multiplier une Matrice par un scalaire
		Matrice res = new Matrice(this.getNbrLig(), this.getNbrCol());
		for(int i = 0; i < res.getNbrLig(); i++) {
			for(int j = 0; j < res.getNbrCol(); j++) {
				res.set(i,j,l*this.get(i,j));
			}
		}
		return res;
	}
	public Matrice opp () {
		//Retourne l'opposé d'une matrice
		return this.scal(-1);
	}
	public void remove (Matrice m) {
		/* Vider une matrice en mettant tout à zéro, on pourrait utiliser : m=m.add(m.opp());
		 * Mais pour éviter les erreurs d'arrondis sur de possibles flottants on explicite les zéros.
		 */
		for(int i=0; i < m.getNbrLig();i++) {
			for(int j=0; j < m.getNbrCol();j++) {
				m.set(i,j,0);
			}
		}
	}
	public Matrice mult (Matrice m) {
		//Multiplier deux Matrices entre elles
		if(this.getNbrCol() != m.getNbrLig()) {
			throw new Error ("Matrice : .mult() : Col et Lig ne correspondent pas");
		}
		Matrice res = new Matrice (this.getNbrLig(), m.getNbrCol());
		for(int i = 0; i < res.getNbrLig(); i++) {
			for(int j = 0; j < res.getNbrCol(); j++) {
				for(int k = 0; k < m.getNbrLig(); k++) {
					res.set(i,j,res.get(i, j) + this.get(i,k) * m.get(k,j));
				}
			}
		}
		return res;
	}
	public Matrice pow(int pow) {
		//Mettre une Matrice à la puissance "pow"
		Matrice res = identite(this.getNbrLig());
		for(int i = 0; i < pow; i++) {
			res = res.mult(this);
		}
		return res;
	}
	
	public int permutLig (int l1, int l2) {
		//permute deux lignes d'une matrice
		if (l1<0||l2<0||l1>this.getNbrLig()||l2>this.getNbrLig()){
			throw new Error("Matrice : .permutLig()() : indice(s) ligne(s) incompatible(s)");
		}
		
		double[] var = this.coeffs[l1];
		this.coeffs[l1] = this.coeffs[l2];
		this.coeffs[l2] = var;
		
		/*
		Matrice var = this.lig(l1);
		System.out.println(this.lig(l2));
		System.out.println(this);
		this.replaceLig(l1,this.lig(l2));
		this.replaceLig(l2,var);
		*/
        if (l1 == l2) {
            return 1;
        } else {
            return -1;
        }
	}
	
	public void transvection (int l1, int l2) {
		//Transvection 
		if(l1 >= this.getNbrCol()){
			throw new Error ("Matrice : .transvection() : l1 >= this.getNbrCol");
		}
		if(this.coeffs[l1][l1] == 0){
			throw new Error ("Matrice : .transvection() : Division par zero");
		}
		double p = this.get(l2,l1) / this.get(l1,l1);
		for(int j = 0; j < this.getNbrCol(); j++){
			if(j == l1){
				this.set(l2,j,0);
			} else {
				this.set(l2,j,this.get(l2,j) - p*this.get(l1,j));
			}
		}
	}
	
	public int ligPlusGrandPivotInf (int c){
		//Cherche un pivot!=0 pour le coef [c][c]
        if (c >= this.getNbrLig() || c >= this.getNbrCol()) {
            throw new Error("Matrice : .ligPlusGrandPivot() : indice incompatible");
        }
		int res = c;
		double max = Math.abs(this.get(c, c));
		for(int i = c+1 ; i < this.getNbrCol()/2; i++){
			System.out.println("i="+i+"c="+c+"this.getNbrCol()="+this.getNbrCol());
			if(Math.abs(this.get(i, c)) > max){
				max = Math.abs(this.get(i, c));
				res = i;
			}
		}
		if(max <= 1E-8){
			res = -1;
		}
		return res;
	}
	
	public int ligPlusGrandPivotSup (int c){
		//Cherche un pivot!=0 pour le coef [c][c]
        if (c >= this.getNbrLig() || c >= this.getNbrCol()) {
            throw new Error("Matrice : .ligPlusGrandPivot() : indice incompatible");
        }
		int res = c;
		double max = Math.abs(this.get(c, c));
		for(int i = 0 ; i < this.getNbrCol()/2; i++){
			System.out.println("i="+i);
			if(Math.abs(this.get(i, c)) > max){
				max = Math.abs(this.get(i, c));
				res = i;
				System.out.println("max = "+max+"res = "+res+"c="+c);
				}
		}
		if(max <= 1E-8){
			res = -1;
		}
		return res;
	}
	
	public int ligPlusGrandPivotE (int c, int n){
		//Cherche un pivot!=0 pour le coef [c][c]
        if (c >= this.getNbrLig() || c >= this.getNbrCol()) {
            throw new Error("Matrice : .ligPlusGrandPivot() : indice incompatible");
        }
		int res = c;
		System.out.println("Entré dans PivotE c="+c+"n="+n);
		double max = 0;
		for(int i = this.getNbrCol()/2-1 ; i >=0 ; i--){
			System.out.println("i="+i);
			if(Math.abs(this.get(i, c)) > max && i != c ){
				max = Math.abs(this.get(i, c));
				System.out.println("i="+i+"c="+c+"max="+max);
				res = i;
			}
		}
		if(max <= 1E-8){
			res = -1;
		}
		if(n==res){
			res = c;
		}
		return res;
	}
	public Matrice clone() {
    	//copie une matrice
		Matrice res = new Matrice(this.getNbrLig(),this.getNbrCol());
		for(int i = 0; i < this.getNbrLig(); i++) {
			for(int j = 0; j < this.getNbrCol(); j++) {
				res.set(i, j, this.get(i, j));
			}
		}
        return res;
    }
	
	public void diagonale(Matrice res) {
		
		for(int i = 0; i < Math.min(res.getNbrLig(),res.getNbrCol()/2);i++) {
			System.out.println("i= "+i);
			if (res.get(i,i) == 0){
				System.out.println("res.get(i,i) == 0");
				if (res.ligPlusGrandPivotInf(i) == -1) {
					System.out.println("Debug : ligPlusGrandPivotInf(i) == -1");
					if (res.ligPlusGrandPivotSup(i) == -1) {
			            throw new Error("Matrice : .diagonalisation() : matrice irréductible");
			        }
					System.out.println("Debug : ligPlusGrandPivotSup(i) != -1");
					if (res.ligPlusGrandPivotE(res.ligPlusGrandPivotSup(i),i) == -1) {
			            throw new Error("Matrice : .diagonalisation() : matrice irréductible");
			        }
					System.out.println("Debug : ligPlusGrandPivotE(ligPlusGrandPivotSup(i),i) != -1");
					int s=res.ligPlusGrandPivotSup(i);
					int q=res.ligPlusGrandPivotE(res.ligPlusGrandPivotSup(i),i);
					System.out.println("Debug : i = " + i + " s = " + s +" q = " + q + " ligPlusGrandPivotSup(i) = " +res.ligPlusGrandPivotSup(i));
					res.permutLig(i,res.ligPlusGrandPivotSup(i)); // On met pivot != 0
					res.permutLig(s,q);
				} else {
					System.out.println("Debug : ligPlusGrandPivotInf(i) != -1");
					res.permutLig(i,ligPlusGrandPivotInf(i)); // On met pivot != 0 
				}
			}
			System.out.println(res);
		}
		
		for(int i = 0; i < Math.min(res.getNbrLig(),res.getNbrCol()/2);i++) {
			for(int n = i+1; n<res.getNbrLig();n++ ) {res.transvection(i, n);}
		}

		System.out.println("Triangle supérieur");
		System.out.println(res);
	}
	
	public void diagonalisation () {
		//On vérifie que aucun pivot = 0
		Matrice res = this.concatCol(identite(this.getNbrLig()));
		
		//Création du triangle supérieur
		
		diagonale(res);
		
		for(int i = 0; i < Math.min(res.getNbrLig(),res.getNbrCol()/2);i++) {
			for(int n = i+1; n<res.getNbrLig();n++ ) {res.transvection(i, n);}
		}

		System.out.println("Triangle supérieur");
		System.out.println(res);
			
		//Diagonalisation
		/*	
		for(int i = Math.min(res.getNbrLig(),res.getNbrCol()/2)-1 ; i>=0 ; i--) {
			for(int n = i-1; n>=0;n-- ) {res.transvection(i, n);} 
		}

		System.out.println("Diagonalier");
		System.out.println(res);
		*/
		// Clarifier la diagonale (=1)

		for(int n = 0; n < Math.min(res.getNbrLig(),res.getNbrCol()/2);n++) {
			System.out.println("n="+n);
			double q=res.get(n, n);
			for(int i = 0; i < res.getNbrCol();i++) {
				System.out.println("i="+i+"res.get(n,i)="+res.get(n,i)+"res.get(n, n)="+res.get(n, n));
				res.set(n, i, (res.get(n,i)/q));
			}
		}
		System.out.println("Mise de la diagonale à 1");
		System.out.println(res);
		res = res.subCol(res.getNbrCol()/2,res.getNbrCol()-1);
		System.out.println("Résultat");
		System.out.println(res);
		this.coeffs = res.coeffs;
	}
	//Main
	
	public static void main (String args[])
	{
		double[][] tabl = new double[][]{
			{+7.07E-01 , +0.00E+00 , +0.00E+00 , +1.00E+00 , +0.00E+00 , +0.00E+00},
			{-7.07E-01 , +0.00E+00 , -1.00E+00 , +0.00E+00 , +1.00E+00 , +0.00E+00},
			{+0.00E+00 , +7.07E-01 , +0.00E+00 , +0.00E+00 , +0.00E+00 , +1.00E+00},
			{+0.00E+00 , +7.07E-01 , +1.00E+00 , +0.00E+00 , +0.00E+00 , +0.00E+00},
			{-7.07E-01 , -7.07E-01 , +0.00E+00 , +0.00E+00 , +0.00E+00 , +0.00E+00},
			{+7.07E-01 , -7.07E-01 , +0.00E+00 , +0.00E+00 , +0.00E+00 , +0.00E+00}};
        Matrice m = new Matrice(tabl);
        double[][] tabl2 = new double[][]{
			{+0.00E+00},
			{+0.00E+00},
			{+0.00E+00},
			{+0.00E+00},
			{+0.00E+00},
			{+1.00E+03}};
		
        Matrice m2 = new Matrice(tabl2);
        System.out.println("==============================================");
        System.out.println("matrice : ");
        System.out.println(m);
        System.out.println("Matrice diagonalisée : ");
        m.diagonalisation();
        m=m.mult(m2);
        System.out.println(m);
	}
	
	
	/*
	public void organiser (List<Double> V, List<String> I) {
		double max;
		int ligne;
		for(int c = 0; c < getNbrCol()-1; c++) { // La dernière ligne ne plus plus être bougée, les autres étants classés
			max = this.get(c, c);
			ligne = c;
			for(int l = c; l < getNbrLig(); l++) {
				if(Math.abs(get(l, c)) > max) {
					max = Math.abs(get(l, c));
					ligne = l;
				}
			}
			if(max == 0) {throw new Error("Pivot non nul introuvable");}
			permutLig(c, ligne);
			permutDouble(V,c,ligne);
			permutString(I,c,ligne);
		}
	}
	public static void permutDouble (List<Double> l, int pos1, int pos2) {
		double var = l.get(pos1);
		l.set(pos1, l.get(pos2));
		l.set(pos2, var);
	}
	public void permutString (List<String> l, int pos1, int pos2) {
		String var = l.get(pos1);
		l.set(pos1, l.get(pos2));
		l.set(pos2, var);
	}
	public void echelonner (List<Double> V) {
		double var;
		for(int it = 1; it < getNbrLig(); it++) { // triangle inf
			for(int l = it; l < getNbrLig(); l++) {
				if(get(it-1, it-1) == 0) {throw new Error("Pivot nul");}
				var = (get(l, it-1)/get(it-1, it-1));
				for(int c = 0; c < getNbrCol(); c++) {
					set(l, c, get(l, c) - var*get(it-1, c));
				}
				V.set(l, V.get(l) - var*V.get(it-1));
			}
		}
		for(int it = getNbrLig()-2; it >= 0; it--) { // triangle sup
			for(int l = it; l >= 0; l--) {
				if(get(it+1, it+1) == 0) {throw new Error("Pivot nul");}
				var = (get(l, it+1)/get(it+1, it+1));
				for(int c = 0; c < getNbrCol(); c++) {
					set(l, c, get(l, c) - var*get(it+1, c));
				}
				V.set(l, V.get(l) - var*V.get(it+1));
			}
		}
	}
	public void reduire (List<Double> V) {
		double var;
		for(int l = 0; l < getNbrLig(); l++) { // diagonale
			var = (1/get(l, l));
			for(int c = 0; c < getNbrCol(); c++) {
				set(l, c, get(l, c)*var);
			}
			V.set(l, V.get(l)*var);
		}
	}
	
	 */
}
