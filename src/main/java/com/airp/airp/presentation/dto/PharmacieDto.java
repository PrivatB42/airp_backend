package com.airp.airp.presentation.dto;

import com.airp.airp.domain.Pharmacie;

import java.time.LocalTime;

import static java.time.LocalTime.now;

public class PharmacieDto {
    private String numero;
    private String nom;
    private String ville;
    private String quartier;
    private LocalTime heureOuverture;
    private LocalTime heureFermeture;
    private String nomGerant;
    private String contact;
    private String statut;
    private String latitude;
    private String longitude;

    private boolean ouvert;

    public PharmacieDto() {
    }

    public PharmacieDto(Pharmacie pharmacie) {
        this.numero = pharmacie.getNumero();
        this.nom = pharmacie.getNom();
        this.ville = pharmacie.getVille();
        this.quartier = pharmacie.getQuartier();
        this.heureOuverture = pharmacie.getHeureOuverture();
        this.heureFermeture = pharmacie.getHeureFermeture();
        this.nomGerant = pharmacie.getNomGerant();
        this.contact = pharmacie.getContact();
        this.statut = pharmacie.getStatut();
        this.latitude = pharmacie.getLatitude();
        this.longitude = pharmacie.getLongitude();

        this.ouvert = now().isBefore(this.heureFermeture) && now().isAfter(this.heureOuverture);
    }


    public String getNom() {
        return nom;
    }

    public String getVille() {
        return ville;
    }

    public String getQuartier() {
        return quartier;
    }

    public LocalTime getHeureOuverture() {
        return heureOuverture;
    }

    public LocalTime getHeureFermeture() {
        return heureFermeture;
    }

    public String getNomGerant() {
        return nomGerant;
    }

    public String getContact() {
        return contact;
    }

    public String getStatut() {
        return statut;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getNumero() {
        return numero;
    }

    public boolean isOuvert() {
        return ouvert;
    }

    @Override
    public String toString() {
        return "PharmacieDto{" +
                "numero='" + numero + '\'' +
                ", nom='" + nom + '\'' +
                ", ville='" + ville + '\'' +
                ", quartier='" + quartier + '\'' +
                ", heureOuverture='" + heureOuverture + '\'' +
                ", heureFermerture='" + heureFermeture + '\'' +
                ", nomGerant='" + nomGerant + '\'' +
                ", contact='" + contact + '\'' +
                ", statut='" + statut + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
