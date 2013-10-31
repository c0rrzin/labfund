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

; GERADORES DE FUNCAO
DISC      K   /D300  ;
SALVA     K   /9000  ;

L_INI     K   /0000  ;
L_TAM     K   /0000  ;

LOADER    JP  /0000     ; inicio da subrotina
          LD  DUMP_UL   ; carrega o valor da UL
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
LOOP      LD  END       ; carrega o endereco a ser
FIML      RS  LOADER


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
