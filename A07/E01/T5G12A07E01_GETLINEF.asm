;========================================
; Arquivo com a rotina GETLINEF
;========================================

GETLINEF >
GL_END >
GL_UL >
GL_BUF >

CONST_GD <
CONST_STORE <
CONST_1 <
CONST_2 <

& /0100

; Constantes
GL_CONST_EOL K /0D0A
GL_CONST_EOF K /FFFF
GL_CONST_300 K /0300

;========================================
; GETLINEF4
; -------
; Parametros: 
;     UNPACK_1 : Endereço de B1
;     UNPACK_2 : Endereço de B2
;     UNPACK_3 : Endereço de B3
;     UNPACK_4 : Endereço de B4
; Retorno:
;     AC       : 0 se acabou o arquivo, 1 caso contrário
;========================================
GL_END K /0000
GL_UL K /0000
GL_BUF K /0000
GL_IS_EOF  K /0000
GL_CURRENT K /0000
GETLINEF K /0000
	; Cria a instrução get data
	LD GL_UL
	+ GL_CONST_300
	+ CONST_GD
	MM GETLINEF_GD_INSTR

	; Reseta o parametro EOF
	LV /0001
	MM GL_IS_EOF

	; Retorna se o buffer é vazio
	LD GL_BUF
	JZ GETLINEF_RET

; Loop de leitura
GETLINEF_LOOP $ =0
	; Verifica o tamanho do buffer
	LD GL_BUF
	- CONST_1
	JZ GETLINEF_EOS
	MM GL_BUF

	; Cria a instrução de salvar no endereço
	LD GL_END
	+ CONST_STORE
	MM GETLINEF_STORE_INSTR
	MM GETLINEF_STORE_EOS_INSTR

	; Lê o disco e salva no buffer e em GL_CURRENT
GETLINEF_GD_INSTR K /0000
	MM GL_CURRENT

	; Fim do arquivo FFFF
	- GL_CONST_EOF
	JZ GETLINEF_EOF

	; Fim da linha
	LD GL_CURRENT
	- GL_CONST_EOL
	JZ GETLINEF_EOS

	; Salva o byte lido e continua
	LD GL_CURRENT
GETLINEF_STORE_INSTR K /0000
	LD GL_END
	+ CONST_2
	MM GL_END
	JP GETLINEF_LOOP

; Fim da linha
GETLINEF_EOL $ =0
	JP GETLINEF_EOS

; Fim do arquivo
GETLINEF_EOF $ =0
	LV /0000
	MM GL_IS_EOF
	JP GETLINEF_EOS

; Coloca o fim da string
GETLINEF_EOS $ =0
	LV /0000
GETLINEF_STORE_EOS_INSTR K /0000
	
; Fim da função
GETLINEF_RET $ =0
	LD GL_IS_EOF
RS GETLINEF

# GETLINEF
