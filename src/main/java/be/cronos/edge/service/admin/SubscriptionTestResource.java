package be.cronos.edge.service.admin;

import be.cronos.edge.service.EdgeServiceApplication;
import be.cronos.edge.service.Station;
import be.cronos.edge.service.admin.websocket.WebsocketManager;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("subscribe")
@RequiredArgsConstructor
public class SubscriptionTestResource {

    private final EdgeServiceApplication edgeServiceApplication;
    private final WebsocketManager websocketManager;

    @GET
    public String test() {
        Station station = edgeServiceApplication.getStation();
        websocketManager.subscribe(station.getId());
        return "subscribed";
    }
}
