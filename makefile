build:
	javac -d ./bin src/IQ/*.java
run:
	java -classpath ./bin IQ.App $(INPUT)
