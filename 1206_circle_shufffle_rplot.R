##
## 2016-11-23 Proportion Plot
## Jeff
##

## question 1: 
## read-in data files






##########################################################################################
##########################################################################################
### 
###   Plot the Proportion of Case, control, and total individuls. 
###
##########################################################################################
plot.func <- function(num){
  
  ## set up the main title txt, only the 0matrix.txt is the unshuffled dataset; 
  if(num == 0){
    main.txt <- 'Unshuffled'
  } else {
    main.txt <- 'Shuffled'
  }
  
  ## paste routine, file name to a string; 
  file.read <- paste("D:/GitHubRepositories/ADM_Statistic_Data/CircleShuffle/proportion_",num,"matrix.txt", sep = "")
  
  ## readin table, 
  ## there are 3 columns in the table, V1~total, V2~case, V3~control
  proportion.original <- read.table(file.read, header=F)
  
  proportion.ori.all <- proportion.original$V1
  proportion.ori.case <- proportion.original$V2
  proportion.ori.control <- proportion.original$V3
  
  ## set the range of x axis
  end <- length(proportion.ori.all)
  ##end <- 10000
  
  ## plot total
  plot(x = 1:end, 
       y = proportion.ori.all[1:end], 
       pch = ".",
       ylim = c(0,0.5),
       xlab = 'SNP 0:229860',
       ylab = 'proportion of Case/Control/All',
       main = main.txt,
       cex=1)
  #lines(x, y, xlim=range(x), ylim=range(y), pch=5)
  
  ## plot case, in red;
  points( proportion.ori.case[1:end], pch = ".", cex =1, col="red")
  
  ## plot control, in blue; 
  points( proportion.ori.control[1:end], pch = ".", cex = 1, col = "green")
  
  ## add legend; 
  legend(x=1,y=0.5,cex=1,lty=rep(1,3),
         legend=c('total','case','control'),
  col=c('black','red','green'))
  
} ## end plot.function;


## plot unshuffled dataset plus another 3 shuffled datasets; 
png(file = '1128_proportion_plot_circleshuffleAll.png', width = 10, height = 10, units = 'in', res = 200)

par(mar=c(4,4,4,4))

par(mfrow = c(2, 2))

for( i in 0:3){
  plot.func(i)
}

dev.off()


## plot 4 shuffled datasets; 
png(file = '1123_proportion_plot2.png', width = 10, height = 10, units = 'in', res = 200)

par(mar=c(4,4,4,4))

par(mfrow = c(2, 2))

for( i in 4:7){
  plot.func(i)
}

dev.off()


#############################################################
## 
## Plot mixscore ADM results:
## 
#############################################################


unshuffled <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/Out/mixscore_shuffle0.out", header = F)

unshuffled <- unshuffled$V1


shuffled1 <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/Out/mixscore_shuffled1.out", header = F)

shuffled1 <- shuffled1$V1


shuffled2 <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/Out/mixscore_shuffled2.out", header = F)

shuffled2 <- shuffled2$V1

shuffled3 <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/Out/mixscore_shuffled3.out", header = F)

shuffled3 <- shuffled3$V1

shuffled4 <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/Out/mixscore_shuffled4.out", header = F)

shuffled4 <- shuffled4$V1

shuffled5 <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/Out/mixscore_shuffled5.out", header = F)

shuffled5 <- shuffled5$V1


shuffled9 <- read.table("D:/GitHubRepositories/ADM_Statistic_Data/Out/mixscore_shuffled9.out", header = F)

shuffled9 <- shuffled9$V1




par(mfrow = c(1, 1))

par(mar = rep(2, 4))

pdf('shuffledandunshuffled.pdf')

## par(mfrow = c(3, 1))
plot(x=1:100000, y=unshuffled[1:100000],
     xlab = 'unshuffled')
text(1, 30, labels = 'unshuffled')

## grid.newpage()

plot(x=1:100000, y=shuffled2[1:100000],
     xlab = 'shuffled #2')
text(1, 40, labels = 'unshuffled')

## grid.newpage()

plot(x=1:100000, y=shuffled3[1:100000],
     xlab = 'shuffled #3')
text(1, 50, labels = 'unshuffled')

dev.off()

par(mfrow = c(1,1))

summary(unshuffled)
t.test(unshuffled)
unshuffled.test <- t.test(unshuffled)
unshuffled.pvalue <- unshuffled.test$p.value
unshuffled.pvalue

t.test(unshuffled, shuffled1)

t.test(unshuffled, shuffled2)

t.test(unshuffled, shuffled3)

t.test(unshuffled)
