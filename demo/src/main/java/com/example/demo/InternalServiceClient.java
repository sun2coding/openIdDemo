package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class InternalServiceClient {

    @Value("${app.internal-service.base-url}")
    private String baseUrl;

    @Value("${app.internal-service.token-endpoint}")
    private String tokenEndpoint;

    public String getInternalToken(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", email);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + tokenEndpoint, 
            request, 
            String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        throw new RuntimeException("Failed to get internal token: " + response.getStatusCode());
    }
}