package be.cronos.edge.service.admin.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RegisterForReflection
public class Event {
    String id;
    String stationId;
    String action;
    String payload;
}
