#!/bin/bash
/liquibase/docker-entrypoint.sh update \
  --changelogFile=changelog.yml \
  --url="${POSTGRES_URL}" \
  --username="${POSTGRES_USER}" \
  --password="${POSTGRES_PASSWORD}"