write("", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex",append=FALSE)
resultDirectory<-"D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/data"
latexHeader <- function() {
  write("\\documentclass{article}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\title{StandardStudy}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\usepackage{amssymb}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\author{A.J.Nebro}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\begin{document}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\maketitle", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\section{Tables}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
}

latexTableHeader <- function(problem, tabularString, latexTableFirstLine) {
  write("\\begin{table}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\caption{", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write(problem, "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write(".SPREAD.}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)

  write("\\label{Table:", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write(problem, "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write(".SPREAD.}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)

  write("\\centering", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\begin{scriptsize}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\begin{tabular}{", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write(tabularString, "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write(latexTableFirstLine, "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\hline ", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
}

printTableLine <- function(indicator, algorithm1, algorithm2, i, j, problem) { 
  file1<-paste(resultDirectory, algorithm1, sep="/")
  file1<-paste(file1, problem, sep="/")
  file1<-paste(file1, indicator, sep="/")
  data1<-scan(file1)
  file2<-paste(resultDirectory, algorithm2, sep="/")
  file2<-paste(file2, problem, sep="/")
  file2<-paste(file2, indicator, sep="/")
  data2<-scan(file2)
  if (i == j) {
    write("-- ", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  }
  else if (i < j) {
    if (is.finite(wilcox.test(data1, data2)$p.value) & wilcox.test(data1, data2)$p.value <= 0.05) {
      if (median(data1) <= median(data2)) {
        write("$\\blacktriangle$", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
      }
      else {
        write("$\\triangledown$", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE) 
      }
    }
    else {
      write("--", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE) 
    }
  }
  else {
    write(" ", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  }
}

latexTableTail <- function() { 
  write("\\hline", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\end{tabular}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\end{scriptsize}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
  write("\\end{table}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
}

latexTail <- function() { 
  write("\\end{document}", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
}

### START OF SCRIPT 
# Constants
problemList <-c("DTLZ1", "DTLZ2", "DTLZ3") 
algorithmList <-c("NSGAII", "SPEA2", "EMAS") 
tabularString <-c("lcc") 
latexTableFirstLine <-c("\\hline  & SPEA2 & EMAS\\\\ ") 
indicator<-"SPREAD"

 # Step 1.  Writes the latex header
latexHeader()
tabularString <-c("| l | p{0.15cm }p{0.15cm }p{0.15cm } | p{0.15cm }p{0.15cm }p{0.15cm } | ") 

latexTableFirstLine <-c("\\hline \\multicolumn{1}{|c|}{} & \\multicolumn{3}{c|}{SPEA2} & \\multicolumn{3}{c|}{EMAS} \\\\") 

# Step 3. Problem loop 
latexTableHeader("DTLZ1 DTLZ2 DTLZ3 ", tabularString, latexTableFirstLine)

indx = 0
for (i in algorithmList) {
  if (i != "EMAS") {
    write(i , "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
    write(" & ", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)

    jndx = 0
    for (j in algorithmList) {
      for (problem in problemList) {
        if (jndx != 0) {
          if (i != j) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
          } 
          if (problem == "DTLZ3") {
            if (j == "EMAS") {
              write(" \\\\ ", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
            } 
            else {
              write(" & ", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
            }
          }
     else {
    write("&", "D:\Storage\Uni\9\Inteligencja\Projekt\results/DTLZStudy/R/SPREAD.Wilcoxon.tex", append=TRUE)
     }
        }
      }
      jndx = jndx + 1
    }
    indx = indx + 1
  }
} # for algorithm

  latexTableTail()

#Step 3. Writes the end of latex file 
latexTail()

