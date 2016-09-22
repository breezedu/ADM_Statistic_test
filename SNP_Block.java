package data_manipulating;

/************
 * SNP_Block class
 * @author Jeff
 * 
 * For each SNP_block object, there are two values, snp and count; 
 * snp = 0, 1, and 2, while 0 represents 00 SNP, 1 represents 01 SNP, 2 represents 11 SNP;
 * count represents the total number of current type SNPs;
 * 
 */
public class SNP_Block {
	
	//private snp and count variables
	private int snp;
	private int count;
	
	//private ArrayList<Integer> snp_list; // = new ArrayList<Integer>();

	//public this.snp and this.count variables
	public SNP_Block(int snp, int count){
		super();
		
		this.snp = snp;
		this.count = count; 
		//this.snp_list = new ArrayList<Integer>();
		
	} //
	
	//get snp value, 0 means 00, 1 means 01, 2 means 11;
	public int getSNP(){
		return snp;
	}
	
	
	//get SNP counts for current block object;
	public int getCount(){
		return count;
	}
	
	//set up this.snp value;
	public void setSNP(int snp){
		this.snp = snp;
	}
	
	//set up this.count value;
	public void setCount(int count){
		this.count = count;
	}
	
}//end of SNP_Block object class;
