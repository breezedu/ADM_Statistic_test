package data_manipulating;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class D1108_proportionOfDataset {
	
	/*********
	 * 
	 * scan a dataset, check how many 0s, 1s, and 2s in there.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		//update the file name according to the loop number i
		String path = "D:/GitHubRepositories/ADM_Statistic_Data/shuffled/";
		String file_name = "0"
				+ "matrix.txt";
		
		//create a file object
		File file = new File(path + file_name);
		
		//create a file scanner:
		Scanner readin = new Scanner(new FileReader(file)); 
		
		//initial the counts for 0, 1, and 2;
		int ones = 0;
		int twos = 0;
		int zeros = 0;
		
		//use a while loop to check each line, then check each character in the line if it is '0', '1', or '2';
		while(readin.hasNextLine()){
										
			String currStr = readin.nextLine();
			
			//check each character
			for(int i=0; i<currStr.length(); i++){
				
				//use a switch-case loop;
				switch( currStr.charAt(i) ){
					
				case '0': zeros ++; break;
				case '1': ones ++; 	break;
				case '2': twos ++; 	break;
				
				} //end switch - case 
				
			}// end for i<currStr.length loop;			
			
			
		} //end while readin.hasnextline() loop;
		
		readin.close();
		
		System.out.println("There are: " + zeros + " zeros, " + ones + " ones, and " + twos + " twos.");
		//There are: 4131889 zeros. 45138703 ones and 80600308 twos.
	} // end main()

}//ee
