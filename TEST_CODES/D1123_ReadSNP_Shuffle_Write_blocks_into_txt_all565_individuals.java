package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Collections;
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
public class D1123_ReadSNP_Shuffle_Write_blocks_into_txt_all565_individuals {
	
	/*******************************************************
	 * create a read_snp object based on Read_in_SNPs_data()
	 * to run the code on duke cluster, have to put the sub-methods below the main() here
	 * so, the read_snp object is not really useful when submitting the code 'javac' to the dscr
	 * 
	 */
	static Read_in_SNPs_data read_snp = new Read_in_SNPs_data();
	
	
	/*********************************************************
	 * main() 
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
		// Under this folder, there are 22 chromosome datasets.
		
		//desktop routine: D:/GitHubRepositories/ADM_Statistic_Data/
		//laptop routine: "D:/GitHub/ADM_Statistic_Data/";
		//DSCR cluster routine: "/work/AndrewGroup/ADM_test/ADM_Statistic_Data/"
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/";
		routine = "D:/GitHub/ADM_Statistic_Data/";
		
		//3rd initial a buffered writer
		//BufferedWriter writer = null;
		String output_file = "1117_individual_blocks.txt";
		
		File file = new File(routine + output_file);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		
		//for each individual, we will reformat the SNPs-blocks, and put all blocks in an ArrayList
		//here we initiate another ArrayList to store all snp-blocks list for all 565 individuals;
		ArrayList<ArrayList<SNP_Block>> all565_snp_blocks = new ArrayList<ArrayList<SNP_Block>>();
		
		for(int indiv = 1; indiv <566; indiv++){
			
			//initial an arraylist to store all SNP blocks:
			ArrayList<SNP_Block> temp_SNP_list = new ArrayList<SNP_Block>();
			
			//get all snp-blocks for current individual
			for(int i=0; i<22; i++){
				
				//System.out.println("Read in file: " + files[i]);
				
				temp_SNP_list = get_snp_blocks_from_one_chromosome(routine, files[i], indiv, temp_SNP_list);
			
			}//end for i<22 loop;
			
			
			//put current individual's block-list in the all565_snp_blocks list;
			all565_snp_blocks.add(temp_SNP_list); 
			
			//write each blocks, getSNP and getCount, to the txt document
			write_block_list_into_txt(writer, file, temp_SNP_list);
			
			
			//ArrayList<SNP_Block> temp_list = new ArrayList<SNP_Block>(temp_SNP_list);			
			//Collections.shuffle(temp_list);
					
			//System.out.println("Individual #" + indiv + " done! There are " + sum_snps + " snps in total.");
			
		} //end for indiv < 566 loop;		
		
		System.out.println("There are " + all565_snp_blocks.size() + " individuals.");
		
		writer.close(); 
		
		//check the over all 0-blocks, 1-blocks, and 2-blocks
		check_Block_Counts(all565_snp_blocks);
		
		
		//transfer snp-blocks into 0,1, or 2 strings, and inverse the matrix, then print the inversed matrix to a txt document;
		//pass all 565 individuals' SNP blocks arraylist, shuffle order, total snp numbers, and routine;
		inverse_matrix_write2txt(all565_snp_blocks, routine, 0);
		
		
		//Repeat the inverse_matrix_write2txt() method for shuffled individuals for 10 times.
		// pass a number of shuffling times to method Shuffling_SNP_blocks()
		//Pass the arrayList of 565 individuals to a method Shuffling_SNP_blocks(), to shuffle the dataset based on SNP-blocks 
		// write the shuffled SNP blocks into a matrix, inverse the matrix and write it into a txt document;
		
		Shuffling_SNP_blocks(all565_snp_blocks, 10, routine);
		
		
		
	}//end main();
	
	
	
	/******************
	 * 
	 * @param all565_snp_blocks
	 */
	private static void check_Block_Counts(ArrayList<ArrayList<SNP_Block>> snp_blocks) {
		// TODO Auto-generated method stub
		int blocks_0 = 0;
		int blocks_1 = 0;
		int blocks_2 = 0;
		int firstblock_0 = 0;
		int firstblock_1 = 0;
		int firstblock_2 = 0;
		int lastblock_0 = 0;
		int lastblock_1 = 0;
		int lastblock_2 = 0;
		
		
		for(int i=0; i<snp_blocks.size(); i++){
			
			ArrayList<SNP_Block> temp = snp_blocks.get(i);
			
			for(int j=0; j<temp.size(); j++){
				
				switch(temp.get(j).getSNP()){
				case 0: blocks_0 ++; break;
				case 1: blocks_1 ++; break;
				case 2: blocks_2 ++; break; 
				
				}//end switch() case
				
			}//end for j<temp.size() loop;
			
			//only count the first block;
			switch(temp.get(0).getSNP()){
			
			case 0: firstblock_0 ++; break;
			case 1: firstblock_1 ++; break;
			case 2: firstblock_2 ++; break;
			
			} //end switch() case
			
			int len = temp.size(); 
			//only count the last block;
			switch(temp.get(len-1).getSNP()){
			
			case 0: lastblock_0 ++; break;
			case 1: lastblock_1 ++; break;
			case 2: lastblock_2 ++; break;
			
			} //end switch() case
			
			
		} //end for i<snp_blocks.size() loop;
		
		System.out.println("\nThe first blocks:   0-blocks, 1-blocks, 2-blocks..");
		print_ProportionsOfThree(firstblock_0, firstblock_1, firstblock_2);
		
		System.out.println("The last blocks:");
		print_ProportionsOfThree(lastblock_0, lastblock_1, lastblock_2);
	
		System.out.println("Over all blocks:");
		print_ProportionsOfThree(blocks_0, blocks_1, blocks_2);
		
	}//end check_Block_Counts() method;



	private static void print_ProportionsOfThree(int firstblock_0, int firstblock_1, int firstblock_2) {
		// TODO Auto-generated method stub
		
		System.out.println("\t\t\t" + firstblock_0 + "\t" + firstblock_1 + "\t" + firstblock_2);
		
		System.out.print("\t\t\t" );
		System.out.printf(  "%.2f", 100*(double)firstblock_0/(firstblock_0 + firstblock_1 + firstblock_2) );
		System.out.print("%\t");
		System.out.printf(	"%.2f", 100*(double)firstblock_1/(firstblock_0 + firstblock_1 + firstblock_2) );
		System.out.print("%\t");
		System.out.printf(	"%.2f", 100*(double)firstblock_2/(firstblock_0 + firstblock_1 + firstblock_2) );
		System.out.println("%");;
		
	} //end print_ProportionsOfThree() method; 



	/****************
	 * 
	 * @param all565_snp_blocks
	 * @param shuffle_times will output ## of shuffled datasets;
	 * @param routine
	 * @throws IOException
	 */
	private static void Shuffling_SNP_blocks(ArrayList<ArrayList<SNP_Block>> all565_snp_blocks, int shuffle_times, String routine) throws IOException {
		// TODO Auto-generated method stub
		
		for(int i=1; i<shuffle_times; i++){
			ArrayList<ArrayList<SNP_Block>> temp_list = new ArrayList<ArrayList<SNP_Block>>(all565_snp_blocks);
			
			ArrayList<ArrayList<SNP_Block>> new_list = new ArrayList<ArrayList<SNP_Block>>();
			
			//shuffle every individual's SNP-blocks
			for(int j=0; j<temp_list.size(); j++){

				//shuffle the SNP-blocks within a individual but leave the first and last blocks unshuffled;
				new_list.add( shuffle_middle( temp_list.get(j) ) );
			
			}
			// Collections.shuffle(temp_list);
			
			check_Block_Counts(new_list);
			
			inverse_matrix_write2txt(new_list, routine, i);
			
		} //end for i<shuffle_times loop;
		
	}//end shuffling_SNP_bocks() method; 



	/**************
	 * Only shuffle the middle part of an individual's SNP-blocks;
	 * 
	 * @param temp_list
	 * @return
	 */
	private static ArrayList<SNP_Block> shuffle_middle(ArrayList<SNP_Block> arrayList) {
		// TODO Auto-generated method stub
		//1st, create a new ArrayList;
		ArrayList<SNP_Block> new_list = new ArrayList<SNP_Block>();
		
		//2nd, get middle SNP-blocks in the original list, put them to the new list;
		for(int i=1; i<arrayList.size()-1; i++){
			
			new_list.add( arrayList.get(i) );
			
		} //end for loop;
		
		//3rd, shuffle the new list 5 times;
		Collections.shuffle(new_list);
		Collections.shuffle(new_list);
		Collections.shuffle(new_list);
		Collections.shuffle(new_list);
		Collections.shuffle(new_list);
		
		//4th, create a return list
		ArrayList<SNP_Block> ret_list = new ArrayList<SNP_Block>();
		
		//4.1, add first SNP-block to the return list;
		ret_list.add(arrayList.get(0));
		
		//4.2, add middle SNP-blocks to the return list;
		ret_list.addAll(new_list);
		
		//4.3, add the last SNP-block to the return list;
		ret_list.add( arrayList.get(arrayList.size()-1) );
		
		return ret_list;
		
	}//end shuffle_middle() method; 



	/*******************
	 * In the inversed matrix, each col represents one individual
	 * each row represents for a given index, an individual has what type of SNP
	 * 
	 * @param all565_snp_blocks
	 * @param routine
	 * @param shuffle_num
	 * @throws IOException 
	 */
	private static void inverse_matrix_write2txt( ArrayList<ArrayList<SNP_Block>> all565_snp_blocks, String routine, int shuffle_num) throws IOException {
		// TODO Auto-generated method stub
		int snps_sum = 0;
		for(int i=0; i<all565_snp_blocks.get(0).size(); i++){
			snps_sum += all565_snp_blocks.get(0).get(i).getCount();
		}
		
		int indiv_sum = all565_snp_blocks.size(); 
		
		System.out.println("Shuffle #" + shuffle_num + " done! There are " + indiv_sum + " individuals and there are " + snps_sum + " snps in total.");
		
		 
		
		//initial 565 individuals to store SNP sequence for each person;
		
		
		//create a matrix to store SNP characters for each individual, 
		//it would be a 565 * 229868, 565 individuals by 229868 SNPs
		
		int[][] indiv = new int[indiv_sum][snps_sum];
		
		for(int i=0; i<all565_snp_blocks.size(); i++){
			
			int index = 0;	
			
			for(int j=0; j<all565_snp_blocks.get(i).size(); j++){
				
				SNP_Block temp_block = all565_snp_blocks.get(i).get(j);
				
				for(int k=0; k<temp_block.getCount(); k++){
					
					indiv[i][index] = temp_block.getSNP();
					index ++;
				}
								
			}
						
		//	System.out.println("transfer snp blocks to snp sequence for individual " + i + " done.");
			
		}//end for i<all565_snp_blocks.size() loop;
		
		

		//write inversed matrix to a txt document;
		System.out.println("\t Write this group of individuals' SNP matrix to a txt document.\n");
		
		//overwrite the routine, 
		routine = routine + "shuffled/";
		
		File file = new File(routine + shuffle_num + "matrix.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		
		//write each row to the txt file;
		for(int i=0; i<snps_sum; i++){
			
			for(int j=0; j<indiv_sum; j++){
				
				writer.write(indiv[j][i] + "");
			}
			
			writer.write("\n");	
		}
		
		
		writer.close();;
		
	} //end inverse_matrix_write2txt() method;




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
	

	
	
	
	/**********
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
			writer.write(temp_SNP_list.get(i).getSNP() + ":" + temp_SNP_list.get(i).getCount() + "\t");
			
			
			/****
				for(int j=0; j<temp_SNP_list.get(i).getCount(); j++){
					
					/*************
					 * this line will write SNP matrix line to the txt document
					 * 1111100002222 ----
					 *
					writer.write(temp_SNP_list.get(i).getSNP() + "\t");
				}
		
			 */
		} 
		writer.write("\n");
		
	} //end write_block_list_into_txt() method



}//ee
