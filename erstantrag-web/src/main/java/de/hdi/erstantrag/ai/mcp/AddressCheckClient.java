package de.hdi.erstantrag.ai.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Component;

import java.util.Map;

public class AddressCheckClient {

    private final McpClientTransport transport;

    public AddressCheckClient(McpClientTransport transport) {
        this.transport = transport;
    }

    public AddressResponse checkAddress(String anschrift, String plz, String ort) throws Exception {
        var client = McpClient.sync(this.transport).build();
        client.initialize();
        client.ping();
        McpSchema.CallToolResult addressMcpResponse = client.callTool(new McpSchema.CallToolRequest("checkAddressValid",
                Map.of("anschrift", anschrift, "plz", plz, "ort", ort)));
        McpSchema.TextContent content = (McpSchema.TextContent) addressMcpResponse.content().getFirst();
        String text = content.text();
        ObjectMapper mapper = new ObjectMapper();
        AddressResponse addressResponse = mapper.readValue(text, AddressResponse.class);
        client.closeGracefully();
        return addressResponse;
    }
}

