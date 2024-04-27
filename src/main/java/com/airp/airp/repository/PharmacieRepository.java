package com.airp.airp.repository;

import com.airp.airp.domain.Pharmacie;
import com.airp.airp.domain.Pharmacie_;
import com.airp.airp.repository.commons.CrudRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PharmacieRepository implements CrudRepository<Pharmacie> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Pharmacie> getClazz() {
        return Pharmacie.class;
    }

    /**
     * Recherche un pharmacie par nom d'pharmacie
     *
     * @param numero le numero de la pharmacie
     * @return un optional contenant pharmacie si elle existe
     */
    public Optional<Pharmacie> rechercherParNumero(String numero) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pharmacie> query = builder.createQuery(Pharmacie.class);
        Root<Pharmacie> root = query.from(Pharmacie.class);

        query.where(builder.equal(root.get(Pharmacie_.numero), numero.trim()));

        try {
            return Optional.of(entityManager.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
