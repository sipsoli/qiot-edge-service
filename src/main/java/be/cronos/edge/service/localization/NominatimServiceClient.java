package be.cronos.edge.service.localization;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@RegisterRestClient(configKey = "nominatim-api")
public interface NominatimServiceClient {

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<Location> query(@QueryParam("q") String address,
                         @QueryParam("format") String format,
                         @QueryParam("addressdetails") int detailLevel,
                         @QueryParam("limit") int limit);
}
