package be.cronos.edge.service.context;

import io.vertx.core.http.HttpServerResponse;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import static be.cronos.edge.service.context.CurrentContext.cleanContext;

@Provider
public class ContextResponseFilter implements ContainerResponseFilter {

    @Context
    HttpServerResponse response;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        response.putHeader(CustomHeaders.CORRELATION_ID, CurrentContext.getCorrelationId());
        cleanContext();
    }

}
