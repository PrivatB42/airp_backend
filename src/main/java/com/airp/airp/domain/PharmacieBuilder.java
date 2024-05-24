package com.airp.airp.domain;

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
				this.statut
		);
	}
}
