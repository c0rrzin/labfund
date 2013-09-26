#!/bin/bash

echo " "



echo "compiling main"
# monta o mvn para o main
java -cp MLR.jar montador.MvnAsm T5G13A03E02_main_12.asm ;

echo "compiling rotinas"
# monta o mvn para o main
java -cp MLR.jar montador.MvnAsm T5G13A03E02_rotinas_12.asm ;

echo "compiling constantes"
# monta o mvn para o main
java -cp MLR.jar montador.MvnAsm T5G13A03E02_const_12.asm ;

echo "linkando arquivos"
# linka os arquivos
java -cp MLR.jar linker.MvnLinker T5G13A03E02_main_12.mvn T5G13A03E02_rotinas_12.mvn T5G13A03E02_const_12.mvn -s final_linkado.mvn;

echo "relocando arquivos"
# reloca os arquivos
java -cp MLR.jar relocator.MvnRelocator final_linkado.mvn final_relocado.mvn 0000 000;

echo "iniciando mvn2013"
# inicia o mvn
java -jar mvn2013.jar;
