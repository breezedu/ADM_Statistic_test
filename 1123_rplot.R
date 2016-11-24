##
## 2016-11-23 Proportion Plot
## Jeff
##

## question 1: 
## read-in data files




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

shuffled5 <- shuffled3$V1


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


##########################################################################################
##########################################################################################
### 
###   Plot the Proportion of Case, control, and total individuls. 
###
##########################################################################################
plot.func <- function(num){
  
  if(num == 0){
    main.txt <- 'Unshuffled'
  } else {
    main.txt <- 'Shuffled'
  }
  
  file.read <- paste("D:/GitHubRepositories/ADM_Statistic_Data/shuffled/proportion_",num,"matrix.txt", sep = "")
  proportion.original <- read.table(file.read, header=F)
  proportion.ori.all <- proportion.original$V1
  proportion.ori.case <- proportion.original$V2
  proportion.ori.control <- proportion.original$V3
  
  end <- length(proportion.ori.all)
  #end <- 100000
  
  plot(x = 1:end, 
       y = proportion.ori.all[1:end], 
       pch = ".",
       ylim = c(0,0.5),
       xlab = 'SNP 0:229860',
       ylab = 'proportion of Case/Control/All',
       main = main.txt,
       cex=1)
  
  points( proportion.ori.case[1:end], pch = ".", cex =1, col="red")
  points( proportion.ori.control[1:end], pch = ".", cex = 1, col = "blue")
  
  legend(x=1,y=0.5,cex=1,lty=rep(1,3),
         legend=c('total','case','control'),
  col=c('black','red','blue'))
  
} ## end plot.function;


png(file = '1123_proportion_plot1.png', width = 10, height = 10, units = 'in', res = 200)

par(mar=c(4,4,4,4))

par(mfrow = c(2, 2))

for( i in 0:3){
  plot.func(i)
}

dev.off()


png(file = '1123_proportion_plot2.png', width = 10, height = 10, units = 'in', res = 200)

par(mar=c(4,4,4,4))

par(mfrow = c(2, 2))

for( i in 4:7){
  plot.func(i)
}

dev.off()
