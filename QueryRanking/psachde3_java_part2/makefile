JFLAGS = -g
JC = javac
FILE= a.txt
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	FileProcessor.java \
	StringOperations.java \
	assignmentP2.java
    
MAIN = assignmentP2

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) input.txt output.txt
clean:
	$(RM) *.class