package de.hdi.erstantrag.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record Partner(
        @Id Long id,
        @NotBlank String partnernummer,
        @NotBlank
        String vorname,
        @NotBlank
        String nachname,
        @NotBlank
        String geburtsdatum,
        @NotBlank
        String geburtsort,
        @NotBlank
        String staatsangehoerigkeit,
        @NotBlank
        String strasse,
        @NotBlank
        String plz,
        @NotBlank
        String ort,
        @NotBlank
        String telefon,
        @NotBlank
        String email,
        @NotBlank
        String steueridentifikationsnummer,
        @NotBlank
        String versicherungsstatus,
        @NotBlank
        String rentenversicherungsnummer,
        @NotBlank
        String krankenkasse,
        @NotBlank @Column("name_kontoinhaber")
        String nameKontoinhaber,
        @NotBlank
        String iban
) {
}
