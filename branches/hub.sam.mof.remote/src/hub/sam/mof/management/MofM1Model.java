package hub.sam.mof.management;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.JDOMException;

import cmof.Package;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

public class MofM1Model extends AbstractMofModel<MofM2Model> implements MofModel {
    
    private Factory factory;

    public MofM1Model(Repository repository, MofM2Model metaModel, String xmiFile, XmiType xmiType, Extent extent, String extentName) {
        super(repository, metaModel, xmiFile, xmiType, extent, extentName);
    }

    @Override
    protected void doSave() throws IOException, MetaModelException, XmiException, JDOMException {
        getXmiType().save(this, this.getMetaModel().getPackage());
    }

    @Override
    protected void doLoad() throws FileNotFoundException, IOException, MetaModelException, XmiException, JDOMException {
        getXmiType().load(this, this.getMetaModel().getPackage());
    }
    
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

}
