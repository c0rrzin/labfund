LOADER		<
LOADER_UL 	<

;========================= N�O ALTERE ESTE TRECHO: IN�CIO ===================;
& /0000
MAIN			JP	START	; Salta para in�cio do main

; PARAMETROS
UL_TEST			K	/0000	; Unidade logica do disco a ser usado

;========================== N�O ALTERE ESTE TRECHO: FIM =====================;

START			LD	UL_TEST
				MM	LOADER_UL	; Invoca loader
				SC 	LOADER		
FIM_MAIN		HM	FIM_MAIN	; Fim do main

# MAIN
				
