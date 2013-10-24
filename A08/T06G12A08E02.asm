&	/0000	;	Origem Realocavel



DUMPER 		> 
DUMP_INI 	>
DUMP_TAM 	>
DUMP_UL 	>
DUMP_BL 	>
DUMP_EXE 	>

;	Entradas
DUMP_INI	K	/0000
DUMP_TAM	K	/0000
DUMP_UL		K	/0000




DUMPER		JP	/0000		;	Origem da sub - rotina
			LD	DUMP_INI	;	Carrega no acumulador o conteudo de DUMP_INI
			MM	LE_END		;	Escreve em LE_END o conteudo do acumulador
			LD	DUMP_TAM	;	Carrega no acumulador o conteudo de DUMP_TAM
			+	LE_END		;	Soma ao conteudo do acumulador o conteudo de LE_END
			MM	VER_FIM		;	Armazena o conteudo acumulador em VER_FIM
			LD	DUMP_UL		;	Carrega no acumulador o onteudo de DUMP_UL
			+	ESC_DADO	;	Soma oa conteudo do acumualdor o conteudo de ESC_DADO
			MM	ESCRITA1
			MM	ESCRITA2
INI			LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
			+	LE_DADO		;	Soma ao conteudo do acumulador a constante LE_DADO
			MM	END_MUT		;	Escreve em END_MUT o conteudo do acumulador
END_MUT		K	/0000		;	Executa esse comando
ESCRITA1	K	/0000		;	Coloca o conteudo do acumulador no arquivo da UL
			LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
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
			RS	DUMPER		;	Fim da subrotina
			
			
;	Constantes
DOIS		K	/0002
LE_DADO		K	/8000
VER_FIM		K	/0000
ESC_DADO 	PD 	/300

;	Variavel	
LE_END		K	/0000


			#	DUMPER