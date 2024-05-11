package com.airp.airp.service;

import com.airp.airp.facade.PharmacieFacade;
import com.airp.airp.repository.PharmacieRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

import static com.airp.airp.exception.PharmacieException.fichierNonPrisEnChargeException;
import static com.airp.airp.exception.PharmacieException.fichierVideException;
import static java.util.Locale.FRANCE;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@Service
public class ExcelService {
    private final PharmacieRepository pharmacieRepository;
    private final PharmacieFacade pharmacieFacade;

    public ExcelService(PharmacieRepository pharmacieRepository, PharmacieFacade pharmacieFacade) {
        this.pharmacieRepository = pharmacieRepository;
        this.pharmacieFacade = pharmacieFacade;
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
                .toFormatter(FRANCE);

        if (!fichier.isEmpty()) {
            String typeFichier = fichier.getContentType();
            if (Objects.equals(typeFichier, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                Workbook workbook = WorkbookFactory.create(fichier.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
                traiterDonneesExcel(sheet, formatter);
                workbook.close();
            } else if (Objects.equals(typeFichier, "text/csv")) {
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

    /**
     * Recupere les données contenues dans le fichier et les enregistre
     *
     * @param sheet     la feuille excel
     * @param formatter le formatteur pour les heures
     */
    private void traiterDonneesExcel(Sheet sheet, DateTimeFormatter formatter) {
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String[] valeurs = new String[row.getLastCellNum()];
            for (int i = 0; i < row.getLastCellNum(); i++) {
                valeurs[i] = recupererValeur(row.getCell(i));
            }
            pharmacieFacade.enregistrerPharmacie(valeurs, formatter);
        }
    }

    /**
     * Récupere les données d'un fichier CSV
     *
     * @param reader    le lecteur de fichier
     * @param formatter le formatteur pour les heures
     * @throws IOException l'exception en cas d'erreur pendant la lecture
     */
    private void traiterDonneesCSV(BufferedReader reader, DateTimeFormatter formatter) throws IOException {
        String line;
        boolean premiereLigne = true;
        while ((line = reader.readLine()) != null) {
            if (premiereLigne) {
                premiereLigne = false;
                continue;
            }
            String[] valeurs = line.split("[,;]");
            pharmacieFacade.enregistrerPharmacie(valeurs, formatter);
        }
    }

    /**
     * Récupère la valeur d'une cellule
     *
     * @param cell la cellule
     * @return la valeur
     */
    public String recupererValeur(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(STRING);
        return cell.getStringCellValue();
    }

}
