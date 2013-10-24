
DUMPER 		> 
DUMP_INI 	>
DUMP_TAM 	>
DUMP_UL 	>
DUMP_BL 	>
DUMP_EXE 	>


& /0000

DUMPER_CONST_2        K /2
DUMPER_CONST_LD       K /8000
DUMPER_CONST_PD_DISCO K /E300
DUMPER_CONST_MAX_TAM  K /1000

;==============================================
; DUMPER
;==============================================
; Parâmetros
DUMP_INI K /0000
DUMP_TAM K /0000
DUMP_UL  K /0000
DUMP_BL  K /0000
DUMP_EXE K /0000
; Inicio da função
DUMPER K /0000

	; Cria a instrução PUT DATA
	LD DUMP_UL
	+ DUMPER_CONST_PD_DISCO
	MM DUMPER_DUMP_MEM

; Loop de dump
DUMPER_LOOP $ =0
	LD DUMP_TAM
	JZ DUMPER_END

	; Faz o dump do endereço
	LD DUMP_INI
	+ DUMPER_CONST_LD
	MM DUMPER_LOAD_MEM
DUMPER_LOAD_MEM K /0000
DUMPER_DUMP_MEM K /0000

	; Incrementa o tamanho
	LD DUMP_INI
	+ DUMPER_CONST_2
	MM DUMP_INI

	; Decrementa o tamanho
	LD DUMP_TAM
	- DUMPER_CONST_2
	MM DUMP_TAM

	; Continua o loop
	JP DUMPER_LOOP

	DUMPER_END $ =0
RS DUMPER 

# DUMPER