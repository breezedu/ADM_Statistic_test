package data_manipulating;

/***********
 * 
 * @author Jeff
 *	
 * The goal of this code is to print out 1000 perl scripts 
 * each perl-script will call genotype/local/global/pheno datasets and run mixscore ADM test
 * After running the test, it will output results to shuffle001.out --- shuffle1000.out
 *  
 */
public class D1020_write_Sbatch_scripts {
	
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
	 */
	
	public static void main(String[] args){
		
		String[] sbatchs = new String[8];
		
		//update each line for sbatchs;
		for(int i=0; i<1000; i++){

			sbatchs[0] = "#!/bin/sh";
			sbatchs[1] = "#SBATCH --mail-type=ALL";
			sbatchs[2] = "#SBATCH --mail-user=jeff.du@duke.edu";
			sbatchs[3] = "#SBATCH -c 2";
			sbatchs[4] = "#SBATCH --mem-per-cpu=5G";
			sbatchs[5] = "#SBATCH --nodes=1";
			sbatchs[6] = "#SBATCH --job-name=mixscore"; 
			
			//update the name of each job;
			sbatchs[6] += i;
			
			sbatchs[7] = "perl mixscoreADM" + i + ".perl /work/AndrewGroup/ADM_test/ADM_Statistic_Data/shuffled/" + i + "matrix.txt ../localancestry.anc ../admix.pheno ../globalancestry.theta";
			
			
			
			
		}
		
		
		
	}//end main()

} //ee
