package com.airp.airp.repository;

import com.airp.airp.domain.Pharmacie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPharmacieRepository extends JpaRepository<Pharmacie, Long> {

	/**
	 * Recherche une pharmacie par son numéro
	 *
	 * @param numero le numéro de la pharmacie
	 * @return un optional contenant pharmacie si elle existe
	 */
	Optional<Pharmacie> findOneByNumero(String numero);
}
