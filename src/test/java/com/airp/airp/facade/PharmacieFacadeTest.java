package com.airp.airp.facade;

import com.airp.airp.domain.Pharmacie;
import com.airp.airp.exception.PharmacieException;
import com.airp.airp.presentation.dto.PharmacieDto;
import com.airp.airp.repository.PharmacieRepository;
import com.airp.airp.utils.PharmacieMockBuilder;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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

    @Test
    public void shouldEnregistrerDonneesExcel_WhenEnregistrerExcel() throws Exception {
        // GIVEN
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pharmacies");

        Row entete = sheet.createRow(0);
        entete.createCell(0).setCellValue("Nom");
        entete.createCell(1).setCellValue("Adresse");
        entete.createCell(2).setCellValue("Ville");
        entete.createCell(3).setCellValue("Code Postal");
        entete.createCell(4).setCellValue("Heure Ouverture");
        entete.createCell(5).setCellValue("Heure Fermeture");
        entete.createCell(6).setCellValue("Tel");
        entete.createCell(7).setCellValue("Email");
        entete.createCell(8).setCellValue("Remarques");

        Row ligneDonnees1 = sheet.createRow(1);
        ligneDonnees1.createCell(0).setCellValue("Pharmacie A");
        ligneDonnees1.createCell(1).setCellValue("Rue A");
        ligneDonnees1.createCell(2).setCellValue("VilleA");
        ligneDonnees1.createCell(3).setCellValue("12345");
        ligneDonnees1.createCell(4).setCellValue("08:00:00");
        ligneDonnees1.createCell(5).setCellValue("18:00:00");
        ligneDonnees1.createCell(6).setCellValue("123456789");
        ligneDonnees1.createCell(7).setCellValue("emailA@test.com");
        ligneDonnees1.createCell(8).setCellValue("Remarque A");

        FileOutputStream fileOut = new FileOutputStream("fichier.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        // WHEN
        File file = new File("fichier.xlsx");
        FileInputStream fichierEntree = new FileInputStream(file);
        MultipartFile fichier = new MockMultipartFile(
                "fichier",
                file.getName(),
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                fichierEntree
        );
        fichierEntree.close();
        //THEN
        //TODO YAPI : Adapter TU
//        pharmacieFacade.enregistrerExcelCsv(fichier);
        file.delete();
    }


    @Test
    public void shouldEnregistrerDonneesCSV_WhenEnregistrerCSV() throws Exception {
        // GIVEN
        File fichierCSV = new File("fichier.csv");
        FileWriter writer = new FileWriter(fichierCSV);
        CSVWriter csvWriter = new CSVWriter(writer);
        String[] entete = {"Nom", "Adresse", "Ville", "Code Postal", "Heure Ouverture",
                "Heure Fermeture", "Tel", "Email", "Remarques"};
        csvWriter.writeNext(entete);

        String[] donnees = {"Pharmacie A", "Rue A", "VilleA", "12345", "08:00:00",
                "18:00:00", "123456789", "emailA@test.com", "Remarque A"};
        csvWriter.writeNext(donnees);
        csvWriter.close();
        // WHEN
        FileInputStream fichierEntree = new FileInputStream(fichierCSV);
        MultipartFile fichier = new MockMultipartFile(
                "fichier",
                fichierCSV.getName(),
                "text/csv",
                fichierEntree
        );
        fichierEntree.close();
        // THEN
        //TODO YAPI : Adapter TU
//        pharmacieFacade.enregistrerExcelCsv(fichier);
        fichierCSV.delete();
    }

    @Test
    public void shouldLeverException_WhenEnregistrerAutreTypeFichier() throws Exception {
        // GIVEN
        File fichierWord = File.createTempFile("donnees", ".docx");
        FileOutputStream fos = new FileOutputStream(fichierWord);
        fos.write("Contenu du document Word".getBytes());
        fos.close();

        PharmacieFacade mockPharmacieFacade = mock(PharmacieFacade.class);
        //TODO YAPI : Adapter TU
//        doThrow(fichierNonPrisEnChargeException("docx")).when(mockPharmacieFacade).enregistrerExcelCsv(any(MultipartFile.class));
        // WHEN
        FileInputStream fichierEntree = new FileInputStream(fichierWord);
        MultipartFile fichier = new MockMultipartFile(
                "fichier",
                fichierWord.getName(),
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                fichierEntree
        );
        fichierEntree.close();
        // THEN
        try {
            //TODO YAPI : Adapter TU
//            mockPharmacieFacade.enregistrerExcelCsv(fichier);
        } catch (PharmacieException e) {
            assertEquals("Le type de fichier non pris en charge : docx", e.getMessage());
        }
    }

    @Test
    public void shouldLeverException_WhenEnregistrerFichiervide() throws Exception {
        // GIVEN
        File fichierVide = File.createTempFile("donnees", ".docx");
        PharmacieFacade mockPharmacieFacade = mock(PharmacieFacade.class);
        //TODO YAPI : Adapter TU
//        doThrow(fichierVideException()).when(mockPharmacieFacade).enregistrerExcelCsv(any(MultipartFile.class));
        // WHEN
        FileInputStream fichierEntree = new FileInputStream(fichierVide);
        MultipartFile fichier = new MockMultipartFile(
                "fichier",
                fichierVide.getName(),
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                fichierEntree
        );
        fichierEntree.close();
        // THEN
        try {
            //TODO YAPI : Adapter TU
//            mockPharmacieFacade.enregistrerExcelCsv(fichier);
        } catch (PharmacieException e) {
            assertEquals("Le fichier importé ne contient aucune donnée", e.getMessage());
        }
    }
}
