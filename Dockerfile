FROM mongo:6.0.2
COPY --chown=999 --chmod=600 application.conf /bitnami/cassandra/conf/application.conf