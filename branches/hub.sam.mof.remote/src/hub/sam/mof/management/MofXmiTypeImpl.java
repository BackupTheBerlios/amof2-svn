package hub.sam.mof.management;

import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.JDOMException;

import cmof.Package;

public class MofXmiTypeImpl implements XmiType {

    public void save(MofModel mofModel, Package metaModelPackage) throws IOException, MetaModelException, XmiException, JDOMException {
        mofModel.getRepository().writeExtentToXmi(mofModel.getXmiFile(), metaModelPackage, mofModel.getExtent());
    }

    public void load(MofModel mofModel, Package metaModelPackage) throws FileNotFoundException, IOException, MetaModelException, XmiException, JDOMException {
        mofModel.getRepository().loadXmiIntoExtent(mofModel.getExtent(), metaModelPackage, new FileInputStream(mofModel.getXmiFile()));
    }

}
