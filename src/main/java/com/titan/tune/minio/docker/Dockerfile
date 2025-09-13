# Multi-stage build pour optimiser la taille de l'image
FROM maven:3.9.5-openjdk-17-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Télécharger les dépendances (cache layer)
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

# Stage de production
FROM eclipse-temurin:17-jre-alpine

# Installer curl pour les health checks
RUN apk add --no-cache curl

# Créer un utilisateur non-root
RUN addgroup -S spring && adduser -S spring -G spring

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR depuis le stage de build
COPY --from=build /app/target/*.jar app.jar

# Créer le répertoire des logs
RUN mkdir -p /app/logs && chown -R spring:spring /app

# Changer vers l'utilisateur non-root
USER spring:spring

# Exposer le port
EXPOSE 8081

# Variables d'environnement par défaut
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=docker

# Point d'entrée
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/api/actuator/health || exit 1
