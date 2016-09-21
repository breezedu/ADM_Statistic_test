package data_manipulating;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/********************************
 * The output of LAMP-LD contains estimated local ancestries, one diploid sample per line.  
 * Encoding of local ancestry for each sample is given only as positions where ancestry changes 
 * (breakpoint) together with ancestry before 
 * (e.g. 01:1000 00:3000 11:50000 encodes local ancestry of 0/1 for all SNPs 0-999, 
 * 0/0 from SNP 1000-2999, 1/1 from SNP 3000-49999); 0-encodes European ancestry, 1- African.
 * 
 * @author Jeff
 *
 */

public class Read_in_SNPs_data {
	
	/**********
	 * We have lampld_chr1.out till lampld_chr22.out, twenty two data files intotal;
	 * 
	 * The format for each individual:
	 * 11:511 01:5063 11:6289 01:7506 00:8158 01:10387 00:12329 01:13196
	 * @throws FileNotFoundException 
	 */
	
	public static void main(String[] args) throws FileNotFoundException{
		
		// 1st, get names for all data files: lampld_chr1.out till lampld_chr22.out;
		String[] files = new String[22];
		
		for(int i=1; i<= 22; i++){
			
			files[i-1] = "lampld_chr" + i + ".out";
		}
		
		/**
		 * check the file names:
		 * 
			for(int i=0; i<files.length; i++){
				System.out.println("file names: " + files[i]);
			}
		*/
		
		// 2nd, get directory routine: D:\GitHub\ADM_Statistic_Data
		//String routine = "D:/GitHub/ADM_Statistic_Data/";
		
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/";
		
		
		// 3rd, get SNPs data for individual-1, 
		// the first line in each lampld_chr*.out data file represents the SNPs blocks for that person
		// there are 565 lines for each document, which represents 565 individuals in total
		
		Scanner chr_1 = new Scanner(new File(routine + "lampld_chr1_test.out"));
		
		int line = 0; 
		String chr_1_SNPs = "";
		while(line < 1){

			chr_1_SNPs = chr_1.nextLine();
			line++;
		}
		
		
		chr_1.close();
		
		System.out.println("The SNPs on chromosome 1 for individual #1: \n \t" + chr_1_SNPs); 
		
		// Use pattern match to find all digits in the string line
		ArrayList<Integer> block_List = new ArrayList<Integer>();
		
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(chr_1_SNPs);
		
		while( m.find() ){
			
			System.out.print("\t" + m.group() );
			int num = Integer.parseInt( m.group() );
			
			block_List.add(num);
			
		}
		
		
		//transfer string array into number array;
		int[] blocks_Num = new int[block_List.size()];
		for(int i=0; i<blocks_Num.length; i++){
			
			blocks_Num[i] = block_List.get(i);
		}
		
		
		System.out.println("\n There are " + block_List.size()/2 + " blocks.");
		
		for(int i=3; i<blocks_Num.length; i+=2){
			blocks_Num[i] = blocks_Num[i] - blocks_Num[i-2]; 
		}
		
		for(int i=0; i<blocks_Num.length; i++){
			
			System.out.print("\t" + blocks_Num[i]);
		} 
		System.out.println();

		
		// USE every two digits to build one SNP block, the first digit represent what kind of SNP, the second digit gives number of SNPs;
		ArrayList<SNP_Block> SNP_block_list = new ArrayList<SNP_Block>();
		
		for(int i=0; i<blocks_Num.length; i+=2){
			
			SNP_Block currBlock = creat_SNP_Blocks(blocks_Num[i], blocks_Num[i+1]);
			
			SNP_block_list.add(currBlock);
		}
		
		
		print_SNP_Blocks(SNP_block_list);
		
		
		//shuffle 5 times
		for(int i=0; i<10; i++){
			Collections.shuffle(SNP_block_list);
			print_SNP_Blocks(SNP_block_list);
		}

		
	} //end main()

	private static void print_SNP_Blocks(ArrayList<SNP_Block> block_list) {
		// TODO Auto-generated method stub
		
		for(int i=0; i<block_list.size(); i++){
			
			for(int j=0; j<block_list.get(i).snp_list.size(); j++){
				
				System.out.print(" " + block_list.get(i).snp_list.get(j));
			}
			
			System.out.print("***");
		}
		
		System.out.println();
	}

	private static SNP_Block creat_SNP_Blocks(int snp, int count) {
		// TODO Auto-generated method stub
		
		//1st, determine SNP type, 11, 01, or 00
		String snp_type = "";
		if(snp == 11)	{
			snp_type = "11";
			
		} else if(snp == 0){
			snp_type = "00";
			
		} else if(snp == 1){
			snp_type = "01";
			
		}
		
		ArrayList<String> snp_list = new ArrayList<String>();
		for(int i=0; i<count; i++){
			
			String type = snp_type;
			snp_list.add(type);
		}
		
		SNP_Block currblock = new SNP_Block();
		currblock.snp = snp_type;
		currblock.snp_list = snp_list;
		
		return currblock;
	}

}//ee
