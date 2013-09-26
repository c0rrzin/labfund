;========================================
; Arquivo de rotinas
; -------
; Rotinas exportadas:
;     CHTOUI
;     UITOCH
;     PACK
;     UNPACK
;     PACK4
;     UNPACK4
;========================================

; Nomes exportados
CHTOUI >
CHTOUI_1 >
CHTOUI_2 >
UITOCH >
UITOCH_1 >
UITOCH_2 >

PACK >
PACK_1 >
PACK_2 >
UNPACK >
UNPACK_1 >
UNPACK_2 >
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

; Nomes importados
CONST_1 <
CONST_2 <
CONST_6 <
CONST_A <
CONST_10 <
CONST_100 <
CONST_1000 <
CONST_LOAD <
CONST_STORE <
CONST_FFFF <
CONST_H2A_0 <
CONST_H2A_A <

; Biblioteca
& /0000


; ===============
; CHTOUI_SINGLE
; ------
; Parametros:
;     AC: Caracter para hexa
;     CHTOUI_SINGLE_INVALID_JP: Pulo caso seja inválido
; Saidas:
;     AC: Hexadecimal
; ==============
	CHTOUI_SINGLE_AC $ =1
	CHTOUI_SINGLE_INVALID_JP $ =1
CHTOUI_SINGLE $ =1
	MM CHTOUI_SINGLE_AC

	; Seta a instrução de inválido
	LD CHTOUI_SINGLE_INVALID_JP
	MM CHTOUI_SINGLE_INVALID_INSTRUCTION

	; Verifica o intervalo do ASCII
	; Inválido pois <0x30
	LD CHTOUI_SINGLE_AC
	- CONST_H2A_0
	JN CHTOUI_SINGLE_INVALID_INSTRUCTION
	; Entre 0x30 e 0x39
	- CONST_A
	JN CHTOUI_SINGLE_BETWEEN_0_9
	; Inválido: Entre 0x3A e 0x3F
	LD CHTOUI_SINGLE_AC
	- CONST_H2A_A
	JN CHTOUI_SINGLE_INVALID_INSTRUCTION
	; Entre 0x41 e 0x46 (A a F)
	- CONST_6
	JN CHTOUI_SINGLE_BETWEEN_A_F

	; Inválido
	JP CHTOUI_SINGLE_INVALID_INSTRUCTION

	; Entre 0x0 e 0x9
	CHTOUI_SINGLE_BETWEEN_0_9 LD CHTOUI_SINGLE_AC
	- CONST_H2A_0
	RS CHTOUI_SINGLE

	; Entre 0xA e 0xF
	CHTOUI_SINGLE_BETWEEN_A_F LD CHTOUI_SINGLE_AC
	- CONST_H2A_A
	+ CONST_A
	RS CHTOUI_SINGLE

	; Inválido
	CHTOUI_SINGLE_INVALID_INSTRUCTION $ =1
	RS CHTOUI_SINGLE
; CHTOUI_SINGLE

; ===============
; CHTOUI
; ------
; Parametros:
;     CHTOUI_1: Endereço da word1
;     CHTOUI_2: Endereço da word2
; Saidas:
;     AC: Valor representado pelos ascii
; ==============
	CHTOUI_1 $ =1
	CHTOUI_2 $ =1

	CHTOUI_TMP_1 $ =1
	CHTOUI_TMP_2 $ =1
	CHTOUI_TMP_3 $ =1
	CHTOUI_TMP_4 $ =1
CHTOUI $ =1
	; Faz o unpack da primeira word
	LD CHTOUI_1
	+ CONST_LOAD
	MM CHTOUI_LOAD_1
	LV CHTOUI_TMP_1
	MM UNPACK_1
	LV CHTOUI_TMP_2
	MM UNPACK_2
	CHTOUI_LOAD_1 $ =1
	SC UNPACK

	; Faz o unpack da segunda word
	LD CHTOUI_2
	+ CONST_LOAD
	MM CHTOUI_LOAD_2
	LV CHTOUI_TMP_3
	MM UNPACK_1
	LV CHTOUI_TMP_4
	MM UNPACK_2
	CHTOUI_LOAD_2 $ =1
	SC UNPACK

	; Seta o pulo da função CHTOUI_SINGLE caso seja inválido
	LV CHTOUI_INVALID
	MM CHTOUI_SINGLE_INVALID_JP

	; Converte os 4 ascii para hexa
	LD CHTOUI_TMP_1
	SC CHTOUI_SINGLE
	MM CHTOUI_TMP_1
	LD CHTOUI_TMP_2
	SC CHTOUI_SINGLE
	MM CHTOUI_TMP_2
	LD CHTOUI_TMP_3
	SC CHTOUI_SINGLE
	MM CHTOUI_TMP_3
	LD CHTOUI_TMP_4
	SC CHTOUI_SINGLE
	MM CHTOUI_TMP_4

	; Salva no acumulador
	LD CHTOUI_TMP_1
	* CONST_10
	+ CHTOUI_TMP_2
	* CONST_10
	+ CHTOUI_TMP_3
	* CONST_10
	+ CHTOUI_TMP_4
	RS CHTOUI

	; Endereço inválido
	CHTOUI_INVALID $ =0
	LD CONST_FFFF
	RS CHTOUI
; CHTOUI

; ===============
; UITOCH_SINGLE
; ------
; Parametros:
;     AC: Hexa a ser convertido em ASCII
; Saidas:
;     AC: Caracter convertido em ASCII
; ==============
	UITOCH_SINGLE_AC $ =1
UITOCH_SINGLE $ =1
	MM UITOCH_SINGLE_AC

	; Verifica o intervalo do Hex
	- CONST_A
	JN UITOCH_SINGLE_BETWEEN_0_9

	; Entre 0xa e 0xf
	+ CONST_H2A_A
	RS UITOCH_SINGLE

	; Entre 0x0 e 0x09
	UITOCH_SINGLE_BETWEEN_0_9 LD UITOCH_SINGLE_AC
	+ CONST_H2A_0
	RS UITOCH_SINGLE
; UITOCH_SINGLE

; ===============
; UITOCH
; ------
; Parametros:
;	 AC: 4 Hexas que serao convertidos em ASCII
; Saidas:
;     UITOCH_1: primeiros 2 hexas convertidos em ASCII
;     UITOCH_2: ultimos 2 hexas convertidos em ASCII
; ==============
	UITOCH_1 $ =1 ; Enrereço onde salvará a primeira word
	UITOCH_2 $ =1 ; Endereço que salvará a segunda word
	UITOCH_AC $ =1 ; Valor do acumulador

	UITOCH_TMP_1 $ =1
	UITOCH_TMP_2 $ =1
	UITOCH_TMP_3 $ =1
	UITOCH_TMP_4 $ =1
; def uitouch (in1, in2, in3, in4)
UITOCH $ =1
	MM UITOCH_AC
	; Faz o unpack
	LV UITOCH_TMP_1
	MM UNPACK4_1
	LV UITOCH_TMP_2
	MM UNPACK4_2
	LV UITOCH_TMP_3
	MM UNPACK4_3
	LV UITOCH_TMP_4
	MM UNPACK4_4
	LD UITOCH_AC
	SC UNPACK4

	LD UITOCH_TMP_1
	SC UITOCH_SINGLE
	MM UITOCH_TMP_1

	LD UITOCH_TMP_2
	SC UITOCH_SINGLE
	MM UITOCH_TMP_2

	LD UITOCH_TMP_3
	SC UITOCH_SINGLE
	MM UITOCH_TMP_3

	LD UITOCH_TMP_4
	SC UITOCH_SINGLE
	MM UITOCH_TMP_4

	; Salva o primeiro character
	LD CONST_STORE
	+ UITOCH_1
	MM UITOCH_STORE_OUT_1
	LD UITOCH_TMP_1
	* CONST_100
	+ UITOCH_TMP_2
	UITOCH_STORE_OUT_1 $ =1

	; Salva o segundo caracter
	LD CONST_STORE
	+ UITOCH_2
	MM UITOCH_STORE_OUT_2
	LD UITOCH_TMP_3
	* CONST_100
	+ UITOCH_TMP_4
	UITOCH_STORE_OUT_2 $ =1
RS UITOCH

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
