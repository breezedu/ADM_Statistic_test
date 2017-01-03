package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/***********
 * 
 * After we get the 1000 shuffled SNP datasets, 
 * Pass the datasets to mixscore for ADM analysis, 
 * We would get 1000 txt documents, which indicates 1000 vectors of 229860 mixscores. 
 * 
 * 1st, read in the 1000 mixscore ADM results, get a 229860*1000 matrix
 * 2nd, get the max value from each row
 * 3rd, save the max values into a txt document
 * 4th, use R to perform distribution simulation, see if the maxim values follow some certain distribution. 
 * 
 * @author Jeff
 *
 */
public class Step7_1222_extract_5Max_ADM_results {
	
	public static void main(String[] args) throws IOException{
		
		/****************************************************************************************/
		//step 0, define routine and file name
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/Out_results/";
		String filename[] = new String[10];
		
		filename[0] = "1130mixscore_matrix.txt";
		
		filename[1] = "1204mixscore_matrix.txt"; 
		filename[2] = "1209mixscore_matrix.txt"; 
		filename[3] = "1210mixscore_matrix.txt"; 
		filename[4] = "1211mixscore_matrix.txt"; 
		filename[5] = "1212mixscore_matrix.txt"; 
		
		filename[6] = "1213mixscore_matrix.txt"; 
		filename[7] = "1214mixscore_matrix.txt"; 
		
		filename[8] = "12133mixscore_matrix.txt"; 
		filename[9] = "12134mixscore_matrix.txt"; 
		
		
		
		for(int i=0; i<10; i++){
			get_5Max_run(routine, filename[i]); 			
		}
			
		
		
	}//end main()
	
	private static void get_5Max_run(String routine, String filename) throws IOException {
		// TODO Auto-generated method stub
		
		//step 1, read in the huge matrix
				//pass routine and file name to the get_mixscore_matrix() method, return a matrix[][]
				double matrix[][] = get_mixscore_matrix(routine, filename); 
				
		 		int row = matrix.length; 
		 		int col = matrix[0].length; 
		 		System.out.println("The dimension of the matrix is: " + row + " * " + col); 
				/****************************************************************************************/
		 		
		 		
		 		
				/****************************************************************************************/		
				//step 2, get first 5 maxim values from each column
				ArrayList<String> max5_mixscores = get_5maxMixscore( matrix );
				
				System.out.println("Print out 10 samples of  5-max values: " ); 
				for( int i=0; i<10; i++){
										
					System.out.println( max5_mixscores.get(i) );
								
				}//end for i<max_mixscores.size loop; 
				/****************************************************************************************/
				
				
				
				/****************************************************************************************/
				//step 3, save the maxium values into a txt document
				save_Matrix(max5_mixscores, routine, filename); 
				System.out.println("Step 3 done! " + filename );
				/****************************************************************************************/
		
	}

	/****************************************************************************************/
	/***********************************
	 * @param filename 
	 * @param filename2 
	 * @throws IOException 
	 * */

	private static void save_Matrix(ArrayList<String> max5_mixscores, String routine, String filename) throws IOException {
		// TODO Auto-generated method stub
		
		String outFile = "5Max_" + filename; 
		File output = new File(routine + outFile); 
		
		BufferedWriter outWriter = new BufferedWriter( new FileWriter(output)); 
		
		for(int i=0; i<max5_mixscores.size(); i++){
			
			outWriter.write(max5_mixscores.get(i) + "\n");
			
		} //end for loop; 
		
		//close buffer writer
		outWriter.close(); 
		
		
	} //end save_Matrix() method; 



	/****************************************************************************************/
	/*******************************
	 * Step 2:
	 * get first 5-max values from each column; 
	 * 2.1 get an arraylist from each column, 
	 * 2.2 remove duplicates
	 * 2.3 sort
	 * 2.4 get five largest values
	 * 
	 * suppose we get 5-max 1.1, 1.2, 1.3, 1.4, 1.5
	 * Save the first numbers as a string separated by "\t"
	 * 
	 * @param matrix
	 * @return
	 */
	private static ArrayList<String> get_5maxMixscore(double[][] matrix) {
		// TODO Auto-generated method stub
		
		ArrayList<String> retAL = new ArrayList<String>(); 
		
		int row = matrix.length; 
		int col = matrix[0].length; 
		
		for(int i=0; i<col; i++){
			
			//initial a hashset to check if a value is duplicated or not
			HashSet<Double> columHash = new HashSet<Double>();
			
			//initial an arrayList to store the Double values without duplicated values
			ArrayList<Double> columnArray = new ArrayList<Double>();
			
			for(int j=0; j<row; j++){
				
				if( !columHash.contains(matrix[j][i])){
					
					//put the new value into HashSet
					columHash.add(matrix[j][i]);
					
					//add the new value to the column-array
					columnArray.add(matrix[j][i]);
					
				}
			
			} //end for j<row loop; 
			
			
			//sort the column-array
			Collections.sort(columnArray);
			
			int last = columnArray.size();
			
			String fiveMax = columnArray.get(last-1) + "\t" + columnArray.get(last-2) + "\t" + columnArray.get(last-3) + "\t" + columnArray.get(last-4) + "\t" + columnArray.get(last-5); 
			retAL.add(fiveMax); 
			
		}//end for i<col loop; 
		
		
		//return the ArrayList of max values in each row of the matrix; 
		return retAL;
		
	}//end get_maxMixscore() method; 

	
	/****************************************************************************************/
	/******************
	 * Step 1: 
	 * @param routine
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	private static double[][] get_mixscore_matrix(String routine, String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		//create a scanner
		Scanner mixScan = new Scanner( new File(routine + filename)); 
		
		String firstLine = mixScan.nextLine(); 
		String[] firstNum = firstLine.split("\t");
		
		System.out.println("There are " + firstNum.length + " double numbers. "); 
		
		// hereby we know there must be 229860 rows, and the num of cols is firstNum.length
		double[][] retMatrix = new double[229860][firstNum.length];
		
		//assign the first row;
		for(int i=0; i<firstNum.length; i++){
			
			retMatrix[0][i] = Double.parseDouble(firstNum[i]); 
		}
		
		//assign all other rows
		int row = 1;
		
		while( mixScan.hasNextLine() ){
			
			String currLine = mixScan.nextLine(); 
			String[] currNum = currLine.split("\t");
			
			// System.out.println("There are " + currNum.length + " double numbers. "); 
									
			//assign the first row;
			for(int i=0; i<currNum.length; i++){
				
				retMatrix[row][i] = Double.parseDouble(currNum[i]);
				
			} //end for i<currNum.length loop; 
			
			row ++; 
		}
		
		//print first 100*100 matrix
		for(int i=0; i<100; i++){
			
			for( int j=0; j<100; j++){
				
				System.out.print("\t" + retMatrix[i][j]); 
				
			}
			
			System.out.println(); 
		}
		
		//close the scanner
		mixScan.close(); 
		
		//return the matrix
		return retMatrix;
		
	} //end get_mixscore_matrix() method; 
	

}//end ee
