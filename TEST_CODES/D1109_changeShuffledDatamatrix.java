package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class D1109_changeShuffledDatamatrix {
	
	
	
	
	
	public static void main(String[] args) throws IOException{
		
		//int shuffleNum = 2;
		
		//update the file name according to the loop number i
		String path = "D:/GitHubRepositories/ADM_Statistic_Data/shuffled/";
		
		
		// call transfer matrix method to transfer every character in the matrix 
		// '0' -> '2', '1' -> '1', and '2' -> '0'; 
		// pass the number of the individual
		for(int i=0; i<10; i++){
			
			transfer_Matrix(i, path);
		}
		
		
		

	} // end main()

	private static void transfer_Matrix(int shuffleNum, String path) throws IOException {
		// TODO Auto-generated method stub
		
		String file_name = shuffleNum + "matrix.txt";
		
		//create a file object
		File file = new File(path + file_name);
		
		System.out.println("Working on " + path + file_name);
		
		//create a file scanner:
		Scanner readin = new Scanner(new FileReader(file)); 
		
		//initial a buffered writer
		//BufferedWriter writer = null;
		String output_file = shuffleNum + "_transed_matrix.txt";
		
		File fileOut = new File(path + output_file);
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut));
		
		//initial the counts for 0, 1, and 2;
		int ones = 0;
		int twos = 0;
		int zeros = 0;
		
		//use a while loop to check each line, then check each character in the line if it is '0', '1', or '2';
		while(readin.hasNextLine()){
										
			String currStr = readin.nextLine();
			String newStr = "";
			
			//check each character
			for(int i=0; i<currStr.length(); i++){
				
				//use a switch-case loop;
				switch( currStr.charAt(i) ){
					
				case '0': zeros ++; newStr += '2'; break;
				case '1': ones ++; 	newStr += '1'; break;
				case '2': twos ++; 	newStr += '0'; break;
				
				} //end switch - case 
				
			}// end for i<currStr.length loop;			
			
			writer.write(newStr + "\n");
			
		} //end while readin.hasnextline() loop;
		
		
		//close readin and writer
		readin.close();
		writer.close(); 
		
		System.out.println("There are: " + zeros + " zeros, " + ones + " ones, and " + twos + " twos.");
		//There are: 4131889 zeros. 45138703 ones and 80600308 twos.
		
	}

}//ee
