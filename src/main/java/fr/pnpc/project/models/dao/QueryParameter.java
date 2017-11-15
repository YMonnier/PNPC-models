package fr.pnpc.project.models.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stephen on 10/11/17.
 */
public class QueryParameter {

    private Map parameters = null;

    private QueryParameter(String name,Object value){
        this.parameters = new HashMap();
        this.parameters.put(name, value);
    }
    public static QueryParameter with(String name, Object value){
        return new QueryParameter(name, value);
    }
    public QueryParameter and(String name,Object value){
        this.parameters.put(name, value);
        return this;
    }
    public Map parameters(){
        return this.parameters;
    }
}
