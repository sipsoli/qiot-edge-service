package be.cronos.edge.service.localization;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocalizationService {

    private final NominatimServiceClient nominatimServiceClient;

    public LocalizationService(@RestClient NominatimServiceClient nominatimServiceClient) {
        this.nominatimServiceClient = nominatimServiceClient;
    }

    public Location retrieveGeoInfo(String address){
        return nominatimServiceClient.query(address, "json", 1, 1)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
