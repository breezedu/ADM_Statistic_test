#!/bin/sh
#SBATCH --mail-type=ALL
#SBATCH --mail-user=jeff.du@duke.edu
#SBATCH -c 4
#SBATCH --mem-per-cpu=8G
#SBATCH --nodes=1
#SBATCH --job-name=ADM_1
java Write_blocksmatrix_into_txt_per_individual