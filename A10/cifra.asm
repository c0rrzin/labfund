		@ 	/0F00	; endereço absoluto
		JP 	INI		; vai para início do programa
KEY		K	/1234
BLOCK1	K	/D32A	
BLOCK2	K	/AEAA	
BLOCK3	K	/DF8C	
BLOCK4	K	/EBDC	
CTE_0	K	/0000	; constante 0


INI		LD	KEY		; carrega chave
		+	BLOCK1	; aplica chave a BLOCK1
		MM  BLOCK1	; sobrescreve valor decifrado
		LD	KEY		; carrega chave
		+	BLOCK2	; aplica chave a BLOCK2
		MM  BLOCK2	; sobrescreve valor decifrado
		LD	KEY		; carrega chave
		+	BLOCK3	; aplica chave a BLOCK3
		MM  BLOCK3	; sobrescreve valor decifrado
		LD	KEY		; carrega chave
		+	BLOCK4	; aplica chave a BLOCK4
		MM  BLOCK4	; sobrescreve valor decifrado

		LD	CTE_0	; DELIMITADOR
		LD	BLOCK1	; Le resultado
		LD	BLOCK2	; Le resultado
		LD	BLOCK3	; Le resultado
		LD	BLOCK4	; Le resultado
		LD	CTE_0	; DELIMITADOR
		
END		HM	END    ; fim

# INI
