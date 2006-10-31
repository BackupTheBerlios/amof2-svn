package cmof.reflection;

import cmof.*;
import cmof.common.ReflectiveCollection;

public interface Extent extends Package {

    public ReflectiveCollection<? extends Object> getObject();

    public ReflectiveCollection<? extends Object> objectsOfType(UmlClass type,
            boolean includeSubtypes);

    public ReflectiveCollection<? extends Link> linksOfType(Association type);

    public ReflectiveCollection<? extends Object> linkedObjects(
            Association association, Object endObject, boolean end1to2direction);

    public boolean linkExists(Association association, Object firstObject,
            Object secondObject);

    /**
     * Querys a object in the extent. This is not part of standard MOF 2.0.
     * Query has to be of format:
     * <pre>
     *     meta-classifier-id ":" element-id ("/" meta-classifier-id ":" element-id)*
     * </pre>
     * Example:
     * <pre>
     * cmofExtent.query("Package:cmof/Class:Property");
     * </pre>
     * @throws Exception when the query has a wrong format. 
     * @return the queried object or null.
     */
    public cmof.reflection.Object query(String queryString) throws Exception;
}
