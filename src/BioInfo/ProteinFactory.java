package BioInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProteinFactory {

	private static String dataPath = "./data/";

	static Protein makeProtein(String path)
	{
		int c;
		String protName = "";
		String AAList = "";
		
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(dataPath + path + ".txt"));

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
					//System.out.println(p.toString());
				}
				
			}
			br.close();	
			return new Protein(protName, AAList);
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
