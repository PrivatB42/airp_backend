package com.airp.airp.domain;

import com.airp.airp.presentation.dto.PharmacieDto;
import jakarta.persistence.*;

import java.time.LocalTime;

import static jakarta.persistence.AccessType.FIELD;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Access(FIELD)
@Table(name = Pharmacie.TABLE_NAME)
public class Pharmacie extends AbstractEntity {

    public static final String TABLE_NAME = "pharmacie";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String ville;

    @Column(nullable = false)
    private String quartier;

    @Column(nullable = false, name = "heure_ouverture")
    private LocalTime heureOuverture;

    @Column(nullable = false, name = "heure_fermeture")
    private LocalTime heureFermeture;

    @Column(nullable = false, name = "nom_gerant")
    private String nomGerant;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    public Pharmacie() {

    }

    public Pharmacie(PharmacieDto pharmacieDto) {
        this.numero = pharmacieDto.getNumero();
        this.nom = pharmacieDto.getNom();
        this.ville = pharmacieDto.getVille();
        this.quartier = pharmacieDto.getQuartier();
        this.heureOuverture = pharmacieDto.getHeureOuverture();
        this.heureFermeture = pharmacieDto.getHeureFermeture();
        this.nomGerant = pharmacieDto.getNomGerant();
        this.contact = pharmacieDto.getContact();
        this.statut = pharmacieDto.getStatut();
        this.latitude = pharmacieDto.getLatitude();
        this.longitude = pharmacieDto.getLongitude();
    }

    public Long getId() {
        return id;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getStatut() {
        return statut;
    }

    public String getContact() {
        return contact;
    }

    public String getNomGerant() {
        return nomGerant;
    }

    public LocalTime getHeureOuverture() {
        return heureOuverture;
    }

    public LocalTime getHeureFermeture() {
        return heureFermeture;
    }

    public String getQuartier() {
        return quartier;
    }

    public String getVille() {
        return ville;
    }

    public String getNom() {
        return nom;
    }

    public String getNumero() {
        return numero;
    }
}
