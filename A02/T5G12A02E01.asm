;========================================
; Teste do programa pack
;========================================
@ /0000
JP MAIN         ; Pula para a função principal
INPUT_1 K /0012 ; Entrada B1
INPUT_2 K /0034 ; Entrada B2
OUTPUT  $ =1     ; Saída

; Função principal
MAIN LD INPUT_1 ; Chama a função pack
	MM PACK_1
	LD INPUT_2
	MM PACK_2
	SC PACK
	MM OUTPUT
FIM HM FIM      ; Acaba o programa

;========================================
; PACK
; -------
; Parametros: 
;     B1 : 200
;     B2 : 202
; AC = resultado
;========================================
@ /0200
	PACK_1 $ =1
	PACK_2 $ =1
	PACK_CTE_100 K /100
PACK $ =1
	LD 	PACK_1
	*	PACK_CTE_100
	+   PACK_2
	RS PACK
	
# 	MAIN
	