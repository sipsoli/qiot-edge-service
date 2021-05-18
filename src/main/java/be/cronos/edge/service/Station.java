package be.cronos.edge.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    String id;
    String serial;
    String name;
    Double latitude;
    Double longitude;
}
