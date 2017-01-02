package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/*****************************
 * Step 5. Merge All MixScore outputs into one matrix
 * 
 * Step 1 would generate 1000 txt documents, each txt represents a matrix (229860*565) of shuffled SNPs
 * After conducting MixScore ADM analysis, following Step 2, 3, and 4, we will get 1000 *.out ADM results
 * in each *.out document, there's a vector with length 229860. 
 * 
 * In this step, we will merge all 1000 mixscore outputs into one huge matrix (229860*1000)
 *  
 * @author Jeff
 *
 */
public class Step5_0102_merge_all_mixscore_outs {
	
	/*********************************************************
	 * 
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		//initial path/routine, 
		String path = "D:/GitHubRepositories/ADM_Statistic_Data/Out/"; 		//this is the routine on desktop;
		path = "/work/AndrewGroup/ADM_test/AdmixScore/mixscore-1.3/";		//this is the routine on duke cluster; 

		
		/*********************
		 * there are 1001 data files: mixscore_shuffle0.out --- mixscore_shuffle1000.out
		 * in each file, there are 229860 lines, each line contains a double value of mixscore result for that SNP position.
		 * 
		 * So, we initial a 229860*1001 matrix at the beginning
		 * 
		 */		
		Double[][] mixScores = new Double[229860][1001];
		
		
		// read in a *.out document, update the corresponding column in the matrix[][];
		for(int i=0; i<1001; i++){
			
			//update the file name according to the loop number i
			String file_name = "mixscore_shuffled" + i + ".out";
			
			//create a file object
			File file = new File(path + file_name);
			
			//create a file scanner:
			Scanner readin = new Scanner(new FileReader(file)); 
			
			int index = 0;
			
			while(readin.hasNextLine()){
											
				mixScores[index][i] = Double.parseDouble( readin.nextLine() );
				
				index ++;
				
			} //end while readin.hasnextline() loop;
			
			System.out.println("datafile " + file_name + " done." + index + " rows");			
			
			//close the scanner;
			readin.close();
			
		}//end for i<1001 loop;
		
		
		System.out.println("mixScore matrix done!.");
		
		
		/**************
		 * The second part
		 * inverse the matrix, and write it into a txt document
		 * 
		 */
		//initial a buffered writer
		//BufferedWriter writer = null;
		String output_file = "0102mixscore_matrix.txt";
		
		File file = new File(path + output_file);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		//write the matrix into document in an inversed way.
		for(int i=0; i<229860; i++){
			
			for(int j=0; j<1000; j++){
				
				writer.write( mixScores[i][j] + "\t");
				
			}
			
			writer.write( mixScores[i][1000] + "\n");
			
		}
		
		//close the writer;
		writer.close();
		System.out.println("Writing done!");
		
	} //end main()

} //ee
