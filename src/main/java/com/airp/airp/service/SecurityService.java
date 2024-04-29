package com.airp.airp.service;

import com.airp.airp.domain.Utilisateur;
import com.airp.airp.enums.StatutUtilisateur;
import com.airp.airp.presentation.dto.auth.AuthDto;
import com.airp.airp.presentation.dto.auth.TokenDto;
import com.airp.airp.repository.UtilisateurRepository;
import com.airp.airp.security.JwtTokenUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

import static com.airp.airp.exception.UtilisateurException.motDePasseIncorrect;
import static com.airp.airp.exception.UtilisateurException.utiilisateurInconnu;
import static com.airp.airp.exception.UtilisateurException.utilisateurInactif;

@Service
public class SecurityService implements UserDetailsService {

    private final JwtTokenUtils jwtTokenUtils;
    private final UtilisateurRepository utilisateurRepository;

    public SecurityService(JwtTokenUtils jwtTokenUtils, UtilisateurRepository utilisateurRepository) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Récupère un utilisateur à partir de son username.
     *
     * @param username le username de l'utilisateur.
     * @return L'utilisateur.
     * @throws UsernameNotFoundException Exception levé lorsqu'aucun utilisateur ne correspond à ce username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.rechercherParUsername(username)
                .map(Utilisateur::buildUser)
                .orElseThrow(() -> utiilisateurInconnu(username));
    }

    /**
     * Récupère un utilisateur à partir de son username et de son password.
     *
     * @param username le username de l'utilisateur.
     * @param password le password de l'utilisateur.
     * @return L'utilisateur.
     * @throws UsernameNotFoundException Exception levé lorsqu'aucun utilisateur ne correspond à ce username.
     */
    private Utilisateur rechercherUtilisateurParUsernameEtPassword(String username, String password) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.rechercherParUsername(username)
                .orElseThrow(() -> utiilisateurInconnu(username));
        if (SecurityService.comparerPassword(password, utilisateur.getPassword())) {
                return utilisateur;
        }
        else {
            throw motDePasseIncorrect();
        }
    }

    /**
     * Authentifie l'utilisateur.
     *
     * @param authDto l'utilisateur.
     * @return le Tokent JWT de l'utilisateur authentifié.
     */
    @Transactional
    public TokenDto autentifier(AuthDto authDto) {
        Utilisateur utilisateur = rechercherUtilisateurParUsernameEtPassword(authDto.getUsername(), authDto.getPassword());
        if (utilisateur.getStatut().equals(StatutUtilisateur.INACTIF)) {
            throw utilisateurInactif();
        }

        SecurityContextHolder.clearContext();
        String accessToken = jwtTokenUtils.generateToken(utilisateur);

        return new TokenDto(accessToken);
    }

    /**
     * Compare les mots de passe.
     *
     * @param password le mot de passe.
     * @param encodePassword le mot de passe crypté.
     * @return le mot de passe crypté.
     */
    private static boolean comparerPassword(String password, String encodePassword) {
        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        return bCryptPasswordEncoder.matches(password, encodePassword);
    }

    /**
     * Crypte le mot de passe de l'utilisateur.
     *
     * @param password le mot de passe.
     * @return le mot de passe crypté.
     */
    public static String crypterPassword(String password) {
        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        return bCryptPasswordEncoder.encode(password);
    }
}
