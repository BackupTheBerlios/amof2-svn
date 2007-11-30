package hub.sam.mof.management;

import cmof.reflection.Extent;
import hub.sam.mof.Repository;

/**
 * Keeps all information of a MOF model together in one place:
 * repository, meta-model, XMI file, extent, extent name, factory, model package (optional)
 * 
 * This allows easy access to basic functions:
 * - retrieving information (like extent, meta model, ...)
 * - saving (to specified XMI file)
 * - getting an appropriate factory for creating instances of meta-model elements in the model extent
 * 
 */
public interface MofModel {
    Repository getRepository();
    Extent getExtent();
    String getExtentName();
    String getXmiFile();
    MofModel getMetaModel();
    void save() throws SaveException;
    void load() throws LoadException;
    void close();
    boolean isAlive();
}
