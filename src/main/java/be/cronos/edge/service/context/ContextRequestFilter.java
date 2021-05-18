package be.cronos.edge.service.context;

import io.vertx.core.http.HttpServerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

@Provider
public class ContextRequestFilter implements ContainerRequestFilter {

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {
        addCorrelationIdHeader();
    }

    private void addCorrelationIdHeader(){
        String correlationId = request.getHeader(CustomHeaders.CORRELATION_ID);
        if (correlationId == null){
            correlationId = UUID.randomUUID().toString();
        }
        CurrentContext.setCorrelationId(correlationId);
        MdcUtil.add(MdcFields.CORRELATION_ID, correlationId);
    }

}
