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


open(OUT,">parameters156");
printf OUT "nsamples:$nsamples\n";
printf OUT "nsnps:$nsnps\n";
printf OUT "genofile:$fg\n";
printf OUT "ancfile:$fa\n";
printf OUT "phenofile:$fp\n";
printf OUT "thetafile:$ft\n";
printf OUT "outfile:mixscore_shuffle156.out\n";
close(OUT);
my $cmd = "./bin/mixscore ADM parameters156";
system($cmd);
printf "Output of MIX score (%d samples, %d SNPs) is in mixscore_shuffle156.out\n", $nsamples, $nsnps;
