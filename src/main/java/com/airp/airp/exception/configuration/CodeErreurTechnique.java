package com.airp.airp.exception.configuration;

/**
 * Codes erreurs techniques qui ne sont liés à aucun métier.<br>
 *
 * @see AbstractCodeErreur
 */
public enum CodeErreurTechnique implements AbstractCodeErreur {
	AUCUN_RESULTAT(9001L),
	ACCES_REFUSE(9002L),
	MOT_DE_PASSE_INCORRECT(9003L),
	UTILISATEUR_INCONNU(9004L),
	UTILISATEUR_INNACTIF(9005L),
	ERREUR_VERSION(9006L),
	ERREUR_INCONNUE(9999L);

	private final Long code;

	private CodeErreurTechnique(Long code) {
		if (code < 9000 || code >= 10000) {
			throw new IllegalStateException("Les codes d'erreurs techniques doivent être compris entre 9000 et 9999");
		}
		this.code = code;
	}

	@Override
	public Long getCode() {
		return code;
	}

	@Override
	public String getType() {
		return this.name();
	}
}
