labfund
=======

Código relacionado a Laboratório de Fundamentos (3o Quadrimestre de 2013)
Site da disciplina: http://disciplinas.stoa.usp.br/course/view.php?id=2332

## Clonando o repositório:

    cd Caminho/do/codigo/da/poli
    git clone https://github.com/c0rrzin/labfund.git (ele criará uma pasta chamada labfund)

## Upando o seu código (utilizando o terminal):

### 1) Criar o branch do grupo:

    git checkout -b TXGYY

Obs.: O branch **master** será usado para upload de arquivos e código gerais.

### 2) Para cada aula, criar uma pasta (AXX) com os exercicios referentes à aula
Exemplo:

    LabFund /
      A01 /
        E01 /
      A02 /
        E01 /
        E02 /
      ...

### 3) Comitar mudanças

    git add <file1> <file2>
    git commit -m "My awesome code"
    git push origin TXGYY

### 4) Baixar mudanças feitas por outros membros do grupo

    git pull origin TXGYY

### TODO
  1) Scripts para linkar / montar / etc
  2) Melhorar documentação (como instalar git, etc.)

## Seja consicente. Não copie código, apenas se inspire :)

