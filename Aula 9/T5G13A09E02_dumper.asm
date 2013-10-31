DUMPER 		> 
DUMP_INI 	>
DUMP_TAM 	>
DUMP_UL 	>

& /0000

ULTIMA_MEM	K	/0000 			; ultima posição da memoria
CONT_CTRL	K	/0000 			; contador de endereços da memória
PROX_POS	K	/0002			; tamanho de uma posição de memória


LOAD		K 	/8000			; operação de LOAD
PUTDATA		K	/E300 			; operação PUT DATA

DUMP_INI	K	/0000
DUMP_TAM	K	/0000
DUMP_UL		K	/0000

DUMPER		K 	/0000 			; início da subrotina
; 
			LD 	DUMP_INI		; carrega o endereço inicial da memória
			MM	CONT_CTRL		; inicia o contador de endereços com o valor do endereço inicial
			+	DUMP_TAM		; soma com o tamanho para obter o ultimo endereço
			-	PROX_POS
			MM	ULTIMA_MEM		; guarda o ultimo endereço da memória
LOOP		LD	CONT_CTRL 		; carrega o próximo endereço da memória
			+	LOAD			; monta a intrução de LOAD
			MM	INSTR_LOAD		; grava a instrução para ser executada (LOAD)
			LD	PUTDATA			; monta a instrução PD
			+	DUMP_UL			; passa a unidade lógica como param.
			MM  INSTR_PD		; grava a instrução PD
INSTR_LOAD  K	/0000			; executa a instrução de LOAD			
INSTR_PD	K	/0000			; escreve conteúdo do acumulador na unidade lógica 0 do dispositivo 3 (disco)
			LD	CONT_CTRL		; carrega o contador de endereços no acumulador
			-	ULTIMA_MEM   	; subtrai o ultimo endereço da memória
			JZ	FIM				; verifica se chegou ao fim da memoria
			LD	CONT_CTRL		; carrega o contador de endereços no acumulador
			+	PROX_POS		; incrementa o contador
			MM	CONT_CTRL		; guarda o novo endereço da memória
			JP	LOOP			; retorna ao início
FIM			RS DUMPER			; fim da subrotina
			
# DUMPER