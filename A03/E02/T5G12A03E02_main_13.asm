; =============================
; Programa principal
; ============================

UITOCH <
UITOCH_1 <
UITOCH_2 <

& /0000
JP MAIN
CHTOUI_IN_1 K /0
CHTOUI_IN_2 K /0
CHTOUI_OUT  $ =1
UITOCH_IN K /7824
UITOCH_OUT_1 $ =1
UITOCH_OUT_2 $ =1

; ===============
; MAIN
; ==============
MAIN $ =0

	; Teste do UITOCH
	LV UITOCH_OUT_1
	MM UITOCH_1
	LV UITOCH_OUT_2
	MM UITOCH_2
	LD UITOCH_IN
	SC UITOCH
END_MAIN HM END_MAIN
# MAIN
