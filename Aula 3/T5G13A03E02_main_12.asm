DC1 >                   ; exportando as variaveis
DC2 >                   ; exportando as variaveis
DC3 >                   ; exportando as variaveis
DUI1 >                  ; Palavra em HEXA a ser transformada em ASCHII
DUI2 >                  ; resultado 1
DUI3 >                  ; resultado 2
DP1    >                ; primeiro caractere a ser packed
DP2    >                ; segundo caractere a ser packed
DP3    >                ; word packed a ser expertada
DU1    >                ; word a ser unpacked
DU2    >                ; primeiro caracter da word unpacked
DU3    >                ; segundo caractere da word unpacked
VARTMP >                ; exporting vartmp
VARTMP2 >               ; exporting vartmp 2
VAREND >                ; exporting VAREND
VAREND2 >               ; exporting VAREND2
CHAREND1  >             ; endereco de char 1
CHAREND2  >             ; endereco de char 1
CHAR1     >             ; primeiro caracter
CHAR2     >             ; segundo caracter
CHAR3     >             ; terceiro caracter
CHAR4     >             ; quarto caracter
CHTOUI <                ; importando subrotina de CHTOUI
UITOCH <                ; importando subrotina de UITOCH

          &  /0000      ; tornando o codigo realocavel
          JP MAIN       ; comecando o main
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
VARTMP    K  /0000      ; temporary variable
VARTMP2   K  /0000      ; temparary variable 2
VAREND    K  DC1        ; endereco de DC1
VAREND2   K  DU2       ; endereco da primeira palavra unpacked
CHAREND1  K  CHAR1      ; endereco de char 1, sera alterado em CHTOUI
CHAREND2  K  CHAR1      ; endereco de char 1, sera alterado em UITOCH
CHAR1     K  /1000      ; primeiro caracter
CHAR2     K  /0100      ; segundo caracter
CHAR3     K  /0010      ; terceiro caracter
CHAR4     K  /0001      ; quarto caracter

;MAIN     SC CHTOUI     ; Para executar esta funcao, comente a proxima e descomente esta.
MAIN      SC UITOCH     ;
FIM       HM FIM        ; final do programa


          # FIM
