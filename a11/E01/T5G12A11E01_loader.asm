LOADER >
LOADER_UL >

& /0000

LOADER_CONST_LOAD     K /8000
LOADER_CONST_STORE    K /9000
LOADER_CONST_2        K /2
LOADER_CONST_GD_DISCO K /D300
LOADER_CONST_ADDR_MAX K /1000

LOADER_ERROR_MESSAGE K /4572
	K /726F
	K /0000

;==============================================
; Rotina do loader
;==============================================
LOADER_UL K =0
LOADER_INI K =0
LOADER_TAM K =0
LOADER_BLOCK_TAM K =0
LOADER_CHECKSUM K =0
LOADER K /0000

	; Cria a instrução GET DATA
	LD LOADER_UL
	+ LOADER_CONST_GD_DISCO
	MM LOADER_GD_INI
	MM LOADER_GD_TAM
	MM LOADER_GD_BLOCK_INI
	MM LOADER_GD_BLOCK_TAM
	MM LOADER_GD_BLOCK_DATA
	MM LOADER_GD_BLOCK_CHECKSUM
	MM LOADER_GD_EXE

	; Carrega o inicial, o tamanho e faz a
	; checagem de endereços válidos
	LOADER_GD_INI K =0
	MM LOADER_INI
	JN LOADER_ERROR
	LOADER_GD_TAM K =0
	MM LOADER_TAM
	JN LOADER_ERROR

	LD LOADER_CONST_ADDR_MAX
	- LOADER_INI
	- LOADER_TAM
	JN LOADER_ERROR


	LOADER_LOOP $ =0
		LD LOADER_TAM
		JZ LOADER_LOOP_END

		LV /0
		MM LOADER_CHECKSUM
		LOADER_GD_BLOCK_INI K /0000
		MM LOADER_INI
		LOADER_GD_BLOCK_TAM K /0000
		MM LOADER_BLOCK_TAM

		LOADER_BLOCK_LOOP $ =0
			LD LOADER_BLOCK_TAM
			JZ LOADER_BLOCK_LOOP_END
			
			LD LOADER_INI
			+ LOADER_CONST_STORE
			MM LOADER_MM_BLOCK_DATA

			LOADER_GD_BLOCK_DATA K /0000
			LOADER_MM_BLOCK_DATA K /0000
			+ LOADER_CHECKSUM
			MM LOADER_CHECKSUM

			LD LOADER_INI
			+ LOADER_CONST_2
			MM LOADER_INI

			LD LOADER_TAM
			- LOADER_CONST_2
			MM LOADER_TAM
			LD LOADER_BLOCK_TAM
			- LOADER_CONST_2
			MM LOADER_BLOCK_TAM

			JP LOADER_BLOCK_LOOP


		LOADER_BLOCK_LOOP_END $ =0

		LOADER_GD_BLOCK_CHECKSUM K /0000
		- LOADER_CHECKSUM
		JZ LOADER_LOOP
		JP LOADER_ERROR

	LOADER_LOOP_END $ =0

	LOADER_GD_EXE K /0000

	RS LOADER

;==============================================
; Rotina de erro do loader
;==============================================
LOADER_ERROR_CURRENT K /0000
LOADER_ERROR $ =0
	LV LOADER_ERROR_MESSAGE
	MM LOADER_ERROR_CURRENT

LOADER_ERROR_LOOP $ =0
	LD LOADER_ERROR_CURRENT
	+  LOADER_CONST_LOAD
	MM LOADER_ERROR_LD
LOADER_ERROR_LD K /0000
	JZ LOADER_ERROR_END

	PD /100
	LD LOADER_ERROR_CURRENT
	+ LOADER_CONST_2
	MM LOADER_ERROR_CURRENT

	JP LOADER_ERROR_LOOP

LOADER_ERROR_END RS LOADER



# LOADER