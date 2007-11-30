package hub.sam.mof.management;

import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;
import hub.sam.mof.xmi.XmiImportExport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.JDOMException;

import cmof.Package;

public class MagicDrawXmiTypeImpl implements XmiType {
    
    private XmiImportExport diagramInfo;

    public void save(MofModel mofModel, Package metaModelPackage) throws IOException, MetaModelException, XmiException, JDOMException {
        mofModel.getRepository().writeExtentToMagicDrawXmi(mofModel.getXmiFile(), metaModelPackage, mofModel.getExtent(),
                diagramInfo);
    }

    public void load(MofModel mofModel, Package metaModelPackage) throws FileNotFoundException, IOException, MetaModelException, XmiException, JDOMException {
        diagramInfo = mofModel.getRepository().loadMagicDrawXmiIntoExtent(mofModel.getExtent(), metaModelPackage,
                new FileInputStream(mofModel.getXmiFile()));
    }

}
