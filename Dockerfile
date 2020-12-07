FROM openjdk:7
WORKDIR /usr/src/myapp
COPY Kontener.java /usr/src/myapp
RUN curl -L -o /usr/src/myapp/mysql-connector-java-5.1.6.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.6/mysql-connector-java-5.1.6.jar
RUN javac Kontener.java
CMD ["java", "-classpath", "mysql-connector-java-5.1.6.jar:.", "Kontener"]

