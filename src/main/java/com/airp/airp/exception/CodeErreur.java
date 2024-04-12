package com.airp.airp.exception;


import com.airp.airp.exception.configuration.HasCodeErreur;

public enum CodeErreur implements HasCodeErreur {
    COMPTE_EXISTANT(7001L),
    NOM_UTILISATEUR_MOT_PASSE_INCORRECT(7002L),
    COMPTE_INEXISTANT(7006L),
    ACCESS_REFUSE(7008L),
    COMPTE_INACTIF(7009L);

    /**
     * Valeur minimale des codes de cette application.
     */
    public static final int CODE_MIN = 7000;

    /**
     * Valeur maximale des codes de cette application.
     */
    public static final int CODE_MAX = 7999;

    private final Long code;

    CodeErreur(Long code) {
        if (code < CODE_MIN || code > CODE_MAX) {
            throw new IllegalStateException(String.format("Le code erreur doit se trouver entre %s et %s", CODE_MIN, CODE_MAX));
        }
        this.code = code;
    }

    @Override
    public Long getCode() {
        return code;
    }
}
