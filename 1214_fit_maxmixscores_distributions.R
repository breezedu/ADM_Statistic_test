
###########################################################################################################
##
## ADM mixscore Project
## Date 12-08-2016
## Aim: Check whether the mixscore ADM analysis results follow normal distribution
## 
## @ student: Jeff Du
## Data source: shuffled 565 individuals SNP datasets
## Models:  normal distribution
##  
###########################################################################################################

###########################################

## read in dataset (vector)
maxMixscore1 <- read.table("D:/GitHub/ADM_Statistic_Data/AdmixOut/cirShuffle_Out/max_1204mixscore_matrix.txt", header = F)

maxMixscore1 <- maxMixscore1$V1

maxMixscore2 <- read.table("D:/GitHub/ADM_Statistic_Data/AdmixOut/cirShuffle_Out/max_1130mixscore_matrix.txt", header = F)

maxMixscore2 <- maxMixscore2$V1

maxMixscore3 <- read.table("D:/GitHub/ADM_Statistic_Data/AdmixOut/cirShuffle_Out/max_1209mixscore_matrix.txt", header = F)

maxMixscore3 <- maxMixscore3$V1

maxMixscore4 <- read.table("D:/GitHub/ADM_Statistic_Data/AdmixOut/cirShuffle_Out/max_1210mixscore_matrix.txt", header = F)

maxMixscore4 <- maxMixscore4$V1

maxMixscore5 <- read.table("D:/GitHub/ADM_Statistic_Data/AdmixOut/cirShuffle_Out/max_1211mixscore_matrix.txt", header = F)

maxMixscore5 <- maxMixscore5$V1

maxMixscore6 <- read.table("D:/GitHub/ADM_Statistic_Data/AdmixOut/cirShuffle_Out/max_1212mixscore_matrix.txt", header = F)

maxMixscore6 <- maxMixscore6$V1

maxMixscore7 <- read.table("D:/GitHub/ADM_Statistic_Data/AdmixOut/cirShuffle_Out/max_12122mixscore_matrix.txt", header = F)

maxMixscore7 <- maxMixscore7$V1

summary(maxMixscore7)
summary(maxMixscore6)
summary(maxMixscore5)
summary(maxMixscore4)
summary(maxMixscore3)
summary(maxMixscore2)
summary(maxMixscore1)


## get unshuflled data mixscore result
mixUnshuffled <- read.table("D:/GitHub/ADM_Statistic_Data/shuffled_local_ancestry_out/mixscore_shuffled0.out", header = F)
mixUnshuffled <- mixUnshuffled$V1

## remove duplicated
mixUnique.unshuffled <- unique(mixUnshuffled)
sort(mixUnique.unshuffled)

#### the 2nd max is 15.9254


maxAll <- c(maxMixscore7, maxMixscore6, maxMixscore5, maxMixscore4, maxMixscore3, maxMixscore2, maxMixscore1) 



maxUnshuffle <- maxMixscore1[1] 
maxUnshuffle

length(maxAll)

sum( maxUnshuffle < maxAll)

sum( maxUnshuffle < maxAll) / (length(maxAll) - 7)


sum( maxAll > 15.9254) -7
(sum( maxAll > 15.9254) -7) / (length(maxAll)-7)



## plot a raw discret figure about the data
end <- length(maxMixscore1)
plot(x=1:end, y=maxMixscore1[1:end],
     xlab = 'Max Mixscores')
text(100, 18, labels = 'max mixscores')

quantile(maxMixscore2[1], probs =  maxMixscore2)
quantile(maxMixscore2[1], probs = seq(maxMixscore2), na.rm = FALSE,
         names = TRUE, type = 7)

sum(maxMixscore2>maxMixscore2[1]) / length(maxMixscore2)

maxScore <- c(maxMixscore2, maxMixscore1[1:end])

sum(maxMixscore2[1] < maxScore) / length(maxScore)

unshuffled <- maxMixscore2[1]
unshuffled

sum(unshuffled > maxMixscore1)

## import libraries 
 install.packages('fitdistrplus')
 install.packages('logspline')

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

