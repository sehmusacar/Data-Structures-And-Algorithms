JC = javac
JFLAGS = -g
JD = javadoc
JDFLAGS = -protected -splitindex -use -author -version -d ./javadoc
TARGET = Main
SOURCES = Main.java AVLTree.java GUIVisualization.java Stock.java StockDataManager.java GenerateInputFile.java

default: $(TARGET)

$(TARGET): $(SOURCES)
	$(JC) $(JFLAGS) $^

run: input.txt
	java -Xint $(TARGET) input.txt

input.txt:
	java GenerateInputFile 

doc:
	$(JD) $(JDFLAGS) $(SOURCES)

clean:
	rm -f *.class input.txt

cleandoc:
	rm -rf ./javadoc

.PHONY: clean cleandoc default run input.txt doc

