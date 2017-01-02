package data_manipulating;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/***********
 * Looks like the shuffles are more extreme than the actual data…which is surprising. 
 * Can you compute the ancestry proportion for each sample for a few datasets?
 * 
 * @author Jeff
 *
 */
public class D1120_Ancestry_proportion {
	
	public static void main(String[] args) throws IOException{
	
		/***********
		 * 1st, read in local ancestry dataet and one of the shuffled dataset;
		 * 
		 */
		
		//define routine
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/shuffled/";
		
		//define the name of unshuffled dataset
		String unshuffled = "0matrix.txt";
		
		//define the name of shuffled dataset #1
		String shuffled1 = "1matrix.txt";
		
		Scanner scan_unshuffled = new Scanner(new File(routine + unshuffled));

		Scanner scan_shuffled = new Scanner(new File(routine + shuffled1));
		
		double all_snpCount = 0;
		int match = 0;
		
		while(scan_unshuffled.hasNextLine()){
			
			String line_unshuffled = scan_unshuffled.nextLine();
			String line_shuffled = scan_shuffled.nextLine();
			
			for(int i=0; i<line_unshuffled.length(); i++){
				
				if( line_unshuffled.charAt(i) == line_shuffled.charAt(i)){
					match++;
				} 
				
				all_snpCount++;
			}//end for i<line.length;
		
		}//end while;
		
		
		scan_unshuffled.close();
		scan_shuffled.close();
		
		System.out.println("There are " + match + " matched SNPs among all " + all_snpCount + " SNPs. " + (double) match/all_snpCount + ". ");
		
	}//end main()

}//ee
