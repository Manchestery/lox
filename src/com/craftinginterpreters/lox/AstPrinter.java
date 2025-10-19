package com.craftinginterpreters.lox;

class AstPrinter implements Expr.Visitor<String> {
  String print(Expr expr) {
    return expr.accept(this);
  }
  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesize(expr.operator.lexeme, expr.left, expr.right);
  }
  
  @Override
  public String visitConditionalExpr(Expr.Conditional expr) {
    return parenthesize("?:", expr.condition, expr.thenBranch, expr.elseBranch);
  }
  
  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesize("group", expr.expression);
  }
  @Override
  public String visitVariableExpr(Expr.Variable expr) {
    return expr.name.lexeme;
  }
  @Override
  public String visitAssignExpr(Expr.Assign expr) {
    return "(assign " + expr.name.lexeme + " " + expr.value.accept(this) + ")";
  }
  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null) return "nil";
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesize(expr.operator.lexeme, expr.right);
  }
  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    for (Expr expr : exprs) {
        builder.append(" ");
        builder.append(expr.accept(this));
    }
    builder.append(")");

    return builder.toString();
  }
    public static void main(String[] args) {
        // 测试原有表达式: -123 * (45.67)
        Expr expression1 = new Expr.Binary(
            new Expr.Unary(
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(123)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(
                new Expr.Literal(45.67)));

        System.out.println("原表达式: " + new AstPrinter().print(expression1));
        
        // 测试逗号表达式: 1, 2, 3
        Expr commaExpr = new Expr.Binary(
            new Expr.Binary(
                new Expr.Literal(1),
                new Token(TokenType.COMMA, ",", null, 1),
                new Expr.Literal(2)
            ),
            new Token(TokenType.COMMA, ",", null, 1),
            new Expr.Literal(3)
        );
        
        System.out.println("逗号表达式: " + new AstPrinter().print(commaExpr));
        
        // 测试三元表达式: true ? 42 : 0
        Expr conditionalExpr = new Expr.Conditional(
            new Expr.Literal(true),
            new Expr.Literal(42),
            new Expr.Literal(0)
        );
        
        System.out.println("三元表达式: " + new AstPrinter().print(conditionalExpr));
        
        // 测试嵌套三元表达式: a ? b : c ? d : e
        Expr nestedConditional = new Expr.Conditional(
            new Expr.Literal("a"),
            new Expr.Literal("b"),
            new Expr.Conditional(
                new Expr.Literal("c"),
                new Expr.Literal("d"),
                new Expr.Literal("e")
            )
        );
        
        System.out.println("嵌套三元表达式: " + new AstPrinter().print(nestedConditional));
    }
}