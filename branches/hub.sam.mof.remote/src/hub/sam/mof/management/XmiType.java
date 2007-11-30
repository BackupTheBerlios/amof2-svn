package hub.sam.mof.management;

import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.JDOMException;

import cmof.Package;

public interface XmiType {
    void save(MofModel mofModel, Package metaModelPackage) throws IOException, MetaModelException, XmiException, JDOMException;
    void load(MofModel mofModel, Package metaModelPackage) throws FileNotFoundException, IOException, MetaModelException, XmiException, JDOMException;
}
