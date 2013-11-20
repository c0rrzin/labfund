DP1           >               ; primeiro caractere a ser packed
DP2           >               ; segundo caractere a ser packed
DP3           >               ; word packed a ser expertada
CHTOUI        > 
;UITOCH        >
PACK          >

UM            >
DOIS          >
CINCO         >


              &  /0000        ;

UM            K  /0001        ;
DOIS          K  /0002        ;
TRES          K  /0003        ;
QUATRO        K  /0004        ;
CINCO         K  /0005        ;
DZES          K  /0010        ;
DCES          K  /0100        ;
GIGAS         K  /1000        ;
MUM           K  /FFFF        ;
TTA0          K  /0030        ;
TTAA          K  /003A        ;
QTA1          K  /0041        ;
QTA7          K  /004B        ;
IT            K  /0000        ;
IT2           K  /0000        ;
CA            K  /000A        ;
VARTMP        K  /0000        ;
VARTMP2       K  /0000        ;

DC1       K  'LI        ; primeiro palavra em ASCHII a ser transofrmada em HEXA (/3132)
DC2       K  'XO        ; segunda plavra em ASCHII a ser transofrmada em HEXA (/3334)
DC3       k  /0000      ; uniao das transformacoes (/1234)
DUI1      K  /8000      ; Palavra em HEXA a ser transformada em ASCHII
DUI2      K  /0000      ; resultado 1
DUI3      K  /0000      ; resultado 2
DP1       K  /0000      ; primeiro caractere a ser packed
DP2       K  /0000      ; segundo caractere a ser packed
DP3       K  /0000      ; word packed a ser expertada
DU1       K  /0000      ; word a ser unpacked
DU2       K  /0000      ; primeiro caracter da word unpacked
DU3       K  /0000      ; segundo caractere da word unpacked

CHTOUI    JP /0000      ; endereco de retorno da subrotina
          MM DU1        ; salva do acumulador para variavel de unpack
          SC UNPACK     ; chama a subrotina de unpack
          LD DU3        ; carrega o primeiro caractere (/0031)
          MM VARTMP     ; salva na variavel temporaria
          SC TFORM      ; chama subrotina de transformacao
          JN END1       ; se o retorno for negativo, palavra invalida, retorna o que ja esta no acumulador
          MM VARTMP2    ; 
          LD DU2        ; carrega o segundo caracter
          MM VARTMP     ; carrega em var temp
          SC TFORM      ; chama sub rotina de transformacao
          JN END1       ; se negativo, caractere invalido, retorna
          *  DZES       ; 
          +  VARTMP2    ;
          JP ENDCTU     ;
END1      LV /0005      ; carrega o erro 3
          JP SHOW_ERR1  ;
          K  /0000      ;
SHOW_ERR1 OS /00EE      ; 

ENDCTU    RS CHTOUI     ; retorno da subrotina

PACK      JP /0000      ; endereco de volta da subrotina
          LD DP1        ; Carrega o dado 1;
          *  DCES       ; Desloca 2 bytes para esquerda
          +  DP2        ; LOAD com dado 2
          MM DP3        ; salva no dado 3
          RS PACK       ; Chama fim de subrotina

UNPACK    JP /0000      ; endereco de volta da subrotina
          LD DU1        ; Carrega o dado 1;  EX : 2002
          /  DCES       ; desloca 2 bytes para a direita  EX: 0020
          MM DU2        ; salva no primeiro dado de saida
          *  DCES       ; desloca 2 bytes para a esquerda EX: 2000
          MM DU3        ; salva na saida s2 para buscar depois
          LD DU1        ; carrega o dado de entrada EX : 2002
          -  DU3        ; faz menos o primeiro dado salvo EX : 0002
          MM DU3        ; salva o real valor na saida S1
          RS UNPACK     ; return

TFORM     JP /0000      ; endereco de volta da subrotina
          LD VARTMP     ; carega a variavel
          -  TTA0       ; tira trinta
          JN FIMT       ; se negativo, caractere invalido, retorna negativo
          LD VARTMP     ; se nao, recarrega valor da variavel
          -  TTAA       ; tira 3A
          JN TM30       ; o numero é entro 0 e 9, retirar 30 e salvar na variavel de caractere
          LD VARTMP     ; se nao, recarrega o valor da variavel
          -  QTA1       ; tira 41
          JN FIMT       ; se negativo, caractere invalido, retorna o numero negativo
          LD VARTMP     ; se nao, recarrega o valor da variavel
          -  QTA7       ; retira 47
          JN TMAF       ; se negativo, "numero" entre A e F, retirar 40 e salvar na variavel de caractere
          LD MUM        ; se nao, retornar negativo
          JP FIMT       ; chama fim da subrotina
TM30      LD VARTMP     ; carrega o numero a ser salvo
          -  TTA0       ; retira 30
          MM VARTMP     ; salva o numero
;          SC SAVE       ; chama subrotina de salvar no caractere devido
          JP FIMT       ; retorna
TMAF      LD VARTMP     ; carrega o numero de A a F a ser transformado
          -  QTA1       ; tira 41
          +  CA         ; adiciona a constante /000A
          MM VARTMP     ; salva na variavel
;          SC SAVE       ; salva no devido caractere
FIMT      RS TFORM      ; retorno da subrotina de transformacao

;UITOCH    JP /0000        ; retorno de subrotina
;INITUI    LD DUI1         ; carrega palavra a ser desmontada
;          MM DU1          ; salva na constante de unpack
;          SC UNPACK       ; chama unpack
;LOOP1     LD VAREND2      ; carrega o endereco da atual word INICIO DO LOOP
;          +  LOAD         ; gera funcao de dar LOAD
;          MM LOAD3        ; salva no proximo endereco
;LOAD3     K  /0000        ; carrega o endereco da word unpacked
;          MM VARTMP       ; salva na variavel temporaria
;          /  DZES         ; divide po /0010 (pega o valor da "dezena")
;          MM VARTMP2      ; salva em vartmp2
;          *  DZES         ; multiplica por /0010 (agora temos a dezena em si)
;          MM VARTMP2      ; (salva na variavel)
;          LD VARTMP       ; carrega
;          -  VARTMP2      ; pegou a "unidade" ( Ex 12 - 10 = 02)
;          MM VARTMP2      ; salva
;          LD VARTMP       ; gera a dezena de novo
;          /  DZES         ;
;          MM VARTMP       ;
;          SC TFMUI        ; gera subrotina de salvar o valor certo
;          LD VARTMP2      ; carrega a proxima unidade
;          MM VARTMP       ; salva na variavel a ser transformada
;          SC TFMUI        ; gera subrotina de salvar o valor certo
;          LD IT2          ; carrega constante de iteracoes
;          +  UM           ; adiciona um
;          MM IT2          ; salava em IT2
;          -  DOIS         ; ve se ja foram quatro iteracoes
;          JZ JOIN2        ; pula para a parte de unir todos os 4 caracteres
;          LD VAREND2      ; se nao, carrega o endereco
;          +  DOIS         ; soma 2
;          MM VAREND2      ; salva na variavel de endereco
;          JP LOOP1        ; retorna para o comeco do loop
;JOIN2     LD CHAR1        ; load primeiro caractere
;          *  DCES         ;
;          +  CHAR2        ; junta com o segundo caractere
;          MM DUI2         ;
;          LD CHAR3        ; junta com o terceiro caractere
;          *  DCES         ;
;          +  CHAR4        ; junta com o quarto caractere
;          MM DUI3         ;
;END2      RS UITOCH       ; retornar

;TFMUI     JP /0000        ; retorno de subrotina
;          LD VARTMP       ; carega a variavel
;          -  CA           ; tira 000A
;          JN TMU30        ; se negativo, caractere entre 0 e 9, adiciona 30 e salva
;TMUAF     LD VARTMP       ; se nao, caractere entre A e F, tira CA e adiciona 00041
;          -  CA           ; tira 000A
;          +  QTA1         ; adiciona 41
;          MM VARTMP       ; salva o valor certo
;          SC SAVEUI       ; sava no caractere devido
;          JP FIMTFU       ; pula para fim da subrotina
;TMU30     LD VARTMP       ; se nao, recarrega o valor da variavel
;          +  TTA0         ; soma 30
;          MM VARTMP       ; salva o valor certo
;          SC SAVEUI       ; chama subrotina de salvar no caractere devido
;FIMTFU    RS TFMUI


;SAVEUI    JP /0000        ; endereco de retorno da subrotina
;          LD CHAREND2     ; carrega o endereco da variavel
;          +  SV           ; gera funcao de dar save
;          MM SV2          ; salva a instrucao
;          LD VARTMP       ; carrega a variavel
;SV2       K  /0000        ; salva no endereco
;          LD CHAREND2     ; cerrega a variavel, transforma no proximo endereco e salva.
;          +  DOIS         ;
;          MM CHAREND2     ;
;          RS SAVEUI       ;


# FIM
