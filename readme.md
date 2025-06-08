## Premise

I was interested in checking out how can telemetry data be gathered from spring boot application. 

When it comes to generating metrics/traces we can approach it by:
- Using Micrometer
- Use open telemetry auto instrumentation 

What can be confusing is that both otel auto-instrumentation and micrometer can generate telemetry data in number of protocols.
Example. Micrometer can generate telemetry data in **otlp format** (and others). By default, open telemetry auto-instrumentation will generate telemetry data in otlp.

In this demo we will set up two services generating traces and metrics, using different approaches. 
One will use otel auto instrumentation, other micrometer.
 

## Requirements
- Java 21
- Docker

## Caveats
I'm running this in wsl which 

## Compontents

- Greetings app, Caller app
  - Uses otel auto instrumentation 
  - Metrics -  generated in otpl format
  - Traces - generated in otpl format
- Otel collector
  - Gathers traces from both applications
  - Gathers metrics using otel format. **Metrics are pushed to collector**
  - Metrics are then exposed by prometheus exporter
- Prometheus
  - Scrapes metrics from **Otel collector**

## Java instrumentation

We use open telemetry build pack for auto instrumentation
```xml

<buildpacks>
  <buildpack>docker.io/paketobuildpacks/java</buildpack>
  <buildpack>docker.io/paketobuildpacks/opentelemetry</buildpack>
</buildpacks>
<env>
    <BP_OPENTELEMETRY_ENABLED>true</BP_OPENTELEMETRY_ENABLED>
</env>
```
By default, it only [enables tracing](https://github.com/paketo-buildpacks/opentelemetry?tab=readme-ov-file#behavior). 
Also read through behavior
- you need explicitly to opt in for generating metrics
- you need to enable java agent
- set correct otel exporter, by default its localhost

```shell
# Runtime environment for greetings app
OTEL_JAVAAGENT_ENABLED=true
OTEL_METRICS_EXPORTER=otlp
OTEL_EXPORTER_OTLP_ENDPOINT=<collector-host>
```


## Caller service

We use spring boot Micrometer 

# TODO




## Setting up demo


- Build docker images
```
cd ./greetings-service
./mvnw spring-boot:build-image
```


## Caveats
