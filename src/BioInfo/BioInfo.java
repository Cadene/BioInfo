package BioInfo;

public class BioInfo {

	public static void main(String[] args) {
		
		String string = "== BioInfo ==\n\n";
		
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
		//string += family.NFrequency.toString();
		string += "Frequence null en '-' : " + family.getNFrequency('-') + "\n\n";
		
		
		string += "Protein a analyser :\n";
		Protein protein = ProteinFactory.makeProtein("test_seq");
		string += protein.toString() + "\n\n";
		
		
		string += "Log-vraisemblance i=0 : ";
		string += family.getLogOddsRatio(protein, 0) + "\n\n";
		

		string += "Generation de la courbe des logs vraisemblances ... ";
		family.generateCurveLOR(protein);
		string += "done!\n\n";
		
		
		string += "Calcule des occurences doubles ... ";
		string = BioInfo.afficher(string);
		string += "done !\n";
		family.calculateNum2();
		
		string += family.getNumber2('A',38,'-',43) + "\n";
		string += family.getNumber2('-',43,'A',38) + "\n";
		string += "\n";
		
		
		string += "Calcule des poids doubles ... ";
		string = BioInfo.afficher(string);
		string += "done !\n";
		family.calculateWeights2();
		
		string += family.getWeight2('A',38,'-',43) + "\n";
		string += family.getWeight2('-',43,'A',38) + "\n";
		string += "\n";
		
		/*int i = 49;
		string += family.getWeight(protein.getAA(i), i);*/
		System.out.println(string);
		
	}
	
	public static String afficher(String string)
	{
		System.out.print(string);
		return "";
	}

}
