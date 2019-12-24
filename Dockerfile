FROM tomcat:jdk11-openjdk 
MAINTAINER Amit Patil

RUN pwd

COPY target/spring-mvc-revise.war /usr/local/tomcat/webapps/

#copy server.xml file with gzip compression enable to replace existing one that ships with container
COPY server.xml /usr/local/tomcat/conf