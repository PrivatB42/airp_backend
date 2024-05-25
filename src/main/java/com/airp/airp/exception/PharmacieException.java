package com.airp.airp.exception;

import com.airp.airp.exception.configuration.AbstractApplicationException;
import com.airp.airp.exception.configuration.CodeErreurTechnique;

import java.util.Collection;

import static com.airp.airp.exception.configuration.CodeErreurTechnique.PHARMACIE_EXISTANTE;
import static com.airp.airp.exception.configuration.CodeErreurTechnique.PHARMACIE_INCONNUE;
import static java.util.Collections.singleton;

public class PharmacieException extends AbstractApplicationException {

	private PharmacieException(CodeErreurTechnique codeErreur, String message) {
		super(codeErreur.getCode(), message);
	}

	/**
	 * Construit une pharmacie exception
	 */
	private PharmacieException(CodeErreurTechnique codeErreur, String message, Collection<String> params) {
		super(codeErreur.getCode(), message, params);
	}

	/**
	 * Exception pour pharmacie deja existante.
	 */
	public static PharmacieException pharmacieExistanteException(String code) {
		return new PharmacieException(PHARMACIE_EXISTANTE, "Une pharmacie existe déjà avec le code %s", singleton(code));
	}

	/**
	 * Exception pour pharmacie non trouvée.
	 *
	 * @param code le code de la pharmacie non trouvée
	 */
	public static PharmacieException pharmacieIntrouvableException(String code) {
		return new PharmacieException(PHARMACIE_INCONNUE, "Pharmacie introuvable avec le code %s", singleton(code));
	}
}
