package com.unicore.classroom_service.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequestDetails(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponseDetails(response);
        return response;
    }

    private void logRequestDetails(HttpRequest request, byte[] body) {
        try {
            log.info("====== Outgoing Request ======");
            log.info("URI         : " + request.getURI());
            log.info("Method      : " + request.getMethod());
            log.info("Headers     : " + request.getHeaders());
            log.info("Body        : " + new String(body, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Error logging request details: " + e.getMessage());
        }
    }

    private void logResponseDetails(ClientHttpResponse response) throws IOException {
        try {
            String responseBody = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            log.info("====== Incoming Response ======");
            log.info("Status code : " + response.getStatusCode());
            log.info("Status text : " + response.getStatusText());
            log.info("Headers     : " + response.getHeaders());
            log.info("Body        : " + responseBody);
        } catch (Exception e) {
            log.error("Error logging response details: " + e.getMessage());
        }
    }
}
