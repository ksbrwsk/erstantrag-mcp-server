package de.hdi.erstantrag.repository;

import de.hdi.erstantrag.model.Partner;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface PartnerRepository extends ListCrudRepository<Partner, Long> {
    Optional<Partner> findByNachnameAndVornameAndGeburtsdatum(String nachname, String vorname, String geburtsdatum);
}
