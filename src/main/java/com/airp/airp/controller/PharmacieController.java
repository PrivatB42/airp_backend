package com.airp.airp.controller;

import com.airp.airp.configuration.logger.Logged;
import com.airp.airp.facade.PharmacieFacade;
import com.airp.airp.presentation.dto.PharmacieDto;
import com.airp.airp.service.ExcelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ws/pharmacie")
@SecurityRequirement(name = "Authorization")
public class PharmacieController {

    private final PharmacieFacade pharmacieFacade;
    private final ExcelService excelService;

    public PharmacieController(PharmacieFacade pharmacieFacade, ExcelService excelService) {
        this.pharmacieFacade = pharmacieFacade;
        this.excelService = excelService;
    }

    /**
     * Liste toutes les pharmacies.
     *
     * @return la liste {@link PharmacieDto} des pharmacies.
     */
    @GetMapping("/lister")
    @Logged
    public List<PharmacieDto> lister() {
        return pharmacieFacade.lister();
    }

    /**
     * Enregistre une pharmacie.
     *
     * @param pharmacieDto la pharmacie.
     */
    @PostMapping("/enregistrer")
    @Logged
    public void enregistrer(@RequestBody PharmacieDto pharmacieDto) {
        pharmacieFacade.enregistrer(pharmacieDto);
    }

    /**
     * Enregistre une pharmacie.
     *
     * @param fichier contenant les pharmacies.
     */
    @PostMapping("/importer")
    @Logged
    public void importer(@RequestParam("fichier") MultipartFile fichier) throws IOException {
        excelService.enregistrerExcelCsv(fichier);
    }
}
