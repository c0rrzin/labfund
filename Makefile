#========================================
# Compila cada Aula
#========================================

# Inclui as variaveis básicas
TOPDIR=${CURDIR}/
include ${TOPDIR}/UsedVars.mk

# Aulas a serem compiladas
AULAS = A01 A02 A03

# Limpa as aulas
AULAS_CLEAN=$(AULAS:=.clean)

# Phony
.PHONY: clean aulas $(AULAS) $(AULAS_CLEAN)
.SILENT: aulas $(AULAS)  $(AULAS_CLEAN)

# Alvo padrão
all: aulas

# Alvo padrão
clean: $(AULAS_CLEAN)

# Compila as aulas
aulas: $(AULAS)

# Compila cada aula
$(AULAS):
	@echo "$(CURSPACES)Compilando aula $@"
	$(MAKE) -sC $@

# Limpa a aula
$(AULAS_CLEAN):
	@echo "$(CURSPACES)Limpando aula $(basename $@)"
	$(MAKE) -sC $(basename $@) clean
