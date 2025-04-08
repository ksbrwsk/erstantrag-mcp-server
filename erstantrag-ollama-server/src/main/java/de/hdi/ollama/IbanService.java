package de.hdi.ollama;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class IbanService {

    private final ChatClient chatClient;

    public IbanService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .build();
    }

    public IbanResponse checkIbanValid(String iban) {
        log.info("Check if the IBAN is valid - {}", iban);
        PromptTemplate pt = new PromptTemplate("""
                Please check, if the given IBAN {iban} is valid.
                Return the data in RFC8259 compliant JSON format with the following fields:
                
                iban: type string, the iban number you have checked
                valid: type boolean, the result of your check
                """);
        Prompt p = pt.create(Map.of("iban", iban));
        var content = this.chatClient.prompt(p)
                .call()
                .entity(IbanResponse.class);
        return content;
    }
}
