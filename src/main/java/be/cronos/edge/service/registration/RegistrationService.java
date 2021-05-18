package be.cronos.edge.service.registration;

import be.cronos.edge.service.StationConfiguration;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@ApplicationScoped
public class RegistrationService {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationServiceClient registrationServiceClient;
    private final StationConfiguration stationConfiguration;


    public RegistrationService(@RestClient RegistrationServiceClient registrationServiceClient,
                               StationConfiguration stationConfiguration) {
        this.registrationServiceClient = registrationServiceClient;
        this.stationConfiguration = stationConfiguration;
    }

    public String register(String serial, String name, double longitude, double latitude, String keystorePassword){
        try {
            RegisterRequest registerRequest = new RegisterRequest(serial, name, latitude, longitude, keystorePassword);
            LOG.debug("attempting registration process: {}", registerRequest);
            RegisterResponse registerResponse = registrationServiceClient.createRegisterRequest(registerRequest);
            LOG.info("station {} successfully registered with DataHub", registerResponse.getId());
            writeKeystoreToDisk(registerResponse.getKeystore());
            writeTruststoreToDisk(registerResponse.getTruststore());
            return registerResponse.getId();
        } catch (IOException e) {
            throw new RegistrationException("error occurred while registering station", e);
        }
    }

    private void writeKeystoreToDisk(String keystoreBase64Encoded) throws IOException {
        Path keystorePath = Path.of(stationConfiguration.getMqtts().getKeystore().getPath());
        byte[] keystoreContent = Base64.getDecoder().decode(keystoreBase64Encoded.getBytes(StandardCharsets.UTF_8));
        Files.createDirectories(keystorePath.getParent());
        Files.write(keystorePath, keystoreContent);
    }

    private void writeTruststoreToDisk(String trustStoreBase64Encoded) throws IOException {
        Path truststorePath = Path.of(stationConfiguration.getMqtts().getTruststore().getPath());
        byte[] truststoreContent = Base64.getDecoder().decode(trustStoreBase64Encoded.getBytes(StandardCharsets.UTF_8));
        Files.createDirectories(truststorePath.getParent());
        Files.write(truststorePath, truststoreContent);
    }
}
