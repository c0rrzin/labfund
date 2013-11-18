LOADER    >
LOADER_UL   >


          &   /0000

; VARIAVEIS EXPORTADAS
LOADER_UL K   /0000  ;

; CONSTANTES
SUM       K   /0000  ;
END       K   /0000  ;
CONT      K   /0001  ;
MUM       K   /FFFF  ;
ZERO      K   /0000  ;
UM        K   /0001  ;
DOIS      K   /0002  ;
FFE       K   /0FFE  ;
ENDFIM    K   /0000  ;
DIFTMP    K   /0000  ;
PTAM      K   /0000  ; variavel para salvar o tamanho do bloco

; GERADORES DE FUNCAO
DISC      K   /D300  ;
SALVA     K   /9000  ;

L_INI     K   /0000  ;
L_TAM     K   /0000  ;

LOADER    JP  /0000     ; inicio da subrotina
          LD  LOADER_UL   ; carrega o valor da UL
          +   DISC      ; forma operacao de pegar dado do disco
          MM  READ
          SC  LE        ; le o primeiro dado (endereco de inicio)
          MM  END       ; salva no endereco a ser usado
          MM  L_INI     ;
          SC  LE        ; le o tamanho
          MM  L_TAM     ;
          SC  VALIDA    ; valida se o load nao passara do ultimo endereco com os dados passados
; REFACTOR, enviar mensagem ao monitor ??
          JN  FIML     ; retorna -1 caso nao seja valido
; END REFACTOR
LOOP      SC  LE        ;  le o endereco inicial do pacote
          MM  END       ;  salva na variavel de endereco
          MM  SUM       ;  salva no valor de checksum
          SC  LE        ;  LE o tamanho do pacote
          MM  PTAM      ;  salva no tamanho de bloco
          +   SUM       ;  adiciona no checksum
          MM  SUM       ;
LOOP2     LD  END       ;  carrega o endereco
          +   SALVA     ;  gera funcao de salvar
          MM  TOMM      ;  guarda instrucao para ser executada
          SC  LE        ;  LE dado do arquivo
TOMM      K   /0000     ;  salva o dado na respectiva memoria
          +   SUM       ;  adiciona ao checksum
          MM  SUM       ;
          LD  CONT      ;
          -   PTAM      ;  ve se eh o ultimo do bloco
          JZ  ENDB      ;  se é o ultimo, chamar subrotina de conferir checksum
          LD  CONT      ;  CASO CONTARIO, INCREMENTA END E CONT PARA CONTINUAR NO BLOCO
          +   UM        ;
          MM  CONT      ;
          LD  END       ;
          +   DOIS      ;
          MM  END       ;
          JP  LOOP2
ENDB       SC  CHECKSUM  ;
          JN  FIML      ; SE NEGATIVO, CHECKSUM NAO BATEU, RETORNAR ROTINA
          LD  ZERO      ;
          MM  SUM       ;
          LD  UM        ;
          MM  CONT      ;
          LD  END       ; GERA O PROXIMO ENDERECO
          +   DOIS      ;
          -   ENDFIM    ; CONFERE SE É O ULTIMO ENDERECO
          JZ  QFIM      ; SE SIM, ACABARAM OS BLOCOS
          JP  LOOP      ; SE NAO, VOLTA PARA INICIO DO LOOP
QFIM      SC  LE        ;  carrega o endereco executavel para o acumulador
FIML      RS  LOADER


CHECKSUM  JP  /0000       ; subrotina de ler checksum
          SC  LE          ; le o checksum do disco
          -   SUM
          JZ  CERTO       ; SE DEU 0, ESTA CERTO
          LD  MUM         ; caso contrario, retorna 1 e para a exucacao
          JP  FIMC        ;
CERTO     LD  ZERO        ;
FIMC      RS  CHECKSUM      ;  fim da subrotina

LE        JP  /0000       ; subrotina de ler dado do disco
READ      K   /0000       ; aqui sera guardada a instrucao de ler ( GD - /0300)
          RS  LE          ; retorno da subrotina

VALIDA    JP  /0000       ;  valida se o endereco final ainda é valido
          LD  L_TAM    ; calcula o endereco final ( DUMP_INI + 2*(TAM))
          *   DOIS        ;
          +   L_INI    ; (o endereco final de leitura)
          MM  ENDFIM      ; salva como final do endereco (para uso posterior)
          -   FFE         ; compara com FFE
          JN  ISNEG       ; se negativo, endereco esta dentro do limite, retorna 0
          LD  MUM         ; caso contrario, retorna 1 e para a exucacao
          JP  FIMV        ;
ISNEG     LD  ZERO        ;
FIMV      RS  VALIDA      ;  fim da subrotina

# FIML
