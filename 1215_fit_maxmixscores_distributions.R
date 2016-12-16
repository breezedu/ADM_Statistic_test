
###########################################################################################################
##
## ADM mixscore Project
## Date 12-15-2016
## Aim: Check whether the mixscore ADM analysis results follow normal distribution
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

## there are 13 max mixscore ADM vectors, each contains 1001 max values of mixscores, the first one is the unshuffled dataset's max value
max.vectors <- rep(NA, 13)


### get the max ADM mixscore value from unshuffled dataset
### the first colum represent result from the unshuffled dataset
test.vectors <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/Out_results/max_mixscore_1.txt", header = F)
test.vector <- test.vectors$V1

# get the first max value
max.unshuffled <- test.vector[1]

# print check
max.unshuffled


## initial an empty vector, to store all max values from 10,000 datasets of shuffled datasets.
vector.all <- NULL


#######################################################
## use a for loop, to get all datasets,
## read in each datasets as a table, 
## get the first colum 'V1', which is a vector
## combine the current vector with vector.all
## check the length and summary of the vector.all
#######################################################
for( i in 1:10){
  
  ##// use paste() method to get routines for all 10 files
  file.name <- paste(routine, 'max_mixscore_', i, '.txt', sep = '')
  
  ## check the filr name
  print(file.name)
  
  ## read in table, then pick the first colum V1
  max.vectors <- read.table(file.name, header = F )
  
  print( summary(max.vectors) )
  print( sum(max.vectors > max.unshuffled))
  
  vector.all <- c(vector.all, max.vectors$V1)
}

## check the length of the max.values.vector
length(vector.all)

## summary
summary(vector.all)

## 
sum(vector.all > max.unshuffled)

## over all 10,000 shuffled datasets, there are 179 max values greater than the max mixscore value of unshuffled dataset

## check the percentile of maxium mixscore ADM results greater than that of the unshuffled dataset 
sum(vector.all > max.unshuffled) / (length(vector.all) - 10)


####################
##  [1] 0.0179    ##
####################
















## extract vectors from tables
for( i in 1:12){
  
  max.vectors[i] <- max.vectors[i]$V1
  
  print( head(max.vectors[i]) )
  
}


max.vectors[4]

vector.all <- NULL
for(i in 1:12){
  
  print(summary(max.vectors[i]))
  
}

length(vector.all)


summary(vector.all)

length(max.vectors[2])
file.name <- paste(routine, 'max_mixscore_', 2, '.txt', sep = "")

table.2 <- read.table(file.name, header = F)

vec.v <- table.2$V1


maxMixscore2 <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/max_1204mixscore_matrix.txt", header = F)

maxMixscore2 <- maxMixscore2$V1


## plot a raw discret figure about the data
end <- length(maxMixscore1)
plot(x=1:end, y=maxMixscore1[1:end],
     xlab = 'Max Mixscores')
text(100, 18, labels = 'max mixscores')


## import libraries 
## install.packages('fitdistrplus')
## install.packages('logspline')

library(fitdistrplus)
library(logspline)


## Computes descriptive parameters of an empirical distribution for non-censored data and provides a skewness-kurtosis plot.
descdist(maxMixscore2, discrete = F)


########################################
## fit to normal distribution
fit.norm <- fitdist(maxMixscore2, "norm")

## plot fit to normal distribution
png(file = '1208_fit_maxmixscore2norm.png', width = 10, height = 10, units = 'in', res = 200)
plot(fit.norm)
text(0.5, 0.5, labels = 'Normal')
dev.off()
# text(1, 1, labels = 'normal')


fit.norm$aic



########################################
## fit a Weibull distribution
fit.weibull <- fitdist(maxMixscore2, "weibull")

## plot weibull distribution
png(file = '1208_fit_maxmixscore2weibull.png', width = 10, height = 10, units = 'in', res = 200)
plot(fit.weibull)
text(0.5, 0.5, labels = 'Weibull')
dev.off()

fit.weibull$aic 




########################################
fit.logistic <- fitdist(maxMixscore2, "lnorm")

## plot
png(file = '1208_fit_maxmixscore2logistic.png', width = 10, height = 10, units = 'in', res = 200)
plot(fit.logistic)
text(0.5, 0.5, labels = 'Log Normal')
dev.off()


fit.logistic$aic

########################################
fit.gamma <- fitdist(maxMixscore2, "gamma")

## plot gamma distribution
png(file = '1208_fit_maxmixscore2gama.png', width = 10, height = 10, units = 'in', res = 200)
plot(fit.gamma)
text(0.5, 0.5, labels = 'Gamma')
dev.off()
# text(0, 1, labels = 'gamma')


fit.gamma$aic

############################










###############################################################################

## simulate the KS-statistic under the null
n.sims <- 229860

stats <- replicate(n.sims, {      
  r <- rweibull(n = length(x)
                , shape= fit.weibull$estimate["shape"]
                , scale = fit.weibull$estimate["scale"]
  )
  as.numeric(ks.test(r
                     , "pweibull"
                     , shape= fit.weibull$estimate["shape"]
                     , scale = fit.weibull$estimate["scale"])$statistic
  )      
})


## ECDF of the simulated KS-statistics
plot(ecdf(stats), las = 1, main = "KS-test statistic simulation (CDF)", col = "darkorange", lwd = 1.7)
grid()











#####################################################################

maxmix <- c(maxMixscore2, maxMixscore1[2:1001])

length(maxmix)

descdist(maxmix, discrete = F)


## fit a Weibull distribution
fit.weibull <- fitdist(maxmix, "weibull")

## fit a normal distribution
fit.norm <- fitdist(maxmix, "norm")

fit.logistic <- fitdist(maxmix, "lnorm")

fit.gamma <- fitdist(maxmix, "gamma")


############################
## plot fits

plot(fit.norm)
# text(1, 1, labels = 'normal')

plot(fit.weibull)

plot(fit.logistic)

plot(fit.gamma)
# text(0, 1, labels = 'gamma')

fit.norm$aic
fit.norm$sd 
fit.weibull$aic 



##########################################
set.seed(1234)

sample.mean <- mean(maxMixscore1)
sample.sd <- sd(maxMixscore1)

simu.data <- rnorm(2000, sample.mean, sample.sd)

descdist(simu.data, discrete = F)
no.fit <- fitdist(simu.data, "norm")
plot(no.fit)

require(MASS)

fits <- list(
  no = fitdistr(simu.data, "normal"),
  lo = fitdistr(simu.data, "log Normal"),
  ca = fitdistr(simu.data, "cauchy"),
  we = fitdistr(simu.data, "weibull")
)

sapply(fits, function(i) i$loglik)


fits.maxMixscore <- list(
  no = fitdistr(maxMixscore1, "normal"),
  lo = fitdistr(maxMixscore1, "logistic"),
  ca = fitdistr(maxMixscore1, "cauchy"),
  we = fitdistr(maxMixscore1, "weibull")
)

sapply(fits.maxMixscore, function(i) i$loglik)

