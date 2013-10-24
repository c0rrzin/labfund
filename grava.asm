
      @     /0000   ; endereço absoluto
      JP    INI     ; vai para inicio do programa
VAL   K     /1234   ; valor a ser escrito no disco
INI   LD    VAL     ; carrega vaor no acumulador
      PD    /300    ; escreve valor do acumulador no disco
                    ; cujo ID é 00 (operacao de "append")
END   HM    END     ; fim

      # INI
