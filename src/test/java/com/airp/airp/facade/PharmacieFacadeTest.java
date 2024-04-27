package com.airp.airp.facade;

import com.airp.airp.domain.Pharmacie;
import com.airp.airp.presentation.dto.PharmacieDto;
import com.airp.airp.repository.PharmacieRepository;
import com.airp.airp.utils.PharmacieMockBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

/**
 * Classe de test de PharmacieFacadeTest
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
public class PharmacieFacadeTest {

    @Mock
    private PharmacieRepository pharmacieRepository;

    @InjectMocks
    @Spy
    private PharmacieFacade pharmacieFacade;

    @Test
    public void shouldListerToutesLesPharmacies_whenLister() {
        // GIVEN
        Pharmacie pharmacie = new PharmacieMockBuilder()
                .setId(1L)
                .setNumero("PHA1")
                .setNom("Pharmacie Z")
                .setHeureOuverture(of(7, 0))
                .setHeureFermeture(of(20, 0))
                .build();
        Pharmacie pharmacie2 = new PharmacieMockBuilder()
                .setId(2L)
                .setNumero("PHA2")
                .setNom("Pharmacie A")
                .setHeureOuverture(of(7, 30))
                .setHeureFermeture(of(20, 0))
                .build();

        Stream<Pharmacie> pharmacies = Stream.of(pharmacie, pharmacie2);
        when(pharmacieRepository.lister()).thenReturn(pharmacies);

        // WHEN
        List<PharmacieDto> pharmacieDtos = pharmacieFacade.lister();

        // THEN
        assertThat(pharmacieDtos)
                .hasSize(2)
                .extracting(PharmacieDto::getNumero, PharmacieDto::getNom)
                .containsExactly(
                        tuple("PHA2", "Pharmacie A"),
                        tuple("PHA1", "Pharmacie Z"));
    }
}
