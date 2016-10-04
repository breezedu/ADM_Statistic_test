package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class D1003_append_string2file {
	
	public static void main(String[] args) throws IOException{
		
		//initial read-in files, file name + file path
		String file_name =	"individual_matrix_1.txt";
		String path = "D:/GitHubRepositories/ADM_Statistic_Data/matrix/"; 
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
		
		System.out.println("Read finished. There are " + rows.length + " lines, and " + rows[0].length() + " items on each row. ");
		readin.close(); 
		
		
		String file2_name = "individual_matrix_1_inverse.txt";
		File file2 = new File(path + file2_name);
		
		//write new data file format, inverse col and row;
		BufferedWriter output = new BufferedWriter(new FileWriter(file2, true));
		
		for(int i=0; i<rows.length; i++){			
				
			output.write( rows[i] + "\n");				
			
		}
		
		

		
		output.close(); 
		
	}//end main();
	

}
