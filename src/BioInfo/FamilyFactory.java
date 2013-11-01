package BioInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FamilyFactory {

	private static String dataPath = "./data/";

	static Family makeFamily(String path)
	{
		int c;
		String protName = "";
		String AAList = "";
		ArrayList<Protein> proteins;
		Protein p;
		
		int q = 0;
		int i = 0;
		
		
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
						if(q==0){
							i++;
						}
					}
					q=i;
					p = new Protein(protName, AAList);
					proteins.add(p);
					//System.out.println(p.toString());
				}
				
			}
			br.close();	
			return new Family(proteins, q);
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
