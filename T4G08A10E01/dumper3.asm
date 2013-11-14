DUMPER >
ENDINI >
INST >
BLKTAM >
COMP >
UNDL >

INICIO &  /0000
		
;SUB-ROTINA DUMPER
DUMPER	K /0000
		JP INIT
UNDL	K /0000		; unidade logica
PTDT	K /E300		; tipo Disco 0x3
DOIS 	K /0002
ENDINI 	K /0000
LOAD 	K /8000 	; 8000
COMP 	K /0000
BLKTAM	K /0000		; tamanho dos blocos
CONT	K /0000		; contador usador pra leitura dos blocos
UM		K /0001
CHECKS	K /0000		; armazena checksum
ULT		K /0000		; flag pra saber se estamos no ultimo bloco
INST	K /0000		; variavel pra armazenar a primeira instrucao executavel
INIT	LD	UNDL	; se tivesse entrado com /0003, deveria multiplicar por 100 antes
		+	PTDT
		; GRAVA EM TODOS LUGARES COM PD
		MM	P1
		MM	P2
		MM	P3
		MM	P4
		MM	P5
		MM	P6
		MM	P7
		MM	P8
		; FIM DA GRAVACAO
		LD	BLKTAM
		JZ	FIM		; vai direto pro fim se o parametro de entrada para o tamanho dos blocos for 0
		LD	COMP	
		JZ	FIM		; vai direto pro fim se o comprimento for 0
		-	BLKTAM
		JN	CORR	; se comprimento < tamanho dos blocos, entao tamanho do bloco = comprimento
		JP	INIT2
CORR	LD	COMP
		MM	BLKTAM
INIT2	LD	ENDINI
P1		PD	/300	; grava no dumper o endereco inicial
		LD	COMP	
P2		PD	/300	; grava o comprimento total
INIT3	LD	BLKTAM
		MM	CONT
		LD	ENDINI
P3		PD	/300	; grava endereco inicial do bloco
		LD	COMP	; carrega o tamanho de tudo
		-	BLKTAM
		MM	COMP	; comprimento que falta processar
		JN	ULTIMO
		JZ	ULTIMO
		JP	NULTIMO
ULTIMO	LV	/0001	; flag pra indicar se e o ultimo bloco ou nao
		MM	ULT
		LD	COMP	; 0 OU NEGATIVO
		+	BLKTAM
		MM	BLKTAM
		MM	CONT
P4		PD	/300
		JP	MAINB
NULTIMO	LD	BLKTAM
P5		PD	/300	; grava o tamanho de cada bloco na frente
MAINB	LD  ENDINI 	; carrega conteudo de ENDINI
		MM	CHECKS	; guarda valor no checksum
		+	LOAD	; constroi operacao
		MM	AA
AA		K	/0000
P6		PD	/300	; coloca no dump
		+	CHECKS	; incrementa o conteudo da memoria no checksum
		MM	CHECKS	; guarda checksum
		LD 	AA		; carrega o que tem no AA
		;-	LOAD 	; CORRECAO, pois no "in" temos 8000+endereco
					; nao e necessaria pois vamos carregar o proximo endereco, afinal
		+	DOIS
		MM	AA
		LD	CONT
		-	UM
		MM	CONT
		JZ	CHKSM
		JP	AA
CHKSM	LD 	CHECKS	
		+	BLKTAM	; adiciona o tamanho do bloco ao checksum
		MM	CHECKS
P7		PD	/300
		LD	ULT		; flag criada pra saber se e o ultimo bloco ou nao
		-	UM
		JZ	FIM
		LD	BLKTAM	
		*	DOIS
		+	ENDINI	; INCREMENTA PARA O PROXIMO BLOCO
		MM	ENDINI
		JP	INIT3
FIM		LD	INST
P8		PD	/300
		RS	DUMPER 
#	INICIO
