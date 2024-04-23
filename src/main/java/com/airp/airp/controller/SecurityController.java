package com.airp.airp.controller;

import com.airp.airp.configuration.logger.Logged;
import com.airp.airp.presentation.dto.auth.AuthDto;
import com.airp.airp.presentation.dto.auth.TokenDto;
import com.airp.airp.service.SecurityService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/securite")
public class SecurityController {

	private final SecurityService securityService;

	public SecurityController(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * Permet d'authentifier l'utilisateur.
	 *
	 * @param authDto les informations de connexion.
	 * @return le token d'authentification
	 */
	@PostMapping("/auth")
	@Logged
	public TokenDto authentification(@RequestBody AuthDto authDto) {
		return securityService.autentifier(authDto);
	}

}
