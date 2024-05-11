package com.airp.airp.facade;

import com.airp.airp.domain.Pharmacie;
import com.airp.airp.presentation.dto.PharmacieDto;
import com.airp.airp.repository.PharmacieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.airp.airp.exception.PharmacieException.pharmacieExistanteException;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.time.LocalTime.parse;
import static java.util.Comparator.comparing;

@Service
public class PharmacieFacade {

    private final PharmacieRepository pharmacieRepository;

    public PharmacieFacade(PharmacieRepository pharmacieRepository) {
        this.pharmacieRepository = pharmacieRepository;
    }

    /**
     * Liste toutes les pharmacies.
     *
     * @return la liste {@link PharmacieDto} des pharmacies.
     */
    @Transactional(readOnly = true)
    public List<PharmacieDto> lister() {
        return pharmacieRepository.lister()
                .map(PharmacieDto::new)
                .sorted(comparing(PharmacieDto::getNom, CASE_INSENSITIVE_ORDER)
                        .thenComparing(PharmacieDto::getNumero, CASE_INSENSITIVE_ORDER))
                .toList();
    }

    /**
     * Enregistre une pharmacie.
     *
     * @param pharmacieDto la pharmacie.
     */
    @Transactional
    public void enregistrer(PharmacieDto pharmacieDto) {
        pharmacieRepository.rechercherParNumero(pharmacieDto.getNumero())
                .orElseThrow(() -> pharmacieExistanteException(pharmacieDto.getNumero()));
        Pharmacie pharmacie = new Pharmacie(pharmacieDto);
        this.pharmacieRepository.save(pharmacie);
    }



    /**
     * Enregistre une pharmacie
     *
     * @param valeurs   les valeurs de la pharmacies
     * @param formatter le formatteur pour les heures
     */
    @Transactional
    public void enregistrerPharmacie(String[] valeurs, DateTimeFormatter formatter) {
        String numero = supprimerGriffes(valeurs[0]);
        Pharmacie PharmacieExistant = pharmacieRepository.rechercherParNumero(numero).orElse(new Pharmacie(numero));
        PharmacieExistant.mettreAJour(
                supprimerGriffes(valeurs[0]),
                supprimerGriffes(valeurs[1]),
                supprimerGriffes(valeurs[2]),
                supprimerGriffes(valeurs[3]),
                parse(supprimerGriffes(valeurs[4]), formatter),
                parse(supprimerGriffes(valeurs[5]), formatter),
                supprimerGriffes(valeurs[6]),
                supprimerGriffes(valeurs[7]),
                supprimerGriffes(valeurs[8]).toUpperCase()
        );
        pharmacieRepository.save(PharmacieExistant);
    }

    /**
     * Retire les guillemets qui encadrent une chaine de caractères
     *
     * @param texte le texte à traiter
     * @return le texte sans griffes autour
     */
    private String supprimerGriffes(String texte) {
        if (texte == null || texte.isEmpty()) {
            return texte;
        }
        if ((texte.startsWith("'") && texte.endsWith("'")) || (texte.startsWith("\"") && texte.endsWith("\""))) {
            return texte.substring(1, texte.length() - 1);
        } else {
            return texte;
        }
    }
}