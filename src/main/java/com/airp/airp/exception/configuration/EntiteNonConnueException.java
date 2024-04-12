package com.airp.airp.exception.configuration;

/**
 * Exception lancée lorsqu'un objet Entity du modèle n'a pas été trouvé.<br/>
 *
 * @see AbstractApplicationException
 */
public class EntiteNonConnueException extends AbstractApplicationException {

	public EntiteNonConnueException(String cleTraduction, Object... params) {
		super(CodeErreurTechnique.ENTITE_NON_CONNUE, cleTraduction, params);
	}
}
