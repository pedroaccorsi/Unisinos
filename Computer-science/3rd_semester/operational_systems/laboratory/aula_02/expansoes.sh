#!/bin/bash

#Um exemplo com expans~oes

texto="Testando..."
echo $texto
echo "$texto"
echo '$texto'
echo `$texto`
comando=$(date)
echo $comando
echo Hoje e $comando
echo HOje e \"o dia"\ de ganhar \$\$\$
echo 'Hoje e "o dia" de ganhar $$$'
echo este e um texto que\
> ocupa duas linhas
echo seu diretorio atual e $(pwd)