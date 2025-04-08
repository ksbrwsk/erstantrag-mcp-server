package de.hdi.erstantragmcpserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@Slf4j
public class AddressService {

    private final RestClient restClient;

    public AddressService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://localhost:8085")
                .build();
    }

    @Tool(description = "Check if the address is a valid german address")
    public AddressResponse checkAddressValid(String anschrift, String plz, String ort) {
        log.info("Check if the address is correct -> {}, {}, {}", anschrift, plz, ort);
        AddressResponse addressResponse = this.restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/address/check")
                        .queryParam("anschrift", anschrift)
                        .queryParam("plz", plz)
                        .queryParam("ort", ort)
                        .build())
                .retrieve()
                .body(AddressResponse.class);
        return addressResponse;
    }
}
