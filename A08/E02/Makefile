#========================================
# Base para compilar um programa usando a MVN
#========================================

# Inclui as variaveis básicas
TOPDIR=${CURDIR}/../../
include ${TOPDIR}/UsedVars.mk

# Arquivos fontes
SRCS=T5G12A03E02_main_13.asm \
	T5G12A03E02_const_13.asm \
	T5G12A03E02_rotinas_13.asm

OUT=T5G12A03E02.mvn
BASE_RELOCACAO=0000
ENDERECO_INICIO=000

# Pasta de objetos
OBJ_DIR=obj

# Cria os nomes dos objetos fontes 
OBJS=$(SRCS:%.asm=$(OBJ_DIR)/%.mvn)
LSTS=$(SRCS:%.asm=$(OBJ_DIR)/%.lst)

# Sempre faz a limpeza
.PHONY: clean

# Alvo padrão
all: $(OBJ_DIR) $(OUT)

# Copia os asm para a pasta
$(OBJ_DIR)/%.asm: %.asm
	$(CP) $< $@

# Compila o asm para lst e mvn
$(OBJ_DIR)/%.mvn: $(OBJ_DIR)/%.asm
	echo "$(CURSPACES)Compilando $<"
	$(JAVA) -cp $(MLR) montador.MvnAsm $<

# Linka os mvn gerados
$(OBJ_DIR)/link.mvn: $(OBJS)
	echo "$(CURSPACES)Linkando $(OBJS)"
	$(JAVA) -cp $(MLR) linker.MvnLinker $(OBJS) -s $(OBJ_DIR)/link.mvn

# Realoca o arquivo
$(OUT): $(OBJ_DIR)/link.mvn
	echo "$(CURSPACES)Realocando"
	$(JAVA) -cp $(MLR) relocator.MvnRelocator $(OBJ_DIR)/link.mvn $(OUT) $(BASE_RELOCACAO) $(ENDERECO_INICIO)
	$(MV) $(OUT:.mvn=.run) $(OBJ_DIR)/$(OUT:.mvn=.run)

# Cria o diretório de objeto
$(OBJ_DIR):
	echo "$(CURSPACES)Criando pasta \"$(OBJ_DIR)\""
	$(MKDIR) $(OBJ_DIR)

# Alvo padrão
clean:
	@echo "$(CURSPACES)Removendo $(LSTS) $(OBJS) $(OBJ_DIR)/$(OUT:.mvn=.run) $(OBJ_DIR)/link.mvn"
	$(RM) $(LSTS) $(OBJS) $(OBJ_DIR)/$(OUT:.mvn=.run) $(OBJ_DIR)/link.mvn $(OUT)

