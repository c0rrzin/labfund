DP1    >                ; primeiro caractere a ser packed
DP2    >                ; segundo caractere a ser packed
DP3    >                ; word packed a ser expertada
DU1    >                ; word a ser unpacked
DU2    >                ; primeiro caracter da word unpacked
DU3    >                ; segundo caractere da word unpacked
ST1    >                ; primeira word (PRIMEIRA STRING)
ST2    >                ; primeira word (SEGUNDA STRING) ['va']
DS1    >                ; numero de words iguais
PACK   <                ; importa subrotina de PACK
UNPACK <                ; importa subrotina de UNPACK
STRCMP <                ; importa subrotina de STRCMP
ETMP1  >
ETMP2  >

          &  /0000      ; enderecos absolutos dos dados
INIT      JP MAIN
DP1       K  /0034      ; primeiro caractere a ser packed
DP2       K  /0036      ; segundo caractere a ser packed
DP3       K  /0000      ; word packed a ser expertada
DU1       K  /0000      ; word a ser unpacked
DU2       K  /0000      ; primeiro caracter da word unpacked
DU3       K  /0000      ; segundo caractere da word unpacked
ST1       K  'va        ; primeira word (PRIMEIRA STRING)
          K  'ic        ; segunda word (PRIMEIRA STRING)
          K  'om        ; terceira word (PRIMEIRA STRING)
          K  'fe        ; quarta word (PRIMEIRA STRING)
          K  /0000      ; quinta word (PRIMEIRA STRING)   [/0000] (fim)
          K  /0000      ; sexta word (PRIMEIRA STRING)
          K  /0000      ; setima word (PRIMEIRA STRING)
          K  /0000      ; oitava word (PRIMEIRA STRING)
ST2       K  'va        ; primeira word (SEGUNDA STRING) ['va']
          K  'ic        ; segunda word (SEGUNDA STRING)  ['ic']
          K  'op        ; terceira word (SEGUNDA STRING) ['om']
          K  'qp        ; quarta word (SEGUNDA STRING)   ['fe']
          K  /0000      ; quinta word (SEGUNDA STRING)   [/0000] (fim)
          K  /0000      ; sexta word (SEGUNDA STRING)
          K  /0000      ; setima word (SEGUNDA STRING)
          K  /0000      ; oitava word (SEGUNDA STRING)
DS1       K  /0000      ; numero de words iguais

ETMP2     K   ST2     ; endereco da word da string 2
ETMP1     k   ST1     ; endereco da word da string 1



MAIN      SC STRCMP     ; chama STRCMP
          SC PACK       ; chama PACK
          ;SC UNPACK     ; o unpack é chamado na funcao de strcmp, portanto, se uma fincionar, a outra esta funcionando
FIMPRG    HM FIMPRG     ; fim do programa
          # MAIN        ; fim do texto fisico
