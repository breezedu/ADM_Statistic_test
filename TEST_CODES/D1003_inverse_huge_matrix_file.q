#!/bin/sh
#SBATCH --mail-type=ALL
#SBATCH --mail-user=jeff.du@duke.edu
#SBATCH -c 4
#SBATCH --mem-per-cpu=8G
#SBATCH --nodes=1
#SBATCH --job-name=InvMatrix
java D1003_inverse_huge_matrix_file