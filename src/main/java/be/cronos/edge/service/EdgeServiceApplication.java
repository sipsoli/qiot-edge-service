package be.cronos.edge.service;

import be.cronos.edge.service.registration.RegistrationFactory;
import be.cronos.edge.service.admin.websocket.WebsocketManager;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;

import static be.cronos.edge.service.context.CurrentContext.cleanContext;

@ApplicationScoped
@RequiredArgsConstructor
public class EdgeServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(EdgeServiceApplication.class);

    private final RegistrationFactory registrationFactory;
    private final WebsocketManager websocketManager;

    private boolean websocketConnectionEstablished = false;
    private Station station;

    void onStart(@Observes StartupEvent event){

        String folder = ConfigProvider.getConfig().getValue("qiot.folder.root", String.class);
        LOG.info("configured config folder for https certificates: {}", folder);

        String filenames = Arrays.toString(Path.of(folder).toFile().list());
        LOG.info("listing content: {}", filenames);

        this.station = registrationFactory.registerStation();

        LOG.info("Station {} is starting...", station.getName());
        connectToWebSocketManager();
    }

    void onStop(@Observes ShutdownEvent event){
        LOG.info("The application is stopping...");
        websocketManager.unsubscribe();
        cleanContext();
    }

    public Station getStation() {
        return station;
    }

    private void connectToWebSocketManager(){
        while (!websocketConnectionEstablished){
            try {
                LOG.info("attempting to connect to websocket admin manager");
                websocketManager.subscribe(station.getId());
                websocketConnectionEstablished = true;
            } catch (Exception e){
                websocketConnectionEstablished = false;
                LOG.warn("could not connect to websocket admin manager" +
                        ", station analytics will not be transmitted: {}", e.getMessage());
                try {
                    Thread.sleep(Duration.ofMinutes(1).toMillis());
                } catch (InterruptedException interruptedException) {
                    LOG.warn("error while awaiting invocation: {}", e.getMessage());
                }
            }
        }
    }

}
