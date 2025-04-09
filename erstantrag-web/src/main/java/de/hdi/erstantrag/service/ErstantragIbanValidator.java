package de.hdi.erstantrag.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hdi.erstantrag.ai.mcp.IbanCheckClient;
import de.hdi.erstantrag.ai.mcp.IbanResponse;
import io.modelcontextprotocol.client.transport.WebFluxSseClientTransport;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ErstantragIbanValidator {

    @SneakyThrows
    public IbanResponse checkIban(String iban) {
        var transport = new WebFluxSseClientTransport(WebClient.builder().baseUrl("http://localhost:8081"));
        IbanResponse ibanResponse = new IbanCheckClient(transport).checkIban(iban);
        return ibanResponse;
    }

    private IbanResponse transform(String content) {
        ObjectMapper mapper = new ObjectMapper();
        IbanResponse ibanResponse;
        try {
            ibanResponse = mapper.readValue(content, IbanResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse IBAN response", e);
        }
        return ibanResponse;
    }

}
