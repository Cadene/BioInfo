package BioInfo;

public class BioInfo {

	public static void main(String[] args) {
		
		Family family = FamilyFactory.makeFamily("Dtrain");
		
		//System.out.println(family.toString(2));
		
		family.calculateOcc();
		System.out.println(family.isGoodOcc());
		
		System.out.println(family.getOccurences().get(""+'-'+1));
		
		//System.out.println(family.displayOcc());
		
		family.calculateWeights();
		//System.out.println(family.displayWeights());
		System.out.println(family.getWeight('-',0));
		
		family.calculateREntropy();
		System.out.println(family.displayREntropy());
		System.out.println(family.getREntropy(0));
		
		
		
		System.out.println("Fin");
	}

}
