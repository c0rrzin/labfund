Como no sistema Unix o EOL corresponde 0x0A, para nao ter problema de ler
o fim da linha junto com o primeiro nibble da proxima, foi adicionado um SP (0x20)
antes do fim de linha, em todas as linhas. Assim a constante de EOL ficou como 0x200A.
