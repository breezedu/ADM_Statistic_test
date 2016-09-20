package data_manipulating;

/********************************
 * The output of LAMP-LD contains estimated local ancestries, one diploid sample per line.  
 * Encoding of local ancestry for each sample is given only as positions where ancestry changes 
 * (breakpoint) together with ancestry before 
 * (e.g. 01:1000 00:3000 11:50000 encodes local ancestry of 0/1 for all SNPs 0-999, 
 * 0/0 from SNP 1000-2999, 1/1 from SNP 3000-49999); 0-encodes European ancestry, 1- African.
 * 
 * @author Jeff
 *
 */

public class Read_in_SNPs_data {
	
	/**********
	 * We have lampld_chr1.out till lampld_chr22.out, twenty two data files intotal;
	 * 
	 * The format for each individual:
	 * 11:511 01:5063 11:6289 01:7506 00:8158 01:10387 00:12329 01:13196
	 */
	
	public static void main(String[] args){
		
		// 1st, get names for all data files: lampld_chr1.out till lampld_chr22.out;
		String[] files = new String[22];
		
		for(int i=1; i<= 22; i++){
			
			files[i-1] = "lampld_chr" + i + ".out";
		}
		
		for(int i=0; i<files.length; i++){
			System.out.println("file names: " + files[i]);
		}
		
		
		// 2nd, get directory routine: D:\GitHub\ADM_Statistic_Data
		String routine = "D:/GitHub/ADM_Statistic_Data/";
		
		
		
		
		
	} //end main()

}//ee
