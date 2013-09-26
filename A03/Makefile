#========================================
# Compila cada exercício da aula3
#========================================

# Exercícios a serem compilados
EXCERCISES := E01 E02

# Algumas variaveis
CURSPACES:="$(SPACES)"
SPACES:="$(CURSPACES)    "
CP?=cp
MV?=mv
MKDIR?=mkdir -p

# Exporta o projeto
export SPACES CP MV MKDIR

# Limpa os objetos
EXCERCISES_CLEAN:=$(EXCERCISES:=.clean)

# Opções do make para sempre compilar certas coisas
.PHONY: clean excercises $(EXCERCISES) $(EXCERCISES_CLEAN)
.SILENT: excercises $(EXCERCISES)

# Alvo padrão
all: excercises

# Limpeza
clean: $(EXCERCISES_CLEAN)

# Compila os exercícios
excercises: $(EXCERCISES)

# Compila o exercício
$(EXCERCISES):
	@echo "$(CURSPACES)Exercicio $@"
	$(MAKE) -sC $@ all

$(EXCERCISES_CLEAN): 
	@echo "$(CURSPACES)Limpando exercicio $(basename $@)"
	$(MAKE) -sC $(basename $@) clean
