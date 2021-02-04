package com.bamboo.employee.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public abstract class EntityRepository<T> {

    private Class<T> entityType;

    @PersistenceContext
    public EntityManager entityManager;


    public void setEntityType(Class<T> clazzToSet) {
        this.entityType = clazzToSet;
    }

    public T findById(int theId) {
        return entityManager.find(entityType, theId);
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public List<T> findAll() {
        Query theQuery = entityManager.createQuery("from " + entityType.getName());
        return (List<T>) theQuery.getResultList();
    }

    public void deleteById(int theId) {
        T entity = findById(theId);
        entityManager.remove(entity);
    }
}
