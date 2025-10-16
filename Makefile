# Makefile for Lox Interpreter (Windows版本)
# ==========================================

# 基本配置
BUILD_DIR = build
SRC_FILES = src/com/craftinginterpreteres/lox/*.java Expr.java
MAIN_CLASS = com.craftinginterpreters.lox.Lox

# 默认目标
all: compile

# 显示帮助
help:
	@echo Lox 解释器构建系统
	@echo ===================
	@echo 可用命令:
	@echo   make compile     - 编译所有文件
	@echo   make run         - 运行交互模式
	@echo   make test        - 运行测试
	@echo   make clean       - 清理编译文件
	@echo   make help        - 显示帮助

# 创建构建目录
$(BUILD_DIR):
	@if not exist $(BUILD_DIR) mkdir $(BUILD_DIR)

# 编译
compile: $(BUILD_DIR)
	@echo 编译 Lox 源文件...
	javac -d $(BUILD_DIR) $(SRC_FILES)
	@echo 编译完成!

# 运行交互模式
run: compile
	@echo 启动 Lox 解释器...
	java -cp $(BUILD_DIR) $(MAIN_CLASS)

# 测试扫描器
test: compile
	@echo 测试词法扫描器...
	@echo print 1 + 2 * 3; > test.lox
	java -cp $(BUILD_DIR) $(MAIN_CLASS) test.lox
	@del test.lox

# 测试 AST 打印器  
test-ast: compile
	@echo 测试 AST 打印器...
	java -cp $(BUILD_DIR) com.craftinginterpreters.lox.AstPrinter

# 清理
clean:
	@echo 清理构建文件...
	@if exist $(BUILD_DIR) rmdir /s /q $(BUILD_DIR)
	@if exist test.lox del test.lox
	@echo 清理完成!

# 重新构建
rebuild: clean compile

.PHONY: all help compile run test test-ast clean rebuild