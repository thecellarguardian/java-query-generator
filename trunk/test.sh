#!/bin/sh

javac \
	-cp paql \
	-g \
	-Xlint:all \
	paql/test/Test.java \
	paql/lib/System/System.java \
	paql/lib/Compiler/LexicalAnalyzer/LexicalAnalyzer.java \
	paql/lib/Compiler/LexicalAnalyzer/OutputType/Token/Token.java \
    paql/lib/Compiler/LexicalAnalyzer/OutputType/Token/EvaluableToken/EvaluableToken.java \
    paql/src/PAQLCompiler/PAQLLexicalAnalyzer/PAQLLexicalAnalyzer.java \
	paql/src/PAQLCompiler/PAQLLexicalAnalyzer/PAQLTokenClass/PAQLTokenClass.java \
	paql/lib/Compiler/SyntacticAnalyzer/SyntacticAnalyzer.java \
	paql/lib/Compiler/SyntacticAnalyzer/OutputType/ParseTree/ParseTree.java \
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/PAQLParseTreeClass/PAQLParseTreeClass.java\
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/PAQLSyntacticAnalyzer.java \
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/OutputType/Description/Element/KeyDeclaration/KeyDeclaration.java \
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/OutputType/Description/Element/VariableDeclaration/VariableDeclaration.java \
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/OutputType/Description/Element/Element.java \
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/OutputType/Description/Element/DeclarationBlock/DeclarationBlock.java \
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/OutputType/Description/Container/Container.java \
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/OutputType/Description/Description.java \
	paql/src/PAQLCompiler/PAQLSyntacticAnalyzer/OutputType/Description/Query/Query.java
