package be.cronos.edge.service.registration;

import be.cronos.edge.service.Station;
import be.cronos.edge.service.StationConfiguration;
import be.cronos.edge.service.localization.Location;
import be.cronos.edge.service.localization.LocalizationService;
import be.cronos.edge.service.sensor.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@ApplicationScoped
@RequiredArgsConstructor
public class RegistrationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationFactory.class);

    private final SensorService sensorService;
    private final RegistrationService registrationService;
    private final LocalizationService localizationService;
    private final StationConfiguration stationConfiguration;
    private final ObjectMapper objectMapper;

    public Station registerStation() {
        Station station = getRegisteredStation();
        if (Objects.nonNull(station)){
            LOG.info("station {} already registered, skipping registration.", station.getName());
            return station;
        }

        String stationAddress = stationConfiguration.getStation().getAddress();
        String stationName = stationConfiguration.getStation().getName();
        String keystorePassword = stationConfiguration.getMqtts().getKeystore().getPassword();
        Location location = localizationService.retrieveGeoInfo(stationAddress);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String serial = sensorService.getSerial();

        String stationId = registrationService.register(serial, stationName, latitude, longitude, keystorePassword);
        station = new Station(stationId, serial, stationName, longitude, latitude);
        storeStationDataToDisk(station);
        return station;
    }

    private Station getRegisteredStation(){
        Path stationDataFilePath = Path.of(stationConfiguration.getDataFile());
        boolean stationDataExists = Files.exists(stationDataFilePath);
        if (stationDataExists) {
            try {
                return objectMapper.readValue(stationDataFilePath.toFile(), Station.class);
            } catch (IOException e) {
                LOG.warn("could not parse existing station data file: {}", e.getMessage());
            }
        }
        return null;
    }

    private void storeStationDataToDisk(Station station){
        LOG.debug("attempting to store station data to disk: {}", station);
        Path stationDataFilePath = Path.of(stationConfiguration.getDataFile());
        try {
            Files.createDirectories(stationDataFilePath.getParent());
            objectMapper.writeValue(stationDataFilePath.toFile(), station);
            LOG.info("successfully stored station data to disk: {}", station);
        } catch (IOException e) {
            LOG.error("could not store station data to disk", e);
        }
    }

}
