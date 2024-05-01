package com.airp.airp.exception;

import com.airp.airp.exception.configuration.AbstractApplicationException;
import com.airp.airp.exception.configuration.TypeErreur;

import static com.airp.airp.exception.configuration.CodeErreurTechnique.ERREUR_VERSION;

public class VersionException extends AbstractApplicationException {

	public VersionException(Long codeErreur, String typeErreur, String message) {
		super(codeErreur, typeErreur, message);
	}

	/**
	 * Exception levée lorsqu'une erreur quelconque a été rencontrée pendant la récupération des version.
	 */
	public static VersionException erreurDeRecuperationDeVersion() {
		return new VersionException(ERREUR_VERSION.getCode(), TypeErreur.ERROR.name(),
				"Une erreur s'est produite lors de la récupération de la version de l'application");
	}
}
