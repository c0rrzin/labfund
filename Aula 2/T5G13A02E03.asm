          @   /0000  ; endereço do inicio do programa
I         K   INIT   ; endereço de inicio de simulacao

          @   /0002  ; endereco do numero de words iguais
S         K   /0000  ; numero de words iguais

          @   /0004  ; endereco da primeira word
E1        K   'va    ; primeira word (PRIMEIRA STRING)
          K   'ic    ; segunda word (PRIMEIRA STRING)
          K   'om    ; terceira word (PRIMEIRA STRING)
          K   'fe    ; quarta word (PRIMEIRA STRING)
          K   /0000  ; quinta word (PRIMEIRA STRING)   [/0000] (fim)
          K   /0000  ; sexta word (PRIMEIRA STRING)
          K   /0000  ; setima word (PRIMEIRA STRING)
          K   /0000  ; oitava word (PRIMEIRA STRING)

          @   /0014  ; endereco da segunda word
E2        K   'va    ; primeira word (SEGUNDA STRING) ['va']
          K   'ic    ; segunda word (SEGUNDA STRING)  ['ic']
          K   'op    ; terceira word (SEGUNDA STRING) ['om']
          K   'qp    ; quarta word (SEGUNDA STRING)   ['fe']
          K   /0000  ; quinta word (SEGUNDA STRING)   [/0000] (fim)
          K   /0000  ; sexta word (SEGUNDA STRING)
          K   /0000  ; setima word (SEGUNDA STRING)
          K   /0000  ; oitava word (SEGUNDA STRING)


UM        K   /0001  ; constante para adicionar na contagem de strings iguais
DOIS      k   /0002  ; constante de SHIFT (proximo endereco)
DZES      K   /0010  ; constante de diferenca de endereco entre strings (dezesseis)
ETMP1     k   E1     ; endereco da word da string 1
TMP1      K   /0000  ; word da string 1
TMP11     K   /0000  ; PACK 1 da word unpacked da string 1
TMP12     K   /0000  ; PACK 2 da word unpacked da string 1
ETMP2     K   E2     ; endereco da word da string 2
TMP2      K   /0000  ; word da string 2
TMP21     K   /0000  ; PACK 1 da word unpacked da string 2
TMP22     K   /0000  ; PACK 2 da word unpacked da string 2

INIT      SC  STRCMP ; chama subrotina de string compare
FIM       HM  FIM    ; fim do programa

          @   /0050  ; endereco da subrotina de STRCMP
STRCMP    K   /0000  ; endereco de retorno da subrotina
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
          MM  EN     ; salva ela na word a ser unpacked
          SC  UNPACK ; chama subrotina de unpack
          LD  SU1    ; pega primeiro caractere
          MM  TMP11  ; salva na temporary correspondente
          MM  WD1    ; ja salva na word a ser comparada
          LD  SU2    ; pega segundo caractere
          MM  TMP12  ; salva na temporary correspondente
          LD  TMP2   ; carrega a primeira word
          MM  EN     ; salva ela na word a ser unpacked
          SC  UNPACK ; chama subrotina de unpack
          LD  SU1    ; pega primeiro caractere
          MM  TMP21  ; salva na temporary correspondente
          MM  WD2    ; ja salva na word a ser comparada
          LD  SU2    ; pega segundo caractere
          MM  TMP22  ; salva na temporary correspondente
          SC  WCMP   ; chama subrotina de comparacao dos primeiros caracteres (que ja foram salvos nela)
          LD  TMP12  ; carrega segundo caractere da word 1
          MM  WD1    ; salva na word a ser comparada
          LD  TMP22  ; carrega segundo caractere da word 2
          MM  WD2    ; carrega na word a ser comparada
          SC  WCMP   ; chama subrotina de word compare
          SC  SHIFT  ; carrega proximos valores de endereco e words a serem comparados
          JP  LOOP   ; recomeca o loop
ESCMP     LD  S      ; carrega o numero de ocorrencias para o acumulador
          RS  STRCMP ; retorno da subrotina

          @   /0200  ; Endereco da subrotina de UNPACK
UNPACK    K   /0000  ; endereco de volta da subrotina
          JP  IUNP   ; vai para inicio da subrotina
EN        K   /0000  ; word a ser unpacked
SU1       K   /0000  ; Saida 1
SU2       K   /0000  ; Saida 2
DES       k   /0100  ; constante de deslocamento de 2 bytes
IUNP      LD  EN     ; Carrega o dado 1;  EX : 2002
          /   DES    ; desloca 2 bytes para a direita  EX: 0020
          MM  SU1    ; salva no primeiro dado de saida
          *   DES    ; desloca 2 bytes para a esquerda EX: 2000
          MM  SU2    ; salva na saida sU2 para buscar depois
          LD  EN     ; carrega o dado de entrada EX : 2002
          -   SU2    ; faz menos o primeiro dado salvo EX : 0002
          MM  SU2    ; salva o real valor na saida SU1
          RS  UNPACK ; return

          @   /0220  ; Funcao de load
ADD       LD  /0000  ; constante de dar load (a ser somado)

          @   /0230  ; endereco da subrotina de compara 0000 (fim de string)
ISZERO    K   /0000  ; endereco de retorno de subrotina
          LD  TMP1   ; carrega a word da primeira string
          JZ  RISZ   ; a word é 0, fim de string, retorna com 0
          LD  TMP2   ; se nao é zero, checar se a word da string 2 é 0
          JZ  RISZ   ; a word é 0, fim de string, retorna com 0
          LV  /0001  ; nenhuma string é 0, carrega valor 1 no acumulador
RISZ      RS  ISZERO ; retorno de subrotina

          @  /0240   ; subrotina de carrega proximas words
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

          @  /0260   ; subrotina de camparacao entre duas words
WCMP      K  /0000   ; endereco de retorno da subrotina
          JP IWC     ; vai para incio da subrotina
WD1       K  /0000   ; word 1 a ser comparada
WD2       K  /0000   ; wrod 2 a ser comparada
IWC       LD WD1     ; carrega word 1
          -  WD2     ; subtrai da word 2
          JZ CONT    ; se é zero, adiciona 1 no contador antes de continuar
FIMW      RS WCMP    ; fim de subrotina
CONT      LD S       ; carrega saida do programa (numero de ocorrencias iguais)
          +  UM      ; soma um a esse numero
          MM S       ; salva no numero de ocorrencias
          JP FIMW    ; retorna subrotina

          #  INIT    ; fim do programa
