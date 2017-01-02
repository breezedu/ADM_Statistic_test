package data_manipulating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*****************************
 * The goal of this code is to submit 1000 jobs to the cluster
 * 
 * @author Jeff
 *
 */
public class Step4_0102_Submit_1000_sbatch_cmd {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		
		for(int i= 0; i<1001; i++){
			
			String cmd = "sbatch sbatch_mixscoreADM.q" + i;
			
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec(cmd);
			pr.waitFor();
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while ((line=buf.readLine())!=null) {
			System.out.println(line);
			}
			
		}//end for i<1000 loop;

	} //end main()

}//ee
