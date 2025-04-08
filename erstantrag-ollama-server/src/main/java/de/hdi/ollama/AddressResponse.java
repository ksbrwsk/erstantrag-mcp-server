package de.hdi.ollama;

import lombok.Data;

@Data
public class AddressResponse {
    private String anschrift;
    private String plz;
    private String ort;
    private boolean valid;

    public AddressResponse() {
        // Default constructor
    }

    public AddressResponse(String anschrift, String plz, String ort, boolean isValid) {
        this.anschrift = anschrift;
        this.plz = plz;
        this.ort = ort;
        this.valid = isValid;
    }
}
