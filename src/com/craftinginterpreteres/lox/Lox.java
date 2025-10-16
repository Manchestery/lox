package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Arrays;

public class Lox {
  static boolean hadError = false;
  static List<String> sourceLines;
  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      System.out.println("Usage: jlox [script]");
      System.exit(64); 
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      runPrompt();
    }
  }
  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));
    if (hadError) System.exit(65);
  }
  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);
    for(;;) {
    System.out.print("> ");
    String line = reader.readLine();
    if (line == null) break;
    run(line);
    hadError = false;
    }
  }
  private static void run(String source) {
    // Implement the logic to interpret and execute the source code
    sourceLines = Arrays.asList(source.split("\n"));
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();
    for(Token token : tokens) {
      System.out.println(token);
    }
  }
  static void error(int line, String message) {
    report(line, "",message);
  }
  static void error(int line, int column, String message) {
    report(line, "", message, column, column);
}
  static void error(int line,int startCol, int endCol, String message) {
      report(line, "", message, startCol, endCol);
  }
  private static void report(int line,String where, String message) {
    System.err.println("[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }
  private static void report(int line, String where, String message, int startCol, int endCol) {
    System.err.println("[line " + line + "] Error" + where + ": " + message);
    if (sourceLines != null && line > 0 && line <= sourceLines.size()) {
      String sourceLine = sourceLines.get(line - 1);
      System.err.println();
      System.err.printf("    %d | %s%n", line, sourceLine);
      System.err.print("      | ");
      for(int i = 0; i<sourceLine.length(); i++) {
        if(i >= startCol && i<= endCol) {
          System.err.print("^");
        }else if (i < startCol) {
          if (sourceLine.charAt(i) == '\t') {
            System.err.print("\t");
          } else {
            System.err.print(" ");
          }
        } else {
          break;
        }
      }
      if (startCol <= endCol) {
        System.err.println("-- Here.");
      } else {
        System.err.println();
      }
    }
    hadError = true;
}
}