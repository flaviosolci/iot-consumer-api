package br.com.iot.consumer.api.repository;

import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SensorEventRepository {

    private final DatabaseClient databaseClient;

    public SensorEventRepository(DatabaseClient databaseClient) {this.databaseClient = databaseClient;}

    public Mono<Void> save(SensorEventEntity sensorEventEntity) {
        return databaseClient.insert()
                .into(SensorEventEntity.class)
                .using(sensorEventEntity)
                .then();
    }
}
