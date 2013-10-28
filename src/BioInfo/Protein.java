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
	
	
	public String toString(){
		return protName + "\n" + AAList;
	}

}
