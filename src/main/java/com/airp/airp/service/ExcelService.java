package com.airp.airp.service;

import com.airp.airp.domain.Pharmacie;
import com.airp.airp.domain.PharmacieBuilder;
import com.airp.airp.presentation.dto.ImportPharmacieDto;
import com.airp.airp.repository.JpaPharmacieRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.airp.airp.exception.PharmacieException.fichierNonPrisEnChargeException;
import static com.airp.airp.exception.PharmacieException.fichierVideException;
import static java.lang.Double.parseDouble;
import static java.time.LocalTime.parse;
import static java.util.Locale.FRANCE;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@Service
public class ExcelService {
	private final JpaPharmacieRepository pharmacieRepository;

	public ExcelService(JpaPharmacieRepository pharmacieRepository) {
		this.pharmacieRepository = pharmacieRepository;
	}

	/**
	 * Enregistre une pharmacie à partir d'un fichier excel/csv.
	 *
	 * @param fichier le fichier.
	 * @return la liste des pharmacies non enregistrées
	 */
	@Transactional
	public List<ImportPharmacieDto> enregistrerExcelCsv(MultipartFile fichier) throws IOException {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.parseCaseInsensitive()
				.appendPattern("[HH:mm:ss][HH:mm]")
				.toFormatter(FRANCE);
		final String typeFichierOriginal = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

		if (!fichier.isEmpty()) {
			List<ImportPharmacieDto> pharmaciesNonEnregistrees;
			String typeFichier = fichier.getContentType();
			if (Objects.equals(typeFichier, typeFichierOriginal)) {
				Workbook workbook = WorkbookFactory.create(fichier.getInputStream());
				return enregistrerDonneesExcel(workbook, formatter);
			} else if (Objects.equals(typeFichier, "text/csv")) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(fichier.getInputStream()));
				return enregistrerDonneesCsv(reader, formatter);
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
	 * @param workbook     le classeur excel
	 * @param formatter le formatteur pour les heures
	 * @return la liste des pharmacies non enregistrées
	 */
	@Transactional
	private List<ImportPharmacieDto> enregistrerDonneesExcel(Workbook workbook, DateTimeFormatter formatter) throws IOException {
		List<ImportPharmacieDto> pharmaciesNonEnregistrees = new ArrayList<>();
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			String[] valeurs = new String[row.getLastCellNum()];
			for (int i = 0; i < row.getLastCellNum(); i++) {
				valeurs[i] = recupererValeur(row.getCell(i));
			}
			pharmaciesNonEnregistrees.add(creerPharmacie(valeurs, formatter));
		}
		workbook.close();
		return pharmaciesNonEnregistrees;
	}

	/**
	 * Récupère les données d'un fichier CSV
	 *
	 * @param reader    le lecteur de fichier
	 * @param formatter le formatteur pour les heures
	 * @return la liste des pharmacies non enregistrées
	 * @throws IOException l'exception en cas d'erreur pendant la lecture
	 */
	@Transactional
	private List<ImportPharmacieDto> enregistrerDonneesCsv(BufferedReader reader, DateTimeFormatter formatter) throws IOException {
		String line;
		boolean premiereLigne = true;
		List<ImportPharmacieDto> pharmaciesNonEnregistrees = new ArrayList<>();
		while ((line = reader.readLine()) != null) {
			if (premiereLigne) {
				premiereLigne = false;
				continue;
			}
			String[] valeurs = line.split("[,;]");
			pharmaciesNonEnregistrees.add(creerPharmacie(valeurs, formatter));
		}
		reader.close();
		return pharmaciesNonEnregistrees;
	}

	/**
	 * Enregistre une pharmacie
	 *
	 * @param valeurs   les données de la pharmacie à enregistrer
	 * @param formatter le formatteur pour les heures
	 * @return la pharmacie si elle n'a pas pu être enregistrée
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ImportPharmacieDto creerPharmacie(String[] valeurs, DateTimeFormatter formatter) {
		ImportPharmacieDto importPharmacieDto = new ImportPharmacieDto(valeurs);
		try {
			String numero = supprimerGriffes(importPharmacieDto.getNumero());
			Pharmacie pharmacieExistant = pharmacieRepository.findOneByNumero(numero).orElse(new Pharmacie(numero));
			PharmacieBuilder pharmacieBuild = new PharmacieBuilder();

			pharmacieBuild.setNumero(numero)
					.setNom(supprimerGriffes(importPharmacieDto.getNom()))
					.setVille(supprimerGriffes(importPharmacieDto.getVille()))
					.setQuartier(supprimerGriffes(importPharmacieDto.getQuartier()))
					.setHeureOuverture(parse(supprimerGriffes(importPharmacieDto.getHeureOuverture()), formatter))
					.setHeureFermeture(parse(supprimerGriffes(importPharmacieDto.getHeureFermeture()), formatter))
					.setNomGerant(supprimerGriffes(importPharmacieDto.getNomGerant()))
					.setContact(supprimerGriffes(importPharmacieDto.getContact()))
					.setLongitude(parseEnLongitude(supprimerGriffes(importPharmacieDto.getLongitude())).toString())
					.setLatitude(parseEnLatitude(supprimerGriffes(importPharmacieDto.getLatitude())).toString());
			pharmacieExistant.mettreAJour(
					pharmacieBuild.build()
			);
			pharmacieRepository.save(pharmacieExistant);
		} catch (Exception e) {
			return importPharmacieDto;
		}
		return null;
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

	Double parseEnLatitude(String coordonnee) throws NumberFormatException {
		double latitude = parseDouble(coordonnee);
		if (latitude >= -90 && latitude <= 90) {
			return latitude;
		} else {
			throw new NumberFormatException();
		}
	}

	Double parseEnLongitude(String coordonnee) throws NumberFormatException {
		double longitude = parseDouble(coordonnee);
		if (longitude >= -180 && longitude <= 180) {
			return longitude;
		} else {
			throw new NumberFormatException();
		}
	}
}
