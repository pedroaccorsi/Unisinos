#!/bin/bash
#default MON_DIR=/home/accorsi/sis_op_lab/aula_02

if [ -d $MON_DIR ]; then
    lv_previous_ls=$(ls -1 $MON_DIR --ignore-backups)
    while [ true ]; 
        do 

            lv_current_ls=$(ls -1 $MON_DIR --ignore-backups)

            if [ "$lv_current_ls" != "$lv_previous_ls" ]; then

#               lv_new_instruction="$* &" it should set it to bg, but I'm not sure how to test it
                lv_new_instruction="$*"
                $lv_new_instruction 
 
            fi

            lv_previous_ls=$lv_current_ls
            sleep 2

        done
else 
    echo O parametro "$MON_DIR" nao eh um diretorio
    exit 2 > /dev/stderr
fi

