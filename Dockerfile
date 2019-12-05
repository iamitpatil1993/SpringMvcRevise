FROM tomcat:jdk11-openjdk 
MAINTAINER Amit Patil

RUN pwd

COPY target/spring-mvc-revise.war /usr/local/tomcat/webapps/
COPY server.xml /usr/local/tomcat/conf