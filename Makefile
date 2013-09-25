#========================================
# Compila cada Aula
#========================================

# Aulas a serem compiladas
AULAS = A01 A02 A03

# Variaveis utilizadas
JAVA=java
MLR=${CURDIR}/MLR.jar
MVN=${CURDIR}/mvn2013.jar
CP?=cp
MV?=mv
MKDIR?=mkdir -p
CURSPACES:="$(SPACES)"
SPACES:="$(CURSPACES)  "

# Exporta as variaveis
export JAVA MLR MVN SPACES CP MV MKDIR

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
