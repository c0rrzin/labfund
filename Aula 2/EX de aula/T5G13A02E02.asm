          @  /0000 ; endereço primeiro dado
I         K  /0020 ; endereço de retorno da subrotina
EN        K  /FA10 ; Entrada
S1        K  /0000 ; Saida 1
S2        K  /0000 ; Saida 2
DES       k  /0100 ; constante de deslocamento de 2 bytes


          @  /0020 ; Estabelecendo o inicio do pragrama para 0010
INIT      SC UNPACK  ; Chama subrotina de PACK
END       HM END   ;

          @  /0100 ; Endereco da subrotina
UNPACK    K  /0000 ; endereco de volta da subrotina
          LD EN    ; Carrega o dado 1;  EX : 2002
          /  DES   ; desloca 2 bytes para a direita  EX: 0020
          MM S1    ; salva no primeiro dado de saida
          *  DES   ; desloca 2 bytes para a esquerda EX: 2000
          MM S2    ; salva na saida s2 para buscar depois
          LD EN    ; carrega o dado de entrada EX : 2002
          -  S2    ; faz menos o primeiro dado salvo EX : 0002
          MM S2    ; salva o real valor na saida S1
          RS UNPACK ; return
          #  INIT  ; fim do programa
