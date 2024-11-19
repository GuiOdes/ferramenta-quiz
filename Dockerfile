# Etapa 1: Build da aplicação
FROM gradle:8.3-jdk17 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de configuração do Gradle e o código fonte
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
COPY src ./src

# Realiza o build da aplicação
RUN ./gradlew clean build -x test -x detekt

# Etapa 2: Criação da imagem final
FROM eclipse-temurin:17-jre

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=builder /app/build/libs/*.jar app.jar

# Expõe a porta que a aplicação utiliza
EXPOSE 8080

# Define o comando de entrada para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
