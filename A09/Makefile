#========================================
# Compila cada exercício da aula3
#========================================

# Inclui as variaveis básicas
TOPDIR=${CURDIR}/../
include ${TOPDIR}/UsedVars.mk

# Exercícios a serem compilados
EXCERCISES := E03

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
