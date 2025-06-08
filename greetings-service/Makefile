IMAGE_NAME := greetings-app
IMAGE_TAG := 0.0.1

.PHONY: build-docker
.PHONY: run-docker-debug

build-docker:
	./mvnw spring-boot:build-image

run-docker-debug:
	docker run -p 8080:8080 \
		--env OTEL_JAVAAGENT_ENABLED=true \
		--env OTEL_METRIC_EXPORT_INTERVAL=15000 \
		--env OTEL_METRICS_EXPORTER=console \
		--env OTEL_SERVICE_NAME=$(IMAGE_NAME) \
		--env OTEL_TRACES_EXPORTER=console \
		--env OTEL_LOGS_EXPORTER=console \
		$(IMAGE_NAME):$(IMAGE_TAG)