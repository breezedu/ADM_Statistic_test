package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class D1003_append_string2file {
	
	
	/*********
	 * the main() method;
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		
		//first, check which individual belongs to case, and which belongs to control group; 
		String path = "D:/GitHubRepositories/ADM_Statistic_Data/matrix/"; 
		
		String[] Case_ctr = get_case_control_info(path); 
		
		
		
		for(int i=1; i<566; i++){
			
			int indiv = i;
			
			inverse_matrix(indiv, Case_ctr[indiv], path);
			
		}
		

		
	}//end main();
	
	

	/***************
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException 
	 */
	private static String[] get_case_control_info(String path) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String file_name = "samples.csv";
		//String path = "D:/GitHubRepositories/ADM_Statistic_Data/matrix/"; 
		
		String[] retStr = new String[566]; 
		
		int pivot = 1;
		
		File file = new File(path + file_name);
		Scanner readin = new Scanner(new FileReader(file)); 
		
		while(readin.hasNextLine()){
			
			String currLine = readin.nextLine(); 
			if(currLine.contains("Case")){
				
				retStr[pivot] = "case";
				System.out.println(pivot + " case " );
			
			} else {
				
				retStr[pivot] = "control"; 
				System.out.println(pivot + " control " );
				
			}
			
			pivot++ ;
			
		}//end while; 

		
		
		readin.close(); 
		return retStr;
		
	} //end get_case_control_info() method; 



	/************
	 * 
	 * @param indiv
	 * @param path 
	 * @param CaseCtr 
	 * @throws IOException
	 */
	private static void inverse_matrix(int indiv, String CasCtr, String path) throws IOException {
		// TODO Auto-generated method stub
		
		//initial read-in files, file name + file path
		String file_name =	"individual_matrix_" + indiv + ".txt";
		//String path = "D:/GitHubRepositories/ADM_Statistic_Data/matrix/"; 
		
		
		File file = new File(path + file_name);
		
		Scanner readin = new Scanner(new FileReader(file));
		
		//work on the first row:
		String first_line = readin.nextLine(); 
		String[] rows = first_line.split("\t");
		
		
		int pivot = 1;
		
		while(readin.hasNextLine()){
			System.out.println(" working on line: " + pivot); 
			pivot++; 
			
			String[] currList = readin.nextLine().split("\t");
			
			for(int i=0; i<currList.length; i++){
				
				rows[i] = rows[i] +" " + currList[i]; 
			}
			
			
			
		}//end while loop;
		
		System.out.println("Individual #" + indiv +" finished. There are " + rows.length + " lines, and " + rows[0].length() + " items on each row. ");
		readin.close(); 
		
		
		String file2_name = "individual_matrix_" + indiv + "_inverse.txt";
		File file2 = new File(path +"/" + CasCtr + "/" + file2_name);
		
		//write new data file format, inverse col and row;
		BufferedWriter output = new BufferedWriter(new FileWriter(file2, true));
		
		for(int i=0; i<rows.length; i++){			
				
			output.write( rows[i] + "\n");				
			
		}
		
		

		
		output.close(); 
		
	}
	

}
