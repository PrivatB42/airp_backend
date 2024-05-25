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

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public void setHeureOuverture(LocalTime heureOuverture) {
		this.heureOuverture = heureOuverture;
	}

	public void setHeureFermeture(LocalTime heureFermeture) {
		this.heureFermeture = heureFermeture;
	}

	public void setNomGerant(String nomGerant) {
		this.nomGerant = nomGerant;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setOuvert(boolean ouvert) {
		this.ouvert = ouvert;
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
}
