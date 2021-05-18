package be.cronos.edge.service.registration;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1")
@RegisterRestClient(configKey = "registration-api")
public interface RegistrationServiceClient {

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    RegisterResponse createRegisterRequest(RegisterRequest request);

    @GET
    @Path("/register/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    RegisterResponse getRegistrationRequest(@PathParam("id") String id);
}
