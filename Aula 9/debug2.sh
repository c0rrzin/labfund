#!/bin/bash

echo " "



echo "compiling main"
# monta o mvn para o main
java -cp MLR.jar montador.MvnAsm T5G13A09E02_main.asm ;

echo "compiling dumper"
# monta o mvn para o main
java -cp MLR.jar montador.MvnAsm T5G13A09E02_dumper.asm ;

echo "compiling loader"
# monta o mvn para o main
java -cp MLR.jar montador.MvnAsm T5G13A09E02_loader.asm ;

echo "linkando arquivos"
# linka os arquivos
java -cp MLR.jar linker.MvnLinker T5G13A09E02_main.mvn T5G13A09E02_dumper.mvn T5G13A09E02_dumper.mvn -s final2_linkado.mvn;

echo "relocando arquivos"
# reloca os arquivos
java -cp MLR.jar relocator.MvnRelocator final2_linkado.mvn final2_relocado.mvn 0000 000;

echo "iniciando mvn2013"
# inicia o mvn
java -jar mvn2013.jar;
