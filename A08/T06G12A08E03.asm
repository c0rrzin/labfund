&	/0000	;	Origem Realocavel



DUMPER 		> 
DUMP_INI 	>
DUMP_TAM 	>
DUMP_UL 	>
DUMP_BL 	>
DUMP_EXE 	>

;	Entradas
DUMP_INI	K	/0000	;	Endereço Inicial
DUMP_TAM	K	/0000	;	Tamanho total da imagem
DUMP_UL		K	/0000	;	Numero da unidade logica
DUMP_BL		K	/0000	;	Tamanho do bloco
DUMP_EXE	K	/0000	;	Endereço da primeira instruçao executavel




DUMPER		JP	/0000		;	Origem da sub - rotina
			LD	DUMP_UL		;	Carrega no acumulador o onteudo de DUMP_UL
			+	ESC_DADO	;	Soma oa conteudo do acumualdor o conteudo de ESC_DADO
			MM	ESCRITA1
			MM	ESCRITA2
			MM	ESCRITA3
			MM	ESCRITA4
			MM	ESCRITA5
			MM	ESCRITA6
			MM	ESCRITA7
			MM	ESCRITA8
			MM	ESCRITA9
			LD	DUMP_INI	;	Carrega no acumulador o conteudo de DUMP_INI
			MM	LE_END		;	Escreve em LE_END o conteudo do acumulador
ESCRITA4	K	/0000
			LD	DUMP_TAM	;	Carrega no acumulador o conteudo de DUMP_TAM
			+	LE_END		;	Soma ao conteudo do acumulador o conteudo de LE_END
			MM	VER_FIM		;	Armazena o conteudo acumulador em VER_FIM
			LD	DUMP_BL		;	Carrega no acumulador o conteudo de DUMP_BL
ESCRITA5	K	/0000
			MM	TAM_BL		;	Escreve em TAM_BL o conteudo do acumulador
INI			LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
			+	LE_DADO		;	Soma ao conteudo do acumulador a constante LE_DADO
			MM	END_MUT		;	Escreve em END_MUT o conteudo do acumulador
END_MUT		K	/0000		;	Executa esse comando
ESCRITA1	K	/0000		;	Coloca o conteudo do acumulador no arquivo da UL
			+	CHECK_SUM	;	Soma ao conteudo do acumulador CHECK_SUM
			MM	CHECK_SUM	;	Armazena o conteudo do acumulador em CHECK_SUM
			LD	TAM_BL		;	Carrega no acumulador o conteudo de TAM_BL
			-	UM			;	Subtrai do conteudo do acumulador a constante UM
			MM	TAM_BL		;	Armazena o conteudo do acumualdor em TAM_BL
			JZ	FIM_BL		;	Pula para o rotulo FIM_BL
			JP	CONT2		;	Desvio incondicional para CONT2
FIM_BL		LD	CHECK_SUM	
ESCRITA3	K	/0000
			LD	DUMP_BL		;	Carrega no acumulador o conteudo de DUMP_BL
			MM	TAM_BL		;	Escreve em TAM_BL o conteudo do acumulador
			LD	ZERO		;	Carrega no acumulador a constante ZERO
			MM	CHECK_SUM	;	Armazena o conteudo do acumulador em CHECK_SUM
			LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
ESCRITA6	K	/0000
			LD	DUMP_BL	;	Carrega no acumulador o conteudo de DUMP_TAM
ESCRITA7	K	/0000

CONT2		LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
			+	DOIS		;	Soma DOIS ao conteudo do acumulador
			MM	LE_END		;	Armazena o conteudo do acumulador em LE_END
			-	VER_FIM		;	Subtrai do conteudo do acumulador VER_FIM
			JZ	CONT
			JP	INI			;	Desvio incondicional para INI
CONT		LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
			+	LE_DADO		;	Soma ao conteudo do acumulador a constante LE_DADO
			MM	ULT			;	Escreve em END_MUT o conteudo do acumulador
ULT			K	/0000		;	Executa esse comando
ESCRITA2	K	/0000		;	Coloca o conteudo do acumulador no arquivo da UL
			+	CHECK_SUM	;	Soma ao conteudo do acumulador CHECK_SUM
ESCRITA8	K	/0000	
			LD	DUMP_EXE	;	Carrega no acumulador o conteudo de DUMP_EXE
ESCRITA9	K	/0000
			RS	DUMPER		;	Fim da subrotina
			
			
;	Constantes
ZERO		K	/0000
UM			K	/0001
DOIS		K	/0002
LE_DADO		K	/8000
VER_FIM		K	/0000
ESC_DADO 	PD 	/300

;	Variavel	
LE_END		K	/0000
CHECK_SUM	K	/0000
TAM_BL		K	/0000

			#	DUMPER