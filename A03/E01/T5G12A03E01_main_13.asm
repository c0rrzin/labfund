
UNPACK <
UNPACK_1 <
UNPACK_2 <
PACK <
PACK_1 <
PACK_2 <
STRCMP <
STRCMP_1 <
STRCMP_2 <

;========================================
; Programa para executar o STRCMP
;========================================
& /0000
JP MAIN

; Pack
PACK_INPUT_1 K /0001 
PACK_INPUT_2 K /0002
PACK_OUTPUT  $ =1

; Unpack                   
UNPACK_INPUT K /0304 
UNPACK_OUTPUT_1 $ =1
UNPACK_OUTPUT_2 $ =1

; STRCMP
STRCMP_INPUT_1 K /7761
	K /6993
	K /6f6d
	K /6665
	K /0000
	$ =3
STRCMP_INPUT_2 K /7761
	K /6993
	K /6f6d
	K /6665
	K /0000
	$ =3
STRCMP_OUTPUT $ =1


;========================================
; MAIN
; -------
; Programa de teste
;========================================
MAIN 	$ =0
	LD PACK_INPUT_1
	MM PACK_1
	LD PACK_INPUT_2
	MM PACK_2
	SC PACK
	MM PACK_OUTPUT

	LV UNPACK_OUTPUT_1
	MM UNPACK_1
	LV UNPACK_OUTPUT_2
	MM UNPACK_2
	LD UNPACK_INPUT
	SC UNPACK

	LV STRCMP_INPUT_1
	MM STRCMP_1
	LV STRCMP_INPUT_2
	MM STRCMP_2
	SC STRCMP
	MM STRCMP_OUTPUT
END HM END

# MAIN