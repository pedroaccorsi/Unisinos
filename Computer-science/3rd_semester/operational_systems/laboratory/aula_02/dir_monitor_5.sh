#!/bin/bash
#default MON_DIR=/home/accorsi/sis_op_lab/aula_02

function is_directory {
    if [ -d $1 ];
        then    echo "true"
        else    echo "false"
    fi    
}
 
function get_ls_from_dir {
    echo $(ls -1 $1 --ignore-backups)
}


#function should_create_bg_process {
#    if [[ ( "$1" != "$2" ) 
#        && ("$3" -lt 5) ]]; then
#       echo "true"
#        return 1
#   fi
#    echo "false"
#}
 
if [ $( is_directory $MON_DIR ) = "true" ]; then

    lv_previous_ls=$( get_ls_from_dir $MON_DIR )  
    lv_counter=0

        while [ true ]; 
            do 
            
                lv_current_ls=$( get_ls_from_dir $MON_DIR )  
                    
#               tentei encapsular esse IF, mas não consigo enviar por parâmetro o valor de $ls_current_ls, por exemplo,
#               porque ele tem espaços, daí o sistema interpreta que cada espaço seria um argumento...
#               exemplo: ls_arg = "um dois três", aí ao chamar: should_create_bg_process $ls_arg, dentro da função os parâmetros seriam:
#               $1 = um, $2 = 2, $3 = 3... aí dava ruim.
#               pensei em usar variáveis globais, daí, mas aí perde-se o propósito da função

                if [[ ( "$lv_current_ls" != "$lv_previous_ls" ) 
                    && ("$lv_counter" -lt 5) ]]; then

                        lv_counter=$lv_counter+1

#                       lv_new_instruction="$* &" it should set it to bg, but I'm not sure how to test it
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