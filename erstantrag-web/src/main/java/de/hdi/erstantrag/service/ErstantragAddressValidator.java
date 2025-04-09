package de.hdi.erstantrag.service;

import de.hdi.erstantrag.ai.mcp.AddressCheckClient;
import de.hdi.erstantrag.ai.mcp.AddressResponse;
import io.modelcontextprotocol.client.transport.WebFluxSseClientTransport;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ErstantragAddressValidator {

    public AddressResponse checkAddressValid(String anschrift, String plz, String ort) throws Exception {
        var transport = new WebFluxSseClientTransport(WebClient.builder().baseUrl("http://localhost:8082"));
        AddressResponse addressResponse = new AddressCheckClient(transport).checkAddress(anschrift, plz, ort);
        return addressResponse;
    }
}
