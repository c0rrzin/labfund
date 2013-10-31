DUMPER 		> 
DUMP_INI 	>
DUMP_TAM 	>
DUMP_UL 	>

& /0000

ULTIMA_MEM	K	/0000 			; ultima posi��o da memoria
CONT_CTRL	K	/0000 			; contador de endere�os da mem�ria
PROX_POS	K	/0002			; tamanho de uma posi��o de mem�ria


LOAD		K 	/8000			; opera��o de LOAD
PUTDATA		K	/E300 			; opera��o PUT DATA

DUMP_INI	K	/0000
DUMP_TAM	K	/0000
DUMP_UL		K	/0000

DUMPER		K 	/0000 			; in�cio da subrotina
; 
			LD 	DUMP_INI		; carrega o endere�o inicial da mem�ria
			MM	CONT_CTRL		; inicia o contador de endere�os com o valor do endere�o inicial
			+	DUMP_TAM		; soma com o tamanho para obter o ultimo endere�o
			-	PROX_POS
			MM	ULTIMA_MEM		; guarda o ultimo endere�o da mem�ria
LOOP		LD	CONT_CTRL 		; carrega o pr�ximo endere�o da mem�ria
			+	LOAD			; monta a intru��o de LOAD
			MM	INSTR_LOAD		; grava a instru��o para ser executada (LOAD)
			LD	PUTDATA			; monta a instru��o PD
			+	DUMP_UL			; passa a unidade l�gica como param.
			MM  INSTR_PD		; grava a instru��o PD
INSTR_LOAD  K	/0000			; executa a instru��o de LOAD			
INSTR_PD	K	/0000			; escreve conte�do do acumulador na unidade l�gica 0 do dispositivo 3 (disco)
			LD	CONT_CTRL		; carrega o contador de endere�os no acumulador
			-	ULTIMA_MEM   	; subtrai o ultimo endere�o da mem�ria
			JZ	FIM				; verifica se chegou ao fim da memoria
			LD	CONT_CTRL		; carrega o contador de endere�os no acumulador
			+	PROX_POS		; incrementa o contador
			MM	CONT_CTRL		; guarda o novo endere�o da mem�ria
			JP	LOOP			; retorna ao in�cio
FIM			RS DUMPER			; fim da subrotina
			
# DUMPER