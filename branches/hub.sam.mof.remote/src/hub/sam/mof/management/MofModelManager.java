/***********************************************************************
 * MAS -- MOF Action Semantics
 * Copyright (C) 2007 Markus Scheidgen, Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mof.management;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.OclHelper;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;

import java.util.HashMap;
import java.util.Map;

import cmof.reflection.Extent;

/**
 * Manages MofModels that are based on the three meta layer architecture where the M3-model is CMOF,
 * the M2-model is a meta-model as instance of CMOF and the M1-model is a model as instance of the M2-model.
 * 
 * The manager can handle one meta-model (M2) and many instances of that model (M1). It allows you to load models
 * from an XMI file or from a statically created extent. But you must pay attention to always load the M2 model before
 * any M1 models!
 * When you load a particular model, all relevant information will be saved in a MofModel object. It can be retrieved
 * by invoking the methods getM2Model() and getM1Model(extentName) where the extent name is used to identify multiple
 * M1 models.
 * 
 * The MofModelManager can be configured to prevent reloading the same model XMI multiple times into different
 * extents. In such a case, where an XMI file has already been loaded, the manager will simply return the
 * corresponding MofModel.
 *
 */
public class MofModelManager {
    
    private final Repository repository;
    private M3MofModel cmofModel;
    private M2MofModel m2Model;
    private Map<String, M1MofModel> m1Models = new HashMap<String, M1MofModel>();
    private static int uniqueExtentId = 0;
    
    private OclHelper oclHelper;
    
    /**
     * Every XMI file that is loaded through the manager is mapped to its corresponding MofModel.
     * This prevents loading the same XMI file multiple times into different extents.
     */
    private static boolean xmiFileBoundToMofModel = true;
    private static Map<String, MofModel> xmiFileToMofModel = new HashMap<String, MofModel>();
    
    public static boolean isXmiFileBoundToMofModel() {
        return xmiFileBoundToMofModel;
    }

    public static void setXmiFileBoundToMofModel(boolean value) {
        MofModelManager.xmiFileBoundToMofModel = value;
    }

    public MofModelManager(Repository repository) {
        this.repository = repository;
    }
    
    public M3MofModel getCmofModel() {
        if (cmofModel == null) {
            cmofModel = new M3MofModel(repository);
        }
        return cmofModel;
    }
    
    /**
     * Loads the M2 model.
     * 
     */
    public void loadM2Model(String xmiFile, XmiKind xmiKind, final String extentName, String packageQuery) throws LoadException {
        MofModel existingModel = checkIfXmiFileAlreadyLoaded(xmiFile);
        if (existingModel != null) {
            m2Model = (M2MofModel) existingModel;
        }
        else {
            String uniqueExtentName = getUniqueExtentName(extentName);
            Extent modelExtent = createModelExtent(uniqueExtentName, getCmofModel());
            XmiType xmiType = getXmiTypeForXmiKind(xmiKind);
            if (xmiType == null) {
                throw new LoadException("Unkown extension for XMI file '" + xmiFile + "'.");
            }
            m2Model = new M2MofModel(repository, getCmofModel(), xmiFile, xmiType, modelExtent, uniqueExtentName, packageQuery);
            m2Model.load();
            xmiFileToMofModel.put(xmiFile, m2Model);
        }
    }

    /**
     * Loads the M2 model and decides on the XMI file ending which method of loading will be used
     * (e.g. Plain XMI or MagicDraw XMI).
     * 
     */
    public void loadM2Model(String xmiFile, String extentName, String packageQuery) throws LoadException {
        if (xmiFile.endsWith(".mdxml")) {
            // only use with M2 models !
            loadM2Model(xmiFile, XmiKind.md, extentName, packageQuery);
        }
        else {
            loadM2Model(xmiFile, XmiKind.mof, extentName, packageQuery);
        }
    }
    
    public void loadM2Model(Extent modelExtent, String packageQuery) throws LoadException {
        ((ExtentImpl) modelExtent).configureExtent(getCmofModel().getPackage());
        // TODO must specifiy XMI file and type if model should be saveable
        m2Model = new M2MofModel(repository, getCmofModel(), null, null, modelExtent, null, packageQuery);
    }
    
    /**
     * Loads the M1 model (with default XMI kind MOF).
     * 
     */
    public void loadM1Model(String xmiFile, String extentName) throws LoadException {
        loadM1Model(xmiFile, XmiKind.mof, extentName);
    }

    /**
     * Loads the M1 model.
     * 
     */
    public void loadM1Model(String xmiFile, XmiKind xmiKind, final String extentName) throws LoadException {
        M1MofModel m1Model = null;
        MofModel existingModel = checkIfXmiFileAlreadyLoaded(xmiFile);
        if (existingModel != null) {
            m1Model = (M1MofModel) existingModel;
        }
        else {
            String uniqueExtentName = getUniqueExtentName(extentName);
            Extent modelExtent = createModelExtent(uniqueExtentName, m2Model);
            XmiType xmiType = getXmiTypeForXmiKind(xmiKind);
            if (xmiType == null) {
                throw new LoadException("Unkown extension for XMI file '" + xmiFile + "'.");
            }
            m1Model = new M1MofModel(repository, m2Model, xmiFile, xmiType, modelExtent, uniqueExtentName);
            m1Model.load();
            m1Models.put(m1Model.getExtentName(), m1Model);
            xmiFileToMofModel.put(xmiFile, m1Model);
        }
    }
    
    private XmiType getXmiTypeForXmiKind(XmiKind xmiKind) {
        if (xmiKind == XmiKind.mof) {
            return new MofXmiTypeImpl();
        }
        else if (xmiKind == XmiKind.md) {
            return new MagicDrawXmiTypeImpl();
        }
        return null;
    }

    public void loadM1Model(Extent modelExtent, String extentName) throws LoadException {
        if (m2Model == null) {
            throw new IllegalStateException("You have to load the M2 model before the M1 model!");
        }
        ((ExtentImpl) modelExtent).configureExtent(m2Model.getPackage());
        // TODO must specify XMI file and XMI type if model should be saveable
        M1MofModel m1Model = new M1MofModel(repository, m2Model, null, null, modelExtent, extentName);
        m1Models.put(m1Model.getExtentName(), m1Model);
    }
    
    private static String getUniqueExtentId() {
        return new Integer(uniqueExtentId++).toString();
    }

    private MofModel checkIfXmiFileAlreadyLoaded(String xmiFile) {
        MofModel mofModel = null;
        if (isXmiFileBoundToMofModel()) {
            mofModel = xmiFileToMofModel.get(xmiFile);
            if (mofModel != null) {
                if (mofModel.isAlive()) {
                    return mofModel;
                }
                else {
                    xmiFileToMofModel.remove(xmiFile);
                }
            }
        }
        return null;
    }
    
    private String getUniqueExtentName(String originalExtentName) {
        return originalExtentName + " " + getUniqueExtentId();
    }
    
    private Extent createModelExtent(String extentName, MofModel metaModel) {
        // TODO: reuse existing model extent or generate unique extent name ?
        return repository.createExtent(extentName, metaModel.getExtent());
    }
    
    public M2MofModel getM2Model() {
        return m2Model;
    }
    
    public void setM2Model(M2MofModel model) {
        this.m2Model = model;
    }
    
    /**
     * Returns the first M1 model in the list of all M1 models.
     * 
     * @deprecated use getFirstM1Model() or getM1Model(extentName)
     * @return
     */
    @Deprecated
    public M1MofModel getM1Model() {
        return getFirstM1Model();
    }
    
    public M1MofModel getFirstM1Model() {
        return m1Models.values().iterator().next();
    }
    
    public M1MofModel getM1Model(String extentName) {
        return m1Models.get(extentName);
    }

    public void addM1Model(M1MofModel model) {
        this.m1Models.put(model.getExtentName(), model);
    }
        
    public M2MofModel createM2Model(String extentName, String persistenceXmiFile, XmiKind xmiKind) {
        Extent m2Extent = repository.createExtent(extentName, getCmofModel().getExtent());
        m2Model = new M2MofModel(repository, getCmofModel(), persistenceXmiFile, getXmiTypeForXmiKind(xmiKind), m2Extent, extentName, null);
        return m2Model;
    }
    
    public M2MofModel createM2Model(String extentName) {
        return createM2Model(extentName, null, null);
    }
    
    /**
     * creates a M1 model as instance of the M2 model in a new extent.
     * 
     * @param extentName
     * @param persistenceXmiFile optional, specify if model should be saveable
     * @return
     */
    public M1MofModel createM1Model(String extentName, String persistenceXmiFile, XmiKind xmiKind) {
        Extent m1Extent = repository.createExtent(extentName, getM2Model().getExtent());
        M1MofModel m1Model = new M1MofModel(repository, getM2Model(), persistenceXmiFile, getXmiTypeForXmiKind(xmiKind), m1Extent, extentName);
        m1Models.put(m1Model.getExtentName(), m1Model);
        return m1Model;
    }
    
    public M1MofModel createM1Model(String extentName) {
        return createM1Model(extentName, null, null);
    }
    
    /**
     * Supported adapters: OclHelper
     * 
     * @param <T>
     * @param adapterClass
     * @return
     */
    public <T> T getAdapter(Class<T> adapterClass) {
        if (OclHelper.class == adapterClass) {
            if (oclHelper == null) {
                oclHelper = new OclHelper(this);
            }
            return (T) oclHelper;
        }
        return null;
    }

}
