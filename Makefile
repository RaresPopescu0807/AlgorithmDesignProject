build:
	javac -sourcepath src src/thebugbusters/pa/project/Main.java
clean:
	find src -name "*.class" -delete
run:
	java -cp src thebugbusters.pa.project.Main
