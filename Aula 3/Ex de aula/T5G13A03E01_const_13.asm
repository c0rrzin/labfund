DES    >                ; constante de deslocamente de 2bytes
UM     >                ; constante para adicionar na contagem de strings iguais
DOIS   >                ; constante de SHIFT (proximo endereco)
DZES   >                ; constante de diferenca de endereco entre strings (dezesseis)
;ETMP1  >                ; endereco da word da string 1
TMP1   >                ; word da string 1
TMP11  >                ; PACK 1 da word unpacked da string 1
TMP12  >                ; PACK 2 da word unpacked da string 1
;ETMP2  >                ; endereco da word da string 2
TMP2   >                ; word da string 2
TMP21  >                ; PACK 1 da word unpacked da string 2
TMP22  >                ; PACK 2 da word unpacked da string 2
ST1    <                ; endereço da primeira word da ST1
ST2    <                ; endereco da primeira word da ST2


          &   /0000   ; torna o codigo realocavel
DES       K   /0100   ;
UM        K   /0001  ; constante para adicionar na contagem de strings iguais
DOIS      k   /0002  ; constante de SHIFT (proximo endereco)
DZES      K   /0010  ; constante de diferenca de endereco entre strings (dezesseis)
;ETMP1     k   ST1     ; endereco da word da string 1
TMP1      K   /0000  ; word da string 1
TMP11     K   /0000  ; PACK 1 da word unpacked da string 1
TMP12     K   /0000  ; PACK 2 da word unpacked da string 1
;ETMP2     K   ST2     ; endereco da word da string 2
TMP2      K   /0000  ; word da string 2
TMP21     K   /0000  ; PACK 1 da word unpacked da string 2
TMP22     K   /0000  ; PACK 2 da word unpacked da string 2
          #   CONST  ; fim do constatns
