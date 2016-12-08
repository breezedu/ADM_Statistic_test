package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
public class Step6_1208_extract_Max_ADM_results {
	
	public static void main(String[] args) throws IOException{
		
		/****************************************************************************************/
		//step 1, read in the huge matrix
		String routine = "D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/";
		String filename = "1130mixscore_matrix.txt";
		
		filename = "1204mixscore_matrix.txt"; 
		
		double matrix[][] = get_mixscore_matrix(routine, filename); 
		
 		int row = matrix.length; 
 		int col = matrix[0].length; 
 		System.out.println("The dimension of the matrix is: " + row + " * " + col); 
		/****************************************************************************************/
 		
 		
 		
		/****************************************************************************************/		
		//step 2, check the maxim value from each colum
		ArrayList<Double> max_mixscores = get_maxMixscore( matrix ); 
		System.out.println("Print out the max values: " ); 
		for( int i=0; i<max_mixscores.size(); i++){
			
			
			if( i%100 ==0){
				
				System.out.println(); 
			}
			
			System.out.print("\t" + max_mixscores.get(i));
						
		}//end for i<max_mixscores.size loop; 
		/****************************************************************************************/
		
		
		
		/****************************************************************************************/
		//step 3, save the maxium values into a txt document
		save_Matrix(max_mixscores, routine, filename); 
		/****************************************************************************************/
		
		
	}//end main()
	
	/****************************************************************************************/
	/***********************************
	 * @param filename 
	 * @param filename2 
	 * @throws IOException 
	 * */

	private static void save_Matrix(ArrayList<Double> max_mixscores, String routine, String filename) throws IOException {
		// TODO Auto-generated method stub
		
		String outFile = "max_" + filename; 
		File output = new File(routine + outFile); 
		
		BufferedWriter outWriter = new BufferedWriter( new FileWriter(output)); 
		
		for(int i=0; i<max_mixscores.size(); i++){
			
			outWriter.write(max_mixscores.get(i) + "\n");
			
		} //end for loop; 
		
		//close buffer writer
		outWriter.close(); 
		
		
	} //end save_Matrix() method; 



	/****************************************************************************************/
	/*******************************
	 * Step 2:
	 * @param matrix
	 * @return
	 */
	private static ArrayList<Double> get_maxMixscore(double[][] matrix) {
		// TODO Auto-generated method stub
		
		ArrayList<Double> retAL = new ArrayList<Double>(); 
		
		int row = matrix.length; 
		int col = matrix[0].length; 
		
		for(int i=0; i<col; i++){
			
			double max = 0; 
			
			for(int j=0; j<row; j++){
				
				if( matrix[j][i] > max){
					
					max = matrix[j][i]; 
				}
			
			} //end for j<row loop; 
			
			retAL.add(max); 
			
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
