package com.airp.airp.exception.configuration;


import jakarta.persistence.criteria.FetchParent;
import jakarta.persistence.metamodel.Attribute;


/**
 * Exception lancée lorsqu'il est impossible de créer les fetchs
 */
public class FetchBuilderException extends RuntimeException {

	/**
	 * Crée une exception
	 *
	 * @param fetchParent le parent sur lequel est effectué le fetch.
	 * @param attribute   l'attribut qu'il est impossible de fecther à partir du fetchParent.
	 */
	public FetchBuilderException(FetchParent<?, ?> fetchParent, Attribute<?, ?> attribute) {
		super(String.format("Impossible d'effectuer le fetch de l'attribut %s à partir de %s",
				attribute.getName(),
				fetchParent));
	}
}
