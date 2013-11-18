#!/bin/bash

echo " "



echo "compiling MAIN"
# monta o mvn para o main
java -cp MLR.jar montador.MvnAsm T5G13A10E01_main.asm ;

echo "compiling BATCH"
# monta o mvn para o batch
java -cp MLR.jar montador.MvnAsm T5G13A10E01_batch.asm ;

echo "compiling DUMPER"
# monta o mvn para o dumper
java -cp MLR.jar montador.MvnAsm T5G13A10E01_dumper.asm ;

echo "compiling LOADER"
# monta o mvn para o loader
java -cp MLR.jar montador.MvnAsm T5G13A10E01_loader.asm ;

echo "compiling ROTINAS"
#monta o mvn para rotinas
java -cp MLR.jar montador.MvnAsm T5G13A10E01_rotinas.asm;

echo "linkando arquivos"
# linka os arquivos
java -cp MLR.jar linker.MvnLinker T5G13A10E01_main.mvn T5G13A10E01_batch.mvn T5G13A10E01_dumper.mvn T5G13A10E01_loader.mvn T5G13A10E01_rotinas.mvn -s final_linkado.mvn;

echo "relocando arquivos"
# reloca os arquivos
java -cp MLR.jar relocator.MvnRelocator final_linkado.mvn final_relocado.mvn 0000 000;

echo "iniciando mvn2013"
# inicia o mvn
java -jar mvn2013.jar;
