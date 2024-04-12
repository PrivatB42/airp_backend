package com.airp.airp.exception;

import com.airp.airp.exception.configuration.AbstractApplicationException;
import com.airp.airp.exception.configuration.HasCodeErreur;

import static com.airp.airp.exception.CodeErreur.*;

public class UtilisateurException extends AbstractApplicationException {

	/**
	 * Construit une exception
	 */
	private UtilisateurException(HasCodeErreur codeErreur, String message, Object... params) {
		super(codeErreur, message, params);
	}

	/**
	 * Exception pour utlilisateur deja existant
	 */
	public static UtilisateurException utilisateurExistantException() {
		return new UtilisateurException(CodeErreur.COMPTE_EXISTANT, "Un utilisateur existe déjà avec cet username");
	}

	/**
	 * Exception pour login ou mot de passe incorrect
	 */
	public static UtilisateurException nomUtilisateurOuMotPasseIncorrectException() {
		return new UtilisateurException(NOM_UTILISATEUR_MOT_PASSE_INCORRECT, "Username ou mot de passe incorrect.");
	}

	public static UtilisateurException utilisateurInExistantException() {
		return new UtilisateurException(CodeErreur.COMPTE_INEXISTANT, "Désolé, un compte avec ce username existe deja");
	}

	/**
	 * Exception pour utilisateur inactif
	 */
	public static UtilisateurException utilisateurInactifException() {
		return new UtilisateurException(COMPTE_INACTIF, "Votre compte est inactif. Veuillez contacter le service client");
	}

	/**
	 * Exception pour access non autorisé
	 */
	public static UtilisateurException accesNonAutoriseException() {
		return new UtilisateurException(ACCESS_REFUSE, "Vous ne disposez pas des droits d'accès à cet espace.");
	}

	/**
	 * Exception token invalide
	 */
	public static UtilisateurException tokenInvalideException() {
		return new UtilisateurException(ACCESS_REFUSE, "Desolé votre token n'est plus valide.");
	}
}
