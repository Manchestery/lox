package com.craftinginterpreters.lox

class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;
    
    Token(TokenType type, String lexeme, Object literal, int line) {
        this.lexeme = lexeme;
        this.line = line;
        this.literal = literal;
        this.type = type;
    }

    pulic String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
