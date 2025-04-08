package de.hdi.ollama;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class OllamaRestController {

    private final IbanService ibanService;
    private final AddressService addressService;

    public OllamaRestController(IbanService ibanService, AddressService addressService) {
        this.ibanService = ibanService;
        this.addressService = addressService;
    }

    @GetMapping("/api/iban/check")
    ResponseEntity<IbanResponse> handleCheckIban(@NotNull @RequestParam("iban") String iban) {
        IbanResponse ibanResponse = ibanService.checkIbanValid(iban);
        return ResponseEntity.ok(ibanResponse);
    }

    @GetMapping("/api/address/check")
    ResponseEntity<AddressResponse> handleCheckAddress(@NotNull @RequestParam("anschrift") String anschrift,
                                                       @NotNull @RequestParam("plz") String plz,
                                                       @NotNull @RequestParam("ort") String ort) {
        AddressResponse addressResponse = addressService.checkAddressValid(anschrift, plz, ort);
        return ResponseEntity.ok(addressResponse);
    }

}
