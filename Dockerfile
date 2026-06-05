# =========================================================================
# ESTÁGIO 1: COMPILAÇÃO (O Render vai usar isso para gerar a pasta 'target')
# =========================================================================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /code

# Copia as configurações e o seu código fonte para dentro do container
COPY pom.xml /code/
COPY src /code/src/

# Compila o Quarkus e gera a pasta target/quarkus-app que estava faltando
RUN mvn clean package -DskipTests

# =========================================================================
# ESTÁGIO 2: EXECUÇÃO (Igual ao seu original, mas pegando os arquivos acima)
# =========================================================================
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24

ENV LANGUAGE='en_US:en'
WORKDIR /deployments

# Copia os arquivos que foram compilados lá no Estágio 1
COPY --from=build --chown=185 /code/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /code/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /code/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /code/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]