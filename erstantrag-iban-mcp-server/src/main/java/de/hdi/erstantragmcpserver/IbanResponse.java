package de.hdi.erstantragmcpserver;

import lombok.Data;

@Data
public class IbanResponse {
    private String iban;
    private boolean valid;

    public IbanResponse() {
        // Default constructor
    }

    public IbanResponse(String iban, boolean isValid) {
        this.iban = iban;
        this.valid = isValid;
    }
}
