package com.airp.airp.service;

import com.airp.airp.domain.Pharmacie;
import com.airp.airp.repository.PharmacieRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
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
import static java.time.LocalTime.parse;
import static java.util.Locale.FRANCE;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@Service
public class ExcelService {
	private final PharmacieRepository pharmacieRepository;

	public ExcelService(PharmacieRepository pharmacieRepository) {
		this.pharmacieRepository = pharmacieRepository;
	}

	/**
	 * Enregistre une pharmacie à partir d'un fichier excel/csv.
	 *
	 * @param fichier le fichier.
	 */
	@Transactional
	public void enregistrerExcelCsv(MultipartFile fichier) throws IOException {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.parseCaseInsensitive()
				.appendPattern("[HH:mm:ss][HH:mm]")
				.toFormatter(FRANCE);
		final String typeFichierOriginal = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

		if (!fichier.isEmpty()) {
			String typeFichier = fichier.getContentType();
			if (Objects.equals(typeFichier, typeFichierOriginal)) {
				Workbook workbook = WorkbookFactory.create(fichier.getInputStream());
				Sheet sheet = workbook.getSheetAt(0);
				enregistrerDonneesExcel(sheet, formatter);
				workbook.close();
			} else if (Objects.equals(typeFichier, "text/csv")) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(fichier.getInputStream()));
				enregistrerDonneesCsv(reader, formatter);
				reader.close();
			} else {
				throw fichierNonPrisEnChargeException(typeFichier);
			}
		} else {
			throw fichierVideException();
		}
	}

	/**
	 * Récupère les données contenues dans le fichier et les enregistre
	 *
	 * @param sheet     la feuille excel
	 * @param formatter le formatteur pour les heures
	 */
	private void enregistrerDonneesExcel(Sheet sheet, DateTimeFormatter formatter) {
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			String[] valeurs = new String[row.getLastCellNum()];
			for (int i = 0; i < row.getLastCellNum(); i++) {
				valeurs[i] = recupererValeur(row.getCell(i));
			}
			enregistrerPharmacie(valeurs, formatter);
		}
	}

	/**
	 * Récupère les données d'un fichier CSV
	 *
	 * @param reader    le lecteur de fichier
	 * @param formatter le formatteur pour les heures
	 * @throws IOException l'exception en cas d'erreur pendant la lecture
	 */
	private void enregistrerDonneesCsv(BufferedReader reader, DateTimeFormatter formatter) throws IOException {
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

	/**
	 * Enregistre une pharmacie
	 *
	 * @param valeurs   les valeurs de la pharmacies
	 * @param formatter le formatteur pour les heures
	 */
	public void enregistrerPharmacie(String[] valeurs, DateTimeFormatter formatter) {
		String numero = supprimerGriffes(valeurs[0]);
		Pharmacie PharmacieExistant = pharmacieRepository.rechercherParNumero(numero).orElse(new Pharmacie(numero));
		PharmacieExistant.mettreAJour(
				Pharmacie.builder()
						.setNumero(numero)
						.setNom(supprimerGriffes(valeurs[1]))
						.setVille(supprimerGriffes(valeurs[2]))
						.setQuartier(supprimerGriffes(valeurs[3]))
						.setHeureOuverture(parse(supprimerGriffes(valeurs[4]), formatter))
						.setHeureFermeture(parse(supprimerGriffes(valeurs[5]), formatter))
						.setNomGerant(supprimerGriffes(valeurs[6]))
						.setContact(supprimerGriffes(valeurs[7]))
						.setStatut(supprimerGriffes(valeurs[8]).toUpperCase())
						.build()
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
