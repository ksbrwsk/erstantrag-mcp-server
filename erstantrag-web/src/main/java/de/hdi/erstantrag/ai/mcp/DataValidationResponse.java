package de.hdi.erstantrag.ai.mcp;

public class DataValidationResponse {

    private boolean ibanValid;
    private boolean addressValid;

    public boolean isIbanValid() {
        return ibanValid;
    }

    public void setIbanValid(boolean ibanValid) {
        this.ibanValid = ibanValid;
    }

    public boolean isAddressValid() {
        return addressValid;
    }

    public void setAddressValid(boolean addressValid) {
        this.addressValid = addressValid;
    }

    public boolean isDataValid() {
        return ibanValid && addressValid;
    }

    @Override
    public String toString() {
        return "DataValidationResponse{" +
                "ibanValid=" + ibanValid +
                ", addressValid=" + addressValid +
                '}';
    }
}
