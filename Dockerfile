FROM ibmjava:8-sfj
LABEL maintainer="IBM Java Engineering at IBM Cloud"

COPY ./target/Banking-0.0.1-SNAPSHOT.jar /app.jar


ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
ENV NODE_ENV production
ENV PORT 8080
EXPOSE 8080