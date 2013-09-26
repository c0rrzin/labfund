
DP1    <                ; primeiro caractere a ser packed
DP2    <                ; segundo caractere a ser packed
DP3    <                ; word packed a ser expertada
DU1    <                ; word a ser unpacked
DU2    <                ; primeiro caracter da word unpacked
DU3    <                ; segundo caractere da word unpacked
ST1    <                ; primeira word (PRIMEIRA STRING)
ST2    <                ; primeira word (SEGUNDA STRING) ['va']
DS1    <                ; numero de words iguais
DES    <                ; vido das constantes (valor 0100)
UM     <                ; constante para adicionar na contagem de strings iguais
DOIS   <                ; constante de SHIFT (proximo endereco)
DZES   <                ; constante de diferenca de endereco entre strings (dezesseis)
ETMP1  <                ; endereco da word da string 1
TMP1   <                ; word da string 1
TMP11  <                ; PACK 1 da word unpacked da string 1
TMP12  <                ; PACK 2 da word unpacked da string 1
ETMP2  <                ; endereco da word da string 2
TMP2   <                ; word da string 2
TMP21  <                ; PACK 1 da word unpacked da string 2
TMP22  <                ; PACK 2 da word unpacked da string 2
PACK   >                ; exporta subrotina de PACK
UNPACK >                ; exporta subrotina de UNPACK
STRCMP >                ; exporta subrotina de STRCMP


          &  /0100      ; Endereco da subrotina
PACK      JP /0000      ; endereco de volta da subrotina
          LD DP1        ; Carrega o dado 1;
          *  DES        ; Desloca 2 bytes para esquerda
          +  DP2        ; add com dado 2
          MM DP3        ; salva no dado 3
          RS PACK       ; Chama fim de subrotina

UNPACK    JP  /0000      ; endereco de volta da subrotina
          LD DU1        ; Carrega o dado 1;  EX : 2002
          /  DES        ; desloca 2 bytes para a direita  EX: 0020
          MM DU2        ; salva no primeiro dado de saida
          *  DES        ; desloca 2 bytes para a esquerda EX: 2000
          MM DU3        ; salva na saida s2 para buscar depois
          LD DU1        ; carrega o dado de entrada EX : 2002
          -  DU3        ; faz menos o primeiro dado salvo EX : 0002
          MM DU3        ; salva o real valor na saida S1
          RS UNPACK     ; return



STRCMP    JP   /0000  ; endereco de retorno da subrotina
LOOP      LD  ETMP1  ; carrega endereco da word a ser carregada, str1 (INICIO DO LOOP)
          +   ADD    ; gera endereco de load
          MM  PROX3  ; salva no proximo endereco
PROX3     K   /0000  ; carrega a word da str 1
          MM  TMP1   ; salva na temprary word 1
          LD  ETMP2  ; carrega endereco da word a ser carregada, str2
          +   ADD    ; gera endereco de load
          MM  PROX4  ; salva no proximo endereco
PROX4     K   /0000  ; carrega a word da str 2
          MM  TMP2   ; salva na temprary word 2
          SC  ISZERO ; checa se algum dos dados é 0
          JZ  ESCMP  ; se é 0, alguma das strings chegou ao fim, retornar sub rotina
                     ; checamos o fim de loop, agora precisamors unpack as words e comparar caracter a caracter
          LD  TMP1   ; carrega a primeira word
          MM  DU1     ; salva ela na word a ser unpacked
          SC  UNPACK ; chama subrotina de unpack
          LD  DU2    ; pega primeiro caractere
          MM  TMP11  ; salva na temporary correspondente
          MM  WD1    ; ja salva na word a ser comparada
          LD  DU3    ; pega segundo caractere
          MM  TMP12  ; salva na temporary correspondente
          LD  TMP2   ; carrega a primeira word
          MM  DU1     ; salva ela na word a ser unpacked
          SC  UNPACK ; chama subrotina de unpack
          LD  DU2    ; pega primeiro caractere
          MM  TMP21  ; salva na temporary correspondente
          MM  WD2    ; ja salva na word a ser comparada
          LD  DU3    ; pega segundo caractere
          MM  TMP22  ; salva na temporary correspondente
          SC  WCMP   ; chama subrotina de comparacao dos primeiros caracteres (que ja foram salvos nela)
          LD  TMP12  ; carrega segundo caractere da word 1
          MM  WD1    ; salva na word a ser comparada
          LD  TMP22  ; carrega segundo caractere da word 2
          MM  WD2    ; carrega na word a ser comparada
          SC  WCMP   ; chama subrotina de word compare
          SC  SHIFT  ; carrega proximos valores de endereco e words a serem comparados
          JP  LOOP   ; recomeca o loop
ESCMP     LD  DS1      ; carrega o numero de ocorrencias para o acumulador
          RS  STRCMP ; retorno da subrotina

ADD       LD  /0000  ; constante de dar load (a ser somado)


ISZERO    K   /0000  ; endereco de retorno de subrotina
          LD  TMP1   ; carrega a word da primeira string
          JZ  RISZ   ; a word é 0, fim de string, retorna com 0
          LD  TMP2   ; se nao é zero, checar se a word da string 2 é 0
          JZ  RISZ   ; a word é 0, fim de string, retorna com 0
          LV  /0001  ; nenhuma string é 0, carrega valor 1 no acumulador
RISZ      RS  ISZERO ; retorno de subrotina


SHIFT     K  /0000   ; endereco de retorno da subrotina
          LD ETMP1   ; carrega o atual endereco da word string 1
          +  DOIS    ; soma o proximo endereco
          MM ETMP1   ; salva de volta no endereco
          +  ADD     ; monta funcao de load
          MM PROX    ; guarda no proximo endereco
PROX      K  /0000   ; carrega a nova word da string 1
          MM TMP1    ; salva na temporary word da string 1
          LD ETMP2   ; carrega o atual endereco da word string 2
          +  DOIS    ; soma o proximo endereco
          MM ETMP2   ; salva de volta no endereco
          +  ADD     ; monta funcao de load
          MM PROX2   ; guarda no proximo endereco
PROX2     K  /0000   ; carrega a nova word da string 2
          MM TMP2    ; salva na temporary word da string 2
          RS SHIFT   ; retorno da sub rotina

WCMP      K  /0000   ; endereco de retorno da subrotina
          JP IWC     ; vai para incio da subrotina
WD1       K  /0000   ; word 1 a ser comparada
WD2       K  /0000   ; wrod 2 a ser comparada
IWC       LD WD1     ; carrega word 1
          -  WD2     ; subtrai da word 2
          JZ CONT    ; se é zero, adiciona 1 no contador antes de continuar
FIMW      RS WCMP    ; fim de subrotina
CONT      LD DS1       ; carrega saida do programa (numero de ocorrencias iguais)
          +  UM      ; soma um a esse numero
          MM DS1       ; salva no numero de ocorrencias
          JP FIMW    ; retorna subrotina

          #  ROT    ; fim do programa
