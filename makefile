# cartelle
DOT_DIR = app/build/generated/graphs
IMG_DIR = app/build/generated/img

# lista dei file senza estensione
FORMULAS = p_xor_q p_xor_q_xor_r p_or_q p_or_q_or_r p_and_q p_and_q_and_r not_p_and_q

# target principale
all: generate_dot generate_png

# genera i file .dot eseguendo il programma Java
generate_dot:
	@echo "Generazione BDD (.dot)..."
	java -cp app/build/classes/java/main mathematicallogic.Main

# genera i file .png dai .dot
generate_png: $(FORMULAS:%=$(IMG_DIR)/%.png)

$(IMG_DIR)/%.png: $(DOT_DIR)/%.dot
	@mkdir -p $(IMG_DIR)
	dot -Tpng $< -o $@

# pulizia
clean:
	rm -rf $(DOT_DIR)/*.dot $(IMG_DIR)/*.png
