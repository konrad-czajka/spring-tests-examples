package konradczajka.springtestsexamples.externalapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExtApiClient {
    private RestTemplate restTemplate;
    private String apiUrl;

    public ExtApiClient(RestTemplate restTemplate, @Value("${ext.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public String fetchData(int id) {
        String externalData = restTemplate.getForObject(apiUrl + "/data/" + id, String.class);
        return "result: " + externalData;
    }
}
