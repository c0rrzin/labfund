&	/0000	;	Origem Realocavel



DUMPER 		> 
DUMP_INI 	>
DUMP_TAM 	>
DUMP_UL 	>
DUMP_BL 	>
DUMP_EXE 	>


DUMPER		JP	/0000		;	Origem da sub - rotina
INI			LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
			+	LE_DADO		;	Soma ao conteudo do acumulador a constante LE_DADO
			MM	END_MUT		;	Escreve em END_MUT o conteudo do acumulador
END_MUT		K	/0000		;	Executa esse comando
			PD	/300		;	Coloca o conteudo do acumulador no arquivo da UL
			LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
			+	DOIS		;	Soma DOIS ao conteudo do acumulador
			MM	LE_END		;	Armazena o conteudo do acumulador em LE_END
			-	VER_FIM		;	Subtrai do cconteudo do acumulador VER_FIM
			JZ	CONT
			JP	INI			;	Desvio incondicional para INI
CONT		LD	LE_END		;	Carrega no acumulador o conteudo de LE_END
			+	LE_DADO		;	Soma ao conteudo do acumulador a constante LE_DADO
			MM	ULT			;	Escreve em END_MUT o conteudo do acumulador
ULT			K	/0000		;	Executa esse comando
			PD	/300		;	Coloca o conteudo do acumulador no arquivo da UL			
			RS	DUMPER		;	Fim da subrotina
			
			
;	Constantes
DOIS		K	/0002
LE_DADO		K	/8000
VER_FIM		K	/0FFE

;	Variavel	
LE_END		K	/0000


			#	DUMPER