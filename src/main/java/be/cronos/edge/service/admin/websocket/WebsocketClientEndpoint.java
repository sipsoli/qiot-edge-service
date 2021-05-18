package be.cronos.edge.service.admin.websocket;

import be.cronos.edge.service.admin.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;

@ClientEndpoint
@RequiredArgsConstructor
public class WebsocketClientEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(WebsocketClientEndpoint.class);

    private Session session = null;
    private final MessageHandler messageHandler;

    @OnOpen
    public void onOpen(Session session) {
        LOG.info("opening websocket connection {}", session.getId());
        this.session = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        LOG.info("closing websocket connection {} for reason {}", session.getId(), reason);
        this.session = null;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOG.error("error on subscription {} error {}", session.getId(), throwable.getMessage(), throwable);
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

}
