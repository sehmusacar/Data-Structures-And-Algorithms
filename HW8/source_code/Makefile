# Makefile for Social Network Analysis System

# Variables
JAVAC = javac
JAVA = java
MAINCLASS = Main
SOURCES = Person.java SocialNetworkGraph.java Main.java
CLASSES = $(SOURCES:.java=.class)

# Default target
all: $(CLASSES)

# Compile .java files to .class files
%.class: %.java
	$(JAVAC) $<

# Run the program
run: all
	$(JAVA) $(MAINCLASS)

# Generate JavaDoc
javadoc:
	javadoc -d doc $(SOURCES)

# Clean up .class files and documentation
clean:
	rm -f *.class
	rm -rf doc

# PHONY targets
.PHONY: all run clean javadoc
