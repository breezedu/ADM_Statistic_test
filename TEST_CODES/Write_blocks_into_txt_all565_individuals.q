#!/bin/sh
#SBATCH --mail-type=ALL
#SBATCH --mail-user=jeff.du@duke.edu
#SBATCH -c 4
#SBATCH --mem-per-cpu=8G
#SBATCH --nodes=1
#SBATCH --job-name=InvMatrix
java Write_blocks_into_txt_all565_individuals