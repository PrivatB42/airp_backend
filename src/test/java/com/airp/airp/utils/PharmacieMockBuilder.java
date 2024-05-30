package com.airp.airp.utils;

import com.airp.airp.domain.Pharmacie;

import java.time.LocalTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PharmacieMockBuilder {
    private Long id;
    private String numero;
    private String nom;
    private String ville;
    private String quartier;
    private LocalTime heureOuverture;
    private LocalTime heureFermeture;
    private String nomGerant;
    private String statut;
    private String latitude;
    private String longitude;

    public PharmacieMockBuilder() {

    }

    public Pharmacie build() {
        Pharmacie mock = mock(Pharmacie.class);

        when(mock.getId()).thenReturn(id);
        when(mock.getNumero()).thenReturn(numero);
        when(mock.getNom()).thenReturn(nom);
        when(mock.getVille()).thenReturn(ville);
        when(mock.getQuartier()).thenReturn(quartier);
        when(mock.getHeureOuverture()).thenReturn(heureOuverture);
        when(mock.getHeureFermeture()).thenReturn(heureFermeture);
        when(mock.getNomGerant()).thenReturn(nomGerant);
        when(mock.getStatut()).thenReturn(statut);
        when(mock.getLatitude()).thenReturn(latitude);
        when(mock.getLongitude()).thenReturn(longitude);

        return mock;
    }

    public PharmacieMockBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PharmacieMockBuilder setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public PharmacieMockBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public PharmacieMockBuilder setVille(String ville) {
        this.ville = ville;
        return this;
    }

    public PharmacieMockBuilder setQuartier(String quartier) {
        this.quartier = quartier;
        return this;

    }

    public PharmacieMockBuilder setHeureOuverture(LocalTime heureOuverture) {
        this.heureOuverture = heureOuverture;
        return this;
    }

    public PharmacieMockBuilder setHeureFermeture(LocalTime heureFermeture) {
        this.heureFermeture = heureFermeture;
        return this;
    }

    public PharmacieMockBuilder setNomGerant(String nomGerant) {
        this.nomGerant = nomGerant;
        return this;
    }

    public PharmacieMockBuilder setStatut(String statut) {
        this.statut = statut;
        return this;
    }

    public PharmacieMockBuilder setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public PharmacieMockBuilder setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }
}
