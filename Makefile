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

# Phony
.PHONY: aulas $(AULAS)
.SILENT: aulas $(AULAS)

# Alvo padrão
all: aulas

# Compila as aulas
aulas: $(AULAS)

# Compila cada aula
$(AULAS):
	@echo "Compilando aula $@"
	$(MAKE) -sC $@ all
