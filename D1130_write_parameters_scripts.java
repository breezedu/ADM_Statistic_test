package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/***********
 * 
 * @author Jeff
 *	
 * The goal of this code is to print out 1000 perl scripts 
 * each perl-script will call genotype/local/global/pheno datasets and run mixscore ADM test
 * After running the test, it will output results to shuffle001.out --- shuffle1000.out
 *  
 */
public class D1130_write_parameters_scripts {
	
	
	/****************
	 *  * 
	 *  * @param args
	 *  
	 *  nsamples:565
     *	nsnps:229860
	 *	genofile:nothing
	 *	ancfile:/work/AndrewGroup/ADM_test/ADM_Statistic_Data/shuffled/0matrix.txt
	 *	phenofile:/work/AndrewGroup/ADM_test/AdmixScore/admix.pheno
	 *	thetafile:/work/AndrewGroup/ADM_test/AdmixScore/globalancestry.theta
	 *	orfile:1106or0.out
	 *	outfile:mixscore_shuffled000.out
	 *	
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
		String[] perl = new String[8];
		
		
		for(int i=0; i<1001; i++){
			
			perl[0] = "nsamples:565";
			perl[1] = "nsnps:229860"; 
			
			perl[2] = "genofile:nothing"; 
			perl[3] = "ancfile:/work/AndrewGroup/ADM_test/ADM_Statistic_Data/CircleShuffle/" + i + "matrix.txt";
			
			perl[4] = "phenofile:/work/AndrewGroup/ADM_test/AdmixScore/admix.pheno";
			perl[5] = "thetafile:/work/AndrewGroup/ADM_test/AdmixScore/globalancestry.theta"; 
			perl[6] = "orfile:1130_or_" + i + ".out.or";
			perl[7] = "outfile:mixscore_shuffled" + i + ".out"; 			
			
			
			//initial the output file
			File output = new File("D:/GitHubRepositories/ADM_Statistic_test/Parameters/parameters_" + i + ".par");
			
			//create a buffer writer
			BufferedWriter outWriter = new BufferedWriter(new FileWriter(output));
			
			//write all perl lines to a perl script
			for(int j=0; j<8; j++){
				
				outWriter.write(perl[j]  + "\n");
			}
			
			System.out.println("output file: " + i + " parameters finished.");
			outWriter.close();
		}
		
		
		
	}//end main()

} //ee
