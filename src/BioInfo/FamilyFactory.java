package BioInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FamilyFactory {

	private static String dataPath = "./data/";

	static Family makeFamily(String path)
	{
		int c;
		String protName = "";
		String AAList = "";
		ArrayList<Protein> proteins;
		Protein p;
		
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(dataPath + path + ".txt"));

			proteins = new ArrayList<Protein>();

			while ((c = br.read()) != -1)
			{
				if(c == '>')
				{
					protName = "";
					while ((c = br.read())  != '\n')
					{
						protName += (char)c;
					}
					AAList = "";
					while ((c = br.read())  != '\n')
					{
						AAList += (char)c;
					}
					p = new Protein(protName, AAList);
					proteins.add(p);
					//System.out.println(p.toString());
				}
				
			}
			br.close();	
			return new Family(proteins);
		}

		catch (FileNotFoundException exc)
		{
			System.out.println ("File not found");
		}
		catch (IOException ioe)
		{
			System.out.println ("Erreur IO");
		}
		return null;
	}
	
	static ArrayList<Character> makeAAList()
	{
		ArrayList<Character> AATypes = new ArrayList<Character>();
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
		return AATypes;
	}

	public static HashMap<String, Double> makeDistances()
	{
		
		int c = 1;
		String pos = "";
		String dist = "";
		int nbPos;
		HashMap<String, Double> distances = new HashMap<String, Double>();
		
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(dataPath + "distances.txt"));
			
			while (c != -1)
			{
				nbPos=0;
				pos = "";
				while ((c = br.read()) != -1)
				{	
					if(c == 32) // ' '
						nbPos++;
					if(nbPos==2)
						break;
					pos += (char)c;
				}
				
				dist = "";
				while ((c = br.read())  != -1 && c != '\n')
				{
					dist += (char)c;
				}
				
				//System.out.println(pos + " : " + dist);
				if(!dist.isEmpty())
					distances.put(pos, new Double(dist));
				
			}
			
			br.close();	
			return distances;
		}

		catch (FileNotFoundException exc)
		{
			System.out.println ("File not found");
		}
		catch (IOException ioe)
		{
			System.out.println ("Erreur IO");
		}
		return null;
	}
}
