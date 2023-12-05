FROM openjdk:17
#FROM alpine:latest

#RUN apk add openjdk17

ADD open-messenger-0.0.1-SNAPSHOT.jar ~/open-messenger-0.0.1-SNAPSHOT.jar


EXPOSE 8080/tcp

WORKDIR ~/

ENTRYPOINT [ "java", "-jar", "open-messenger-0.0.1-SNAPSHOT.jar"]

# /Users/tomaszbadon/git/OpenMessenger/docker/open-messenger
# /Users/tomaszbadon/git/OpenMessenger/open-messenger/build/lib

# docker build --tag open-messenger-app:latest .