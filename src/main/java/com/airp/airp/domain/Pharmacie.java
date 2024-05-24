package com.airp.airp.domain;

import com.airp.airp.presentation.dto.PharmacieDto;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

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

	@Column(name = "heure_ouverture")
	private LocalTime heureOuverture;

	@Column(name = "heure_fermeture")
	private LocalTime heureFermeture;

	@Column(name = "nom_gerant")
	private String nomGerant;

	@Column
	private String contact;

	@Column(nullable = false)
	private String statut;

	@Column
	private String latitude;

	@Column
	private String longitude;

	public Pharmacie() {

	}

	public Pharmacie(String numero, String nom, String ville, String quartier, LocalTime heureOuverture,
	                 LocalTime heureFermeture, String nomGerant, String contact, String statut) {
		this.numero = numero;
		this.nom = nom;
		this.ville = ville;
		this.quartier = quartier;
		this.heureOuverture = heureOuverture;
		this.heureFermeture = heureFermeture;
		this.nomGerant = nomGerant;
		this.contact = contact;
		this.statut = statut;
	}

	public Pharmacie(String numero) {
		this.numero = numero;
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

	public void mettreAJour(Pharmacie pharmacie) {
		this.numero = pharmacie.getNumero();
		this.nom = pharmacie.getNom();
		this.ville = pharmacie.getVille();
		this.quartier = pharmacie.getQuartier();
		this.heureOuverture = pharmacie.getHeureOuverture();
		this.heureFermeture = pharmacie.getHeureFermeture();
		this.nomGerant = pharmacie.getNomGerant();
		this.contact = pharmacie.getContact();
		this.statut = pharmacie.getStatut();
	}

	public static PharmacieBuilder builder() {
		return new PharmacieBuilder();
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
