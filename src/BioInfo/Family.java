package BioInfo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Family {

	private ArrayList<Character> AATypes; 
	
	private ArrayList<Protein> proteins;
	
	private HashMap<String, Integer> numbers;
	private HashMap<String, Double> weights;
	private ArrayList<Double> REntropy;
	private HashMap<Character, Double> NFrequency;
	
	private HashMap<String, Integer> numbers2;
	private HashMap<String, Double> weights2;
	private HashMap<String, Double> mutualInfo;
	
	private ArrayList<String> miSortKey;
	private ArrayList<Double> miSortValue;
	private ArrayList<Double> miSortDist;
	
	private HashMap<String, Double> distances;
	
	private ArrayList<Double> fractions;
	
	/* Constructeurs */
	
	public Family(ArrayList<Protein> proteins)
	{
		this.AATypes = FamilyFactory.makeAAList();
		
		this.proteins = proteins;
		
		this.numbers = new HashMap<String,Integer>();
		this.weights = new HashMap<String,Double>();
		this.REntropy = new ArrayList<Double>();
		this.NFrequency = new HashMap<Character, Double>();
		
		this.numbers2 = new HashMap<String,Integer>();
		this.weights2 = new HashMap<String,Double>();
		this.mutualInfo = new HashMap<String,Double>(10000,(float) 0.70);
		
		this.miSortKey = new ArrayList<String>();
		this.miSortValue = new ArrayList<Double>();
		this.miSortDist = new ArrayList<Double>();
		
		this.distances = new HashMap<String,Double>();
		
		this.fractions = new ArrayList<Double>();
	}
	
	/* Getters */
	
	public ArrayList<Protein> getProteins(){
		return proteins;
	}
	public HashMap<String,Integer> getOccurences(){
		return numbers;
	}
	public HashMap<String,Double> getWeights(){
		return weights;
	}
	public String getAA(int i){
		return AATypes.get(i).toString();
	}
	public ArrayList<Character> getAATypes(){
		return AATypes;
	}
	public int getQ(){
		return AATypes.size();
	}
	public int getM(){
		return this.proteins.size();
	}
	public int getL(){
		return this.proteins.get(0).length();
	}
	
	
	/* toString */
	
	public String toString(int length){
		String s = "";
		for(int i=0; i<length; i++){
			s += proteins.get(i).toString() + "\n";
		}
		return s;
	}
	
	public String toString(){
		String s = "";
		s += "Nb AA differents (q) : " + getQ() + "\n";
		s += "Taille (M) : " + getM() + "\n";
		s += "Longueur (L) : " + getL() + "\n";
		return s;
	}
	
	
	/* Occurences */
	
	public void calculateNum(){
		Integer actualInt;
		String actualAA;
		
		for(Protein p : proteins){
			
			// une ligne
			for(int i=0; i<p.getAAList().length(); i++){
				
				actualAA = "" + p.getAAList().charAt(i) + i;
				actualInt = this.numbers.get(actualAA);
				if(actualInt == null){
					this.numbers.put(actualAA , new Integer(1));
				}else{
					actualInt++;
					this.numbers.put(actualAA, actualInt);
				}
			}
		}
	}
	
	public String displayNum(){
		String s = "";
		for(String AA : this.numbers.keySet()){
			s += "[ " + AA + ", " + this.numbers.get(AA) + " ]\n";
		}
		return s;
	}
	
	public int getNumber(char a, int i)
	{
		Integer temp = numbers.get(""+a+i);
		if(temp == null)
			return 0;
		return temp;
	}
	
	public boolean isGoodNum(){
		int value;
		Integer inter;
		for(int i=0; i< this.getQ(); i++){
			value=0;
			for(Character c : this.AATypes){
				inter = this.numbers.get(""+c+i);
				if(inter != null)
					value += inter;
			}
			if(value != this.getM()){
				return false;
			}
		}
		return true;
	}
	
	
	/* Poids */
	
	public void calculateWeights()
	{
		int temp;
		for(String AA : this.numbers.keySet())
		{
			temp = this.numbers.get(AA);
			
			if (temp != 0) // optimisation
			{
				this.weights.put(AA, 
					(temp + 1)
					/
					(double) (this.getM() + this.getQ()));
			}
		}
	}
	
	public String displayWeights()
	{
		String s = "";
		for(String AA : this.weights.keySet()){
			s += "[ " + AA + ", " + this.weights.get(AA) + " ]\n";
		}
		return s;
	}
	
	public double getWeight(char a,int i)
	{
		Double rslt = weights.get(""+a+i);
		
		if(rslt != null){
			return rslt;
		}else{
			return 1/ (double)(getM()+getQ());
		}
	}
	
	
	/* Entropie relative */
	
	public void calculateREntropy()
	{
		double log2 = Math.log(2);
		double log2Q = Math.log(this.getQ()) / log2;
		double value;
		double temp;
		
		for(int i=0; i< this.getL(); i++)
		{
			value = log2Q;
			for(Character AA : this.AATypes)
			{
				temp = this.getWeight(AA,i);
				value += temp * (Math.log(temp) / log2);
			}
			this.REntropy.add(i,value);
		}
	}
	
	public String displayREntropy(){
		String s = "";
		for(int i=0; i< this.getL(); i++){
			s += "[ " + i + ", " + this.REntropy.get(i) + " ]\n";
		}
		return s;
	}
	
	public double getREntropy(int i){
		return this.REntropy.get(i);
	}
	
	public void generateCurveREntropy()
	{
		try
		{
            String fileName = "./data/REntropy.txt";
            PrintWriter out  = new PrintWriter(new FileWriter(fileName));
            
            for(int i=0; i < this.getL(); i++)
            {
            	out.println(i + " " + REntropy.get(i));
            }
         
            out.close();
	    }
	    catch(Exception e){
	      e.printStackTrace();
	    }
	}
	
	
	/* Frequence du modele nul */
	
	public void calculateNFrequency()
	{
		double value;
		
		for(Character AA : AATypes)
		{
			value = 0;
			for(int i=0; i< getL(); i++)
			{
				value += getWeight(AA.charValue(),i);
			}
			value /= getL();
			NFrequency.put(AA,value);
		}
	}
	
	public String displayNFrequency()
	{
		String s = "";
		for(Character AA : AATypes)
		{
			s += "[ " + AA + ", " + NFrequency.get(AA) + " ]\n";
		}
		return s;
	}
	
	public double getNFrequency(char a)
	{
		return NFrequency.get(a);
	}
	
	
	/* log-vraisemblance */
	
	public double proba(Protein p, int begin)
	{
		double rslt = 1.0;
		for(int i=begin; i < getL() + begin; i++)
		{
			System.out.println(p.getAA(i));
			rslt *= getWeight(p.getAA(i),i-begin);
		}
		return rslt;
	}
	
	public double probaNull(Protein p, int begin)
	{
		double rslt = 1.0;
		for(int i=begin; i < getL() + begin; i++)
		{
			rslt *= getNFrequency(p.getAA(i));
		}
		return rslt;
	}
	
	public double getLogOddsRatio(Protein p, int begin)
	{
		double log2 = Math.log(2);
		double rslt = 0.0;
		
		for(int i = begin; i < this.getL() + begin; i++)
		{
			rslt += Math.log(	
						this.getWeight(p.getAA(i), i-begin)	/
						this.getNFrequency(p.getAA(i))
					)	/ log2;
		}
		
		return rslt;
	}
	
	public ArrayList<Double> getLogOddsRatio(Protein p)
	{
		int max = p.length() - getL();
		ArrayList<Double> logs = new ArrayList<Double>();
		
		for(int i=0; i <= max; i++)
		{
			logs.add(getLogOddsRatio(p,i));
		}
		
		return logs;
	}
	
	public void generateCurveLOR(Protein p)
	{
		ArrayList<Double> logs;
		try
		{
            String fileName = "./data/LogOddsRatio.txt";
            PrintWriter out  = new PrintWriter(new FileWriter(fileName));
            
            logs = getLogOddsRatio(p);
            
            for(int i=0; i < logs.size(); i++)
            {
            	out.println(i + " " + logs.get(i));
            }
         
            out.close();
	    }
	    catch(Exception e){
	      e.printStackTrace();
	    }
	}
	
	
	/* Occurences doubles*/
	
	public void calculateNum2()
	{
		Integer actualInt;
		String actualAAi, actualAAj, actualAA;
		
		for(Protein p : proteins)
		{
			for(int i=0; i<p.length(); i++)
			{	
				actualAAi = "" + p.getAA(i) + i;
				
				for(int j=i+1; j<p.length(); j++)
				{
					actualAAj = "" + p.getAA(j) + j;
					
					actualAA = actualAAi + actualAAj;
					
					actualInt = this.numbers2.get(actualAA);
					
					if(actualInt == null)
						actualInt = new Integer(1);
					else
						actualInt++;

					this.numbers2.put(actualAA, actualInt);
				}
				
			}
		}
	}
	
	public int getNumber2(char a, int i, char b, int j)
	{
		Integer temp = numbers2.get(""+a+i+b+j);
		if (temp == null)
			return 0;
		return temp;
	}
	
	
	/* Poids doubles */
	
	public void calculateWeights2()
	{
		int temp = 0;
		double temp2 = 0.0;
		double temp3 = 0.0;
		
		for(String AAd : this.numbers2.keySet())
		{
			temp = this.numbers2.get(AAd);
			
			if(temp != 0) // si =0 on ne stock pas dans la HM, mais on genere avec getWeight2
			{
				temp2 = (1.0 / (double) this.getQ());
				temp3 = (double) (this.getM() + this.getQ());
				this.weights2.put(AAd, (temp + temp2) / temp3);
			}
		}
	}
	
	public double getWeight2(char a, int i, char b, int j)
	{
		Double rslt = weights2.get(""+a+i+b+j);
		
		if(rslt != null){
			return rslt;
		}else{
			return (1/(double) getQ()) / (double)(getM()+getQ());
		}
	}
	
	
	/* Information mutuelle */
	
	public void calculateMutualInfo()
	{
		double temp;
		double temp2;
		double log2 = Math.log(2);
		
		for(int i=0; i< getL()-1; i++)
		{
			for(int j=i+1; j< getL(); j++)
			{
				temp2 = 0.0;
				for(Character a : this.getAATypes())
				{
					for(Character b : this.getAATypes())
					{
						temp = this.getWeight2(a, i, b, j);
						temp2 += temp * Math.log( temp / ( this.getWeight(a, i) * this.getWeight(b, j) ) ) / log2;
					}
				}
				this.mutualInfo.put(i+" "+j, temp2);
			}
			
		}
	}
	
	public double getMutualInfo(int i, int j)
	{
		Double temp = this.mutualInfo.get(i+" "+j);
		if(temp == null)
			return 0.0;
		return temp;
	}
	
	private HashMap<String,Double> cloneMutualInfo()
	{
		HashMap<String,Double> copyMI = new HashMap<String,Double>();
		
		for(String key : mutualInfo.keySet())
		{
			copyMI.put(key, mutualInfo.get(key));
		}
		return copyMI;
	}
	
	public void calculateSortMutualInfo()
	{
		double maxV;
		String maxK = "";
		Double temp;
		HashMap<String,Double> copyMI = this.cloneMutualInfo();
		int max = copyMI.size();
		
		for(int i=0; i<max; i++)
		{
			maxV = 0.0;
			for(String key : copyMI.keySet())
			{
				temp = copyMI.get(key);
				if(temp > maxV) {
					maxV = temp;
					maxK = key;
				}
			}
			this.miSortValue.add(maxV);
			this.miSortKey.add(maxK);
			copyMI.remove(maxK);
		}
	}
	
	public String displaySortMutualInfo()
	{
		String s = "";
		for(int i=0; i<miSortKey.size(); i++)
		{
			s += this.miSortKey.get(i) + "\t" +this.miSortValue.get(i) + "\t" + this.miSortDist.get(i) + "\n";
		}
		return s;
	}
	
	
	/* Distances */
	
	public void calculateDistances()
	{
		this.distances = FamilyFactory.makeDistances();
		for(int i=0; i<miSortKey.size(); i++)
		{
			this.miSortDist.add(this.distances.get(this.miSortKey.get(i)));
		}
	}
	
	public String displayDistances()
	{
		String s ="";
		for(String key : distances.keySet())
		{
			s += key + " : " + distances.get(key) + "\n"; 
		}
		return s;
	}
	
	public double getFraction(int nbPaires)
	{
		int nbPaires8 = 0;
		for(int i=0; i<nbPaires; i++)
		{
			if(this.miSortDist.get(i)<8)
				nbPaires8++;
		}
		return nbPaires8 / (double) nbPaires;
	}
	
	/* Fractions */
	
	public void calculateFractions()
	{
		this.fractions.add(0, 0.0);
		for(int i=1; i<=50; i++)
		{
			this.fractions.add(i, getFraction(i));
		}
	}
	
	public void generateCurveFractions()
	{
		try
		{
            String fileName = "./data/Fractions.txt";
            PrintWriter out  = new PrintWriter(new FileWriter(fileName));
            
            for(int i=1; i < fractions.size(); i++)
            {
            	out.println(i + " " + fractions.get(i));
            }
         
            out.close();
	    }
	    catch(Exception e){
	      e.printStackTrace();
	    }
	}
	
	
	
	
	
}
