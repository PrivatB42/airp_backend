package com.airp.airp.domain;

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

	private String statut = "ACTIF";

	@Column
	private String latitude;

	@Column
	private String longitude;

	public Pharmacie() {
	}

	protected Pharmacie(String numero, String nom, String ville, String quartier, LocalTime heureOuverture,
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

	protected Pharmacie(String numero, String nom, String ville, String quartier, LocalTime heureOuverture,
						LocalTime heureFermeture, String nomGerant, String contact, String statut, String latitude, String longitude) {
		this(numero, nom, ville, quartier, heureOuverture, heureFermeture, nomGerant, contact, statut);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Crée une pharmacie en settant le numéro
	 *
	 * @param numero le numero de la pharmacie
	 */
	public Pharmacie(String numero) {
		this.numero = numero;
	}

	/**
	 * Met à jour les données de la pharmacie
	 *
	 * @param pharmacie la pharmacie contenant les données à mettre à jour
	 */
	public void mettreAJour(Pharmacie pharmacie) {
		this.setNumero(pharmacie.getNumero());
		this.setNom(pharmacie.getNom());
		this.setVille(pharmacie.getVille());
		this.setQuartier(pharmacie.getQuartier());
		this.setNomGerant(pharmacie.getNomGerant());
		this.setContact(pharmacie.getContact());
		this.setLongitude(pharmacie.getLongitude());
		this.setLatitude(pharmacie.getLatitude());
		this.setHeureOuverture(pharmacie.getHeureOuverture());
		this.setHeureFermeture(pharmacie.getHeureFermeture());
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

	protected void setNumero(String numero) {
		this.numero = numero;
	}

	protected void setNom(String nom) {
		this.nom = nom;
	}

	protected void setVille(String ville) {
		this.ville = ville;
	}

	protected void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	protected void setHeureOuverture(LocalTime heureOuverture) {
		this.heureOuverture = heureOuverture;
	}

	protected void setHeureFermeture(LocalTime heureFermeture) {
		this.heureFermeture = heureFermeture;
	}

	protected void setNomGerant(String nomGerant) {
		this.nomGerant = nomGerant;
	}

	protected void setContact(String contact) {
		this.contact = contact;
	}

	protected void setStatut(String statut) {
		this.statut = statut;
	}

	protected void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	protected void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
