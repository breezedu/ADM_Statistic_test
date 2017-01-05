package data_manipulating;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**********************************************************************************
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
public class Step9_0105_ReadSNP_PrintChromosomeBoundaries {
	
	/*********************************************************
	 * main() 
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		// 1st, get directory routine: D:\GitHub\ADM_Statistic_Data
		// String routine = "D:/GitHub/ADM_Statistic_Data/";
		// Under this folder, there are 22 chromosome datasets.
		
		//desktop routine: D:/GitHubRepositories/ADM_Statistic_Data/
		//laptop routine: "D:/GitHub/ADM_Statistic_Data/";
		//DSCR cluster routine: "/work/AndrewGroup/ADM_test/ADM_Statistic_Data/"
		
		
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/";
		//routine = "D:/GitHub/ADM_Statistic_Data/";
		//routine = "/work/AndrewGroup/ADM_test/ADM_Statistic_Data/"; 
		
		/******************************
		 * Part I
		 * 
		 * Pass the working routine/directory to the method.
		 * 
		 * 1. create a buffer reader to read-in all SNPs.
		 * 2. for each individual, create an ArrayList<SNP_blocks>, to store all SNP-blocks.
		 * 3. create another ArrayList<> to store all 565 individuals' SNP-blocks.
		 * 
		 */
		
		Part_I_getSNPBlocks_PrintBoundaries( routine ); 
		
		
		
		/*******************************
		 * Part II
		 * 
		 * Pass the ArrayList of all 565 individuals' SNP-blocks to the method. 
		 * 
		 * 1. check Block Counts for all 565 individuals' blocks;
		 * 2. write all blocks to a txt document, here in this code, the output document: 0102_individual_blocks.txt.
		 * * 
		 */
				
		
		
		
	}//end main();
	
	
	
	/*************************
	 * Part I
	 * 
	 * 1. create a buffer reader to read-in all SNPs 
	 * 2. for each individual, create an ArrayList<SNP_blocks>, to store all SNP-blocks
	 * 3. create another ArrayList<> to store all 565 individuals' SNP-blocks
	 * * * 
	 * @param routine
	 * @throws IOException
	 */
	private static void Part_I_getSNPBlocks_PrintBoundaries(String routine) throws IOException {
		// TODO Auto-generated method stub
		// 1st, get names for all data files: lampld_chr1.out till lampld_chr22.out;
		String[] files = new String[22];
				
		for(int i=1; i<= 22; i++){
					
			files[i-1] = "lampld_chr" + i + ".out";
		}
				
				
		//for each individual, we will reformat the SNPs-blocks, and put all blocks in an ArrayList
		//here we initiate another ArrayList to store all snp-blocks list for all 565 individuals;
		ArrayList< ArrayList<SNP_Block> > all565_snp_blocks = new ArrayList<ArrayList<SNP_Block>>();
				
		for(int indiv = 1; indiv <566; indiv++){
					
			//initial an arraylist to store all SNP blocks:
			ArrayList<SNP_Block> temp_SNP_list = new ArrayList<SNP_Block>();
					
			//get all snp-blocks for current individual
			System.out.print("Individual #" + indiv + "\t"); 
			for(int i=0; i<22; i++){
						
				//System.out.println("Read in file: " + files[i]);
						
				temp_SNP_list = get_snp_blocks_from_one_chromosome(routine, files[i], indiv, temp_SNP_list);
				
				
				//get # of SNPs in the arrayList
				int num_SNPs = get_numOfSNPs(temp_SNP_list); 
				
				//System.out.print(temp_SNP_list.size() + "Blocks ");
				System.out.print(num_SNPs + " " );
				// System.out.print(temp_SNP_list.size() + "Blocks-" + num_SNPs + "SNPs\t" );
				
				
				
			}//end for i<22 loop;
			
			System.out.println(); 
					
			//put current individual's block-list in the all565_snp_blocks list;
			all565_snp_blocks.add(temp_SNP_list); 
					

					
					
			//ArrayList<SNP_Block> temp_list = new ArrayList<SNP_Block>(temp_SNP_list);			
			//Collections.shuffle(temp_list);
							
			//System.out.println("Individual #" + indiv + " done! There are " + sum_snps + " snps in total.");
					
		} //end for indiv < 566 loop;		
				
		System.out.println("There are " + all565_snp_blocks.size() + " individuals.");
			
		
	}//end part_I_get_SNPBlocks4All() method; 


	
	private static int get_numOfSNPs(ArrayList<SNP_Block> SNPBlocks) {
		// TODO Auto-generated method stub
		
		int num = 0; 
		
		for(int i=0; i<SNPBlocks.size(); i++){
			
			num += SNPBlocks.get(i).getCount(); 
		}
		
		return num;
	}//end of get_numOfSNPs() method; 



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
	static ArrayList<SNP_Block> get_snp_blocks_from_one_chromosome(	String routine, String file, int individual_num,
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
			
		/********************************************************
		 * Here, the 22 chromosome base SNP blocks data is the genotype data,
		 * to convert the genotype data into local ancestry data, we have to use 2 to minus each SNP
		 * 
		 * blocsk_Num[i] = 2 - number_List.get(i);
		 * 
		 */
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
		
	} //end get_snp_blocks_from_one_chromosome() method
	
	

	
	/***********************************
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
			snp_type = 0;
			
		} else if(snp == 0){
			snp_type = 2;
			
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
