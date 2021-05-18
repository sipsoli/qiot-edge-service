package be.cronos.edge.service.sensor;

import be.cronos.edge.service.sensor.emitter.GasStreamEmitter;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

@Priority(5000)
public class CustomExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionMapper.class);

    @Override
    public RuntimeException toThrowable(Response response) {
        int status = response.getStatus();
        String msg = getBody(response);

        LOG.info("exception caught: {}", msg);

        RuntimeException e;
        switch (status){
            case 412: e = new ValidationException(msg);
            break;
            default:
                e = new WebApplicationException(status);
        }
        return e;
    }

    private String getBody(Response response){
        ByteArrayInputStream is = (ByteArrayInputStream) response.getEntity();
        byte[] bytes = new byte[is.available()];
        is.read(bytes, 0, is.available());
        return new String(bytes);
    }
}
