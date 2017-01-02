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
public class Step3_1115_write_Sbatch_scripts {
	
	/********************
	 * 
	 * 
	 	#!/bin/sh
		#SBATCH --mail-type=ALL
		#SBATCH --mail-user=jeff.du@duke.edu
		#SBATCH -c 2
		#SBATCH --mem-per-cpu=5G
		#SBATCH --nodes=1
		#SBATCH --job-name=mixscore
		perl mixscoreADM2.perl /work/AndrewGroup/ADM_test/ADM_Statistic_Data/shuffled/2matrix.txt ../localancestry.anc ../admix.pheno ../globalancestry.theta
		
	 * 
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException{
		
		String[] sbatchs = new String[8];
		
		//update each line for sbatchs;
		for(int i=0; i<1001; i++){

			sbatchs[0] = "#!/bin/sh";
			sbatchs[1] = "#SBATCH --mail-type=ALL";
			sbatchs[2] = "#SBATCH --mail-user=todedo@gmail.com";
			sbatchs[3] = "#SBATCH -c 2";
			sbatchs[4] = "#SBATCH --mem-per-cpu=5G";
			sbatchs[5] = "#SBATCH --nodes=1";
			sbatchs[6] = "#SBATCH --job-name=mixs" + i + "th"; 
			
			//update the name of each job;
			//sbatchs[6] += i;
			
			sbatchs[7] = "./bin/mixscore ADM parameters_" + i + ".par";
			
			File output = new File("D:/GitHubRepositories/ADM_Statistic_test/Sbatches/sbatch_mixscoreADM.q" + i);
			
			//create a buffer writer
			BufferedWriter outWriter = new BufferedWriter(new FileWriter(output));
			
			//write all perl lines to a perl script
			for(int j=0; j<8; j++){
				
				outWriter.write(sbatchs[j]  + "\n");
			}
					
			outWriter.close();
			
			
		}
		
		
		
	}//end main()

} //ee
