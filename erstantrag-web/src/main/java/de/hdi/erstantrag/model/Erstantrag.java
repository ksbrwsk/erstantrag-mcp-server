package de.hdi.erstantrag.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class Erstantrag {
        @Id Long id;
        private String antragnummer;
        @NotBlank
        private String vorname;
        @NotBlank
        private String nachname;
        @NotBlank
        private String geburtsdatum;
        @NotBlank
        private String geburtsort;
        @NotBlank
        private String staatsangehoerigkeit;
        @NotBlank
        private String strasse;
        @NotBlank
        private String plz;
        @NotBlank
        private String ort;
        @NotBlank
        private String telefon;
        @NotBlank
        private String email;
        @NotBlank @Column("gewuenschter_rentenbeginn")
        private String gewuenschterRentenbeginn;
        @Column("bescheid_rentenversicherung")
        private boolean bescheidRentenversicherung;
        @Column("sonstiger_nachweis")
        private boolean sonstigerNachweis;
        @Column("besondere_vereinbarungen_vorhanden")
        private boolean besondereVereinbarungenVorhanden;
        @Column("besondere_vereinbarungen")
        private String besondereVereinbarungen;
        @NotBlank
        private String steueridentifikationsnummer;
        @NotBlank @Column("auszahlung_dienstverhaeltnis")
        private String auszahlungDienstverhaeltnis;
        @NotBlank
        private String versicherungsstatus;
        @NotBlank
        private String rentenversicherungsnummer;
        @NotBlank
        private String krankenkasse;
        @Column("erfuellung_elterneigenschaft")
        private boolean erfuellungElterneigenschaft;
        @Column("name_kinder")
        private String nameKinder;
        @NotBlank @Column("name_kontoinhaber")
        private String nameKontoinhaber;
        @NotBlank
        private String iban;
        private String anmerkungen;
}
