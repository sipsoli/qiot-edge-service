package be.cronos.edge.service;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Data
@NoArgsConstructor
@ConfigProperties(prefix = "qiot")
public class StationConfiguration {

    private Station station;
    private Admin admin;
    private Mqtts mqtts;
    private Mqtt mqtt;
    @ConfigProperty(name = "datafile.path")
    private String dataFile;

    @Data
    @NoArgsConstructor
    public static class Station {
        private String name;
        private String address;
    }

    @Data
    @NoArgsConstructor
    public static class Admin {
        private String webSocketAddress;
    }

    @Data
    @NoArgsConstructor
    public static class Mqtt {

        @ConfigProperty(name = "client.connection")
        private Connection connection;
        @ConfigProperty(name = "client.topic")
        private Topic topic;

        @Data
        @NoArgsConstructor
        public static class Connection {
            private boolean generatedClientId;
            private String host;
            private int port;
            private boolean ssl;
        }

        @Data
        @NoArgsConstructor
        public static class Topic {
            @ConfigProperty(name = "gas.topic")
            private String gasTopic;
            @ConfigProperty(name = "gas.qos")
            private int gasQos;
            @ConfigProperty(name = "pollution.topic")
            private String pollutionTopic;
            @ConfigProperty(name = "pollution.qos")
            private int pollutionQos;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Mqtts {

        @ConfigProperty(name = "ks")
        private Keystore keystore;;
        @ConfigProperty(name = "ts")
        private Truststore truststore;

        @Data
        @NoArgsConstructor
        public static class Keystore {
            private String path;
            private String password;
        }

        @Data
        @NoArgsConstructor
        public static class Truststore {
            private String path;
            private String password;
        }
    }

}
