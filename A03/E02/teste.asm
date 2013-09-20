; ===============
; UITOCH
; ------
; Parametros:
; UITOCH_1: 4 Hexas que serao convertidos em ASCII
; Saidas:
; UITOCH_OUT_1: primeiros 2 hexas convertidos em ASCII
; UITOCH_OUT_2: ultimos 2 hexas convertidos em ASCII
; EX.:
; UITOCH_1 /1234
; Retorno:
; UITOCH_OUT_1 /3132
; UITOCH_OUT_2 /3334
; ==============
@ /0000
I JP MAIN
OUT_1 K /0
OUT_2 K /0
MAIN $ =0
SC UITOCH
RETURN LD UITOCH_OUT_1
MM OUT_1
LD UITOCH_OUT_2
MM OUT_2
XPTO HM XPTO
# MAIN


CONST_H2A_0  K /30
CONST_H2A_A  K /41
CONST_1      K /1
CONST_A      K /A
CONST_10     K /10
CONST_F      K /F
CONST_ERROR  K /FFFF
CONST_100    K /100
CONST_1000   K /1000
CONST_STORE  K /9000
UITOCH_1 K /700F ; VARIAVEL DE ENTRADA
UITOCH_TMP_1 $ =1
UITOCH_TMP_2 $ =1
UITOCH_TMP_3 $ =1
UITOCH_TMP_4 $ =1
UITOCH_OUT_1 $ =1 ; VARIAVEIS DE SAIDA
UITOCH_OUT_2 $ =1 ; VARIAVEIS DE SAIDA
; def uitouch (in1, in2, in3, in4)
UITOCH $ =1
  LV UITOCH_TMP_1
  MM UNPACK4_1
  ; unpack(uitouch_1)
  LV UITOCH_TMP_2
  MM UNPACK4_2
  LV UITOCH_TMP_3
  MM UNPACK4_3
  LV UITOCH_TMP_4
  MM UNPACK4_4
  LD UITOCH_1
  SC UNPACK4
  LD UITOCH_TMP_1
  JP CASE


  ; loop {
    ;loop variables
  LOOP_COUNTER $ =1
  CURRENT_HEX $ =1
  CONVERTED_ASCII $ =1

  ;case current_hex
  CASE MM CURRENT_HEX


  - CONST_A
  JN ELSE_2 ; if 0xF > current_hex > 0x9
  + CONST_H2A_A
  MM CONVERTED_ASCII
  JP INSTR
  ELSE_2 + CONST_A ; else (current_hex entre 9 e 0)
  + CONST_H2A_0
  MM CONVERTED_ASCII ; converted_ascii = acumulador


  INSTR LD LOOP_COUNTER
  JZ FIRST_ITERATION ; if counter = 0; jump
  - CONST_1
  JZ SECOND_ITERATION ; if counter = 1; jump
  - CONST_1
  JZ THIRD_ITERATION ; if counter = 2; jump
  ; else (counter = 3)
  LD UITOCH_TMP_3
    * CONST_100
    + CONVERTED_ASCII ; UITOCH_OUT_2 = 100*UITOCH_TMP_3 + CONVERTED_ASCII
    MM UITOCH_OUT_2
    JP END_CASE
    ; end loop

  THIRD_ITERATION LD CONVERTED_ASCII
    MM UITOCH_TMP_3 ; UITOCH_TMP_3 = CONVERTED_ASCII
    LV /3
    MM LOOP_COUNTER ; counter = 3
    LD UITOCH_TMP_4
    JP CASE
    ; next

  SECOND_ITERATION LD UITOCH_TMP_1
    * CONST_100
    + CONVERTED_ASCII
    MM UITOCH_OUT_1 ; UITOCH_OUT_1 = 100*UITOCH_TMP_1 + CONVERTED_ASCII
    LV /2
    MM LOOP_COUNTER ; counter = 2
    LD UITOCH_TMP_3
    JP CASE
    ; next

  FIRST_ITERATION LD CONVERTED_ASCII
    MM UITOCH_TMP_1 ; UITOCH_TMP_1 = CONVERTED_ASCII
    LV /1
    MM LOOP_COUNTER ; counter = 1
    LD UITOCH_TMP_2
    JP CASE
    ; next

  END_CASE $ =0
  RS UITOCH
  ; end loop }

  ;========================================
; UNPACK
; -------
; Parametros: 
;     UNPACK_1 : Endereço de B1
;     UNPACK_2 : Endereço de B2
;     AC       : Valor a sofrer o UNPACK B1B2
;========================================
  ; Parametros
  UNPACK_1 $ =1 ;ENDEREÇO DE B1
  UNPACK_2 $ =1 ;ENDEREÇO DE B2
  ; variaveis
  UNPACK_AC $ =1             ; Acumulador
  UNPACK_1_TMP $ =1          ; Valor temporário de B1
  UNPACK_2_TMP $ =1          ; Valor temporário de B2
  
; Inicio da função
UNPACK  MM UNPACK_AC
  ; Salva o valor do acumulador
  
  ; Pega o B2
  *  CONST_100
  /  CONST_100
  MM UNPACK_2_TMP

  ; Pega o B1
  LD UNPACK_AC
  -  UNPACK_2_TMP
  /  CONST_100
  MM UNPACK_1_TMP
  
  ; Salva o B1
  LD UNPACK_1
  + CONST_STORE
  MM UNPACK_STORE_1
  LD UNPACK_1_TMP
  UNPACK_STORE_1 $ =1
  
  ; Salva o B2
  LD UNPACK_2
  + CONST_STORE
  MM UNPACK_STORE_2
  LD UNPACK_2_TMP
  UNPACK_STORE_2 $ =1
; Retorna  
RS UNPACK

;========================================
; PACK4
; -------
; Parametros: 
;     PACK4_1
;     PACK4_2
;     PACK4_3
;     PACK4_4
; AC = resultado
;========================================
  PACK4_1 $ =1
  PACK4_2 $ =1
  PACK4_3 $ =1
  PACK4_4 $ =1
PACK4 $ =1
  LD  PACK4_1
  * CONST_10
  +   PACK4_2
  * CONST_10
  +   PACK4_3
  * CONST_10
  +   PACK4_4
  RS PACK4

;========================================
; UNPACK4
; -------
; Parametros: 
;     UNPACK_1 : Endereço de B1
;     UNPACK_2 : Endereço de B2
;     UNPACK_3 : Endereço de B3
;     UNPACK_4 : Endereço de B4
;     AC       : Valor a sofrer o UNPACK B1B2B3B4
;========================================
  ; Parametros
  UNPACK4_1 $ =1 ;ENDEREÇO DE B1
  UNPACK4_2 $ =1 ;ENDEREÇO DE B2
  UNPACK4_3 $ =1 ;ENDEREÇO DE B3
  UNPACK4_4 $ =1 ;ENDEREÇO DE B4
  ; variaveis
  UNPACK4_AC $ =1             ; Acumulador
  UNPACK4_1_TMP $ =1          ; Valor temporário de B1
  UNPACK4_2_TMP $ =1          ; Valor temporário de B2
  UNPACK4_3_TMP $ =1          ; Valor temporário de B1
  UNPACK4_4_TMP $ =1          ; Valor temporário de B2
  
; Inicio da função
UNPACK4   $ =1
  ; Salva o valor do acumulador
  MM UNPACK4_AC
  
  ; Pega o B1
  /  CONST_1000
  * CONST_1000
  MM UNPACK4_1_TMP

  ; Pega o B2
  LD UNPACK4_AC
  -  UNPACK4_1_TMP
  /  CONST_100
  * CONST_100
  MM UNPACK4_2_TMP

  ; Pega o B3
  LD UNPACK4_AC
  -  UNPACK4_1_TMP
  -  UNPACK4_2_TMP
  /  CONST_10
  * CONST_10
  MM UNPACK4_3_TMP

  ; Pega o B4
  LD UNPACK4_AC
  -  UNPACK4_1_TMP
  -  UNPACK4_2_TMP
  -  UNPACK4_3_TMP
  MM UNPACK4_4_TMP
  
  ; Salva o B1
  LD UNPACK4_1
  + CONST_STORE
  MM UNPACK4_STORE_1
  LD UNPACK4_1_TMP
  / CONST_1000
  UNPACK4_STORE_1 $ =1
  
  ; Salva o B2
  LD UNPACK4_2
  + CONST_STORE
  MM UNPACK4_STORE_2
  LD UNPACK4_2_TMP
  / CONST_100
  UNPACK4_STORE_2 $ =1

  ; Salva o B3
  LD UNPACK4_3
  + CONST_STORE
  MM UNPACK4_STORE_3
  LD UNPACK4_3_TMP
  / CONST_10
  UNPACK4_STORE_3 $ =1

  ; Salva o B4
  LD UNPACK4_4
  + CONST_STORE
  MM UNPACK4_STORE_4
  LD UNPACK4_4_TMP
  UNPACK4_STORE_4 $ =1
; Retorna  
RS UNPACK4

# UNPACK4