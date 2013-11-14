DUMPER <
ENDINI <
INST <
BLKTAM <
COMP <
UNDL <

LDR <
UNDL2 <

;UNP >
;VALOR >
;PRIMEIRO <
;SEGUNDO <

;PACK >
;PRIM >
;SEGM >

;INICIO DO TRATAMENTO DO MBS
PROG	&	/0000
		JP 	IN
INS		K	/FFFF	; primeira instrucao executavel convencionada em 0xffff
BARRA	K	/2F2F	; "//"
JOB1	K	/4A4F	; "JO"
JOB2	K	/4262	; "Bb"
EOL		K	/0D0A	; "CRLF"
DU		K	/4455	; "DU"
LO		K	/4C4F	; "LO"
BB		K	/2020	; "bb"
ZERX	K	/3078	; "0x"
FIM		K	/2F2A	; "/*"	
ERRO	K	/0000	;
CMD		K	/0000
OPER	K	/0000
PRMTRO	K	/0000
CONT	K	/0005	; numero de parametros do Dumper

IN		GD	/300
		-	BARRA	; verifica //
		JZ	OK1
		OS	/001
		JP	END
OK1		GD	/300	; verifica JO
		-	JOB1
		JZ	OK2
		OS	/001
		JP	END
OK2		GD	/300	; verifica Bb
		-	JOB2
		JZ	OK3
		OS	/001
		JP	END
OK3		GD	/300	; verifica EOL
		-	EOL
		JZ	OK4
		OS	/001
		JP	END
; VERIFICACAO DE JOB TERMINADA
OK4		LV	/0005
		MM	CONT	; inicializa o contador do Dumper, caso ele seja executado mais de 1x num mesmo Job
		GD	/300	; verifica //
		MM	OPER	; guarda pra verificar posteriormente se e /*
		-	BARRA
		JZ	OK5
		LD	OPER
		-	FIM		; Se nao e //, pode ser /*... verifica isso e se encontrar, vai pro fim
		JZ	END	
		OS	/004	; Se nao encontrar nem // nem /*, algo esta errado.
					; Supondo que a intencao era "terminar", o erro lancado e 004
		JP	END
OK5		GD	/300	; guarda a operacao a ser feita 
		MM	CMD
		GD	/300	; verifica EOL
		-	EOL
		JZ	OK6
		OS	/002
		JP	END
OK6		LD	CMD		; carrega a operacao anteriormente armazenada
		-	DU		; verifica se operacao e DU
		JZ	DU1
		LD	CMD
		-	LO		; verifica se operacao e LO
		JZ	LO1		; 
		OS	/002	; se nao e, erro.
		JP	END
		
	; Tratamento do //DU
DU1		GD	/300
		-	ZERX	; verifica 0x
		JZ	OK7
		OS	/003
		JP	END
	;	PRIMEIRO TRATAMENTO DE PARAMETRO
OK7		GD	/300	; leu o primeiro byte e transformou em ASCII
		MM	VALOR
		SC	UNP		; CHAMA UNPACK
		LD	PRIMEIRO
		MM	VAR1
		LD	SEGUNDO
		MM	VAR2
		SC	CNV		; chama a sub-rotina responsavel por converter ASCII
		LD	VART
		MM	PRIM
		GD	/300	; le o segundo byte que e transformado em ASCII
		MM	VALOR
		SC	UNP		; CHAMA UNPACK
		LD	PRIMEIRO
		MM	VAR1
		LD	SEGUNDO
		MM	VAR2
		SC	CNV		; chama a sub-rotina responsavel por converter ASCII
		LD	VART
		MM	SEGM
		SC	PACK	; chama o PACK pra compor o parametro de fato
		MM	PRMTRO	; guarda o parametro
		; Tratamento de parametro - descobre qual e qual
		LD	CONT
		-	UM		; QUINTO PARAMETRO - UNIDADE LOGICA - UNDL
		JZ	PAR1
		LD	CONT
		-	DOIS	; QUARTO PARAMETRO - PRIMEIRA INSTRUCAO - INST
		JZ	PAR2
		LD	CONT
		-	TRES	; TERCEIRO PARAMETRO - COMPRIMENTO TOTAL - COMP
		JZ	PAR3
		LD	CONT
		-	QUATRO	; SEGUNDO PARAMETRO - ENDERECO INICIAL - ENDINI
		JZ	PAR4
		LD	CONT
		-	CINCO	; PRIMEIRO PARAMETRO - TAMANHO DO BLOCO - BLKTAM
		JZ	PAR5
PAR1	LD	PRMTRO
		MM	UNDL
		JP	DU5
PAR2	LD	PRMTRO
		MM	INST
		JP	DU5
PAR3	LD	PRMTRO
		MM	COMP
		JP	DU5
PAR4	LD	PRMTRO
		MM	ENDINI
		JP	DU5
PAR5	LD	PRMTRO
		MM	BLKTAM
		;JP	DU5 - NAO HA NECESSIDADE
		; FIM DO TRATAMETO DE PARAMETRO
DU5		LD	CONT
		-	UM
		MM	CONT	; vai decrementando o contador pra saber qual parametros estamos tratando
		JZ	DU2
		JP	DU3
DU2		GD	/300	; fim da linha dos parametros 
		-	EOL		; verifica EOL
		JZ	DU4
		OS	/003	; NAO ACHOU END OF LINE NOS PARAMETROS DO DUMPER 
		JP	END
DU3		GD	/300
		-	BB		; verifica se ha o "bb" entre os parametros
		JZ	DU1
		OS	/003	; erro de sintaxe: nao achou o "bb"
		JP	END
DU4		SC	DUMPER	; DEPOIS DE TUDO, CHAMA O DUMPER!
		JP	OK4		; VOLTA TUDO!
		; FIM DO TRATAMENTO DE //DU

	; Inicio do tratameto de //LO
LO1		GD	/300
		-	ZERX
		JZ	OK8
		OS	/003
		JP	END
		;	PRIMEIRO TRATAMENTO DE PARAMETRO
OK8		GD	/300	; leu o primeiro byte e transformou em ASCII
		MM	VALOR
		SC	UNP		; CHAMA UNPACK
		LD	PRIMEIRO
		MM	VAR1
		LD	SEGUNDO
		MM	VAR2
		SC	CNV		; chama a sub-rotina responsavel por converter ASCII
		LD	VART
		MM	PRIM
		GD	/300	; le o segundo byte que e transformado em ASCII
		MM	VALOR
		SC	UNP		; CHAMA UNPACK
		LD	PRIMEIRO
		MM	VAR1	; O PRIMEIRO BYTE EH GRAVADO NO VAR2 DO CNV
		LD	SEGUNDO
		MM	VAR2
		SC	CNV		; chama a sub-rotina responsavel por converter ASCII
		LD	VART
		MM	SEGM
		SC	PACK
		MM	UNDL2	; GRAVA DIRETO na variavel do Loader pois so tem um parametro (Unidade Logica) 		
		GD	/300
		-	EOL		; VERIFICA O END OF LINE NO PARAMETRO DO LOADER
		JZ	LO2
		OS	/0003	; NAO ENCONTROU END OF LINE
		JP	END
LO2		SC	LDR		; CHAMA O LOADER!
		JP	OK4
	; FIM DO TRATAMETO DO //LO		

; Trecho usado pra testar o conversor ASCII
DEPUR	LV	/0030
		MM	VAR1
		LV	/0031
		MM	VAR2
		SC	CNV
		LD VART
;  FIM do trecho
END		HM 	IN		; bom... acaba aqui.

; FIM DO TRATAMENTO DA MBS
		
; SUB-ROTINA QUE CONVERTE ASCII
; Exemplo: VAR1: 0031 VAR2: 0032 entao, VART = 0012
; Esse VART e utilizado depois pelo PACK para compor a instrucao final
CNV		K	/0000
		JP	IV
VART	K	/0000
VAR1	K	/0000
VAR2	K	/0000
FLAG	K	/0000
IV		LV	/0000
		MM	FLAG	; ZERA FLAG
		; Aqui e somente para inicializar a funcao. Como ela e usada varias vezes para cada parametro, as instrucoes
		; acabam sendo reescritas por VAR2. Logo, no inicio, deve-se re-gravar o VAR1.
		LV	VAR1
		+	LOAD
		MM	IV3
		MM	LD1
		MM	LD2
		MM	LD3
		MM	LD4
		MM	LD5
		MM	LD6
		MM	LD7
		MM	LD8
		MM	LD9
		MM	LDA
		MM	LDB
		MM	LDC
		MM	LDD
		MM	LDE
		MM	LDF
		
		LV	VAR1
		+	GRAVA
		MM	GR0
		MM	GR1
		MM	GR2
		MM	GR3
		MM	GR4
		MM	GR5
		MM	GR6
		MM	GR7
		MM	GR8
		MM	GR9
		MM	GRA
		MM	GRB
		MM	GRC
		MM	GRD
		MM	GRE
		MM	GRF
		; FIM da inicializacao e tratamento do VAR1
		; VERIFICA 0
IV3		LD	VAR1
		-	CTE_0
		JZ	CTE0
		; VERIFICA 1
LD1		LD	VAR1
		-	CTE_1
		JZ	CTE1
		; VERIFICA 2
LD2		LD	VAR1
		-	CTE_2
		JZ	CTE2
		; E ASSIM POR DIANTE...
LD3		LD	VAR1
		-	CTE_3
		JZ	CTE3
LD4		LD	VAR1
		-	CTE_4
		JZ	CTE4
LD5		LD	VAR1
		-	CTE_5
		JZ	CTE5
LD6		LD	VAR1
		-	CTE_6
		JZ	CTE6
LD7		LD	VAR1
		-	CTE_7
		JZ	CTE7
LD8		LD	VAR1
		-	CTE_8
		JZ	CTE8
LD9		LD	VAR1
		-	CTE_9
		JZ	CTE9
LDA		LD	VAR1
		-	CTE_A
		JZ	CTEA
LDB		LD	VAR1
		-	CTE_B
		JZ	CTEB
LDC		LD	VAR1
		-	CTE_C
		JZ	CTEC
LDD		LD	VAR1
		-	CTE_D
		JZ	CTED
LDE		LD	VAR1
		-	CTE_E
		JZ	CTEE
LDF		LD	VAR1
		-	CTE_F
		JZ	CTEF
		; SE NAO ESTA ENTRE 0 A F, ENTAO HA ALGUM ERROR.
		OS	/003
		JP END2
		
		; CARREGA O VALOR 0 E O GUARDA EM VAR1
CTE0	LV	/0000
GR0		MM	VAR1
		JP	IV2
		; CARREGA O VALOR 1 E O GUARDA EM VAR1
CTE1	LV	/0001
GR1		MM	VAR1
		JP	IV2
		; E ASSIM POR DIANTE
CTE2	LV	/0002
GR2		MM	VAR1
		JP	IV2
		
CTE3	LV	/0003
GR3		MM	VAR1
		JP	IV2
		
CTE4	LV	/0004
GR4		MM	VAR1
		JP	IV2
		
CTE5	LV	/0005
GR5		MM	VAR1
		JP	IV2
		
CTE6	LV	/0006
GR6		MM	VAR1
		JP	IV2
		
CTE7	LV	/0007
GR7		MM	VAR1
		JP	IV2
		
CTE8	LV	/0008
GR8		MM	VAR1
		JP	IV2
		
CTE9	LV	/0009
GR9		MM	VAR1
		JP	IV2
		
CTEA	LV	/000A
GRA		MM	VAR1
		JP	IV2
		
CTEB	LV	/000B
GRB		MM	VAR1
		JP	IV2
		
CTEC	LV	/000C
GRC		MM	VAR1
		JP	IV2
		
CTED	LV	/000D
GRD		MM	VAR1
		JP	IV2
		
CTEE	LV	/000E
GRE		MM	VAR1
		JP	IV2
		
CTEF	LV	/000F
GRF		MM	VAR1
		JP	IV2
		
IV2		LD	FLAG	; DECREMENTA-SE NOSSO FLAG PARA SABER SE ESTAMOS TRATANDO VAR2 OU VAR1
		-	UM
		JZ	END2
		; INICIALIZA E TRATA VAR2, REESCREVENDO EM TODAS AS POSICOES ANTERIORMENTE OCUPADAS POR VAR1
		LV	VAR2
		+	LOAD
		MM	IV3
		MM	LD1
		MM	LD2
		MM	LD3
		MM	LD4
		MM	LD5
		MM	LD6
		MM	LD7
		MM	LD8
		MM	LD9
		MM	LDA
		MM	LDB
		MM	LDC
		MM	LDD
		MM	LDE
		MM	LDF
		
		LV	VAR2
		+	GRAVA
		MM	GR0
		MM	GR1
		MM	GR2
		MM	GR3
		MM	GR4
		MM	GR5
		MM	GR6
		MM	GR7
		MM	GR8
		MM	GR9
		MM	GRA
		MM	GRB
		MM	GRC
		MM	GRD
		MM	GRE
		MM	GRF
		
		LV	/0001	; SETA A FLAG EM 1 PARA DIZER QUE ESTAMOS TRATANDO VAR2
		MM	FLAG		
		JP	IV3

END2	LD	VAR1
		*	DEZ		; MULTIPLICA POR 10 E ADICIONA PARA COMPOR A INSTRUCAO FINAL
		+	VAR2
		MM	VART	; SAIDA IHUL
		RS	CNV
		

		
  ; Variaveis
PRIMEIRO	K	/0000
SEGUNDO		K	/0000
  ; Constantes
CEM		K	/0100
DEZ		K	/0010
EFEF 	K 	/FF00		
LOAD	K	/8000
GRAVA	K	/9000
UM		K	/0001
DOIS	K	/0002
TRES	K	/0003
QUATRO	K	/0004
CINCO	K	/0005
CTE_0	K	/0030
CTE_1	K	/0031
CTE_2	K	/0032
CTE_3	K	/0033
CTE_4	K	/0034
CTE_5	K	/0035
CTE_6	K	/0036
CTE_7	K	/0037
CTE_8	K	/0038
CTE_9	K	/0039
CTE_A	K	/0041
CTE_B	K	/0042
CTE_C	K	/0043
CTE_D	K	/0044
CTE_E	K	/0045
CTE_F	K	/0046

  ;FUNCAO UNPACK DO EXERCICIO 1
UNP K /0000 
  JP UN  
  VALOR K /ABCD
  VARAUX K /0000
  PRIB K /0000
  SEGB K /0000
UN LD VALOR	; P=rótulo LD=load E=endereço simbólico da constante 034C 
  JN T	
  /  CEM	; PARTE POSITIVA (CALCULA PRIMEIRAMENTE O PRIMEIRO BYTE E DEPOIS O SEGUNDO BYTE)
  MM PRIMEIRO	; 
  LD VALOR
  *	 CEM
  /  CEM
  JN I
  JP Z
I -  EFEF
Z MM SEGUNDO
  JP CAR	; FIM
T LD VALOR ;PARTE NEGATIVA (CALCULA PRIMEIRAMENTE O SEGUNDO BYTE E DEPOIS O PRIMEIRO BYTE)
  *	 CEM
  /  CEM
  JN M
  JP N
M - EFEF ;POIS É NEGATIVO
N MM SEGUNDO ;GUARDA... BEM SIMPLES
  LD VALOR ;PRIMEIRO BYTE
  - SEGUNDO ;GRAVA NO VARAUX O VALOR
  / CEM ;DIVIDE POR 100, OBTENDO 00XX
  JN R
  JP S
R - EFEF ;RETIRA O "EXCESSO" DE F
S MM PRIMEIRO
  JP CAR
CAR LD PRIMEIRO
    MM PRIB
	LD SEGUNDO
	MM SEGB  
  RS UNP	; FIM
  
;FUNCAO PACK DO EXERCICIO 2
PACK K /0000
  JP G
  PRIM K /0000
  SEGM K /0000
G LD PRIM
  *  CEM ;MULTIPLICA POR 100
  +  SEGM
  RS PACK

# PROG

		