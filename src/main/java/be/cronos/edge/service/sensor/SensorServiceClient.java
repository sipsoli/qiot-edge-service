package be.cronos.edge.service.sensor;

import be.cronos.edge.service.sensor.dto.SystemIdResponse;
import be.cronos.edge.service.sensor.scheduler.SensorClientResponseFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@RegisterRestClient(configKey = "sensor-api")
@RegisterProvider(SensorClientResponseFilter.class)
@RegisterProvider(CustomExceptionMapper.class)
public interface SensorServiceClient {

    @GET
    @Path("/api/sensors/gas")
    //@Path("/gas/all")
    @Produces(MediaType.TEXT_PLAIN)
    String getGas();

    @GET
    @Path("/api/sensors/pollution")
    //@Path("/particulates/all")
    @Produces(MediaType.TEXT_PLAIN)
    String getPollution();

    @GET
    @Path("/api/system/id")
    @Produces(MediaType.TEXT_PLAIN)
    SystemIdResponse getSerial();

}
