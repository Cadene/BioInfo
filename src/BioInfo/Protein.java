package BioInfo;

public class Protein {

	private String protName;
	private String AAList;
	
	public Protein(String protName, String AAList) {
		this.protName = protName;
		this.AAList = AAList;
	}
	public String getProtName(){
		return protName;
	}
	public String getAAList(){
		return AAList;
	}
	public char getAA(int i){
		return AAList.charAt(i);
	}
	
	public int length(){
		return AAList.length();
	}
	
	public String toString(){
		String s = "";
		s += "nom: " + protName + "\n";
		s += "longueur: " + this.length() + "\n";
		s += AAList;
		return s;
	}

}
