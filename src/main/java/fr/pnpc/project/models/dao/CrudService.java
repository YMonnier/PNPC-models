package fr.pnpc.project.models.dao;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Local
public interface CrudService<T> {

    T create(T t);

    T find(Class type, Object id);

    T update(T t);

    void delete(Class type, Object id);

    List findWithNamedQuery(String queryName);

    List findWithNamedQuery(String queryName, int resultLimit);

    List findWithNamedQuery(String namedQueryName, Map parameters);

    List findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit);

    List<T> findByNativeQuery(String sql, Class type);

    List findAll(Class type);
}
