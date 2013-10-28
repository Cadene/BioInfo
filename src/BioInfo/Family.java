package BioInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class Family {

	private ArrayList<Protein> proteins;
	private int q;
	private HashMap<String, Integer> occurences;
	private HashMap<String, Double> weights;
	private HashMap<Integer, Double> rEntropy;

	private ArrayList<Character> AATypes; 
	
	public Family(ArrayList<Protein> proteins, int q){
		this.proteins = proteins;
		this.q = q;
		this.occurences = new HashMap<String,Integer>();
		this.weights = new HashMap<String,Double>();
		this.rEntropy = new HashMap<Integer,Double>();
		AATypes = new ArrayList<Character>();
		AATypes.add('A');
		AATypes.add('C');
		AATypes.add('D');
		AATypes.add('E');
		AATypes.add('F');
		AATypes.add('G');
		AATypes.add('H');
		AATypes.add('I');
		AATypes.add('K');
		AATypes.add('L');
		AATypes.add('M');
		AATypes.add('N');
		AATypes.add('P');
		AATypes.add('Q');
		AATypes.add('R');
		AATypes.add('S');
		AATypes.add('T');
		AATypes.add('V');
		AATypes.add('W');
		AATypes.add('Y');
		AATypes.add('-');
	}
	public ArrayList<Protein> getProteins(){
		return proteins;
	}
	public HashMap<String,Integer> getOccurences(){
		return occurences;
	}
	public HashMap<String,Double> getWeights(){
		return weights;
	}
	public Double getWeight(char a,int i){
		return weights.get(""+a+i);
	}
	public int getQ(){
		return q;
	}
	public int getM(){
		return this.proteins.size();
	}
	public double getREntropy(int i){
		return this.rEntropy.get(i);
	}
	
	public String toString(int length){
		String s = "";
		for(int i=0; i<length; i++){
			s += proteins.get(i).toString() + "\n";
		}
		return s;
	}
	
	public void calculateOcc(){
		Integer actualInt;
		String actualAA;
		
		for(Protein p : proteins){
			
			// une ligne
			for(int i=0; i<p.getAAList().length(); i++){
				
				actualAA = "" + p.getAAList().charAt(i) + i;
				actualInt = this.occurences.get(actualAA);
				if(actualInt == null){
					this.occurences.put(actualAA , new Integer(1));
				}else{
					actualInt++;
					this.occurences.put(actualAA, actualInt);
				}
			}
		}
	}
	public String displayOcc(){
		String s = "";
		for(String AA : this.occurences.keySet()){
			s += "[ " + AA + ", " + this.occurences.get(AA) + " ]\n";
		}
		return s;
	}
	public boolean isGoodOcc(){
		int value;
		Integer inter;
		for(int i=0; i< this.q; i++){
			value=0;
			for(Character c : this.AATypes){
				inter = this.occurences.get(""+c+i);
				if(inter != null)
					value += inter;
			}
			if(value != this.getM()){
				return false;
			}
		}
		return true;
	}
	
	public void calculateWeights(){
		for(String AA : this.occurences.keySet()){
			this.weights.put(AA, 
					(this.occurences.get(AA) + 1)
					/
					(double) (this.getM() + this.getQ()));
		}
	}
	public String displayWeights(){
		String s = "";
		for(String AA : this.weights.keySet()){
			s += "[ " + AA + ", " + this.weights.get(AA) + " ]\n";
		}
		return s;
	}
	
	public void calculateREntropy(){
		double log2 = Math.log(2);
		double log2Q = Math.log(this.getQ()) / log2;
		double value;
		Double inter;
		for(int i=0; i< this.getQ(); i++){
			value = log2Q;
			for(Character AA : this.AATypes){
				inter = this.getWeight(AA,i);
				if(inter != null){
					value += inter * (Math.log(inter) / log2);
				}
			}
			this.rEntropy.put(i,value);
		}
	}
	public String displayREntropy(){
		String s = "";
		for(int i=0; i< this.getQ(); i++){
			s += "[ " + i + ", " + this.rEntropy.get(i) + " ]\n";
		}
		return s;
	}
	
	public void calculateMConservedAA(){
		
	}
	
	
}
