; MAIN
@ /0000
I JP MAIN 
OUTPUT K /0000 ; SAIDA
; LISTA DE CARACTERES DA 1A STRING
STR1_WORD1 K /6566
STR1_WORD2 K /6566
STR1_WORD3 K /0000
STR1_WORD4 K /0000
STR1_WORD5 K /0000
; LISTA DE CARACTERES DA 2A STRING
STR2_WORD1 K /6567
STR2_WORD2 K /6763
STR2_WORD3 K /0000
STR2_WORD4 K /0000
STR2_WORD5 K /0000

MAIN LV /0 ;FAZ NADA
LD STR1_WORD1
MM STRCMP_1
LD STR2_WORD1
MM STRCMP_2
SC STRCMP
END HM END

# I


; UNPACK
; LÓGICA DE UNPACK
@ /0600
  UNPACK_1 $ =1 ;ENDEREÇO DE B1
  UNPACK_2 $ =1 ;ENDEREÇO DE B2
  UNPACK_CONST K /0100
  UNPACK_CONST_STORE K /9000
  UNPACK_CONST_NEG   K /FFFF
  UNPACK_AC $ =1
  UNPACK_1_TMP $ =1
  UNPACK_2_TMP $ =1 
  
UNPACK $ =1
  MM UNPACK_AC
  *  UNPACK_CONST
  /  UNPACK_CONST
  MM UNPACK_2_TMP
  
  LD UNPACK_AC
  -  UNPACK_2_TMP
  /  UNPACK_CONST
  MM UNPACK_1_TMP
  
; MUDAR OS ENDEREÇOS INICIAIS
  LD UNPACK_1
  + UNPACK_CONST_STORE
  MM UNPACK_STORE_1
  LD UNPACK_1_TMP
  UNPACK_STORE_1 $ =1
  LD UNPACK_2
  + UNPACK_CONST_STORE
  MM UNPACK_STORE_2
  LD UNPACK_2_TMP
  UNPACK_STORE_2 $ =1
  
  RS UNPACK

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
@ /0200
	STRCMP_1 $ =1               ; Parametro 1
	STRCMP_2 $ =1               ; Parametro 2
	STRCMP_RET $ =1             ; Variavel de retorno
	STRCMP_CURRENT_1 $ =1       ; Valor dos 2 bytes atuais da string 1
	STRCMP_CURRENT_2 $ =1       ; Valor dos 2 bytes atuais da string 2
	STRCMP_CTE_1 K /1           ; Constante = 1
	STRCMP_CTE_2 K /2           ; Constante = 2
	STRCMP_CTE_LOAD K /8000     ; Constante = /8000 para load
	
@ /0280
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
	+ STRCMP_CTE_LOAD
	MM STRCMP_LOAD_CURRENT_1
STRCMP_LOAD_CURRENT_1 $ =0
	MM STRCMP_CURRENT_1
	; Incrementa a posicao da string 1
	LD STRCMP_1
	+ STRCMP_CTE_2
	MM STRCMP_1

	;
	; Carrega e incrementa a posição da stirng2
	;
	; Carrega os 2 bytes da string 2
	LD STRCMP_2
	+ STRCMP_CTE_LOAD
	MM STRCMP_LOAD_CURRENT_2
STRCMP_LOAD_CURRENT_2 $ =0
	MM STRCMP_CURRENT_2
	; Incrementa a posicao da string 2
	LD STRCMP_2
	+ STRCMP_CTE_2
	MM STRCMP_2
	
	; Faz o unpack da string 1  em STRCMP_CURRENT_1_UNPACKED_X
	LD /0280
	MM UNPACK_1
	LD /0282
	MM UNPACK_2
	LD STRCMP_CURRENT_1
	SC UNPACK
	
	; Faz o unpack da string 2 em STRCMP_CURRENT_2_UNPACKED_X
	LD /0284
	MM UNPACK_1
	LD /0286
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
	JP STRCMP_COMPARE_2
	
; Primeiro byte igual
STRCMP_EQUAL_1 LD STRCMP_RET
	+ STRCMP_CTE_1
	MM STRCMP_RET
	
	; Compara o segundo byte
STRCMP_COMPARE_2 LD STRCMP_CURRENT_1_UNPACKED_2
	JZ STRCMP_RET_LABEL ; Se zero, acabou a string
	LD STRCMP_CURRENT_2_UNPACKED_2
	JZ STRCMP_RET_LABEL ; Se zero, acabou a string
	-  STRCMP_CURRENT_1_UNPACKED_2
	; Checa se são iguais
	JZ STRCMP_EQUAL_2
	JP STRCMP_LOOP_START
	
; Segundo byte igual
STRCMP_EQUAL_2 LD STRCMP_RET
	+ STRCMP_CTE_1
	MM STRCMP_RET
	
	; Continua o loop
	JP STRCMP_LOOP_START
	
;} Fim do loop

	; Label de retorno
STRCMP_RET_LABEL LD STRCMP_RET
	RS STRCMP

