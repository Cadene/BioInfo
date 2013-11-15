package BioInfo;

public class BioInfo {

	public static void main(String[] args) {
		
		String string = "\n= = =     B I O   I N F O R M A T I Q U E     = = =\n\n\n";
		
		string += "-- II. MODELISATION PAR PSWM --\n\n\n";
		
		Family family = FamilyFactory.makeFamily("Dtrain");
		string += "Famille :\n" + family.toString() + "\n";
		
		//System.out.println(family.toString(2));
		
		family.calculateNum();
		//string += family.displayOcc();
		
		string += "Bon calcul des occurences ? " + family.isGoodNum() + "\n\n";
		
		string += "Occurence de '-' en 0 : " + family.getNumber('-',0) + "\n\n";
		
		
		family.calculateWeights();
		//string += family.displayWeights();
		string += "Poid de '-' en 0 : " + family.getWeight('-',0) + "\n\n";
		
		
		family.calculateREntropy();
		//string += family.displayREntropy() + "\n";
		
		string += "Entropie relative en 0 : " + family.getREntropy(0) + "\n\n";
		

		string += "Generation de la courbe des entropies relatives ... ";
		family.generateCurveREntropy();
		string += "done!\n\n";
		
		
		family.calculateNFrequency();
		//string += family.displayNFrequency() + "\n";
		string += "Frequence null en '-' : " + family.getNFrequency('-') + "\n\n";
		
		
		string += "Protein a analyser :\n";
		Protein protein = ProteinFactory.makeProtein("test_seq");
		string += protein.toString() + "\n\n";
		
		
		//string += "-> minilogue : " + family.getLogOddsRatio(protein, 66) + "\n\n";
		
		string += "Log-vraisemblance  : ";
		string += family.getLogOddsRatio(protein, 1) + "\n\n";

		string += "Generation de la courbe des logs vraisemblances ... ";
		family.generateCurveLOR(protein);
		string += "done!\n\n";
		
		

		string += "\n\n-- III. CO-EVOLUTION DE RESIDUES EN CONTACT --\n\n\n";
		
		
		string += "Calcule des occurences doubles ... ";
		string = BioInfo.afficher(string);
		family.calculateNum2();
		string += "done !\n";
		
		string += "occurences A,38,-,43 : " + family.getNumber2('A',38,'-',43) + "\n";
		string += "\n";
		
		
		string += "Calcule des poids doubles ... ";
		string = BioInfo.afficher(string);
		family.calculateWeights2();
		string += "done !\n";
		
		string += "poids A,38,-,43 : " + family.getWeight2('A',38,'-',43) + "\n";
		string += "\n";
		
		
		string += "Calcule de la matrice d'info mutuelle ... ";
		string = BioInfo.afficher(string);
		family.calculateMutualInfo();
		string += "done !\n";
		
		string += "position 0,1 : " + family.getMutualInfo(0,1) + "\n";
		string += "position 21,42 : " + family.getMutualInfo(21,42) + "\n";
		string += "\n";
		
		
		string += "Trie de la matrice d'info mutuelle ... ";
		string = BioInfo.afficher(string);
		family.calculateSortMutualInfo();
		string += "done !\n\n";
		
		
		string += "Calcule des distances ... ";
		string = BioInfo.afficher(string);
		family.calculateDistances();
		string += "done !\n\n";
		
		//string += family.displaySortMutualInfo();
		
		string += "Calcule des fractions ... ";
		string = BioInfo.afficher(string);
		family.calculateFractions();
		string += "done !\n\n";
		
		
		string += "Génération de la courbe des fractions ... ";
		string = BioInfo.afficher(string);
		family.generateCurveFractions();
		string += "done !\n\n";
		
		
		System.out.println(string);
		
	}
	
	public static String afficher(String string)
	{
		System.out.print(string);
		return "";
	}

}
