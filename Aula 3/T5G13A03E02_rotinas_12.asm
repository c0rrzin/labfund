DC1    <                ; primeiro palavra em ASCHII a ser transofrmada em HEXA (/3132)
DC2    <                ; segunda palavra em ASCHII a ser transofrmada em HEXA (/3334)
DC3    <                ; hexadecimal esperado (/1234)
DUI1   <                ; Palavra em HEXA a ser transformada em ASCHII
DUI2   <                ; resultado 1
DUI3   <                ; resultado 2
DP1    <                ; primeiro caractere a ser packed
DP2    <                ; segundo caractere a ser packed
DP3    <                ; word packed a ser expertada
DU1    <                ; word a ser unpacked
DU2    <                ; primeiro caracter da word unpacked
DU3    <                ; segundo caractere da word unpacked
VARTMP <                ; tmp variable
VARTMP2 <               ; vartmp 2
VAREND <                ; address variable
VAREND2 <                ; address variable 2
CHAREND1   <             ; endereco de char 1 para CHTOUI
CHAREND2   <             ; endereco de char 1 para UITOCH
CHAR1     <             ; primeiro caracter
CHAR2     <             ; segundo caracter
CHAR3     <             ; terceiro caracter
CHAR4     <             ; quarto caracter
IT        <             ; importando numero de iteracoes para CHTOUI
IT2       <             ; importando numero de iteracoes para UITOCH
UM        <             ; imprtando constante (/0001)
DOIS      <             ; importando costante (/0002)
QTRO      <             ; importando costante (/0004)
DZES      <             ; imprtando constante (/0010)
DCES      <             ; imprtando constante (/0100)
GIGAS     <             ; imprtando constante (/1000)
MUM       <             ; imprtando constante (/FFFF)
TTA0      <             ; imprtando constante (/0030)
TTAA      <             ; imprtando constante (/003A)
QTA1      <             ; imprtando constante (/0041)
QTA7      <             ; imprtando constante (/0047)
CA        <             ; imprtando constante (/000A)

CHTOUI >                ; exportando subrotina de CHTOUI
UITOCH >                ; exportando subrotina de UITOCH




          &  /0000      ; tornando o codigo realocavel
CHTOUI    JP /0000      ; endereco de retorno da subrotina
INITCH    LD VAREND     ; carrega o endereco da palavra a ser unpacked INICIO do LOOP
          +  LOAD       ; gera funcao de dar load
          MM LOAD1      ; salva no prox endereco
LOAD1     K  /0000      ; load a palavra do momento
          MM DU1        ; salva na variavel de unpack
          SC UNPACK     ; chama a subrotina de unpack
          LD DU2        ; carrega o primeiro caractere (/0031)
          MM VARTMP     ; salva na variavel temporaria
          SC TFORM      ; chama subrotina de transformacao
          JN END1       ; se o retorno for negativo, palavra invalida, retorna o que ja esta no acumulador
          LD DU3        ; carrega o segundo caracter
          MM VARTMP     ; carrega em var temp
          SC TFORM      ; chama sub rotina de transformacao
          JN END1       ; se negativo, caractere invalido, retorna
          LD VAREND     ; carrega endereco
          +  DOIS       ; carrega proximo endereco
          MM VAREND     ; guarda novo endereco
          LD IT         ; carrega constante de iteracoes
          +  UM         ; adiciona um
          MM IT         ; salava em IT
          -  DOIS       ; ve se ja foram duas iteracoes
          JZ JOIN       ; pula para a parte de unir todos os 4 caracteres
          JP INITCH     ;

END1      LD MUM        ; return -1
          JP ENDCTU     ; return function


JOIN      LD CHAR1      ; load primeiro caractere
          +  CHAR2      ; junta com o segundo caractere
          +  CHAR3      ; junta com o terceiro caractere
          +  CHAR4      ; junta com o quarto caractere
          JN END1       ; se a palavra final for negativa, retorna -1
          MM DC3        ; se nao, salva no endereco requerido e retorna
ENDCTU    RS CHTOUI     ; retorno da subrotina


LOAD      LD  /0000     ; dar load (a ser somado)
SV        MM  /0000     ; de dar save (a ser somado)
DVD       /   /0000     ; de dividir (a ser somado)

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
          SC SAVE       ; chama subrotina de salvar no caractere devido
          JP FIMT       ; retorna
TMAF      LD VARTMP     ; carrega o numero de A a F a ser transformado
          -  QTA1       ; tira 41
          +  CA         ; adiciona a constante /000A
          MM VARTMP     ; salva na variavel
          SC SAVE       ; salva no devido caractere
FIMT      RS TFORM      ; retorno da subrotina de transformacao

SAVE      JP /0000        ; retorno de subrotina
          LD CHAREND1     ; carrega o endereco do caractere a ser salvo
          +  LOAD         ; forma subrotina de load
          MM LOAD2        ; salva no proximo endereco
LOAD2     K  /0000        ; load do caractere a ser salvo, que corresponde tambem ao byte a ser guardado
          *  VARTMP       ; multiplica o byte pelo valor a ser guardado
          MM VARTMP       ;
          LD CHAREND1     ; deslocando para o proximo endereco
          +  SV           ; gera funcao de dar MM
          MM SV1          ; guarda no proximo endereco
          LD VARTMP       ; recarrega o valor do caractere a ser salvo
SV1       K  /0000        ; salva caractere
          LD CHAREND1     ; gera proximo endereco
          +  DOIS         ;
          MM CHAREND1     ;
          LV UM           ; retorna 1
          RS SAVE         ; retorna

UITOCH    JP /0000        ; retorno de subrotina
INITUI    LD DUI1         ; carrega palavra a ser desmontada
          MM DU1          ; salva na constante de unpack
          SC UNPACK       ; chama unpack
LOOP1     LD VAREND2      ; carrega o endereco da atual word INICIO DO LOOP
          +  LOAD         ; gera funcao de dar LOAD
          MM LOAD3        ; salva no proximo endereco
LOAD3     K  /0000        ; carrega o endereco da word unpacked
          MM VARTMP       ; salva na variavel temporaria
          /  DZES         ; divide po /0010 (pega o valor da "dezena")
          MM VARTMP2      ; salva em vartmp2
          *  DZES         ; multiplica por /0010 (agora temos a dezena em si)
          MM VARTMP2      ; (salva na variavel)
          LD VARTMP       ; carrega
          -  VARTMP2      ; pegou a "unidade" ( Ex 12 - 10 = 02)
          MM VARTMP2      ; salva
          LD VARTMP       ; gera a dezena de novo
          /  DZES         ;
          MM VARTMP       ;
          SC TFMUI        ; gera subrotina de salvar o valor certo
          LD VARTMP2      ; carrega a proxima unidade
          MM VARTMP       ; salva na variavel a ser transformada
          SC TFMUI        ; gera subrotina de salvar o valor certo
          LD IT2          ; carrega constante de iteracoes
          +  UM           ; adiciona um
          MM IT2          ; salava em IT2
          -  DOIS         ; ve se ja foram quatro iteracoes
          JZ JOIN2        ; pula para a parte de unir todos os 4 caracteres
          LD VAREND2      ; se nao, carrega o endereco
          +  DOIS         ; soma 2
          MM VAREND2      ; salva na variavel de endereco
          JP LOOP1        ; retorna para o comeco do loop
JOIN2     LD CHAR1        ; load primeiro caractere
          *  DCES         ;
          +  CHAR2        ; junta com o segundo caractere
          MM DUI2         ;
          LD CHAR3        ; junta com o terceiro caractere
          *  DCES         ;
          +  CHAR4        ; junta com o quarto caractere
          MM DUI3         ;
END2      RS UITOCH       ; retornar

TFMUI     JP /0000        ; retorno de subrotina
          LD VARTMP       ; carega a variavel
          -  CA           ; tira 000A
          JN TMU30        ; se negativo, caractere entre 0 e 9, adiciona 30 e salva
TMUAF     LD VARTMP       ; se nao, caractere entre A e F, tira CA e adiciona 00041
          -  CA           ; tira 000A
          +  QTA1         ; adiciona 41
          MM VARTMP       ; salva o valor certo
          SC SAVEUI       ; sava no caractere devido
          JP FIMTFU       ; pula para fim da subrotina
TMU30     LD VARTMP       ; se nao, recarrega o valor da variavel
          +  TTA0         ; soma 30
          MM VARTMP       ; salva o valor certo
          SC SAVEUI       ; chama subrotina de salvar no caractere devido
FIMTFU    RS TFMUI


SAVEUI    JP /0000        ; endereco de retorno da subrotina
          LD CHAREND2     ; carrega o endereco da variavel
          +  SV           ; gera funcao de dar save
          MM SV2          ; salva a instrucao
          LD VARTMP       ; carrega a variavel
SV2       K  /0000        ; salva no endereco
          LD CHAREND2     ; cerrega a variavel, transforma no proximo endereco e salva.
          +  DOIS         ;
          MM CHAREND2     ;
          RS SAVEUI       ;

          # FIM
