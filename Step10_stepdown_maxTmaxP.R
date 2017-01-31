##
## step 10, step-down maxT maxP analysis

## on laptop routine: D:\GitHub\ADM_Statistic_Data\AdmixOut\cirShuffle_Out
## read in matrics: 
matrix1 <- as.matrix( read.table("D:/data/ADM/max_mixscore_1.txt", header = F))
matrix2 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/1204mixscore_matrix.txt", header = F))
matrix3 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/1209mixscore_matrix.txt", header = F))
matrix4 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/1210mixscore_matrix.txt", header = F))
matrix5 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/1211mixscore_matrix.txt", header = F))
matrix6 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/1212mixscore_matrix.txt", header = F))
matrix7 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/12133mixscore_matrix.txt", header = F))
matrix8 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/12134mixscore_matrix.txt", header = F))
matrix9 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/1213mixscore_matrix.txt", header = F))
matrix10 <- as.matrix( read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/out_results/2ndMax_1214mixscore_matrix.txt", header = F))




source("https://bioconductor.org/biocLite.R")

biocLite("multtest")
biocLite("BiocGenerics")
biocLite("parallel")

require(multtest)

classlabel <- matrix1[, 1]

mt.maxT(matrix1,classlabel,test="t",side="abs",fixed.seed.sampling="y",B=10000,na=.mt.naNUM,nonpara="n")
mt.minP(X,classlabel,test="t",side="abs",fixed.seed.sampling="y",B=10000,na=.mt.naNUM,nonpara="n")


restT <- mt.maxT(matrix1, classlabel) 



rawp<-resT$rawp[order(resT$index)]
teststat<-resT$teststat[order(resT$index)]

# Plot results and compare to Bonferroni procedure
bonf<-mt.rawp2adjp(rawp, proc=c("Bonferroni"))
allp<-cbind(rawp, bonf$adjp[order(bonf$index),2], resT$adjp[order(resT$index)],resP$adjp[order(resP$index)])

mt.plot(allp, teststat, plottype="rvsa", proc=c("rawp","Bonferroni","maxT","minP"),leg=c(0.7,50),lty=1,col=1:4,lwd=2)
mt.plot(allp, teststat, plottype="pvsr", proc=c("rawp","Bonferroni","maxT","minP"),leg=c(60,0.2),lty=1,col=1:4,lwd=2)
mt.plot(allp, teststat, plottype="pvst", proc=c("rawp","Bonferroni","maxT","minP"),leg=c(-6,0.6),pch=16,col=1:4)

# Permutation adjusted p-values for minP procedure with F-statistics (like equal variance t-statistics)
mt.minP(smallgd,classlabel,test="f",fixed.seed.sampling="n")

# Note that the test statistics used in the examples below are not appropriate 
# for the Golub et al. data. The sole purpose of these examples is to 
# demonstrate the use of the mt.maxT and mt.minP functions.

# Permutation adjusted p-values for maxT procedure with paired t-statistics
classlabel<-rep(c(0,1),19)
mt.maxT(smallgd,classlabel,test="pairt")

# Permutation adjusted p-values for maxT procedure with block F-statistics
classlabel<-rep(0:18,2)
mt.maxT(smallgd,classlabel,test="blockf",side="upper")


###############################################
##
adjPvalues <- read.table("D:/data/ADM/adjustPvalues.txt", header = F)
adjPvalues <- adjPvalues$V1

adjPvalues <- unique(adjPvalues)
adjPvalues

plot(adjPvalues, 
     main = "step down maxT adjust P-values",
     xlab = "remove duplications",
     ylab = "adjust p-values"
     )
dim(matrix1)

unshuffled <- matrix1[,1]
unshuffled <- unique(unshuffled)
length(unshuffled)


