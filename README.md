# qiot-edge-service project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## links

Assignement     https://qiot-project.github.io/blog/usecase-covid-19/

Quarkus guides: https://quarkus.io/guides

Portainer:      http://0.0.0.0:9000/#!/1/docker/containers

Grafana:        http://0.0.0.0:3000/dashboard

Influx DB:      http://0.0.0.0:8086/

# documentation

https://www.openshift.com/learn/topics/edge
https://www.openshift.com/blog/tag/edge

https://developers.redhat.com/blog/2019/01/31/iot-edge-development-and-deployment-with-containers-through-openshift-part-1/
https://developers.redhat.com/blog/2019/02/05/iot-edge-development-and-deployment-with-containers-through-openshift-part-2/

https://developers.redhat.com/blog/2018/08/29/intro-to-podman/
https://developers.redhat.com/blog/2018/11/29/managing-containerized-system-services-with-podman/

https://github.com/redhat-iot/Virtual_IoT_Gateway
https://github.com/alezzandro/iotgw_mainproject

https://quarkus.io/blog/quarkus-native-on-a-raspberry-pi/

## Setting up docker environment

checkout project: https://github.com/qiot-project/qiot-covid19-all-docker and follow instructions.

## Runing the application locally

Because of the certificates i needed to update my /etc/hosts file to
0.0.0.0         registration-service-covid19-dev.apps.cluster-fcd8.fcd8.example.opentlc.com
127.0.0.1       localhost Local local kafka

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## application endpoints

Station statistics
```shell script
curl -X GET http://localhost:8080/v1/station/
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/qiot-edge-service-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Provided examples

### RESTEasy JAX-RS example

REST is easy peasy with this Hello World RESTEasy resource.

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
