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
	QueryOperations.java \
	assignmentP3.java
    
MAIN = assignmentP3

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) input.txt output.txt
clean:
	$(RM) *.class