








###########################################

maxMixscore2 <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/max_1204mixscore_matrix.txt", header = F)

maxMixscore2 <- maxMixscore2$V1

end <- length(maxMixscore1)

plot(x=1:end, y=maxMixscore1[1:end],
     xlab = 'Max Mixscores')
text(100, 18, labels = 'max mixscores')


## install.packages('fitdistrplus')
## install.packages('logspline')

library(fitdistrplus)
library(logspline)

descdist(maxMixscore2, discrete = F)


## fit a Weibull distribution
fit.weibull <- fitdist(maxMixscore2, "weibull")

## fit a normal distribution
fit.norm <- fitdist(maxMixscore2, "norm")

fit.logistic <- fitdist(maxMixscore2, "lnorm")

fit.gamma <- fitdist(maxMixscore2, "gamma")


############################
## plot fits
png(file = '1208_fit_maxmixscore2norm.png', width = 10, height = 10, units = 'in', res = 200)
plot(fit.norm)
text(0.5, 0.5, labels = 'Normal')
dev.off()
# text(1, 1, labels = 'normal')

png(file = '1208_fit_maxmixscore2weibull.png', width = 10, height = 10, units = 'in', res = 200)
plot(fit.weibull)
text(0.5, 0.5, labels = 'Weibull')
dev.off()


png(file = '1208_fit_maxmixscore2logistic.png', width = 10, height = 10, units = 'in', res = 200)
plot(fit.logistic)
text(0.5, 0.5, labels = 'Log Normal')
dev.off()

png(file = '1208_fit_maxmixscore2gama.png', width = 10, height = 10, units = 'in', res = 200)
plot(fit.gamma)
text(0.5, 0.5, labels = 'Gamma')
dev.off()
# text(0, 1, labels = 'gamma')

fit.norm$aic
fit.logistic$aic
fit.weibull$aic 
fit.gamma$aic







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

