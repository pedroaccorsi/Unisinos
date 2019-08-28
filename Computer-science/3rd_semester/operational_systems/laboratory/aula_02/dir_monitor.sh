#!/bin/bash
#default MON_DIR=/home/accorsi/sis_op_lab/aula_02

if [ -d $MON_DIR ]; then
    echo O parametro "$MON_DIR" eh um diretorio
    exit 0
else 
    echo O parametro "$MON_DIR" nao eh um diretorio
    exit 2 > /dev/stderr
fi

