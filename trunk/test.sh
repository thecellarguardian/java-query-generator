#!/bin/sh

javac \
	-cp paql \
	-g \
	-Xlint:all \
	paql/test/*.java \
	paql/lib/System/System.java \
	paql/lib/CompilationStep/Scanner/Scanner.java \
	paql/lib/CompilationStep/Utilities/Word/Word.java \
	paql/src/PAQLCompilationStep/PAQLScanner/PAQLScanner.java