DUMPER    >
DUMP_INI  >
DUMP_TAM  >
DUMP_UL   >
DUMP_BL   >
DUMP_EXE  >

          &   /0000

; EXPORTS
DUMP_INI  K   /0000  ;
DUMP_TAM  K   /0000  ;
DUMP_UL   K   /0000  ;
DUMP_BL   K   /0000  ;
DUMP_EXE  K   /0000  ;

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

; GERADOR DE FUNCOES
DISC      K   /E300  ;
LOAD      K   /8000  ;

DUMPER    JP  /0000  ; inicio da subrotina
          LD  DUMP_UL ; carrega o valor da UL
          +   DISC   ; forma operacao de salvar no disco
          MM  WRTE
          SC  VALIDA  ; valida se o dump nao passara do ultimo endereco com os dados passados
          JN  FIMD     ; retorna -1 caso nao seja valido
          LD  DUMP_INI ; carrega o valor do endereco de inicio na contagem
          MM  END      ; salva no endereco
          SC  GRAVA    ; grava o endereco
          LD  DUMP_TAM ; load tamanho total
          SC  GRAVA    ; grava

LOOP1     LD  END    ; pega o endereco inicial do bloco
          SC  GRAVA  ; grava no disco
          SC  TAM    ; chama subrotina de achar tamanho do bloco e gravar
LOOP2     LD  END    ; carrega o contador
          +   LOAD   ; faz funcao de load
          MM  CARR   ; bota no proximo
CARR      K   /0000  ;
          SC  GRAVA  ;
          +   SUM    ;
          MM  SUM    ;
          LD  CONT   ;
          -   DIFTMP    ; compara se é o ultimo do bloco
          JZ  FIMB    ; acabou este bloco, grava e zera checksum checksum
          LD  CONT   ; aumenta um no cont e 2 no END e reinicia o LOOP
          +   UM     ;
          MM  CONT   ;
          LD  END    ; se nao, passa para o próximo e continua (next end)
          +   DOIS   ;
          MM  END    ;
          JP  LOOP2   ;
FIMB      LD  SUM    ; carrega checksum
          SC  GRAVA  ;
          LD  END    ; pega o proximo endereco (o ENDFIM nao corresponde a area que o dump abrange)
          +   DOIS   ;
          -   ENDFIM ; ve se eh o ultimo do dump
          JZ  FIM    ; se sim, acaba a simulacao
          LD  END    ; se nao, gera proximo endereco e comeca o loop1 de novo
          +   DOIS   ;
          MM  END    ;
          LD  ZERO   ;
          MM  SUM    ; zera as contagens (CONT inicial é 1)
          LD  UM     ;
          MM  CONT   ;
          JP  LOOP1   ; proximo bloco
FIM       LD  DUMP_EXE ;
          SC  GRAVA    ;
FIMD      RS  DUMPER

GRAVA     JP  /0000  ;
WRTE      K   /0000  ; funcao a ser gerada no inicio da DUMPER
          RS  GRAVA  ;

VALIDA    JP  /0000  ;
          LD  DUMP_TAM ;
          *   DOIS   ;
          +   DUMP_INI  ; (o endereco final de leitura)
          MM  ENDFIM  ; salva como final do endereco
          -   FFE    ; compara com FFE
          JN  ISNEG   ;
          LD  MUM     ;
          JP  FIMV    ;
ISNEG     LD  ZERO      ;
FIMV      RS  VALIDA ;

TAM       JP  /0000  ;
          LD ENDFIM  ;  pega o fim do endereco
          -  END     ;  compara com o atual
          /  DOIS    ;  transforma em unidade de contagem
          MM DIFTMP  ; salva esta diferenca temporaria temporaria
          -  DUMP_BL ;  compara com dump bl
          JN RDIF
          LD DUMP_BL ; grava a diferenca
          MM DIFTMP  ; grava na diferenca temporaria (sera usada depois)
          SC GRAVA
          JP FIMT    ;
RDIF      LD DIFTMP
          SC GRAVA   ; grava o a diferenca
FIMT      RS TAM
          # DUMPER
