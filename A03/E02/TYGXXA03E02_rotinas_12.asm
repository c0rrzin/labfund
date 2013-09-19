PACK4 >
PACK4_1 >
PACK4_2 >
PACK4_3 >
PACK4_4 >
UNPACK4 >
UNPACK4_1 >
UNPACK4_2 >
UNPACK4_3 >
UNPACK4_4 >

CONST_10 <
CONST_100 <
CONST_1000 <
CONST_STORE <

; Biblioteca
& /0000

;========================================
; PACK
; -------
; Parametros: 
;     PACK_1 : Primeiro valor a ser dado o pack
;     PACK_2 : Segundo valor a ser dado o pack
;     AC = resultado
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
	LD 	PACK4_1
	*	CONST_10
	+   PACK4_2
	*	CONST_10
	+   PACK4_3
	*	CONST_10
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
UNPACK4 	$ =1
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