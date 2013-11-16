; GETLINEF - MAIN

GETLINEF <
GL_END <
GL_UL <
GL_BUF <

& /0000
MAIN  JP  START

VAL_UL  K   /0001   ; UL do arquivo
VAL_BUF K   =10     ; Tamanho do buffer
BUFFER  $   /000A   ; Buffer: algumas posi��es reservadas

START    GD /301    ;
         JP DOOS
         K  /0001    ;parametro a ser passado
DOOS     OS /01FF
;        LD  VAL_BUF     ; Param 1: tamanho do buffer
;        MM  GL_BUF
;        LD  VAL_UL    ; Param 2: unidade l�gica
;        MM  GL_UL
;        LV  BUFFER    ; Param 3: endere�o do buffer
;        MM  GL_END
;        SC  GETLINEF  ; Chama subrotina
END     HM  END     ; fim do programa

# MAIN
