package com.airp.airp.domain;

import com.airp.airp.presentation.dto.PharmacieDto;

public class PharmacieBuilder {

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
}
