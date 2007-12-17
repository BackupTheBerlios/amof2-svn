package hub.sam.mof.management;

import cmof.reflection.Extent;
import hub.sam.mof.Repository;

/**
 * Keeps all information of an AMOF model together in one place:
 * repository, meta-model, XMI file, extent, extent name
 * 
 * This allows easy access to basic functions:
 * - retrieving information (like extent, meta model, ...)
 * - saving (to specified XMI file)
 * 
 * Specializations of MofModel are: M3MofModel, M2MofModel and M1MofModel. They provide functionality
 * that depends on the meta-layer (e.g. a factory for creating model elements is only available in M2MofModel
 * and M1MofModel).
 * 
 */
public interface MofModel {
    
    Repository getRepository();
    
    Extent getExtent();
    
    String getExtentName();

    String getXmiFile();
    
    MofModel getMetaModel();
    
    /**
     * Saves the model to its XMI file.
     * @throws SaveException
     */
    void save() throws SaveException;
    
    /**
     * Loads the model from its XMI file into the extent.
     * @throws LoadException
     */
    void load() throws LoadException;
    
    /**
     * Removes the extents of this model and its meta-model from the repository.
     * 
     */
    void closeAll();
    
    /**
     * Removes only the extent of this model from the repository.
     * 
     */
    void close();
    
    /**
     * Alive if an extent is associated.
     *
     */
    boolean isAlive();
}
