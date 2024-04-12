package com.airp.airp.repository;

import com.airp.airp.domain.Utilisateur;
import com.airp.airp.domain.Utilisateur_;
import com.airp.airp.repository.commons.CrudRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UtilisateurRepository implements CrudRepository<Utilisateur> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Utilisateur> getClazz() {
        return Utilisateur.class;
    }

    /**
     * Recherche un utilisateur par nom d'utilisateur
     *
     * @param username le nom d'utilisateur
     * @return l'utilisateur
     */
    public Optional<Utilisateur> rechercherParUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Utilisateur> query = builder.createQuery(Utilisateur.class);
        Root<Utilisateur> root = query.from(Utilisateur.class);

        query.where(builder.equal(root.get(Utilisateur_.USERNAME), username.trim()));

        try {
            return Optional.of(entityManager.createQuery(query).getSingleResult());
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

}
