# Étape 2 : Création de l'image finale
FROM openjdk:17-jdk-slim

# Définir l'emplacement du fichier JAR de l'application
ARG JAR_FILE=MFI2-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Exposer le port sur lequel l'application sera accessible
EXPOSE 443
# Définir la commande à exécuter pour démarrer l'application avec le profil de production
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]