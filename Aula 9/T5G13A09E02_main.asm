LOADER    <
LOADER_UL   <
DUMPER    <
DUMP_INI  <
DUMP_TAM  <
DUMP_UL   <

;========================= N�O ALTERE ESTE TRECHO: IN�CIO ===================;
& /0000
MAIN      JP  START ; Salta para in�cio do main

; PARAMETROS
UL_TEST     K /0000 ; Unidade logica do disco a ser usado

;========================== N�O ALTERE ESTE TRECHO: FIM =====================;

; PARAMETROS
END_INICIAL   K /0400 ; Endere�o onde come�a o dump  - 0400
TAMANHO_TEST  K /0032 ; Numero total de palavras a serem "dumpadas"  - 0032

START   LD  END_INICIAL ; Parametros, na ordem acima
        MM  DUMP_INI      ; salva o endereco inicial e o tamanho do DUMP
        LD  TAMANHO_TEST
        MM  DUMP_TAM
        LD  UL_TEST    ; salva a unidade logica a se usar
        MM  LOADER_UL ; Invoca loader
        MM  DUMP_UL   ;
        SC  DUMPER     ;
        SC  LOADER
FIM_MAIN    HM  FIM_MAIN  ; Fim do main

# MAIN
