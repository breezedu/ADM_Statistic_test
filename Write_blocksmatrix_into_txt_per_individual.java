package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/*************************
 * 
 * Read_in_SNPs_data.java could read-in SNPs raw data, creates snp-blocks, store them in an arraylist,
 * then we could shuffle those blocks in the arraylist
 * 
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
	

	/********************************************************
	 * the main() method
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		// 1st, get names for all data files: lampld_chr1.out till lampld_chr22.out;
		String[] files = new String[22];
		
		for(int i=1; i<= 22; i++){
			
			files[i-1] = "lampld_chr" + i + ".out";
		}
		
		
		// 2nd, get directory routine: D:\GitHub\ADM_Statistic_Data
		// String routine = "D:/GitHub/ADM_Statistic_Data/";
		
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/";
		
		
		
		
		//in this test code, we pick individual #2
		// int indiv = 1;
		
		//use a for loop to deal with all individuals
		for(int indiv = 1; indiv <566; indiv++){
			
			//initial an arraylist to store all SNP blocks:
			ArrayList<SNP_Block> temp_SNP_list = new ArrayList<SNP_Block>();
			
			
			//inner for loop to get SNP blocks from all 22 chromosomes
			for(int i=0; i<22; i++){
				
				System.out.println("Read in file: " + files[i]);
				
				temp_SNP_list = Read_in_SNPs_data.get_snp_blocks_from_one_chromosome(routine, files[i], indiv, temp_SNP_list);
				
			} //end inner for i<22 loop;
			
			
			//initial a buffered writer
			//BufferedWriter writer = null;
			String output_file = "individual_matrix_" + indiv + ".txt";
			
			File file = new File("D:/GitHubRepositories/ADM_Statistic_Data/matrix/" + output_file);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			//write each blocks, getSNP and getCount, to the txt document
			write_block_list_into_txt(writer, file, temp_SNP_list);


			//shuffle the SNP blocks within the ArrayList for 1000 time
			for(int i=0; i<1000; i++){
				
				//copy and create the orginal arrayList of SNP-blocks;
				ArrayList<SNP_Block> temp_list = new ArrayList<SNP_Block>(temp_SNP_list);
				
				//shuffle the list
				Collections.shuffle(temp_list);
				
				
				// transfer_blocks_into_matrix(temp_list);
				write_block_list_into_txt(writer, file, temp_list);
				
				
			}//end for i<1000 shuffling loop;			
			
			
			
			//close buffer_writer
			writer.close(); 
			
			
		} //end for indiv < 566 loop;		
		
		
		
	}//end main();

	
	
	/***************************
	 * 
	 * @param writer
	 * @param file
	 * @param temp_SNP_list
	 * @throws IOException
	 */
	private static void write_block_list_into_txt(BufferedWriter writer, File file, ArrayList<SNP_Block> temp_SNP_list) throws IOException {
		// TODO Auto-generated method stub
		
		for(int i=0; i<temp_SNP_list.size(); i++){
			
			/************
			 * This line only write SNP-block into txt document;
			 * 1:5 0:6 2:8 -----
			 */
			// writer.write(temp_SNP_list.get(i).getSNP() + ":" + temp_SNP_list.get(i).getCount() + "\t");
			
			
			
			for(int j=0; j<temp_SNP_list.get(i).getCount(); j++){
				
				/*************
				 * this line will write SNP matrix line to the txt document
				 * 1111100002222 ----
				 */
				writer.write(temp_SNP_list.get(i).getSNP() + "\t");
				
			}//end inner for j<block.length loop;
			
								
		}//end outer for i<temp_SNP_list.size() loop; 
		
		
		writer.write("\n");
		
	}//end of write_block_list_into_txt() method;



}//ee
