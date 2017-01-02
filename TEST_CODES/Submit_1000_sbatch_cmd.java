package data_manipulating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Submit_1000_sbatch_cmd {
	
	/**********
	 * use Runtine.getRuntime() to submit linux command to duke cluster
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		
		// here i not only represents iteration loop number, it is also part of the name of each sbatch script
		// in the /work/AndrewGroup/ADMtest on duke cluster, there are 1000 perl scripts and 1000 sbatch scripts
		// We could call sbatch script and pass parameters to the corresponding perl script:
		for(int i= 0; i<=1000; i++){
			
			// the cmd could be: sbatch sbatch_mixscoreADM.q1 till sbatch sbatch_mixscoreADM.q999
			String cmd = "sbatch sbatch_mixscoreADM.q" + i;
			
			Runtime run = Runtime.getRuntime();
			
			//submit the cmd to the Linux kernel 
			Process pr = run.exec(cmd);
			pr.waitFor();
			
			//get the feedback from Linux kernel
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while ((line=buf.readLine())!=null) {
				System.out.println(line);
			}
			
		}//end for i<1000 loop;

	} //end main()

}//ee
