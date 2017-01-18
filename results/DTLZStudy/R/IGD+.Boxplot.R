postscript("IGD+.Boxplot.eps", horizontal=FALSE, onefile=FALSE, height=8, width=12, pointsize=10)
resultDirectory<-"../data"
qIndicator <- function(indicator, problem)
{
fileNSGAII<-paste(resultDirectory, "NSGAII", sep="/")
fileNSGAII<-paste(fileNSGAII, problem, sep="/")
fileNSGAII<-paste(fileNSGAII, indicator, sep="/")
NSGAII<-scan(fileNSGAII)

fileSPEA2<-paste(resultDirectory, "SPEA2", sep="/")
fileSPEA2<-paste(fileSPEA2, problem, sep="/")
fileSPEA2<-paste(fileSPEA2, indicator, sep="/")
SPEA2<-scan(fileSPEA2)

fileEMAS<-paste(resultDirectory, "EMAS", sep="/")
fileEMAS<-paste(fileEMAS, problem, sep="/")
fileEMAS<-paste(fileEMAS, indicator, sep="/")
EMAS<-scan(fileEMAS)

algs<-c("NSGAII","SPEA2","EMAS")
boxplot(NSGAII,SPEA2,EMAS,names=algs, notch = TRUE)
titulo <-paste(indicator, problem, sep=":")
title(main=titulo)
}
par(mfrow=c(3,3))
indicator<-"IGD+"
qIndicator(indicator, "DTLZ1")
qIndicator(indicator, "DTLZ2")
qIndicator(indicator, "DTLZ3")
