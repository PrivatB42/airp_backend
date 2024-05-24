package com.airp.airp.domain;

import com.airp.airp.presentation.dto.PharmacieDto;

import java.time.LocalTime;

public class PharmacieBuilder {
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

	public PharmacieBuilder() {
	}

	public Pharmacie build(PharmacieDto pharmacieDto) {
		Pharmacie pharmacie = new Pharmacie();
		merge(pharmacie, pharmacieDto);
		return pharmacie;
	}

	public Pharmacie merge(Pharmacie pharmacie, PharmacieDto pharmacieDto) {
		pharmacie.setNom(pharmacieDto.getNom());
		pharmacie.setNumero(pharmacieDto.getNumero());
		pharmacie.setVille(pharmacieDto.getVille());
		pharmacie.setQuartier(pharmacieDto.getQuartier());
		pharmacie.setContact(pharmacieDto.getContact());
		pharmacie.setLatitude(pharmacieDto.getLatitude());
		pharmacie.setLongitude(pharmacieDto.getLongitude());
		pharmacie.setHeureOuverture(pharmacieDto.getHeureOuverture());
		pharmacie.setHeureFermeture(pharmacieDto.getHeureFermeture());
		return pharmacie;
	}

	public PharmacieBuilder setNumero(String numero) {
		this.numero = numero;
		return this;
	}

	public PharmacieBuilder setNom(String nom) {
		this.nom = nom;
		return this;
	}

	public PharmacieBuilder setVille(String ville) {
		this.ville = ville;
		return this;
	}

	public PharmacieBuilder setQuartier(String quartier) {
		this.quartier = quartier;
		return this;
	}

	public PharmacieBuilder setHeureOuverture(LocalTime heureOuverture) {
		this.heureOuverture = heureOuverture;
		return this;
	}

	public PharmacieBuilder setHeureFermeture(LocalTime heureFermeture) {
		this.heureFermeture = heureFermeture;
		return this;
	}

	public PharmacieBuilder setNomGerant(String nomGerant) {
		this.nomGerant = nomGerant;
		return this;
	}

	public PharmacieBuilder setContact(String contact) {
		this.contact = contact;
		return this;
	}

	public PharmacieBuilder setLatitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public PharmacieBuilder setLongitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	public PharmacieBuilder setStatut(String statut) {
		this.statut = statut;
		return this;
	}

	public Pharmacie build() {
		return new Pharmacie(
				this.numero,
				this.nom,
				this.ville,
				this.quartier,
				this.heureOuverture,
				this.heureFermeture,
				this.nomGerant,
				this.contact,
				this.statut,
				this.latitude,
				this.longitude
		);
	}
}
