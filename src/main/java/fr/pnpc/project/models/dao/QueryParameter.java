package fr.pnpc.project.models.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stephen on 10/11/17.
 */
public class QueryParameter {

    /**
     * Parameters query.
     */
    private Map parameters = null;

    /**
     * Constructor
     *
     * @param name The string key name.
     * @param value The value of parameter.
     */
    private QueryParameter(String name,Object value){
        this.parameters = new HashMap();
        this.parameters.put(name, value);
    }

    /**
     * Set a new parameter.
     *
     * @param name The string key name.
     * @param value The value of parameter.
     * @return
     */
    public static QueryParameter with(String name, Object value){
        return new QueryParameter(name, value);
    }

    /**
     * Set a parameter to the current query.
     *
     * @param name The string key name.
     * @param value The value of parameter.
     * @return
     */
    public QueryParameter and(String name,Object value){
        this.parameters.put(name, value);
        return this;
    }

    /**
     * Return a Map with all parameters.
     *
     * @return Single Map of parameters.
     */
    public Map parameters(){
        return this.parameters;
    }
}
