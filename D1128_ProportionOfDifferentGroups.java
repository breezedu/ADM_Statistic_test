package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/********
 * 
 * @author Jeff
 *
 * in the dataset, there are 565 columns, representing 565 individuals; 
 * from the "D:/GitHubRepositories/ADM_Statistic_Data/samples.xlsx" document,
 * we could know which individual belongs to Control or Case group;
 * 
 * In this code section, I am going to calculate the proportions of three groups: case, control, and all
 * Scan samples.xlsx, check each line, if there's "case", then that line represents the corresponding 
 * individual as a member in the case group; 
 * if there's "control", then that line represents a corresponding individual as a member in the control
 * group; 
 * 
 * We put "case", or "control" to an arrayList, following the same order as those lines; 
 *
 */
public class D1128_ProportionOfDifferentGroups {
	
	public static void main(String[] args) throws IOException{
		
		
		//1st, pass a routine and csv file name to a method, return a hashmap
		String routine =  "D:/GitHubRepositories/ADM_Statistic_Data/";
		String sample_file = "samples.csv";
		HashMap<Integer, String> ADM_hash = get_ADM_hash(routine, sample_file);
		
		
		//2nd, pass routine and matrix name to proportion_calculating() method
		routine = "D:/GitHubRepositories/ADM_Statistic_Data/shuffledBySNPs/";
		String matrix_file =""; 
		
		for(int i=1; i<10; i++){
			
			matrix_file = i + "SNP_shuffled_matrix.txt"; 
			
			proportions_Calculating(routine, matrix_file, ADM_hash); 
			
		} //end for i<10 loop; 
		
		
		
		//3rd, check the results;

		 
	}//end main()

	
	
	private static void proportions_Calculating(String routine, String file, HashMap<Integer, String> ADM_hash) throws IOException {
		// TODO Auto-generated method stub
		Scanner matrix_scan = new Scanner(new File(routine + file));
		
		File write_file = new File(routine +"proportion_" + file );
		BufferedWriter writer = new BufferedWriter(new FileWriter(write_file));
		
		//check each line of the matrix file:
		
		int number_of_case = 0;
		int number_of_control = 0;
		int total = 0;
		
		for(int i=0; i<565; i++){
			if(ADM_hash.get(i).equals("case") ) {
				
				number_of_case ++;
				total ++; 
				
			} else if(ADM_hash.get(i).equals("control")) {
				
				number_of_control ++;
				total ++; 
			
			} else {
				
				//do nothing; 
			}
			
		} //end for loop; 
		
		System.out.println("There are " + total + " in total; " + number_of_case + " cases, " + number_of_control + " controls");
		

		
		while( matrix_scan.hasNextLine() ){
			
			double proportion_case = 0;
			double proportion_control = 0;
			double proportion_all = 0;
			
			String currLine = matrix_scan.nextLine(); 
			
			for(int i=0; i<currLine.length(); i++){
				
				int snp = Integer.parseInt(currLine.charAt(i) + ""); //System.out.print(snp + " ");
				
				if( ADM_hash.get(i).equals("case") ) {
					
					proportion_case += snp; 
					proportion_all += snp; 
					
				} else if ( ADM_hash.get(i).equals("control") ) {
					
					proportion_control += snp;
					proportion_all += snp; 
				}
			
			}//end for i<currLine.length() loop;
			//System.out.println();
			
			//System.out.println("proportion total: " + proportion_all/(total * 2) + " case: " + proportion_case/(number_of_case * 2) + " control: " + proportion_control / (number_of_control * 2) );
			
			writer.write(proportion_all/(total * 2) + "\t" + proportion_case/(number_of_case * 2) + "\t" + proportion_control / (number_of_control * 2) +"\n");
			
		} //end while loop; 
		
		
		//close the scanner;
		matrix_scan.close(); 
		writer.close(); 
		
		System.out.println(" proportion " + file + " done. "); 
		
	}//end proportions_Calculating() method; 
	
	
	
	/***********
	 * 
	 * @param routine
	 * @param sample_file
	 * @return
	 * @throws FileNotFoundException
	 */
	private static HashMap<Integer, String> get_ADM_hash(String routine, String sample_file) throws FileNotFoundException {
		// TODO Auto-generated method stub
		

		
		Scanner sample_scan = new Scanner(new File(routine + sample_file));
		
		HashMap<Integer, String> sampleHash = new HashMap<Integer, String>();
		
		int lineNum = 0;
		
		while( sample_scan.hasNextLine() ){
			
			String currLine = sample_scan.nextLine();
			
			if(currLine.contains("case") || currLine.contains("Case")){
				
				//System.out.println("indiv " + lineNum + " is a case.");
				sampleHash.put(lineNum, "case");
			
			} else if( currLine.contains("Control" ) || currLine.contains("control") ){
				
				sampleHash.put(lineNum, "control");
			
			} //end if-else conditions; 
			
			lineNum ++;
		
		}//end while loop;
		
		sample_scan.close(); 
		
		return sampleHash;
	
	}//end get_ADM_hash() method; 

}//ee
