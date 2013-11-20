DUMPER      <
DUMP_INI    <
DUMP_TAM    <
DUMP_UL     <
DUMP_BL     <
DUMP_EXE    <

LOADER      <
LOADER_UL   <

CHTOUI      <
DP1         <
DP2         <
DP3         <

PACK        <

UM          <
DOIS        <
CINCO       < 


BATCH >

              &  /0000 ;

C_DB_SLASH    K  /2F2F        ; //
C_EOL         K  /200A        ; space + EOL 
C_DB_SP       K  /2020        ; space + space
C_EOF         K  /2F2A        ; /*
C_JB          K  /4A42        ; JB
C_DU          K  /4455        ; DU
C_LO          K  /4C4F        ; LO
C_CL          K  /434C        ; CL

C_GD          K  /D000        ; GD

CL_CUR_ADD    K  /0000

CL_OS         K  /F0C0        ; OS

VARTMP        K  /0000        ; variavel temporaria
VARTMP2       K  /0000        ; outra var tmp
DU_CNT        K  /0000        ;
DCSEIS        K  /0100        ;
SAVE          K  /9000        ;

CL_N_PARAMS   K  /000A        ; 
CL_PARAMS_CNT K  /0000        ;

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
              JZ GET_JOB_EOL  ; se é JB vai para CMD, se nao, imprime erro e para
              LV /0001
              JP ERROR        ;

GET_JOB_EOL   SC GET_DATA
              -  C_EOL        ; ve se é fim de linha, se sim pega o dado de novo
              JZ GET_CMD      ;
              LV /0001
              JP ERROR

GET_CMD       SC GET_DATA     ; checa double slash
              -  C_DB_SLASH   ;
              JZ GET_CMD_2    ;
              LV /0002        ;
              JP ERROR        ;

GET_CMD_2     SC GET_DATA     ; pega nome do comando
              MM VARTMP       ; vai para DUMP 
              -  C_DU         ; 
              JZ GET_DU_EOL   ;

              LD VARTMP       ; vai para o LOAD
              -  C_LO         ;
              JZ GET_LO_EOL   ; 

              LD VARTMP       ; vai para o CLEAR
              -  C_CL         ;
              JZ GET_CL_EOL   ;

              LV /0002        ; se nao, foi erro de comando
              JP ERROR        ;

GET_DU_EOL    SC GET_DATA
              -  C_EOL        ; ve se é fim de linha, se sim pega os parametros
              JZ DO_THE_DUMP  ;
              LV /0002
              JP ERROR

GET_LO_EOL    SC GET_DATA
              -  C_EOL        ; ve se é fim de linha, se sim pega os parametros
              JZ DO_THE_LOAD  ;
              LV /0002
              JP ERROR

GET_CL_EOL    SC GET_DATA
              -  C_EOL        ; ve se é fim de linha, se sim pega os parametros
              JZ DO_THE_CLEAR ;
              LV /0002
              JP ERROR

DO_THE_DUMP   LD CINCO        ; zerando o contador
              MM DU_CNT       ; 
              JP DU_LOOP      ; (nao precisa checar db spaces agora) (1 parametro)

DU_PRE_LOOP   SC GET_DATA     ;
              -  C_DB_SP      ; checa se o proximo 
              JZ DU_LOOP
              LV /0003
              JP ERROR

DU_LOOP       SC GET_DATA     ; comeca a pegar os dados do dump
              SC CHTOUI       ; transforma de ascii para bin (EX: 3034 -> 0004)
              MM DP1          ; 
              SC GET_DATA     ; pega a segunda parte do dado
              SC CHTOUI       ; transforma de ascii para bin (Ex: 3030 -> 0000)
              MM DP2          ; 
              SC PACK         ; junta as palavras e rotorna
                              ; no acumulador (e em DP3) (Ex: PACK(0004,0000) = 0400)

              LD DU_CNT       ; descobre qual arguento é e 
                              ; salva no respectivo parametro do dump
              -  CINCO
              JZ DU_1_PRM
              +  UM
              JZ DU_2_PRM
              +  UM
              JZ DU_3_PRM
              +  UM
              JZ DU_4_PRM
              +  UM
              JZ DU_5_PRM

DU_1_PRM      LD DU_CNT
              -  UM 
              MM DU_CNT
              LD DP3          ; a palavra a ser salva
              MM DUMP_BL      ; salva no parametro tamanho bloco do dump
              JP DU_PRE_LOOP  ;

DU_2_PRM      LD DU_CNT
              -  UM 
              MM DU_CNT
              LD DP3          ; a palavra a ser salva
              MM DUMP_INI     ; salva no parametro endereco inicial do dump
              JP DU_PRE_LOOP  ;

DU_3_PRM      LD DU_CNT
              -  UM 
              MM DU_CNT
              LD DP3          ; a palavra a ser salva
              MM DUMP_TAM     ; salva no parametro tamanho total do dump
              JP DU_PRE_LOOP  ;

DU_4_PRM      LD DU_CNT
              -  UM 
              MM DU_CNT
              LD DP3          ; a palavra a ser salva
              MM DUMP_EXE     ; salva no parametro endereco executavel do dump
              JP DU_PRE_LOOP  ;

DU_5_PRM      LD DP3          ; a palavra a ser salva
              MM DUMP_UL      ; salva no parametro UL do dump
              SC DUMPER       ; chama subrotina de dump
              SC GET_DATA
              -  C_EOL        ; ve se é fim de linha, se sim pega o proximo comando
              JZ GET_CMD 
              LV /0006        ; (cmd not found)
              JP ERROR        ; volta a area de comandos


DO_THE_LOAD   SC GET_DATA     ; comeca a pegar os dados do load
              SC CHTOUI       ; transforma de ascii para bin (EX: 3030 -> 0000)
              MM DP1          ; 
              SC GET_DATA     ; pega a segunda parte do dado
              SC CHTOUI       ; transforma de ascii para bin (Ex: 3031 -> 0001)
              MM DP2          ; 
              SC PACK         ; junta as palavras e rotorna
                              ; no acumulador (e em DP3) (Ex: PACK(0000,0001) = 0001)
              MM LOADER_UL    ; salva no loader UL 
              SC LOADER       ; ihaaaa
              SC GET_DATA
              -  C_EOL        ; ve se é fim de linha, se sim pega o proximo comando
              JZ GET_CMD      ;
              LV /0006
              JP ERROR 

DO_THE_CLEAR  LV CALL_CL_OS   ; pega o endereco onde serao salvos os argumentos do OS
              MM CL_CUR_ADD   ;
              LV /0000        ; reseting count
              MM CL_PARAMS_CNT ;
              SC GET_DATA     ; pega o primeiro nibble do primeiro parametro
              MM VARTMP
              -  C_EOL        ; se for fim de linha, nenhum parametro foi passado, show error
              JZ CL_PARAM_ERR ;
              JP CL_LOOP      ; se nao vai para o loop de gt params
CL_PARAM_ERR  LV /0006
              JP ERROR

CL_LOOP       LD VARTMP       ;
              SC CHTOUI       ; transforma de ascii para bin (EX: 3030 -> 0000)
              MM DP1          ; 
              SC GET_DATA     ; pega a segunda parte do dado
              SC CHTOUI       ; transforma de ascii para bin (Ex: 3031 -> 0001)
              MM DP2          ; 
              SC PACK         ; junta as palavras e rotorna
                              ; no acumulador (e em DP3) (Ex: PACK(0000,0001) = 0001)
              LD CL_CUR_ADD   ; gera funcao de salvar o argumento no endereco certo
              +  SAVE         ;
              MM SV_CL_PARAM  ;
              LD DP3          ;
SV_CL_PARAM   K  /0000        ; salva o dado no argumento do OS
              LD CL_PARAMS_CNT ;
              +  UM 
              MM CL_PARAMS_CNT ; incrementando contagens (n de parametros)
              -  CL_N_PARAMS
              JZ CL_PARAM_ERR  ;
              LD CL_CUR_ADD    ; incrementando contagens (endereco do prox parametro)
              +  DOIS
              MM CL_CUR_ADD    ;
              SC GET_DATA      ; ve se é fim de linha, se sim pacabaram os parametros
              MM VARTMP
              -  C_EOL         
              JZ CL_END        ; pula para a parte final do CL
              LD VARTMP        ;
              -  C_DB_SP       ; veio espaco, é esperado um proximo parametro
              JZ GET_NT_PRM    ;
              LV /0003
              JP ERROR         ; pega proximo parametro
GET_NT_PRM    SC GET_DATA      ;
              MM VARTMP        ;
              JP CL_LOOP

CL_END        LD CL_PARAMS_CNT ;   (Ex: 0004, se forem 4 parametros) 
              *  DCSEIS        ;   (Ex: 0400)
              +  CL_OS         ; formou a operacao de chamar o OS (Ex: F4C0)
              MM VARTMP        ;
              LD CL_CUR_ADD    ;   (Ex: 0192)
              +  SAVE          ; formou operacao se salvar (Ex: 9192)
              MM CL_OS_SAVE    ; 
              LD CL_CUR_ADD    ; formando operacao de ir para o get_cmd apos o OS (Ex: 0192)
              +  DOIS          ; Ex: 0194
              MM CL_CUR_ADD    ; 
              +  SAVE          ; Ex: 9194
              MM CL_TO_CMD     ;
              LV GET_CMD       ;
CL_TO_CMD     K  /0000         ; salvou no endereco apos a execucao do OS um comando
                               ; um comando para pular para o GET_CMD, reiniciando o loop
              LD VARTMP        ; (contem a chamada de OS -> Ex: F4C0)
CL_OS_SAVE    K  /0000         ;
              LD CL_CUR_ADD    ; volta o endereco de execucao para estar na chamada de OS
              -  DOIS          ;
              MM CL_CUR_ADD    ; pegou o endereco da operacao de OS de novo
              JP CL_CUR_ADD    ; pula vai para este endereco, que efetuara a chamada de OS

CALL_CL_OS    $ =10            ; guarda 10 enderecos, 9 de parametros e 1 para pular
                               ; de volta ao GET_CMD
              

ERROR         JP SHOW_ERROR 
              K  /0001        ; numero da ul do erro * (so por ser preciso passar um parametro)
SHOW_ERROR    OS /00EE        ; chama a mensagem de erro

FIM           RS BATCH        ; retorna

GET_DATA      JP /0000        ; inicio da rotina de GD 
S_GET_DATA    K  /0000        ; precisa ser previamente salvo com o comando de GD na UL certa
              MM VARTMP       ; salva na variavel temporaria
              -  C_EOF        ;
              JZ FIM          ; retorna a sub rotina do batch caso seja fim do arquivo
              LD VARTMP       ;
              RS GET_DATA     ;

# BATCH
