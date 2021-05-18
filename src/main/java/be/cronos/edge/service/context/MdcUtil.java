package be.cronos.edge.service.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class MdcUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MdcUtil.class);

    public static void add(String fieldName, String value){
        if (isNotBlank(value)){
            LOG.trace("Adding MDC field {} '{}' to MDC", fieldName, value);
            MDC.put(fieldName, value);
        } else {
            LOG.trace("ignoring MDC field {} because value is empty", fieldName);
        }
    }

    public static void remove(String fieldName){
        LOG.trace("Removing {} from MDC", fieldName);
        MDC.remove(fieldName);
    }
}
