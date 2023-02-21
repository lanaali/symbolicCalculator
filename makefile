JAR_FILE=build/jar/Calculator.jar

.PHONY: $(JAR_FILE)
$(JAR_FILE):
	ant jar

.PHONY: main
main: $(JAR_FILE)

.PHONY: run
run: $(JAR_FILE)
	java -jar $(JAR_FILE)

.PHONY: spotbugs
spotbugs:
	ant spotbugs

.PHONY: test
test:
	ant test

.PHONY: clean
clean:
	ant clean

