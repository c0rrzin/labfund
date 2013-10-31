LOADER    <
LOADER_UL   <
DUMPER    <
DUMP_INI  <
DUMP_TAM  <
DUMP_UL   <
DUMP_BL   <
DUMP_EXE  <

;========================= N�O ALTERE ESTE TRECHO: IN�CIO ===================;
& /0000
MAIN      JP  START ; Salta para in�cio do main

; PARAMETROS
UL_TEST     K /0000 ; Unidade logica do disco a ser usado

;========================== N�O ALTERE ESTE TRECHO: FIM =====================;

; PARAMETROS
END_INICIAL   K /0400 ; Endere�o onde come�a o dump  - 0400
TAMANHO_TEST  K /000A ; Numero total de palavras a serem "dumpadas"  - 0032
BL_TEST     K /0002 ; Tamanho do bloco  - 0010
EXE_TEST    K /0440 ; Endere�o onde come�aria a execu��o (valor dummy, apenas para manter o formato)

;========================== N�O ALTERE ESTE TRECHO: FIM =====================;

START     LD  END_INICIAL ; Parametros, na ordem acima
          MM  DUMP_INI
          LD  TAMANHO_TEST
          MM  DUMP_TAM
          LD  UL_TEST
          MM  DUMP_UL
          MM  LOADER_UL ; Invoca loader
          LD  BL_TEST
          MM  DUMP_BL
          LD  EXE_TEST
          MM  DUMP_EXE
          SC  DUMPER    ; Invoca dumper
          SC  LOADER
FIM_MAIN  HM  FIM_MAIN  ; Fim do main

# MAIN
