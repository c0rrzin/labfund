LOADER		<
LOADER_UL 	<

;========================= NÃO ALTERE ESTE TRECHO: INÍCIO ===================;
& /0000
MAIN			JP	START	; Salta para início do main

; PARAMETROS
UL_TEST			K	/0000	; Unidade logica do disco a ser usado

;========================== NÃO ALTERE ESTE TRECHO: FIM =====================;

START			LD	UL_TEST
				MM	LOADER_UL	; Invoca loader
				SC 	LOADER		
FIM_MAIN		HM	FIM_MAIN	; Fim do main

# MAIN
				
