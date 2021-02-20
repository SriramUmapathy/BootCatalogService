FROM openjdk:8
EXPOSE 8089
COPY ./target/book-catalog-0.0.1.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "book-catalog-0.0.1.jar"]