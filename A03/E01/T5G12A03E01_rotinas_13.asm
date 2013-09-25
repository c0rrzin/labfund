PACK >
PACK_1 >
PACK_2 >

UNPACK >
UNPACK_1 >
UNPACK_2 >

STRCMP >
STRCMP_1 >
STRCMP_2 >

CONST_1 <
CONST_2 <
CONST_100 <
CONST_LOAD  <
CONST_STORE <

; Biblioteca de rotinas
& /0000

;========================================
; STRCMP
; -------
; Parametros: 
;     STRCMP_1 : String 1
;     STRCMP_2 : String 2
; AC = Tamanho da string igual
; -------
; Lembrando que cada instrução ocupa 2 bytes
; e cada char da string ocupa 1 byte, apenas
; Sendo necessário o unpack.
;========================================
	STRCMP_1 $ =1               ; Parametro 1
	STRCMP_2 $ =1               ; Parametro 2
	STRCMP_RET $ =1             ; Variavel de retorno
	STRCMP_CURRENT_1 $ =1       ; Valor dos 2 bytes atuais da string 1
	STRCMP_CURRENT_2 $ =1       ; Valor dos 2 bytes atuais da string 2	
	STRCMP_CURRENT_1_UNPACKED_1 $ =1    ; Unpack1 da string1
	STRCMP_CURRENT_1_UNPACKED_2 $ =1    ; Unpack2 da string1
	STRCMP_CURRENT_2_UNPACKED_1 $ =1    ; Unpack1 da string2
	STRCMP_CURRENT_2_UNPACKED_2 $ =1    ; Unpack2 da string2
; Inicio da função
STRCMP $ =1
	; Retorno = 0
	LV /0
	MM STRCMP_RET
	
; Loop{
STRCMP_LOOP_START LV /0 ; Faz nada
	;
	; Carrega e incrementa a posição da stirng1
	;
	; Carrega os bytes da string 1
	LD STRCMP_1
	+ CONST_LOAD
	MM STRCMP_LOAD_CURRENT_1
STRCMP_LOAD_CURRENT_1 $ =1
	MM STRCMP_CURRENT_1
	; Incrementa a posicao da string 1
	LD STRCMP_1
	+ CONST_2
	MM STRCMP_1

	;
	; Carrega e incrementa a posição da stirng2
	;
	; Carrega os 2 bytes da string 2
	LD STRCMP_2
	+ CONST_LOAD
	MM STRCMP_LOAD_CURRENT_2
STRCMP_LOAD_CURRENT_2 $ =1
	MM STRCMP_CURRENT_2
	; Incrementa a posicao da string 2
	LD STRCMP_2
	+ CONST_2
	MM STRCMP_2
	
	; Faz o unpack da string 1  em STRCMP_CURRENT_1_UNPACKED_X
	LV STRCMP_CURRENT_1_UNPACKED_1
	MM UNPACK_1
	LV STRCMP_CURRENT_1_UNPACKED_2
	MM UNPACK_2
	LD STRCMP_CURRENT_1
	SC UNPACK
	
	; Faz o unpack da string 2 em STRCMP_CURRENT_2_UNPACKED_X
	LV STRCMP_CURRENT_2_UNPACKED_1
	MM UNPACK_1
	LV STRCMP_CURRENT_2_UNPACKED_2
	MM UNPACK_2
	LD STRCMP_CURRENT_2
	SC UNPACK
	
	; Testa o primeiro byte
STRCMP_COMPARE_1 LD STRCMP_CURRENT_1_UNPACKED_1
	JZ STRCMP_RET_LABEL ; Se zero, acabou a string
	LD STRCMP_CURRENT_2_UNPACKED_1
	JZ STRCMP_RET_LABEL ; Se zero, acabou a string
	-  STRCMP_CURRENT_1_UNPACKED_1
	; Checa se são iguais
	JZ STRCMP_EQUAL_1 
	JP STRCMP_RET_LABEL
	
; Primeiro byte igual
STRCMP_EQUAL_1 LD STRCMP_RET
	+ CONST_1
	MM STRCMP_RET
	
	; Compara o segundo byte
STRCMP_COMPARE_2 LD STRCMP_CURRENT_1_UNPACKED_2
	JZ STRCMP_RET_LABEL ; Se zero, acabou a string
	LD STRCMP_CURRENT_2_UNPACKED_2
	JZ STRCMP_RET_LABEL ; Se zero, acabou a string
	-  STRCMP_CURRENT_1_UNPACKED_2
	; Checa se são iguais
	JZ STRCMP_EQUAL_2
	JP STRCMP_RET_LABEL
	
; Segundo byte igual
STRCMP_EQUAL_2 LD STRCMP_RET
	+ CONST_1
	MM STRCMP_RET
	
	; Continua o loop
	JP STRCMP_LOOP_START
	
;} Fim do loop

	; Label de retorno
STRCMP_RET_LABEL LD STRCMP_RET
	RS STRCMP

;========================================
; PACK
; -------
; Parametros: 
;     B1 : 200
;     B2 : 202
; AC = resultado
;========================================
	PACK_1 $ =1
	PACK_2 $ =1
PACK $ =1
	LD 	PACK_1
	*	CONST_100
	+   PACK_2
	RS PACK

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
UNPACK 	$ =1
	; Salva o valor do acumulador
	MM UNPACK_AC
	
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

# UNPACK