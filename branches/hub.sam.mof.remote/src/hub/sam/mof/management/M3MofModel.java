package hub.sam.mof.management;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.JDOMException;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;
import cmof.Package;
import cmof.reflection.Extent;

public class M3MofModel extends AbstractMofModel<M3MofModel> implements MofModel {
    
    private final Package cmofPackage;
    
    public M3MofModel(Repository repository) {
        super(repository, null, null, null, repository.getExtent(Repository.CMOF_EXTENT_NAME), Repository.CMOF_EXTENT_NAME);
        cmofPackage = (Package) getExtent().query("Package:cmof");
    }
    
    public Package getPackage() {
        return cmofPackage;
    }

    @Override
    public Extent getExtent() {
        return getRepository().getExtent(Repository.CMOF_EXTENT_NAME);
    }

    @Override
    public String getExtentName() {
        return Repository.CMOF_EXTENT_NAME;
    }

    @Override
    protected void doSave() throws IOException, MetaModelException, XmiException, JDOMException {
        // empty
    }

    @Override
    protected void doLoad() throws FileNotFoundException, IOException, MetaModelException, XmiException, JDOMException {
        // empty
    }

}
