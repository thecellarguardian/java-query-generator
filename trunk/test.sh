#!/bin/sh

javac \
	-cp paql \
	-g \
	-Xlint:all \
	paql/test/*.java \
	paql/lib/System/System.java \
	paql/lib/Compiler/LexicalAnalyzer/LexicalAnalyzer.java \
	paql/lib/Compiler/LexicalAnalyzer/Scanner/Scanner.java \
	paql/lib/Compiler/LexicalAnalyzer/Tokenizer/OutputType/Token/Token.java \
	paql/lib/Compiler/LexicalAnalyzer/Tokenizer/Tokenizer.java \
	paql/lib/Compiler/LexicalAnalyzer/Scanner/OutputType/Word/Word.java \
	paql/src/PAQLCompiler/PAQLScanner/PAQLScanner.java \
	paql/src/PAQLCompiler/PAQLTokenizer/PAQLTokenizer.java \
	paql/src/PAQLCompiler/PAQLTokenizer/PAQLTokenClass/PAQLTokenClass.java
