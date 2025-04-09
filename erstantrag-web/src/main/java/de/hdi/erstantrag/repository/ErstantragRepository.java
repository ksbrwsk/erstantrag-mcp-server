package de.hdi.erstantrag.repository;

import de.hdi.erstantrag.model.Erstantrag;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface ErstantragRepository extends ListCrudRepository<Erstantrag, Long> {
    Optional<Erstantrag> findByAntragnummer(String antragnummer);
}
