DUMPER 		< 
DUMP_INI 	<
DUMP_TAM 	<
DUMP_UL 	<
DUMP_BL 	<
DUMP_EXE 	<

;========================= NÃO ALTERE ESTE TRECHO: INÍCIO ===================;
& /0000
MAIN			JP	START	; Salta para início do main

; PARAMETROS
END_INICIAL		K	/0400	; Endereço onde começa o dump
TAMANHO_TEST	K	/0032	; Numero total de palavras a serem "dumpadas"
UL_TEST			K	/0000	; Unidade logica do disco a ser usado
BL_TEST 		K	/0010	; Tamanho do bloco
EXE_TEST 		K	/0400	; Endereço onde começaria a execução (valor dummy, apenas para manter o formato)

;========================== NÃO ALTERE ESTE TRECHO: FIM =====================;

START			LD	END_INICIAL	; Parametros, na ordem acima
				MM	DUMP_INI
				LD	TAMANHO_TEST
				MM	DUMP_TAM
				LD	UL_TEST
				MM	DUMP_UL
				LD	BL_TEST
				MM	DUMP_BL
				LD	EXE_TEST
				MM	DUMP_EXE
				SC	DUMPER		; Invoca dumper
FIM_MAIN		HM	FIM_MAIN	; Fim do main

# MAIN
				
