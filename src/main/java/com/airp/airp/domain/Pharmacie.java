package com.airp.airp.domain;

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
}
