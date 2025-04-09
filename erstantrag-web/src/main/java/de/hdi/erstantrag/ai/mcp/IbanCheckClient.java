package de.hdi.erstantrag.ai.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.Map;

public class IbanCheckClient {

    private final McpClientTransport transport;

    public IbanCheckClient(McpClientTransport transport) {
        this.transport = transport;
    }

    public IbanResponse checkIban(String iban) throws Exception {
        var client = McpClient.sync(this.transport).build();
        client.initialize();
        client.ping();
        McpSchema.CallToolResult ibanMcpResponse = client.callTool(new McpSchema.CallToolRequest("checkIbanValid",
                Map.of("iban", iban)));
        McpSchema.TextContent content = (McpSchema.TextContent) ibanMcpResponse.content().getFirst();
        String text = content.text();
        ObjectMapper mapper = new ObjectMapper();
        IbanResponse ibanResponse = mapper.readValue(text, IbanResponse.class);
        client.closeGracefully();

        return ibanResponse;
    }
}

