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
public class D1020_write_perl_scripts {
	
	
	/****************
	 *  * 
	 *  * @param args
	 *  
	 *  
	    #!/usr/bin/perl
		use strict;
		
		# ----- This script runs the score MIX. It assumes the genotype data is in integer format. 
		
		unless ( @ARGV == 4 )  {
		  print "usage: mixscore.perl <genotypefile> <localancfile> <phenotypefile> <globalancfile>\n";
		  exit(1);
		}
		
		my $fg = $ARGV[0];
		my $fa = $ARGV[1];
		my $fp = $ARGV[2];
		my $ft = $ARGV[3];
		
		# ----- Get number of samples 
		
		my $n1 = length(`head -n 1 $fg`)-1;
		my $n2 = length(`head -n 1 $fa`)-1;
		my $n3 = length(`head -n 1 $fp`)-1;
		
		my $s4 = `wc -l $ft`;
		$s4 =~ s/^[\s]+//;
		my @E = split(/[\s]+/,$s4);
		my $n4 = $E[0];
		
		unless ( $n2 == $n1 && $n3 == $n1 && $n4 == $n1 )  {
		  printf("error: incompatible file sizes");
		  exit(1);
		}
		my $nsamples = $n1;
		
		# ----- Get number of snps
		
		@E = split(/[\s]+/," " . `wc -l $fg`);
		$n1 = $E[1];
		
		@E = split(/[\s]+/," " . `wc -l $fa`);
		$n2 = $E[1];
		
		unless ( $n1 == $n2 )  {
		  printf("error: incompatible file sizes");
		  exit(1);
		}
		
		my $nsnps = $n1;
		
		
		open(OUT,">parameters");
		printf OUT "nsamples:$nsamples\n";
		printf OUT "nsnps:$nsnps\n";
		printf OUT "genofile:$fg\n";
		printf OUT "ancfile:$fa\n";
		printf OUT "phenofile:$fp\n";
		printf OUT "thetafile:$ft\n";
		printf OUT "outfile:mixscore_shuffle0.out\n";
		close(OUT);
		
		my $cmd = "./bin/mixscore ADM parameters";
		system($cmd);
		
		printf "Output of MIX score (%d samples, %d SNPs) is in mixscore.out\n", $nsamples, $nsnps;

	 */
	public static void main(String[] args){
		
		String[] perl = new String[40];
		
		
		for(int i=0; i<1000; i++){
			
			perl[0] = "#!/usr/bin/perl";
			perl[1] = "use strict;"; 
			
			perl[2] = "# ----- This script runs the score MIX. It assumes the genotype data is in integer format."; 
			perl[3] = "";
			
			perl[4] = "unless ( @ARGV == 4 )  {";
			perl[5] = "		print \"usage: mixscore.perl <genotypefile> <localancfile> <phenotypefile> <globalancfile>\n\";"; 
			perl[6] = "  exit(1);";
			perl[7] = "}"; 
			
			perl[8] = "my $fg = $ARGV[0];";
			perl[9] = "my $fa = $ARGV[1];";
			perl[10] = "my $fp = $ARGV[2];";
			perl[11] = "my $ft = $ARGV[3];";
			perl[12] = "";
			
			perl[13] = "# ----- Get number of samples"; 
			perl[14] = "";
			
			perl[15] = "my $n1 = length(`head -n 1 $fg`)-1;";
			perl[16] = "my $n2 = length(`head -n 1 $fa`)-1;";
			perl[17] = "my $n3 = length(`head -n 1 $fp`)-1;";
			perl[18] = "";
			
			
			perl[19] = "my $s4 = `wc -l $ft`;";
			perl[20] = "$s4 =~ s/^[\\s]+//;";		//$s4 =~ s/^[\s]+//;
			perl[21] = "my @E = split(/[\\s]+/,$s4);";
			perl[22] = "my $n4 = $E[0];";
			perl[23] = "";
			
			perl[24] = "unless ( $n2 == $n1 && $n3 == $n1 && $n4 == $n1 )  {";
			perl[25] = "  printf(\"error: incompatible file sizes\");";
			perl[26] = "  exit(1);";
			perl[27] = "}";
			perl[28] = "my $nsamples = $n1;";
			perl[29] = "";
			
			perl[30] = "# ----- Get number of snps";
			
			perl[31] = "@E = split(/[\\s]+/,\" \" . `wc -l $fg`);";
			perl[32] = "$n1 = $E[1];";
			perl[33] = "";
			
			perl[34] = "@E = split(/[\\s]+/,\" \" . `wc -l $fa`);";
			perl[35] = "$n2 = $E[1];";
			perl[36] = "";
					
			perl[37] = "unless ( $n1 == $n2 )  {";
			perl[38] = "  printf(\"error: incompatible file sizes\");";
			perl[39] = "  exit(1);";
			perl[40] = "}";
			
			perl[41] = "my $nsnps = $n1;"; 
			perl[42] = "\n";
			
			perl[43] = "open(OUT,\">parameters\");";
			perl[44] = "printf OUT \"nsamples:$nsamples\n\";";
			perl[45] = "printf OUT \"nsnps:$nsnps\n\";";
			perl[46] = "printf OUT \"genofile:$fg\n\";";
			perl[39] = "printf OUT \"ancfile:$fa\n\";";
			perl[39] = "printf OUT \"phenofile:$fp\n\";";
			perl[39] = "printf OUT \"thetafile:$ft\n\";";
			perl[39] = "printf OUT \"outfile:mixscore_shuffle0.out\n\";";
			perl[39] = "close(OUT);";
			
			perl[39] = "my $cmd = \"./bin/mixscore ADM parameters\";";
			perl[39] = "system($cmd);";
			
			perl[39] = "printf \"Output of MIX score (%d samples, %d SNPs) is in "+ i + "mixscore.out\n\", $nsamples, $nsnps;";

			
			
		}
		
	}//end main()

} //ee
