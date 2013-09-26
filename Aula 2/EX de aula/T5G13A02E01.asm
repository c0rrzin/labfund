              @  /0000 ; endereço primeiro dado
I             K  /0020 ; endereço de retorno da subrotina
D1            K  /00FA ; dado 1
D2            K  /0010 ; dado 2
D3            K  /0000 ; Resultado
DES           k  /0100 ; constante de deslocamento de 2 bytes


                    ;              @  /0020 ; Estabelecendo o inicio do pragrama para 0010
INIT          SC PACK  ; Chama subrotina de PACK
END           HM END   ;

              @  /0100 ; Endereco da subrotina
PACK          K  /0000 ; endereco de volta da subrotina
              LD D1    ; Carrega o dado 1;
              *  DES   ; Desloca 2 bytes para esquerda
              +  D2    ; add com dado 2
              MM D3    ; salva no dado 3
              RS PACK  ; Chama fim de subrotina

