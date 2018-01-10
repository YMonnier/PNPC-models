package fr.pnpc.project.models.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Stateless
public class CrudServiceBean<T> implements CrudService<T> {

    @PersistenceContext(unitName = "pnpc_db_unit")
    EntityManager em;

    public CrudServiceBean() {
    }

    @Override
    public T create(T t) {
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T find(Class type, Object id) {
        return (T) this.em.find(type, id);
    }

    public void delete(Class type, Object id) {
        Object ref = this.em.getReference(type, id);
        this.em.remove(ref);
    }

    @Override
    public T update(T t) {
        return (T) this.em.merge(t);
    }

    @Override
    public List<T> findWithNamedQuery(String namedQueryName) {
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }

    @Override
    public List<T> findWithNamedQuery(String namedQueryName, Map parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    @Override
    public List<T> findWithNamedQuery(String queryName, int resultLimit) {
        return this.em.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    @Override
    public List<T> findByNativeQuery(String sql, Class type) {
        return this.em.createNativeQuery(sql, type).getResultList();
    }

    @Override
    public List<T> findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit) {
        Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = this.em.createNamedQuery(namedQueryName);
        if (resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Map.Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    @Override
    public List findAll(Class type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> rootEntry = cq.from(type);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
