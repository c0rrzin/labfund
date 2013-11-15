; GETLINEF - MAIN

GETLINEF >
GL_END >
GL_UL >
GL_BUF >

& /0000

; CONSTANTES  EXPORTADAS
GL_END        K  /0000
GL_UL         K  /0000
GL_BUF        K  /0000

;CONSTANTES PROPRIAS
UM            K  /0001
DOIS          K  /0002
C_GETDATA     K  /D000
DISCO         K  /0300
LOAD          K  /8000
SAVE          K  /9000

COUNT         K  /0000
C_EOF         K  /FFFF      ; http://en.wikipedia.org/wiki/End-of-file  -> primeiro paragrafo
C_EOL         K  /0D0A      ; cr + nl (ASCII)
C_EOS         K  /0000      ; http://www.erg.abdn.ac.uk/~gorry/eg2069/ascii.html -> segundo metodo (usado em C tbm)

RETURN        K  /0000

VARTMP        K  /0000
ENDTMP        K  /0000


GETLINEF      JP /0000 ;

              LV /0000
              MM RETURN  ; seta o retorna default para 0x0

              LD GL_UL      ; Forma funcao de pegar dado do disco selecionado
              +  DISCO      ;
              +  C_GETDATA    ;
              MM GETDATA

              LD GL_BUF  ; guarda tamanha do buffer numa variavel que pode ser modificada
              -  UM      ; lembrando que temos que reservar 1 word para escrever o EOS
              MM COUNT

              LD GL_END  ; guarda endereco de inicio numa variavel que pode ser modificada
              MM ENDTMP

LOOP          JP GETDATA
GETDATA       K  /0000    ; pega a word a ser lida no arquivo
              MM VARTMP   ; salva na variavel temporaria

              -  C_EOL    ; se eh o final da linha, vai para final da linha
              JZ EOS

              LD VARTMP   ; se eh o final do arquivo, vai para final do arquivo
              -  C_EOF
              JZ EOF

              LD ENDTMP   ; forma funcao de salvar
              +  SAVE     ;
              MM SAVE_BUF ; salva no endereco da vez
              +  DOIS
              MM SAVE_EOS

              LD VARTMP
SAVE_BUF      K  /0000  ; salva no buffer

              LD COUNT    ; checa se acabou o espaco disponível, se sim salva o fim da string
              -  UM       ; caso contrario, continua
              MM COUNT
              JZ EOS
              LD ENDTMP
              +  DOIS
              MM ENDTMP
              JP LOOP

EOF           LD UM
              MM RETURN

EOS           LD C_EOS  ; pega a constante de final da string e salva no buffer
SAVE_EOS      K  /0000

GL_FIM        LD RETURN  ; salva o dado a retornar no acumulador
              RS GETLINEF


# END
