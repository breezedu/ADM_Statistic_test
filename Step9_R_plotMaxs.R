###########################################################################################################
##
## ADM mixscore Project
## Date 01-05-2017
## Aim: get the indices of maxium values
## 
## @ student: Jeff Du
## Data source: shuffled 565 individuals SNP datasets
## Models:  normal distribution
##  
###########################################################################################################

###########################################

## read in dataset (vector)

## the folder where I put all the mixscore max vectors
routine <- 'D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/Out_results/'

## read in the table and convert it into a matrix
## The dimension of the matrix is 229860*1001, each column represents a mixscore result of
## one shuffled dataset
## the first column is the mixscore result from unshuffled dataset;

matrix1 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/Out_results/1130mixscore_matrix.txt", header = F))

## get row and col
row <- dim(matrix1)[1]
col <- dim(matrix1)[2]

## plot the first max peak(s) for the unshuffled data
unshuffled <- matrix1[, 1]
max <- max(unshuffled) 
max
x = which(unshuffled == max) 
y = rep(max, length(x))


#### Plot the max distribution for unshuffled data only
plot(x, y,
     xlim = c(87300, 87600),      #chromosome #6
     ylim = c(0, 25), 
     main = "Max distribution for unshuffled data",
     xlab = 'Partical Range of SNPs on Chromosome #6', 
     ylab = 'Max values of ADM result'
     )



jpeg(file = 'MaxDist_1001Datasets.jpeg', width = 20, height = 10, units = 'in', res = 200)
#plot()
plot(x, y, 
     xlim =c(1,229860), 
     ylim = c(0, 25), 
     main = "All 1001 Datasets MixScore ADM",  
     xlab = 'Range of SNPs', 
     ylab = 'Max Values of MixScore ADM results' 
     )

## plot other maxs from shuffled datasets, use point() function. 

for( i in 2:col){
  
  shuffled <- matrix1[, i]
  max.sh <- max(shuffled)
  
  x.sh = which((shuffled == max.sh))
  y.sh = rep(max.sh, length(x.sh))
  
  points(x.sh, y.sh)
  
}


# chromosome end points: 18485SNPs 37290SNPs 53314SNPs 68093SNPs 82529SNPs 98885SNPs 111615SNPs 124168SNPs 134591SNPs 146469SNPs 157760SNPs 168980SNPs 177588SNPs 185202SNPs 192232SNPs 199495SNPs 205878SNPs 212743SNPs 217493SNPs 223428SNPs 226673SNPs 229860SNPs 
# 18485 37290 53314 68093 82529 98885 111615 124168 134591 146469 157760 168980 177588 185202 192232 199495 205878 212743 217493 223428 226673 229860 
## copy paste chromosome end from Java output:
chromosome.end = c(18485, 37290, 53314, 68093, 82529, 98885, 111615, 124168, 134591, 146469, 157760, 
                   168980, 177588, 185202, 192232, 199495, 205878, 212743, 217493, 223428, 226673, 229860 )

## initial chromosome start 
chromosome.start = c(1)

## each chromosome start index is the next SNP of last chromosome's last SNP; 
for(i in 2:length( chromosome.end) ){
  chromosome.start = c(chromosome.start, 1+chromosome.end[i-1])
}

## print and check start and end point of each chromosome
chromosome.start
chromosome.end

## plot chromosome #1 - #22, add albine line to split chromosomes
for(chrom in 1:22){
  
  x.chrom = chromosome.start[chrom]:chromosome.end[chrom]
  color <- "red"
  
  if(chrom %% 2 == 0){
    
      y.chrom = rep(1, length(x.chrom) )
      color <- "blue"
  
  } else {
      y.chrom = rep(2, length(x.chrom))
    } 
  
  points(x.chrom, y.chrom, col = color)
  abline(v=chromosome.end[chrom], col="blue")
  
  text(mean(x.chrom), mean(y.chrom+0.5), paste("chr#", chrom),
       cex = .8)
  
}
abline(v=1, col="blue")

dev.off()

### end of plot. 


##########################################################################################
# Plotting snp-blocks
##########################################################################################



createSNPs <- function(seed){
  
  set.seed(seed + 123)
  snps.type <- sample(1:3, 10, replace = T)
  snps.type
  
  seq <- c(10:20)
  seq <- seq*80
  
  snps.length <- sample(seq, 10, replace = T)
  snps.length
  
  ## creat a sequence of SNPs
  snps.seq <- NULL
  
  for( i in 1:10){
    
    for(j in 0:snps.length[i]){
      
      snps.seq <- c(snps.seq, snps.type[i])
      
    }
    
    
  } ## end for i in 1:10
  
  snps.seq <- snps.seq[1:10000]
  snps.seq
  
  return(snps.seq)
}


plot(x = (1:10000), 
     y = rep(0, 10000), 
     ylim = c(0,11), 
     col = "white", 
     main = "Align first 10000 SNPs for 10 people" , 
     xlab = "SNP-blocks", 
     ylab = "Individuals"
     ) 



for(i in 1:10){
  
  snpsequence <- createSNPs(i)
  
  for(j in 1:10000){
    
    color = "green"
    
    if(snpsequence[j] == 1){
      
      color = "blue"
      
    } else if(snpsequence[j] == 2){
      
      color = "red"
    }
    
      points(x = j, y = i, col = color)
      
  } ## end for j in 1:10000
  
} ## end for i in 1:10


