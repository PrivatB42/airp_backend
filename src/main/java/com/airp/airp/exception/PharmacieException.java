package com.airp.airp.exception;

import com.airp.airp.exception.configuration.AbstractApplicationException;
import com.airp.airp.exception.configuration.CodeErreurTechnique;

import java.util.Collection;

public class PharmacieException extends AbstractApplicationException {


    private PharmacieException(CodeErreurTechnique codeErreur, String message, Collection<String> parametres) {
        super(codeErreur.getCode(), message, parametres);
    }

    /**
     * Exception pour pharmacie deja existante.
     */
    public static PharmacieException pharmacieExistanteException() {
        return null; // TODO à implémenter
    }
}
