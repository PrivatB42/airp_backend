package com.airp.airp.exception;

import com.airp.airp.exception.configuration.AbstractApplicationException;
import com.airp.airp.exception.configuration.CodeErreurTechnique;

import java.util.Collection;

import static com.airp.airp.exception.configuration.CodeErreurTechnique.ACCES_REFUSE;
import static com.airp.airp.exception.configuration.CodeErreurTechnique.MOT_DE_PASSE_INCORRECT;
import static com.airp.airp.exception.configuration.CodeErreurTechnique.UTILISATEUR_INCONNU;
import static com.airp.airp.exception.configuration.CodeErreurTechnique.UTILISATEUR_INNACTIF;
import static java.util.Collections.singleton;

public class UtilisateurException extends AbstractApplicationException {

	private UtilisateurException(CodeErreurTechnique codeErreur, String message) {
		super(codeErreur.getCode(), message);
	}

	private UtilisateurException(CodeErreurTechnique codeErreur, String message, Collection<String> parametres) {
		super(codeErreur.getCode(), message, parametres);
	}

	/**
	 * Exception levée si aucun utilisateur n'est trouvé.
	 */
	public static UtilisateurException motDePasseIncorrect() {
		return new UtilisateurException(MOT_DE_PASSE_INCORRECT, "Le mot de passe saisi est incorrect");
	}

	/**
	 * Exception levée si aucun utilisateur n'est trouvé.
	 */
	public static UtilisateurException utilisateurInactif() {
		return new UtilisateurException(UTILISATEUR_INNACTIF, "Cet utilisateur est inactif. Contactez votre administrateur");
	}

	/**
	 * Exception levée si aucun utilisateur n'est trouvé.
	 */
	public static UtilisateurException utiilisateurInconnu(String nomUtilisateur) {
		return new UtilisateurException(UTILISATEUR_INCONNU, "Aucun utilisateur trouvé avec le login %s", singleton(nomUtilisateur));
	}

	/**
	 * Exception levée lorsque le token a expiré.
	 */
	public static UtilisateurException sessionExpiree() {
		return new UtilisateurException(ACCES_REFUSE, "Desolé votre token n'est plus valide");
	}
}
