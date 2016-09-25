package data_manipulating;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/*************************
 * 
 * Read_in_SNPs_data.java could read-in SNPs raw data
 * After shuffling, we could get 1000 re-arranged SNPs blocks for each individual
 * 
 * In this code section, I am trying to print out this blocks into SNP matrix
 * for example
 * 1:5 0:3 2:6 -->> 11111 000 222222
 * 2:6 1:5 0:3 -->> 666666 11111 000
 * 
 * @author Jeff
 *
 */
public class Print_blocks_per_individual {
	
	static Read_in_SNPs_data read_snp = new Read_in_SNPs_data();
	
	public static void main(String[] args) throws FileNotFoundException{
		
		// 1st, get names for all data files: lampld_chr1.out till lampld_chr22.out;
		String[] files = new String[22];
		
		for(int i=1; i<= 22; i++){
			
			files[i-1] = "lampld_chr" + i + ".out";
		}
		
		
		// 2nd, get directory routine: D:\GitHub\ADM_Statistic_Data
		// String routine = "D:/GitHub/ADM_Statistic_Data/";
		
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/";
		
		ArrayList<SNP_Block> tem_SNP_list = new ArrayList<SNP_Block>();
		
		tem_SNP_list = Read_in_SNPs_data.get_snp_blocks_from_one_chromosome(routine, files[0], 1, tem_SNP_list);
		
		
		
	}//end main();

}//ee
