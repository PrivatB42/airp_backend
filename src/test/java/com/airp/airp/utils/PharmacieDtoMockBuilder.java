package com.airp.airp.utils;

import com.airp.airp.presentation.dto.PharmacieDto;

import java.time.LocalTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PharmacieDtoMockBuilder {
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

    private boolean ouvert;

    public PharmacieDtoMockBuilder() {

    }

    public PharmacieDto build() {
        PharmacieDto mock = mock(PharmacieDto.class);

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
        when(mock.isOuvert()).thenReturn(ouvert);
        return mock;
    }

    public PharmacieDtoMockBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PharmacieDtoMockBuilder setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public PharmacieDtoMockBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public PharmacieDtoMockBuilder setVille(String ville) {
        this.ville = ville;
        return this;
    }

    public PharmacieDtoMockBuilder setQuartier(String quartier) {
        this.quartier = quartier;
        return this;

    }

    public PharmacieDtoMockBuilder setHeureOuverture(LocalTime heureOuverture) {
        this.heureOuverture = heureOuverture;
        return this;
    }

    public PharmacieDtoMockBuilder setHeureFermeture(LocalTime heureFermeture) {
        this.heureFermeture = heureFermeture;
        return this;
    }

    public PharmacieDtoMockBuilder setNomGerant(String nomGerant) {
        this.nomGerant = nomGerant;
        return this;
    }

    public PharmacieDtoMockBuilder setStatut(String statut) {
        this.statut = statut;
        return this;
    }

    public PharmacieDtoMockBuilder setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public PharmacieDtoMockBuilder setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public PharmacieDtoMockBuilder setOuvert(boolean ouvert) {
        this.ouvert = ouvert;
        return this;
    }
}
