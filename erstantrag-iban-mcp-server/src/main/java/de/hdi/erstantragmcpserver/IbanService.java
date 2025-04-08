package de.hdi.erstantragmcpserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class IbanService {

    private final RestClient restClient;

    public IbanService(RestClient.Builder clientBuilder) {
        this.restClient = clientBuilder
                .baseUrl("http://localhost:8085")
                .build();
    }

    @Tool(description = "Check if the IBAN is valid")
    public IbanResponse checkIbanValid(String iban) {
        log.info("Check if the IBAN is valid - {}", iban);
        IbanResponse ibanResponse = this.restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/iban/check")
                        .queryParam("iban", iban)
                        .build())
                .retrieve()
                .body(IbanResponse.class);
        return ibanResponse;
    }
}
