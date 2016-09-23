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
		
		/*******************************
		 * check the file names:
		 * 
			for(int i=0; i<files.length; i++){
				System.out.println("file names: " + files[i]);
			}
		*/
		
		// 2nd, get directory routine: D:\GitHub\ADM_Statistic_Data
		// String routine = "D:/GitHub/ADM_Statistic_Data/";
		
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/";
		
		
		// 3rd, get SNPs data for individual-1, 
		// the first line in each lampld_chr*.out data file represents the SNPs blocks for that person
		// there are 565 lines for each document, which represents 565 individuals in total
		//int individual_num = 1;
		ArrayList<SNP_Block> SNP_block_list = new ArrayList<SNP_Block>();
		
		
		//send every chromosome file to get_snp_blocks_from_one_chromosome() method
		//get all the SNP blocks, save them to an ArrayList
		
		
		//for each individual	
		for(int indiv =1; indiv < 566; indiv++){
			
			SNP_block_list = new ArrayList<SNP_Block>();
			
			//for each chromosome call get_snp_blocks_from_one_chromosome() method
			//to get all SNP blocks, and put all these blocks into one arrayList
			for(int i=0; i<22; i++){
				
				SNP_block_list = get_snp_blocks_from_one_chromosome(routine, files[i], indiv, SNP_block_list);
			}
			
			// SNP_block_list = get_snp_blocks_from_one_chromosome(routine, files[i], individual_num, SNP_block_list);
			
			System.out.println("\n There are " + SNP_block_list.size() + " SNP blocks for individual#" + indiv +"\n");
			
			
			print_SNP_Blocks(SNP_block_list);
			
			
			//shuffle 100 times
			for(int i=0; i<100; i++){
				Collections.shuffle(SNP_block_list);
				print_SNP_Blocks(SNP_block_list);
			}
			
			
			/*******************
			 * Hereby, we have got all 1000 shuffled SNP-blocks for current individual;
			 * we could either printout these blocks to a txt document
			 * or perform ADM test at this point for current individual;
			 */
			
		}//enf for indiv < 566 loop;
		
		

		
		/***********************
		 * 
		 * What we got at this step:
		 * 
	The SNPs on chromosome 1 for individual #1: 
	 	 11:511 01:5063 11:6289 01:7506 00:8158 01:10387 00:12329 01:13196 11:14227 01:14621 11:16928 01:18485
	 	 
		11	511	01	5063	11	6289	01	7506	00	8158	01	10387	00	12329	01	13196	11	14227	
		01	14621	11	16928	01	18485
		
	 There are 12 blocks.
		11	511	1	4552	11	1226	1	1217	0	652	1	2229	0	1942	1	867	11	1031
		1	394	11	2307	1	1557
			
	 2:511  1:4552  2:1226  1:1217  0:652  1:2229  0:1942  1:867  2:1031  1:394  2:2307  1:1557 
	 1:2229  0:1942  2:1031  0:652  1:1557  2:2307  2:511  1:394  1:1217  2:1226  1:867  1:4552 
	 1:1557  0:1942  2:511  1:4552  1:2229  1:1217  2:2307  1:394  0:652  2:1031  1:867  2:1226 
	 2:511  1:4552  2:1031  0:652  1:867  2:1226  1:394  2:2307  0:1942  1:1557  1:2229  1:1217 
	 1:1217  1:4552  1:1557  2:1226  1:867  1:2229  0:652  0:1942  2:2307  2:1031  1:394  2:511 
	 1:394  2:511  0:1942  1:4552  1:1557  1:867  2:2307  0:652  1:1217  2:1226  2:1031  1:2229 
	 0:652  1:394  1:4552  2:511  2:2307  1:2229  1:1557  2:1031  1:1217  1:867  2:1226  0:1942 
	 1:394  1:867  0:1942  1:2229  2:511  2:1031  2:1226  1:4552  1:1217  1:1557  2:2307  0:652 
	 2:1226  1:2229  1:1557  0:1942  2:2307  1:867  1:1217  2:511  1:394  0:652  1:4552  2:1031 
	 0:1942  2:1226  1:1217  1:394  1:4552  1:1557  2:2307  2:511  1:867  2:1031  1:2229  0:652 
	 2:1031  1:4552  2:1226  0:652  1:1557  1:1217  1:2229  2:2307  1:394  1:867  2:511  0:1942 
		 * 
		 * 
		 */
		
		
	} //end main()
	
	
	
	/**************************************************************
	 * get_snp_blocks_from_one_chromosome() method
	 * 
	 * pass routine and file name to the method, as well as individual-number (1 to 565),
	 * and an arrayList of SNP_blocks; 
	 * 
	 * The method will pick the specific row of snp-block data, each row represent an individual;
	 * transfer the original block data into a list of SNP-blocks;
	 * 
	 * storeage the SNP-blocks into an ArrayList;
	 * 
	 * Return the arrayList;
	 * 
	 * @param routine
	 * @param file
	 * @param individual_num
	 * @param SNP_block_list
	 * @return
	 * @throws FileNotFoundException
	 */
	private static ArrayList<SNP_Block> get_snp_blocks_from_one_chromosome(	String routine, String file, int individual_num,
			ArrayList<SNP_Block> SNP_block_list) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		//create a scanner to read-in SNP document
		Scanner chr_1 = new Scanner(new File(routine + file));
			
		//each row represents an individual, so here individual_num indicates the individual whose data we want
		int line = 0; 
		String chr_1_SNPs = "";
		while(line < individual_num){
	
			chr_1_SNPs = chr_1.nextLine();
			line++;
		}
			
		//close the scanner;
		chr_1.close();
			
	//	System.out.println("The SNPs on " + file + " for individual #" + individual_num + " \t" + chr_1_SNPs + "\t"); 
			
		
		// Use pattern match to find all digits in the string line
		ArrayList<Integer> number_List = new ArrayList<Integer>();
			
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(chr_1_SNPs);
			
		while( m.find() ){
				
			//System.out.print("\t" + m.group() );
			//pattern match only return a string, here we have to parse string into integer;
			int num = Integer.parseInt( m.group() );
				
			number_List.add(num);
				
			} //end while loop;
			
			
			//transfer string array into number array;
			int[] blocks_Num = new int[number_List.size()];
			for(int i=0; i<blocks_Num.length; i++){
				
				blocks_Num[i] = number_List.get(i);
			}
			
			
	//		System.out.println("\t There are " + number_List.size()/2 + " blocks.");
			
			/*********
			 * in the original dataset, each pair of digits reprent the type of SNP (11, 01, 00) and the last index of that block
			 * in our model, we will shuffle the blocks, so we only need the number/count of SNPs in that block,
			 * So we use one block's last index minus it's former block' last index to get the length of current block,
			 * thus we know the count/number of SNPs in the current block. 
			 */
			for(int i=blocks_Num.length-1; i>2; i-=2){
				blocks_Num[i] = blocks_Num[i] - blocks_Num[i-2]; 
			}
			
	//		for(int i=0; i<blocks_Num.length; i++){
				
	//			System.out.print("\t" + blocks_Num[i]);
	//		} 
	//		System.out.println();
	
			
			// USE every two digits to build one SNP block, the first digit represent what kind of SNP, the second digit gives number of SNPs;
			
			//create SNP block objects
			//then put each snp block object into an arrayList
			for(int i=0; i<blocks_Num.length; i+=2){
				
				SNP_Block currBlock = creat_SNP_Blocks(blocks_Num[i], blocks_Num[i+1]);
				
				SNP_block_list.add(currBlock);
			}
			
		//return the SNP block list 
		return SNP_block_list;
	}
	
	

	/**************************************************************************
	 * a method to print out all SNP blocks in an arrayList
	 * 
	 * @param block_list
	 */
	private static void print_SNP_Blocks(ArrayList<SNP_Block> block_list) {
		// TODO Auto-generated method stub
		
		for(int i=0; i<block_list.size(); i++){
			
			System.out.print(" " + block_list.get(i).getSNP() + ":" + block_list.get(i).getCount() + "\t");
			
			// System.out.print("***");
		}
		
		System.out.println();
	}

	
	
	/**************************************************************************
	 * Create an SNP_Block object method;
	 * 
	 * @param snp
	 * @param count
	 * @return
	 */
	private static SNP_Block creat_SNP_Blocks(int snp, int count) {
		// TODO Auto-generated method stub
		
		//1st, determine SNP type, 11, 01, or 00
		int snp_type = 0;
		if(snp == 11)	{
			snp_type = 2;
			
		} else if(snp == 0){
			snp_type = 0;
			
		} else if(snp == 1){
			snp_type = 1;
			
		}
		
		//create one SNP_block object
		SNP_Block currblock = new SNP_Block(snp_type, count);
		currblock.setSNP( snp_type );
		currblock.setCount( count ); 
		
		return currblock;
	
	} // end creat_SNP_Blocks() method;

}//ee
