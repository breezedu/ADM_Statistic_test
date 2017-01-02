package data_manipulating;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class D1120_portationOfDataset {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		//update the file name according to the loop number i
		String path = "D:/GitHubRepositories/ADM_Statistic_Data/shuffled/";
		String file_name = "0"
				+ "matrix.txt";
		
		
		//Pass routine and file name to method: calculate012_proportion()
		for(int i=0; i<10; i++){
			
			file_name = i + "matrix.txt";
			calculate012_proportions(path, file_name);
		}
		
		/*************
		 * 
		 * 
		 	File: 0matrix.txt. There are: 341434 zeros, 212051 ones, and 11515 twos in the first 1000 lines.
			File: 0matrix.txt. There are: 332628 zeros, 216079 ones, and 16293 twos in the last 1000 lines. 
			
			File: 1matrix.txt. There are: 293598 zeros, 234958 ones, and 36444 twos in the first 1000 lines.
			File: 1matrix.txt. There are: 280261 zeros, 252614 ones, and 32125 twos in the last 1000 lines. 
			
			File: 2matrix.txt. There are: 296932 zeros, 239572 ones, and 28496 twos in the first 1000 lines.
			File: 2matrix.txt. There are: 299466 zeros, 240152 ones, and 25382 twos in the last 1000 lines. 
			
			File: 3matrix.txt. There are: 300987 zeros, 236137 ones, and 27876 twos in the first 1000 lines.
			File: 3matrix.txt. There are: 272477 zeros, 256287 ones, and 36236 twos in the last 1000 lines. 
			
			File: 4matrix.txt. There are: 275436 zeros, 257185 ones, and 32379 twos in the first 1000 lines.
			File: 4matrix.txt. There are: 273247 zeros, 261299 ones, and 30454 twos in the last 1000 lines. 
			
			File: 5matrix.txt. There are: 278815 zeros, 259923 ones, and 26262 twos in the first 1000 lines.
			File: 5matrix.txt. There are: 310151 zeros, 227670 ones, and 27179 twos in the last 1000 lines. 
			
			File: 6matrix.txt. There are: 289816 zeros, 245344 ones, and 29840 twos in the first 1000 lines.
			File: 6matrix.txt. There are: 272688 zeros, 257381 ones, and 34931 twos in the last 1000 lines. 
			
			File: 7matrix.txt. There are: 270482 zeros, 269676 ones, and 24842 twos in the first 1000 lines.
			File: 7matrix.txt. There are: 281688 zeros, 262024 ones, and 21288 twos in the last 1000 lines. 
			
			File: 8matrix.txt. There are: 299347 zeros, 236420 ones, and 29233 twos in the first 1000 lines.
			File: 8matrix.txt. There are: 279039 zeros, 260184 ones, and 25777 twos in the last 1000 lines. 
			
			File: 9matrix.txt. There are: 285624 zeros, 248925 ones, and 30451 twos in the first 1000 lines.
			File: 9matrix.txt. There are: 287400 zeros, 246883 ones, and 30717 twos in the last 1000 lines. 


		 * 
		 */
		
		//There are: 4131889 zeros. 45138703 ones and 80600308 twos.
	} // end main()

	
	/****************
	 * 
	 * @param path
	 * @param file_name
	 * @throws FileNotFoundException
	 */
	private static void calculate012_proportions(String path, String file_name) throws FileNotFoundException {
		
		// TODO Auto-generated method stub
		//create a file object
		//create a file object
		File file = new File(path + file_name);
				
		//create a file scanner:
		Scanner readin = new Scanner(new FileReader(file)); 
				
		//initial the counts for 0, 1, and 2;
		int ones = 0;
		int twos = 0;
		int zeros = 0;
				
		//use a while loop to check each line, then check each character in the line if it is '0', '1', or '2';
		int line = 0;
						
		while( line < 1000 ){
												
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
					
			line++;
					
		} //end while readin.hasnextline() loop;
		
		System.out.println("File: " + file_name + ". There are: " + zeros + " zeros, " + ones + " ones, and " + twos + " twos in the first " + line + " lines.");
		
		//read lines from 1000 to 228860
		while( line < 228860){
			readin.nextLine(); 
			line ++; 
		}
		
		
		//check the last 1000 lines:
		//reset ones, twos, and zeros;
		ones = 0;
		twos = 0;
		zeros = 0;
		
		while( readin.hasNextLine()){
			
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
					
			line++;
			
		}
		
		
		
		readin.close();
				
		System.out.println("File: " + file_name + ". There are: " + zeros + " zeros, " + ones + " ones, and " + twos + " twos in the last 1000 lines. \n");
		//There are: 4131889 zeros. 45138703 ones and 80600308 twos.
	} //end calculate012_proportions() method; 

}//ee
