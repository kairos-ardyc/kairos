FROM liquibase:alpine

WORKDIR /liquibase

COPY --chown=liquibase:liquibase changelog.yml changelog.yml
COPY --chown=liquibase:liquibase changeset/ changeset/
COPY --chown=liquibase:liquibase run.sh run.sh

USER liquibase

RUN chmod +x run.sh

ENTRYPOINT ["./run.sh"]