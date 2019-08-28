#!/bin/bash

#Um exemplo com vari'aveis

nomearq="/etc/passwd"
echo "Verifica a permiss~ao no arquivo de senhas"
ls -l $nomearq
echo "Descobre quantas contas existem no sistema"
wc -l $nomearq

