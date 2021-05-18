package be.cronos.edge.service.sensor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollutionResponse {

    private String stationId;
    private Instant instant;
    private Integer gt0_3um;
    private Integer gt0_5um;
    private Integer gt10um;
    private Integer gt1_0um;
    private Integer gt2_5um;
    private Integer gt5_0um;
    private Integer pm10;
    private Integer pm10_atm;
    private Integer pm1_0;
    private Integer pm1_0_atm;
    private Integer pm2_5;
    private Integer pm2_5_atm;

}
