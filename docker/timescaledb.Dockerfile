FROM timescale/timescaledb:latest-pg12
ENV POSTGRES_PASSWORD=password
EXPOSE 5432
ADD ini.sql /docker-entrypoint-initdb.d/init-user-db.sql