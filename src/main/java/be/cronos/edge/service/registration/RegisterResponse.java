package be.cronos.edge.service.registration;

import lombok.Value;

@Value
public class RegisterResponse {
    String id;
    String truststore;
    String keystore;
}
