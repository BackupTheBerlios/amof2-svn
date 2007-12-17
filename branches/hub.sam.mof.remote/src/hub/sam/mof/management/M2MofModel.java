package hub.sam.mof.management;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.xmi.CMOFToXmi;
import hub.sam.mof.xmi.XmiException;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.JDOMException;

import cmof.Package;
import cmof.Tag;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

public class M2MofModel extends AbstractMofModel<M3MofModel> implements MofModel {
    
    private Factory factory;
    private final String packageQuery;

    /**
     * Creates a M2 model as instance of a M3 model.
     * 
     * @param repository
     * @param metaModel
     * @param xmiFile
     * @param extent
     * @param extentName
     * @param packageQuery specifies in which CMOF package the model elements reside
     */
    public M2MofModel(Repository repository, M3MofModel metaModel, String xmiFile, XmiType xmiType, Extent extent, String extentName, String packageQuery) {
        super(repository, metaModel, xmiFile, xmiType, extent, extentName);
        this.packageQuery = packageQuery;
    }
    
    /**
     * Returns a factory for creating M2 elements as instances of M3 elements.
     * 
     */
    public Factory getFactory() {
        Package forPackage = getMetaModel().getPackage();
        if (forPackage == null) {
            throw new IllegalStateException("Can not create a factory without a meta-model package.");
        }
        if (factory == null) {
            factory = getFactory(forPackage);
        }
        return factory;
    }
    
    /**
     * Retrieves the CMOF package where the models elements reside.
     * 
     */
    public Package getPackage() {
        if (packageQuery != null) {
            return (Package) getExtent().query(packageQuery);
        }
        return null;
    }
    
    /**
     * Allows to set a prefix for the models CMOF package when the generated code resides in a different
     * Java package (e.g. when CMOF package is uml and Java Package is hub.sam.models.uml, then the prefix
     * should be hub.sam.models). This is only allowed for M2 models.
     * 
     */
    public void setJavaPackagePrefix(String prefix) {
        for (Tag tag: getPackage().getTag()) {
            if (tag.getName().equals(JavaMapping.PackagePrefixTagName)) {
                tag.setValue(prefix);
                return;
            }
        }
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
     * Allows to set the XMI namespace prefix tag for this model (which must be a meta-model). This tag is then used
     * to save meta-model element instances (M1 models) in their own XML namespace. You must specify this tag
     * if you want to save and reload M1 models.
     * 
     */
    public void setXmiNamespacePrefix(String nsPrefix) {
        for (Tag tag: getPackage().getTag()) {
            if (tag.getName().equals(CMOFToXmi.NS_PREFIX_TAG_NAME)) {
                tag.setValue(nsPrefix);
                return;
            }
        }
        Factory factory = getFactory();
        if (factory instanceof cmofFactory) {
            cmofFactory cmofFactory = (cmofFactory) factory;
            Tag nsPrefixTag = cmofFactory.createTag();
            nsPrefixTag.setName(CMOFToXmi.NS_PREFIX_TAG_NAME);
            nsPrefixTag.setValue(nsPrefix);
            getPackage().getTag().add(nsPrefixTag);
        }
    }
    
    @Override
    protected void doSave() throws IOException, MetaModelException, XmiException, JDOMException {
        getXmiType().save(this, this.getMetaModel().getPackage());
    }

    @Override
    protected void doLoad() throws FileNotFoundException, IOException, MetaModelException, XmiException, JDOMException {
        getXmiType().load(this, this.getMetaModel().getPackage());
    }

}
