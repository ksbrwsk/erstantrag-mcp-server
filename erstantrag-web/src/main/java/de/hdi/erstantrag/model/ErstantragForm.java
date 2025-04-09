package de.hdi.erstantrag.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ErstantragForm {
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
    @NotBlank
    private String gewuenschterRentenbeginn;
    private boolean bescheidRentenversicherung;
    private boolean sonstigerNachweis;
    private boolean besondereVereinbarungenVorhanden;
    private String besondereVereinbarungen;
    @NotBlank
    private String steueridentifikationsnummer;
    @NotBlank
    private String auszahlungDienstverhaeltnis;
    @NotBlank
    private String versicherungsstatus;
    @NotBlank
    private String rentenversicherungsnummer;
    @NotBlank
    private String krankenkasse;
    private boolean erfuellungElterneigenschaft;
    private String nameKinder;
    @NotBlank
    private String nameKontoinhaber;
    @NotBlank
    private String iban;
    private String anmerkungen;
}