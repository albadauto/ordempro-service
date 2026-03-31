# Estágio 1: Build
# Usa uma imagem do Maven para compilar o projeto
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o arquivo de configuração de dependências primeiro (otimiza o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte e gera o .jar
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
# Usa uma imagem JRE leve para rodar a aplicação
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o JAR gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a API
ENTRYPOINT ["java", "-jar", "app.jar"]