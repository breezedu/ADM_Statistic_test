package data_manipulating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/***********
 * 
 * @author Jeff
 *	
 * The goal of this code is to print out 1001 parameters documents
 * 
 * each time we call mixscore software to do the ADM analysis, we have to pass the parameter document to the mixscore
 * 
 * example:
 *   	CMD ./bin/mixscore ADM parameters_123.par
 * 
 * So, in this step, we will 'print' 1000 parameter documents for later stepps to use
 *  
 * each parameter document will contain 8 lines, as shown below: 
 * 
 * In the end, all these parameter documents have to be put in the directory: 
 * 		/work/AndrewGroup/ADM_test/AdmixScore/mixscore/
 *  
 */
public class Step2_0102_write_parameters_scripts {
	
	
	/*******************************************************************************
	 *  * one example 
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
		
		
		//initial routine/directory
		String routine = "D:/GitHubRepositories/ADM_Statistic_test/Parameters/";
		
		//initial 8 lines to print into the parameter document:
		String[] parameter_line = new String[8];
		
		
		for(int i=0; i<1001; i++){
			
			parameter_line[0] = "nsamples:565";
			parameter_line[1] = "nsnps:229860"; 
			
			parameter_line[2] = "genofile:nothing"; 
			parameter_line[3] = "ancfile:/work/AndrewGroup/ADM_test/ADM_Statistic_Data/CircleShuffle/" + i + "matrix.txt";
			
			parameter_line[4] = "phenofile:/work/AndrewGroup/ADM_test/AdmixScore/admix.pheno";
			parameter_line[5] = "thetafile:/work/AndrewGroup/ADM_test/AdmixScore/globalancestry.theta"; 
			parameter_line[6] = "orfile:1130_or_" + i + ".out.or";
			parameter_line[7] = "outfile:mixscore_shuffled" + i + ".out"; 			
			
			
			//initial the output file
			File output = new File(routine + "parameters_" + i + ".par");
			
			//create a buffer writer
			BufferedWriter outWriter = new BufferedWriter(new FileWriter(output));
			
			//write all perl lines to a perl script
			for(int j=0; j<8; j++){
				
				outWriter.write(parameter_line[j]  + "\n");
			}
			
			System.out.println("output file: " + i + " parameters finished.");
			outWriter.close();
		}
			
		
	}//end main()

	
} //ee
