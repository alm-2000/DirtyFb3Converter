all: build  jar

build: DirtyFb3Converter.java
	javac DirtyFb3Converter.java

jar: DirtyFb3Converter.class
	jar -cvmf MANIFEST.MF DirtyFb3Converter.jar *.class

clean:
	rm -f *.class DirtyFb3Converter.jar