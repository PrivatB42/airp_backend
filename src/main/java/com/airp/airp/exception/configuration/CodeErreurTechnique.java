package com.airp.airp.exception.configuration;

/**
 * Codes erreurs techniques qui ne sont liés à aucun métier.<br>
 *
 * @see HasCodeErreur
 */
public enum CodeErreurTechnique implements HasCodeErreur {
	ENTITE_NON_CONNUE(9001L),
	UNAUTHORIZED_ERREUR(9005L),
	FORBIDDEN_ERREUR(9006L),
	ERREUR_VALIDATION(9200L),
	NEED_REPLAY_EXCEPTION(9997L),
	NO_RESULT_ERROR(9998L),
	RUNTIME_ERROR(9999L);

	private final Long code;

	private CodeErreurTechnique(Long code) {
		if (code < 9000 || code >= 10000) {
			throw new IllegalStateException("Les codes d'erreur techniques doivent être compris entre 9000 et 9999");
		}
		this.code = code;
	}

	@Override
	public Long getCode() {
		return code;
	}
}
