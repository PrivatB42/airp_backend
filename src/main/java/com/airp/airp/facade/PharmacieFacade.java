package com.airp.airp.facade;

import com.airp.airp.domain.Pharmacie;
import com.airp.airp.domain.PharmacieBuilder;
import com.airp.airp.presentation.dto.PharmacieDto;
import com.airp.airp.repository.JpaPharmacieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.airp.airp.exception.PharmacieException.pharmacieExistanteException;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Comparator.comparing;

@Service
public class PharmacieFacade {

	private final JpaPharmacieRepository pharmacieRepository;

	public PharmacieFacade(JpaPharmacieRepository pharmacieRepository) {
		this.pharmacieRepository = pharmacieRepository;
	}

	/**
	 * Liste toutes les pharmacies.
	 *
	 * @return la liste {@link PharmacieDto} des pharmacies.
	 */
	@Transactional(readOnly = true)
	public List<PharmacieDto> lister() {
		return pharmacieRepository.findAll().stream()
				.map(PharmacieDto::new)
				.sorted(comparing(PharmacieDto::getNom, CASE_INSENSITIVE_ORDER)
						.thenComparing(PharmacieDto::getNumero, CASE_INSENSITIVE_ORDER))
				.toList();
	}

	/**
	 * Enregistre une pharmacie.
	 *
	 * @param pharmacieDto la pharmacie.
	 */
	@Transactional
	public void enregistrer(PharmacieDto pharmacieDto) {
		Pharmacie pharmacieExistant = pharmacieRepository.findOneByNumero(pharmacieDto.getNumero())
				.orElse(null);
		if (pharmacieExistant != null) {
			throw pharmacieExistanteException(pharmacieExistant.getNumero());
		}
		Pharmacie pharmacie = new PharmacieBuilder().build(pharmacieDto);
		this.pharmacieRepository.save(pharmacie);
	}
}
