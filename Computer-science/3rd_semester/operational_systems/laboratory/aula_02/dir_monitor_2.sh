#!/bin/bash
#default MON_DIR=/home/accorsi/sis_op_lab/aula_02

if [ -d $MON_DIR ]; then
    while true; 
        do 
            sleep 2
            ls -1 "$MON_DIR" --ignore-backups
            echo -e '\n'
        done
else 
    echo O parametro "$MON_DIR" nao eh um diretorio
    exit 2 > /dev/stderr
fi

