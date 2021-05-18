package be.cronos.edge.service.sensor;

import be.cronos.edge.service.sensor.dto.GasResponse;
import be.cronos.edge.service.sensor.dto.PollutionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequiredArgsConstructor
@Path("/sensors")
public class SensorResource {

    private final SensorService sensorService;

    @GET
    @Path("/gas")
    @Produces(MediaType.APPLICATION_JSON)
    public GasResponse getGas() {
        return sensorService.getGas();
    }

    @GET
    @Path("/pollution")
    @Produces(MediaType.APPLICATION_JSON)
    public PollutionResponse getPollution() {
        return sensorService.getPollution();
    }

}
