#========================================
# Compila cada exercício da aula3
#========================================

# Exercícios a serem compilados
EXCERCISES := E01

# Algumas variaveis
CURSPACES:="$(SPACES)"
SPACES:="$(CURSPACES)    "
CP?=cp
MV?=mv
MKDIR?=mkdir -p

# Exporta o projeto
export SPACES CP MV MKDIR

# Opções do make para sempre compilar certas coisas
.PHONY: excercises $(EXCERCISES)
.SILENT: excercises $(EXCERCISES)

# Alvo padrão
all: excercises

# Compila os exercícios
excercises: $(EXCERCISES)

# Compila o exercício
$(EXCERCISES):
	@echo "$(CURSPACES)Exercicio $@"
	$(MAKE) -sC $@ all
