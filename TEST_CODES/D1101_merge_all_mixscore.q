#!/bin/sh
#SBATCH --mail-type=ALL
#SBATCH --mail-user=jeff.du@duke.edu
#SBATCH -c 2
#SBATCH --mem-per-cpu=5G
#SBATCH --nodes=1
#SBATCH --job-name=mixs0th0
perl mixscoreADM0.perl /work/AndrewGroup/ADM_test/ADM_Statistic_Data/shuffled/0matrix.txt /work/AndrewGroup/ADM_test/AdmixScore/localancestry.anc /work/AndrewGroup/ADM_test/AdmixScore/admix.pheno /work/AndrewGroup/ADM_test/AdmixScore/globalancestry.theta
