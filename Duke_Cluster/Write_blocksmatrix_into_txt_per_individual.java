//package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class Write_blocksmatrix_into_txt_per_individual {
	
	static Read_in_SNPs_data read_snp = new Read_in_SNPs_data();
	

	public static void main(String[] args) throws IOException{
		
		// 1st, get names for all data files: lampld_chr1.out till lampld_chr22.out;
		String[] files = new String[22];
		
		for(int i=1; i<= 22; i++){
			
			files[i-1] = "lampld_chr" + i + ".out";
		}
		
		
		// 2nd, get directory routine: D:\GitHub\ADM_Statistic_Data
		// String routine = "D:/GitHub/ADM_Statistic_Data/";
		
		String routine = "/work/AndrewGroup/ADM_test/ADM_Statistic_Data/";
		
		
		
		
		//in this test code, we pick individual #2
		// int indiv = 1;
		
		for(int indiv = 1; indiv <566; indiv++){
			
			//initial an arraylist to store all SNP blocks:
			ArrayList<SNP_Block> temp_SNP_list = new ArrayList<SNP_Block>();
			
			for(int i=0; i<22; i++){
				
				System.out.println("Read in file: " + files[i]);
				
				temp_SNP_list = Read_in_SNPs_data.get_snp_blocks_from_one_chromosome(routine, files[i], indiv, temp_SNP_list);
			}
			
			
			//initial a buffered writer
			//BufferedWriter writer = null;
			String output_file = "individual_matrix_" + indiv + ".txt";
			
			File file = new File("/work/AndrewGroup/ADM_test/ADM_Statistic_Data/matrix/" + output_file);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			//write each blocks, getSNP and getCount, to the txt document
			write_block_list_into_txt(writer, file, temp_SNP_list);


			
			for(int i=0; i<1000; i++){
				ArrayList<SNP_Block> temp_list = new ArrayList<SNP_Block>(temp_SNP_list);
				
				Collections.shuffle(temp_list);
				
				write_block_list_into_txt(writer, file, temp_list);
				
				transfer_blocks_into_matrix(temp_list);
			}
			
			
			
			
			//close buffer_writer
			writer.close(); 
			
			
		} //end for indiv < 566 loop;		
		
		
		
	}//end main();

	
	
	private static void write_block_list_into_txt(BufferedWriter writer, File file, ArrayList<SNP_Block> temp_SNP_list) throws IOException {
		// TODO Auto-generated method stub
		
		for(int i=0; i<temp_SNP_list.size(); i++){
			
			/************
			 * This line only write SNP-block into txt document;
			 * 1:5 0:6 2:8 -----
			 */
			writer.write(temp_SNP_list.get(i).getSNP() + ":" + temp_SNP_list.get(i).getCount() + "\t");
			
			
			
			for(int j=0; j<temp_SNP_list.get(i).getCount(); j++){
				
				/*************
				 * this line will write SNP matrix line to the txt document
				 * 1111100002222 ----
				 */
				writer.write(temp_SNP_list.get(i).getSNP() + "\t");
			}
		
		
		} 
		writer.write("\n");
		
	}

	private static void transfer_blocks_into_matrix( ArrayList<SNP_Block> temp_list ) {
		// TODO Auto-generated method stub
		
	}

}//ee

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

class Read_in_SNPs_data {
	
	
	
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





class SNP_Block {
	
	//private snp and count variables
	private int snp;
	private int count;
	
	//private ArrayList<Integer> snp_list; // = new ArrayList<Integer>();

	//public this.snp and this.count variables
	public SNP_Block(int snp, int count){
		super();
		
		this.snp = snp;
		this.count = count; 
		//this.snp_list = new ArrayList<Integer>();
		
	} //
	
	//get snp value, 0 means 00, 1 means 01, 2 means 11;
	public int getSNP(){
		return snp;
	}
	
	
	//get SNP counts for current block object;
	public int getCount(){
		return count;
	}
	
	//set up this.snp value;
	public void setSNP(int snp){
		this.snp = snp;
	}
	
	//set up this.count value;
	public void setCount(int count){
		this.count = count;
	}
	
}//end of SNP_Block object class;




