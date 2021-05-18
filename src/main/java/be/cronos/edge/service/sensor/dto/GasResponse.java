package be.cronos.edge.service.sensor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GasResponse {

    private String stationId;
    private Instant instant;
    private BigDecimal adc;
    private BigDecimal nh3;
    private BigDecimal oxidising;
    private BigDecimal reducing;

}
