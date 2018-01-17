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

    /**
     * Default constructor
     * Is require when the constructor is instanciate during
     * the injection
     */
    public CrudServiceBean() {}

    /**
     * Save an entity into the database.
     *
     * @param t Entity object.
     * @return The entity updated(@ID).
     */
    @Override
    public T create(T t) {
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    /**
     * Find an entity with his type and his identifier
     *
     * @param type Entity type object
     * @param id Entity identifier
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public T find(Class type, Object id) {
        return (T) this.em.find(type, id);
    }

    /**
     * Delete an entity into the database.
     *
     * @param type Entity type object.
     * @param id Entity identifier
     */
    public void delete(Class type, Object id) {
        Object ref = this.em.getReference(type, id);
        this.em.remove(ref);
    }

    /**
     * Update an entity into the database.
     *
     * @param t Entity object.
     * @return The entity updated.
     */
    @Override
    public T update(T t) {
        return (T) this.em.merge(t);
    }

    /**
     * Create a QueryBuilder for a named query.
     *
     * @param namedQueryName The string query name.
     * @return QueryBuilder object. @see QueryBuilder
     */
    @Override
    public List<T> findWithNamedQuery(String namedQueryName) {
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }

    /**
     * Create a QueryBuilder for a named query.
     * Function can get parameters a Map for all parameters
     *
     * @param namedQueryName The string query name.
     * @param parameters Parameters for the query.
     * @return QueryBuilder object. @see QueryBuilder
     */
    @Override
    public List<T> findWithNamedQuery(String namedQueryName, Map parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    /**
     * Create a QueryBuilder for a native query with result limit.
     *
     * @param queryName The string query name.
     * @param resultLimit The result limit the query return.
     * @return QueryBuilder object. @see QueryBuilder
     */
    @Override
    public List<T> findWithNamedQuery(String queryName, int resultLimit) {
        return this.em.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    /**
     * Create a QueryBuilder for a native query.
     * They return all entities persist in table
     *
     * @param sql The string query name.
     * @param type Type of object to return.
     * @return QueryBuilder object. @see QueryBuilder
     */
    @Override
    public List<T> findByNativeQuery(String sql, Class type) {
        return this.em.createNativeQuery(sql, type).getResultList();
    }

    /**
     * Create a QueryBuilder for a named query with result limit.
     * Function can get parameters a Map for all parameters.
     *
     * @param namedQueryName The string query name.
     * @param parameters Parameters for the query.
     * @param resultLimit The result limit the query return.
     * @return List object list.
     */
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

    /**
     * Return all entities presisted in target table.
     *
     * @param type Type of object to return.
     * @return List object list.
     */
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
