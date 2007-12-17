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

import hub.sam.mof.PlugInActivator;
import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.JDOMException;

import cmof.Package;
import cmof.reflection.Extent;
import cmof.reflection.ExtentChangeListener;
import cmof.reflection.Factory;
import cmof.reflection.Object;

abstract class AbstractMofModel<E extends MofModel> implements ExtentChangeListener {

    private final static String clonedXmiSuffix = "_cloned.xml";
    private final boolean isCloned;
    private boolean alive = true;
    
    private final Repository repository;
    private final String xmiFile;
    private final XmiType xmiType;
    private final Extent extent;
    private final String extentName;
    private E metaModel;
    
    public AbstractMofModel(Repository repository, E metaModel, String xmiFile, XmiType xmiType, Extent extent, String extentName) {
        this.repository = repository;
        this.metaModel = metaModel;
        this.xmiType = xmiType;
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
    }
    
    public Repository getRepository() {
        return repository;
    }
    
    protected boolean isClonedXmi(String xmiFile) {
        return xmiFile.indexOf(clonedXmiSuffix) != -1;
    }
    
    protected String getUnclonedXmi(String xmiFile) {
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
       
    public String getXmiFile() {
        return xmiFile;
    }

    public E getMetaModel() {
        return metaModel;
    }
    
    protected XmiType getXmiType() {
        return xmiType;
    }
    
    protected Factory getFactory(Package forPackage) {
        if (forPackage == null) {
            throw new IllegalStateException("Can not create a factory without a meta-model package.");
        }
        String className = hub.sam.mof.javamapping.JavaMapping.mapping.getFullQualifiedFactoryNameForPackage(forPackage);
        try {            
            Class factoryClass = PlugInActivator.class.getClassLoader().loadClass(className);
            if (Factory.class.isAssignableFrom(factoryClass)){
                return (Factory) getExtent().getAdaptor(factoryClass);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
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
            doSave();
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
    
    protected abstract void doSave() throws IOException, MetaModelException, XmiException, JDOMException;
    
    public void load() throws LoadException {
        try {
            doLoad();
        }
        catch (FileNotFoundException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        catch (IOException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        catch (MetaModelException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        catch (XmiException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        catch (JDOMException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
    }
    
    protected abstract void doLoad() throws FileNotFoundException, IOException, MetaModelException, XmiException, JDOMException;

    public void close() {
        if (isAlive()) {
            // close this model first
            extent.removeExtentChangeListener(this);
            alive = false;
            if (getExtentName() != null) {
                repository.deleteExtent(getExtentName());
            }
        }
    }
    
    public void closeAll() {
        if (isAlive()) {
            close();
            // close meta-model
            if (getMetaModel() != null) {
                String extentName =  getMetaModel().getExtentName();
                if (extentName != null && !extentName.equals(Repository.CMOF_EXTENT_NAME)) {
                    getMetaModel().closeAll();
                }
            }
        }
    }
    
    @Deprecated
    public void delete() {
        // TODO physical delete (model file)
    }
    
    /**
     * The MofModel is alive as long as its extent is alive.
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
