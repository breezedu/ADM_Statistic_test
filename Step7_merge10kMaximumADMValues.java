package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Step7_merge10kMaximumADMValues {
	
	public static void main(String[] args) throws IOException{
		
		String routine = "D:/GitHubRepositories/ADM_Statistic_test/Datasets4Step8/1_maximum_values/";
		
		String file_out = "max_ADM_all.txt"; 
		
		File file = new File(routine + file_out);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		for(int i=1; i<11; i++){
			
			String file_name = "max_mixscore_" + i + ".txt";
			
			Scanner readin = new Scanner(new FileReader(routine + file_name)); 
			
			//read in the first line, ADM result from unshuffled data
			readin.nextLine(); 
			
			//read in all other lines, write it into file_out document;
			System.out.println("Read in data from file " + file_name);
			int count = 0; 
			while(readin.hasNextLine()){
				
				count++;
				
				String adm_value = readin.nextLine(); 
				writer.write(adm_value);
			}
			
			System.out.println("\t There are " + count + " ADM values.");
			
			//close reader;
			readin.close(); 
		}//end for i<11 loop;
		
		writer.close(); 
	}//end main();

}//ee
