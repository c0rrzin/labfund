DUMPER      <
DUMP_INI    <
DUMP_TAM    <
DUMP_UL     <
DUMP_BL     <
DUMP_EXE    <

LOADER      <
LOADER_UL   <


BATCH >

              &  /0000 ;

C_DB_SLASH    K  /2F2F        ; //
C_EOL         K  /200A        ; space + EOL 
C_DB_SP       K  /2020        ; space + space
C_EOF         K  /2F2A        ; /*
C_JB          K  /4A42        ; JB
C_DU          K  /4455        ; DU
C_LO          K  /4C4F        ; LO

C_GD          K  /D000        ; GD
VARTMP        K  /0000        ; variavel temporaria

BATCH_UL      K  /0300        ;

BATCH         JP /0000        ; inicio da rotina

              LD C_GD         ; formando rotina de GET_DATA
              +  BATCH_UL     ; 
              MM S_GET_DATA   ;

GET_JOB_SL    SC GET_DATA     ; pega primeiro dado (Expected //)
              -  C_DB_SLASH   ;
              JZ GET_JOB_ST   ; se é // ok, se nao imprime erro e para
              LV /0001
              JP ERROR        ;

GET_JOB_ST    SC GET_DATA     ; pega o segundo dado (Expected JB)
              -  C_JB         ; 
              JZ GET_CMD      ; se é JB vai para CMD, se nao, impreime erro e para
              LV /0001
              JP ERROR        ;

GET_CMD       SC GET_DATA     ; checa double slash
              -  C_DB_SLASH   ;
              JZ GET_CMD_2    ;
              LV /0002        ;
              JP ERROR        ;

GET_CMD_2     SC GET_DATA     ; pega nome do comando
              MM VARTMP       ;
              -  C_DU         ; vai para DUMP 
              JZ DO_THE_DUMP  ;

              LD VARTMP       ; 
              -  C_LO         ;
              JZ DO_THE_LOAD  ; vai para o LOAD

              LV /0002        ; se nao foi erro
              JP ERROR        ;

DO_THE_DUMP   SC GET_DATA     ; comeca a pegar os dados do dump
              JP FIM          ;

DO_THE_LOAD   SC GET_DATA     ; comeca a pegar os dados do load
              JP FIM          ;

ERROR         JP SHOW_ERROR 
              K  /0001        ; numero da ul do erro * (so por ser preciso passar um parametro)
SHOW_ERROR    OS /00EE        ; chama a mensagem de erro

FIM           RS BATCH        ; retorna

GET_DATA      JP /0000        ; inicio da rotina de GD 
S_GET_DATA    K  /0000        ; precisa ser previamente salvo com o comando de GD na UL certa
              MM VARTMP       ; salva na variavel temporaria
              -  C_EOL        ; ve se é fim de linha, se sim pega o dado de novo
              JZ S_GET_DATA   ;
              LD VARTMP       ; recarrega o dado carregado
              -  C_EOF        ;
              JZ FIM          ; retorna a sub rotina do batch caso seja fim do arquivo
              LD VARTMP       ;
              RS GET_DATA     ;

# BATCH
