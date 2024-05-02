package com.airp.airp.facade;

import com.airp.airp.domain.Pharmacie;
import com.airp.airp.presentation.dto.PharmacieDto;
import com.airp.airp.repository.PharmacieRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.airp.airp.exception.PharmacieException.*;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Comparator.comparing;
import static org.apache.poi.ss.usermodel.CellType.STRING;

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
        Pharmacie pharmacieExistant = pharmacieRepository.rechercherParNumero(pharmacieDto.getNumero())
                .orElse(null);
        if (pharmacieExistant != null) {
            throw pharmacieExistanteException(pharmacieExistant.getNumero());
        }
        Pharmacie pharmacie = new Pharmacie(pharmacieDto);
        this.pharmacieRepository.save(pharmacie);
    }


    /**
     * Enregistre une pharmacie a partir d'un fichier excel/csv.
     *
     * @param fichier le fichier.
     */
    @Transactional
    public void enregistrerExcelCsv(MultipartFile fichier) throws IOException {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("[HH:mm:ss][HH:mm]")
                .toFormatter(Locale.FRANCE);

        if (!fichier.isEmpty()) {
            String typeFichier = fichier.getContentType();
            if (Objects.equals(typeFichier, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                Workbook workbook = WorkbookFactory.create(fichier.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
                traiterDonneesExcel(sheet, formatter);
                workbook.close();
            } else if (Objects.equals(typeFichier, "text/csv") ) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(fichier.getInputStream()));
                traiterDonneesCSV(reader, formatter);
                reader.close();
            } else {
                throw fichierNonPrisEnChargeException(typeFichier);
            }
        } else {
            throw fichierVideException();
        }
    }

    private void traiterDonneesExcel(Sheet sheet, DateTimeFormatter formatter) {

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String[] valeurs = new String[9];
            for (int i = 0; i < 9; i++) {
                valeurs[i] = recupererValeur(row.getCell(i));
            }
            enregistrerPharmacie(valeurs, formatter);
        }
    }

    private void traiterDonneesCSV(BufferedReader reader, DateTimeFormatter formatter) throws IOException {
        String line;
        boolean premiereLigne = true;
        while ((line = reader.readLine()) != null) {
            if (premiereLigne) {
                premiereLigne = false;
                continue;
            }
            String[] valeurs = line.split("[,;]");
            enregistrerPharmacie(valeurs, formatter);
        }
    }

    private void enregistrerPharmacie(String[] valeurs, DateTimeFormatter formatter) {

        Pharmacie pharmacie = new Pharmacie(
                supprimerGuillemets(valeurs[0]),
                supprimerGuillemets(valeurs[1]),
                supprimerGuillemets(valeurs[2]),
                supprimerGuillemets(valeurs[3]),
                LocalTime.parse(supprimerGuillemets(valeurs[4]), formatter),
                LocalTime.parse(supprimerGuillemets(valeurs[5]), formatter),
                supprimerGuillemets(valeurs[6]),
                supprimerGuillemets(valeurs[7]),
                supprimerGuillemets(valeurs[8])
        );
        Pharmacie PharmacieExistant = pharmacieRepository.rechercherParNumero(pharmacie.getNumero())
                .orElse(null);
        if (PharmacieExistant != null) {
            PharmacieExistant.mettreAJour(
                    supprimerGuillemets(valeurs[0]),
                    supprimerGuillemets(valeurs[1]),
                    supprimerGuillemets(valeurs[2]),
                    supprimerGuillemets(valeurs[3]),
                    LocalTime.parse(supprimerGuillemets(valeurs[4]), formatter),
                    LocalTime.parse(supprimerGuillemets(valeurs[5]), formatter),
                    supprimerGuillemets(valeurs[6]),
                    supprimerGuillemets(valeurs[7]),
                    supprimerGuillemets(valeurs[8])
            );
            pharmacieRepository.save(PharmacieExistant);
        } else {
            pharmacieRepository.save(pharmacie);
        }
    }

    public String recupererValeur(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(STRING);
        return cell.getStringCellValue();
    }

    public String supprimerGuillemets(String texte) {
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