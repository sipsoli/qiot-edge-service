package be.cronos.edge.service.admin.websocket;

import be.cronos.edge.service.StationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

@ApplicationScoped
public class WebsocketManager {

    private static final Logger LOG = LoggerFactory.getLogger(WebsocketManager.class);

    private Session session;
    private final StationConfiguration stationConfiguration;

    public WebsocketManager(StationConfiguration stationConfiguration) {
        this.stationConfiguration = stationConfiguration;
    }

    public void subscribe(String stationId) {
        if (session == null) {
            try {
                String wsURL = stationConfiguration.getAdmin().getWebSocketAddress() + "/stations/" + stationId + "/subscriptions";
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                this.session = container.connectToServer(WebsocketClientEndpoint.class, URI.create(wsURL));
                LOG.info("established websocket session {}", session.getId());
            } catch (Exception e){
                LOG.error("could not establish connection with websocket admin endpoint. feedback'll be disabled", e);
            }
        }
    }

    public void sendMessage(String message){
        if (this.session != null && this.session.isOpen()){
            LOG.debug("sending message to websocket: {}", message);
            this.session.getAsyncRemote().sendText(message);
        }
    }

    public void unsubscribe() {
        if (this.session != null && this.session.isOpen()){
            try {
                LOG.info("closing websocket session {}", session.getId());
                CloseReason reason = new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "unsubscribe");
                this.session.close(reason);
            } catch (IOException e) {
                LOG.error("error while closing websocket", e);
            }
        }
        this.session = null;
    }
}
