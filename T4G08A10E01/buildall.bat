java -cp mlr.jar montador.MvnAsm mbs.asm
java -cp mlr.jar montador.MvnAsm loader3.asm
java -cp mlr.jar montador.MvnAsm dumper3.asm
java -cp mlr.jar montador.MvnAsm cifra.asm
java -cp MLR.jar linker.MvnLinker mbs.mvn dumper3.mvn loader3.mvn -s mbsf.mvn
java -cp MLR.jar relocator.MvnRelocator mbsf.mvn mbsf_rel.mvn 0100 000
PAUSE