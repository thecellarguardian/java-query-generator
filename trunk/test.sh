#!/bin/sh

javac \
	-cp paql \
	-g \
	-Xlint:all \
	paql/test/*.java \
	paql/lib/System/System.java \
	paql/lib/Compiler/LexicalAnalyzer/LexicalAnalyzer.java \
	paql/lib/Compiler/LexicalAnalyzer/OutputType/Token/Token.java \
    paql/lib/Compiler/LexicalAnalyzer/OutputType/Token/EvaluableToken/EvaluableToken.java \
    paql/src/PAQLCompiler/PAQLLexicalAnalyzer/PAQLLexicalAnalyzer.java \
	paql/src/PAQLCompiler/PAQLLexicalAnalyzer/PAQLTokenClass/PAQLTokenClass.java
