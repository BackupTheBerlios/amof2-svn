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

import java.io.IOException;

import org.jdom.JDOMException;

import hub.sam.mof.PlugInActivator;
import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.xmi.CMOFToXmi;
import hub.sam.mof.xmi.XmiException;
import cmof.Package;
import cmof.Tag;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import cmof.reflection.ExtentChangeListener;
import cmof.reflection.Factory;
import cmof.reflection.Object;

/**
 * Keeps all information of a MOF model together in one place:
 * repository, meta-model, xmi file, extent, extent name, factory, model package (optional)
 * 
 * This allows easy access to basic functions:
 * - retrieving information (like extent, meta model, ...)
 * - saving (to specified xmi file)
 * - getting an appropriate factory for creating instances of meta-model elements in the model extent
 * 
 */
public class MofModel implements ExtentChangeListener {

    private final static String clonedXmiSuffix = "_cloned.xml";
    private final boolean isCloned;
    private boolean alive = true;
    
    protected final Repository repository;
    private final String xmiFile;
    private final Extent extent;
    private final String extentName;
    private Factory factory;
    private Package modelPackage;
    private MofModel metaModel;
    
    /**
     * Constructor for M2 or M1-models.
     * 
     * @param repository
     * @param metaModel
     * @param xmiFile
     * @param extent
     * @param modelPackage
     * @param cmofPackage
     */
    public MofModel(Repository repository, MofModel metaModel, String xmiFile, Extent extent, String extentName, Package modelPackage) {
        this.repository = repository;
        this.metaModel = metaModel;
        if (xmiFile != null && isClonedXmi(xmiFile)) {
            this.xmiFile = getUnclonedXmi(xmiFile);
            this.isCloned = true;
        }
        else {
            this.xmiFile = xmiFile;
            this.isCloned = false;
        }
        this.extent = extent;
        extent.addExtentChangeListener(this);
        this.extentName = extentName;
        this.modelPackage = modelPackage;
    }

    /**
     * Constructor for M3-models where the meta-model is the model itself.
     * 
     * @param repository
     * @param xmiFile
     * @param extent
     * @param extentName
     * @param modelPackage
     */
    public MofModel(Repository repository, String xmiFile, Extent extent, String extentName, Package modelPackage) {
        this(repository, null, xmiFile, extent, extentName, modelPackage);
    }

    public boolean isClonedXmi(String xmiFile) {
        return xmiFile.indexOf(clonedXmiSuffix) != -1;
    }
    
    public String getUnclonedXmi(String xmiFile) {
        return xmiFile.substring(0, xmiFile.length() - clonedXmiSuffix.length());
    }
    
    public static String getClonedXmi(String xmiFile) {
        return xmiFile + clonedXmiSuffix;
    }

    public Extent getExtent() {
        return extent;
    }
    
    public String getExtentName() {
        return extentName;
    }
       
    public Factory getFactory() {
        Package forPackage = getMetaModel().getPackage();
        if (forPackage == null) {
            throw new IllegalStateException("Can not create a factory without a meta-model package.");
        }
        if (factory == null) {
            String className = hub.sam.mof.javamapping.JavaMapping.mapping.getFullQualifiedFactoryNameForPackage(forPackage);
            try {            
                Class factoryClass = PlugInActivator.class.getClassLoader().loadClass(className);
                if (Factory.class.isAssignableFrom(factoryClass)){
                    factory = (Factory) extent.getAdaptor(factoryClass);
                    return factory;
                }
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return factory;
    }
   
    public String getXmiFile() {
        return xmiFile;
    }

    /**
     * Accessing the package is only allowed if the meta-model is CMOF.
     * 
     * @return
     */
    public Package getPackage() {
        return modelPackage;
    }
    
    /**
     * Adding a prefix to the package is only allowed if the meta-model is CMOF.
     * 
     * @return
     */
    public void addJavaPackagePrefix(String prefix) {
        Factory factory = getFactory();
        if (factory instanceof cmofFactory) {
            cmofFactory cmofFactory = (cmofFactory) factory;
            Tag prefixTag = cmofFactory.createTag();
            prefixTag.setName(JavaMapping.PackagePrefixTagName);
            prefixTag.setValue(prefix);
            getPackage().getTag().add(prefixTag);
        }
    }
    
    /**
     * Adding a prefix to the package is only allowed if the meta-model is CMOF.
     * 
     * @return
     */
    public void addNsPrefix(String nsPrefix) {
        Factory factory = getFactory();
        if (factory instanceof cmofFactory) {
            cmofFactory cmofFactory = (cmofFactory) factory;
            Tag nsPrefixTag = cmofFactory.createTag();
            nsPrefixTag.setName(CMOFToXmi.NS_PREFIX_TAG_NAME);
            nsPrefixTag.setValue(nsPrefix);
            getPackage().getTag().add(nsPrefixTag);
        }
    }

    public MofModel getMetaModel() {
        return metaModel;
    }
    
    public void save() throws SaveException {
        if (isCloned) {
            throw new SaveException("A cloned XMI cannot be saved.");
        }
        
        String xmiFile = getXmiFile();
        if (xmiFile == null) {
            throw new SaveException("XMI file not specified.");
        }
        
        try {
            repository.writeExtentToXmi(xmiFile, getMetaModel().getPackage(), getExtent());
        }
        catch (IOException e) {
            throw new SaveException("xmi file " + xmiFile, e);
        }
        catch (MetaModelException e) {
            throw new SaveException("xmi file " + xmiFile, e);
        }
        catch (XmiException e) {
            throw new SaveException("xmi file " + xmiFile, e);
        }
        catch (JDOMException e) {
            throw new SaveException("xmi file " + xmiFile, e);
        }
    }
    
    public void close() {
        if (isAlive()) {
            // close this model first
            extent.removeExtentChangeListener(this);
            alive = false;
            if (getExtentName() != null) {
                repository.deleteExtent(getExtentName());
            }
            // close meta-model
            if (getMetaModel() != null) {
                String extentName =  getMetaModel().getExtentName();
                if (extentName != null && !extentName.equals(Repository.CMOF_EXTENT_NAME)) {
                    getMetaModel().close();
                }
            }
        }
    }
    
    @Deprecated
    public void delete() {
        // TODO physical delete (model file)
    }
    
    /**
     * The MofModel is valid as long as its extent is alive.
     * 
     * @return
     */
    public boolean isAlive() {
        return alive;
    }

    public void extendAboutToBeRemoved() {
        extent.removeExtentChangeListener(this);
        alive = false;
    }

    public void newObject(Object newObject) {
    }

    public void removedObject(Object oldObject) {
    }
    
}
