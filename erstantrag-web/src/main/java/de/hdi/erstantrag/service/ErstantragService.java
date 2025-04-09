package de.hdi.erstantrag.service;

import de.hdi.erstantrag.ai.mcp.AddressResponse;
import de.hdi.erstantrag.ai.mcp.DataValidationResponse;
import de.hdi.erstantrag.model.Erstantrag;
import de.hdi.erstantrag.model.ErstantragForm;
import de.hdi.erstantrag.model.Partner;
import de.hdi.erstantrag.repository.ErstantragRepository;
import de.hdi.erstantrag.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ErstantragService {

    private final PartnerRepository partnerRepository;
    private final ErstantragMailAcknowledgement mailAcknowledgement;
    private final ErstantragRepository erstantragRepository;
    private final ErstantragIbanValidator erstantragIbanValidator;
    private final ErstantragAddressValidator erstantragAddressValidator;

    public ErstantragService(PartnerRepository partnerRepository, ErstantragMailAcknowledgement mailAcknowledgement, ErstantragRepository erstantragRepository, ErstantragIbanValidator erstantragIbanValidator, ErstantragAddressValidator erstantragAddressValidator) {
        this.partnerRepository = partnerRepository;
        this.mailAcknowledgement = mailAcknowledgement;
        this.erstantragRepository = erstantragRepository;
        this.erstantragIbanValidator = erstantragIbanValidator;
        this.erstantragAddressValidator = erstantragAddressValidator;
    }

    public DataValidationResponse handleErstantrag(ErstantragForm form) throws Exception {
        DataValidationResponse dataValidationResponse = new DataValidationResponse();
        String nachname = form.getNachname();
        String vorname = form.getVorname();
        String geburtsdatum = form.getGeburtsdatum();
        log.info("Suche Partner mit Nachname: {}, Vorname: {}, Geburtsdatum: {}", nachname, vorname, geburtsdatum);
        Optional<Partner> partner = this.partnerRepository.findByNachnameAndVornameAndGeburtsdatum(nachname, vorname, geburtsdatum);
        boolean valid = true;
        if (partner.isPresent()) {
            String antragnummer = this.createAntragnummer();
            form.setAntragnummer(antragnummer);
            log.info("Partner gefunden: {}", partner.get());
            var erstantrag = new Erstantrag();
            BeanUtils.copyProperties(form, erstantrag);

            var ibanResponse = this.erstantragIbanValidator.checkIban(erstantrag.getIban());
            log.info("Iban geprüft, Ergebnis -> {}", ibanResponse);
            dataValidationResponse.setIbanValid(ibanResponse.isValid());

            var addressResponse = this.erstantragAddressValidator.checkAddressValid(erstantrag.getStrasse(),
                    erstantrag.getPlz(),
                    erstantrag.getOrt());
            log.info("Adresse geprüft, Ergebnis -> {}", addressResponse);
            dataValidationResponse.setAddressValid(addressResponse.isValid());

            if(dataValidationResponse.isDataValid()) {
                this.erstantragRepository.save(erstantrag);
                this.mailAcknowledgement.sendAckMail(form);
                this.mailAcknowledgement.sendSapMail(partner.get());
            }
        }
        return dataValidationResponse;
    }

    private String createAntragnummer() {
        SecureRandom random = new SecureRandom();
        random.setSeed(100);
        int nextInt = random.nextInt();
        String antragnummer = "AN-00-" + StringUtils.leftPad(Integer.toString(nextInt), 4, "0");
        log.info("neuer Erstantrag Nummer: {}", antragnummer);
        return antragnummer;
    }
}
