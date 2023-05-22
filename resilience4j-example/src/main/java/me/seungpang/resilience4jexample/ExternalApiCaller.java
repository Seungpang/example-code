package me.seungpang.resilience4jexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalApiCaller {

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiCaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callApi() {
        return restTemplate.getForObject("/api/external", String.class);
    }

    public String callApiWithDelay() {
        String result = restTemplate.getForObject("/api/external", String.class);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return result;
    }
}
