package be.cronos.edge.service.registration;

import lombok.Value;

@Value
public class RegisterRequest {
    String serial;
    String name;
    double longitude;
    double latitude;
    String keyStorePassword;
}
