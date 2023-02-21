# Calculator

This is a symbolic calculator.

## Building, running and testing

### Dependencies

* [OpenJDK][1]>=11
* [Apache Ant][2]>=1.10
* [GNU Make][3]

### Building, running, testing and cleaning

Build the calculator with:

```
$ make
```

Or run it:

```
$ make run
```

Run the tests:

```
$ make test
```

When you are tired of compiling and running and want to free up some disk space, you can run:

```
$ make clean
```

## Build procedure and source code structure

This project is entirely written in Java. A makefile is used to compile it, but behind the scenes [Ant][2] is used to compile the project. You can study and modify the Ant build script in the file build.xml.

The source code is grouped into four packages:

* `org.ioopm.calculator.ast` contains the abstract syntax tree for an expression. Source code in the directory `ast`.
* `org.ioopm.calculator.parser` has one class `CalculatorParser` which handles the parsing of an expression from a string. Source code in the `parser` directory.
* `org.ioopm.calculator` is the package of the main class `Calculator`. Its source can be found in `calculator/Calculator.java`.
* `org.ioopm.calculator.tests` in the directory `tests` is a set of [JUnit][4] tests of the AST and parser.

[1]: https://openjdk.java.net
[2]: https://ant.apache.org
[3]: https://www.gnu.org/software/make/
[4]: https://junit.org/junit5/
