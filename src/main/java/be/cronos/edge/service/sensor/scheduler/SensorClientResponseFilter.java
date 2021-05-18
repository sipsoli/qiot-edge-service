package be.cronos.edge.service.sensor.scheduler;

import be.cronos.edge.service.context.CurrentContext;
import be.cronos.edge.service.context.CustomHeaders;
import be.cronos.edge.service.context.MdcFields;
import be.cronos.edge.service.context.MdcUtil;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

public class SensorClientResponseFilter implements ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) {
        addCorrelationIdHeader(responseContext.getHeaders());
    }

    private void addCorrelationIdHeader(MultivaluedMap<String, String> headers){
        String correlationId = headers.getFirst(CustomHeaders.CORRELATION_ID);
        CurrentContext.setCorrelationId(correlationId);
        MdcUtil.add(MdcFields.CORRELATION_ID, correlationId);
    }

}
