package be.cronos.edge.service.context;

public final class CurrentContext {

    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();

    public static String getCorrelationId() {
        return CORRELATION_ID.get();
    }

    public static void setCorrelationId(String correlationId) {
        CORRELATION_ID.set(correlationId);
    }

    public static void removeCorrelationId() {
        CORRELATION_ID.remove();
    }

    public static void cleanContext(){
        MdcUtil.remove(MdcFields.CORRELATION_ID);
        CurrentContext.removeCorrelationId();
    }

}