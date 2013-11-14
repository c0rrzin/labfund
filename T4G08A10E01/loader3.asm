LDR >
UNDL2 >

INICIO &  /0000

;SUB-ROTINA LOADER
LDR		K	/0000
		JP	ANT
UNDL2	K	/0000
DOIS	K	/0002
UM		K	/0001
GRAVA	K	/9000	; usada para construir a gravacao
CARR	K	/D300	; usada para construir comando
ENDINI 	K 	/0000 	; END INICIAL
MEMT	K	/0FFF	; memoria total disponivel
TAM		K	/0000 	; TAMANHO 
CONT	K	/0000	; usada para controlar a leitura das palavras em cada bloco 
CHKSM	K	/0000	; checksum
INST	K	/0000	; variavel para armazenar a primeira instrucao executavel
ULT		K	/0000	; flag criado para verificar se estamos no ultimo bloco ou nao
ERRO	K	/0000	; 1 - Nao cabe na memoria 2 - Checksum errado
ANT		LD	UNDL2	
		+	CARR	; se tivesse entrado com 0003, deveria multiplicar por 100 antes
		MM	CARR
		; GRAVA EM TODOS LUGARES ONDE HA GD
		MM	C1		
		MM	C2
		MM	C3
		MM	C4
		MM	C5
		MM	C6
		MM	AC
		; FIM DA GRAVACAO DE GD
C1		GD	/300	; carrega endereco inicial
		MM	ENDINI	; grava end_inicial
C2		GD	/300	; carrega tamanho
		MM	TAM		; grava tamanho
		LD	MEMT		
		-	ENDINI
		MM	MEMT
		-	TAM
		JN	ERR1	; NAO CABE - PRIMEIRO TRATAMENTO DE ERRO
		JP	INI
ERR1	LV	/0001
		MM	ERRO
		JP	END
INI		LV	/0000	; parte inicial de tratamento dos blocos
		MM	CHKSM	; ZERA CHECKSUM		
C3		GD	/300	; CARREGA END_INICIAL DO BLOCO
		MM	ENDINI
		MM	CHKSM	; adiciona ao checksum o endereco inicial
C4		GD	/300	; CARREGA QNTDE DE PALAVRAS NO BLOCO
		MM	CONT	
		+	CHKSM	; Incrementa checksum com o comprimento do bloco
		MM	CHKSM	
AB		LD	ENDINI
		+	GRAVA
		MM	AA
C5		GD	/300
AA		K	/0000	; grava na memoria
		+	CHKSM	; incrementa checksum
		MM	CHKSM
		LD	ENDINI
		+	DOIS	; incrementa endereco
		MM 	ENDINI
		LD	TAM		; decrementa o tamanho
		-	UM
		MM	TAM
		JZ	AE		; se chegar no 0, pular para AE onde atribui-se ao ULT o valor 1
		JP	AF
AE		LV	/0001
		MM	ULT		; valor 1 atribuido ao ULT - ou seja, estamos no ultimo bloco
AF		LD	CONT
		-	UM		; decrementa o contador
		MM	CONT
		JZ	AC
		JP	AB
AC		GD	/300
		-	CHKSM
		JZ	AD		; CHECKSUM OK
		LV	/0002	; ERRO TIPO 2 - CHECKSUM ERRADO
		MM	ERRO
		JP	END		; DEU ERRO NO CHECKSUM - SEGUNDO TRATAMENTO DE ERRO
AD		LD	ULT		; carrega a flag para verificar se estamos no ultimo bloco
		-	UM
		JN	INI		; se nao estamos, entao volta pro INI para tratar o proximo bloco
C6		GD	/300	; poe no acumulador o endereco da primeira instrucao executavel
		MM	INST	; grava o endereco da primeira instrucao executavel e... so.		
END 	RS 	LDR ; fim
# INICIO