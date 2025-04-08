package de.hdi.ollama;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AddressService {

    private final ChatClient chatClient;

    public AddressService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .build();
    }

    public AddressResponse checkAddressValid(String anschrift, String plz, String ort) {
        log.info("Check if the german adress is valid -> {}, {}, {} ", anschrift, plz, ort);
        PromptTemplate pt = new PromptTemplate("""
                Please check, if the given ADDRESS {anschrift}, {plz}, {ort} is a valid german address.
                Return the data in RFC8259 compliant JSON format with the following fields:
                
                anschrift: type string, the anschrift you have checked
                plz: type string, the plz you have checked
                ort: type string, the ort you have checked
                valid: type boolean, the result of your check
                """);
        Prompt p = pt.create(Map.of("anschrift", anschrift, "plz", plz, "ort", ort));
        var content = this.chatClient.prompt(p)
                .call()
                .entity(AddressResponse.class);
        return content;
    }
}
