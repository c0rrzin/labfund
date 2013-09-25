#========================================
# Base para compilar um programa usando a MVN
#========================================

# Arquivos fontes
SRCS=T5G12A03E01_main_13.asm \
	T5G12A03E01_const_13.asm \
	T5G12A03E01_rotinas_13.asm

OUT=T5G12A03E01.mvn
BASE_RELOCACAO=0000
ENDERECO_INICIO=000

# Pasta de objetos
OBJ_DIR=obj

# Cria os nomes dos objetos fontes 
OBJS=$(SRCS:%.asm=$(OBJ_DIR)/%.mvn)

# Algumas variaveis
CURSPACES:="$(SPACES)"
SPACES:="$(CURSPACES)    "
CP?=cp
MV?=mv
MKDIR?=mkdir -p

# Exportação
export SPACES

# Copia os asm para a pasta
$(OBJ_DIR)/%.asm: %.asm
	$(CP) $< $@

# Compila o asm para lst e mvn
$(OBJ_DIR)/%.mvn: $(OBJ_DIR)/%.asm
	echo "$(CURSPACES)Compilando $<"
	$(JAVA) -cp $(MLR) montador.MvnAsm $< >/dev/null

# Linka os mvn gerados
$(OBJ_DIR)/link.mvn: $(OBJS)
	echo "$(CURSPACES)Linkando $(OBJS)"
	$(JAVA) -cp $(MLR) linker.MvnLinker $(OBJS) -s $(OBJ_DIR)/link.mvn >/dev/null

# Realoca o arquivo
$(OUT): $(OBJ_DIR)/link.mvn
	echo "$(CURSPACES)Realocando"
	$(JAVA) -cp $(MLR) relocator.MvnRelocator $(OBJ_DIR)/link.mvn $(OUT) $(BASE_RELOCACAO) $(ENDERECO_INICIO) >/dev/null
	$(MV) $(OUT:.mvn=.run) $(OBJ_DIR)/$(OUT:.mvn=.run)

# Cria o diretório de objeto
$(OBJ_DIR):
	echo "$(CURSPACES)Criando pasta \"$(OBJ_DIR)\""
	$(MKDIR) $(OBJ_DIR)

# Alvo padrão
all: $(OBJ_DIR) $(OUT)

