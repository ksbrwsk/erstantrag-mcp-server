package de.hdi.erstantrag.ai;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.JsonMetadataGenerator;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DocumentReader {

    private final VectorStore vectorStore;
    private final TokenTextSplitter tokenTextSplitter;

    public DocumentReader(VectorStore vectorStore, TokenTextSplitter tokenTextSplitter) {
        this.vectorStore = vectorStore;
        this.tokenTextSplitter = tokenTextSplitter;
    }

    /**
     * This method is responsible for loading the data into the application.
     * It is currently commented out, but if uncommented, it would be run after dependency
     * injection is done to perform any initialization.
     */
    @PostConstruct
    void loadData() {
        log.info("Loading data");
        this.loadFaq();
    }

    /**
     * This method is responsible for loading the FAQ data into the application.
     * It reads the data from the 'faq.json' file and adds it to the VectorStore.
     */
    void loadFaq() {
        ClassPathResource resource = new ClassPathResource("faq.json");
        log.info("Loading faq.json");
        var documents = new JsonReader(resource, new FilenameJsonMetadataGenerator("FAQs"),
                "question", "answer").get();
        for (var document : documents) {
            var split = tokenTextSplitter.apply(List.of(document));
            this.vectorStore.add(split);
        }
    }

    /**
     * This method is responsible for loading the Nutzungsbedingungen data into the application.
     * It reads the data from the 'faq.json' file and adds it to the VectorStore.
     */
    static class FilenameJsonMetadataGenerator implements JsonMetadataGenerator {
        private final String filename;

        FilenameJsonMetadataGenerator(String filename) {
            this.filename = filename;
        }

        @Override
        public Map<String, Object> generate(Map<String, Object> jsonMap) {
            return Map.of("source", filename);
        }
    }
}