package be.cronos.edge.service.sensor;

import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
@RequiredArgsConstructor
public class SensorHealthCheck implements HealthCheck {

    private static final Logger LOG = LoggerFactory.getLogger(SensorHealthCheck.class);

    private static final String SENSOR_COLLECTOR_NAME = "Sensor collector - liveness check";

    private final SensorService sensorService;

    @Override
    public HealthCheckResponse call() {
        boolean pollutionHealthCheck = pollutionCollectorUp();
        boolean gasHealthCheck = gasCollectorUp();

        HealthCheckResponseBuilder healthBuilder = HealthCheckResponse.named(SENSOR_COLLECTOR_NAME)
                .withData("pollution-collector", pollutionHealthCheck ? "[OK]" : "[KO]")
                .withData("gas-collector", gasHealthCheck ? "[OK]" : "[KO]");

        if (pollutionHealthCheck && gasHealthCheck){
            healthBuilder.up();
        } else {
            healthBuilder.down();
        }

        return healthBuilder.build();
    }

    private boolean pollutionCollectorUp(){
        try {
            sensorService.getPollution();
            return true;
        } catch (Exception e){
            LOG.warn("error while collecting pollution data", e);
            return false;
        }
    }

    private boolean gasCollectorUp(){
        try {
            sensorService.getGas();
            return true;
        } catch (Exception e){
            LOG.warn("error while collecting gas data", e);
            return false;
        }
    }
}
