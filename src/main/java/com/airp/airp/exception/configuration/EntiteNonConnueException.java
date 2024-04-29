package com.airp.airp.exception.configuration;

import java.util.Collection;

import static com.airp.airp.exception.configuration.CodeErreurTechnique.AUCUN_RESULTAT;

/**
 * Exception lancée lorsqu'un objet Entity du modèle n'a pas été trouvé.<br/>
 *
 * @see AbstractApplicationException
 */
public class EntiteNonConnueException extends AbstractApplicationException {

	public EntiteNonConnueException(String message, Collection<String> params) {
		super(AUCUN_RESULTAT.getCode(), message, params);
	}
}
