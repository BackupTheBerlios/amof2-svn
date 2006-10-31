/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package hub.sam.mof;

import cmof.reflection.*;
import hub.sam.mof.codegeneration.*;
import hub.sam.mof.reflection.*;
import hub.sam.mof.bootstrap.*;

import java.util.*;
import core.abstractions.elements.Element; 

/**
 * A repository manages a set of extents that can contain 
 * models on all meta-modeling levels.
 */
public class Repository {

    private final Map<String, Extent> extents = new HashMap<String, Extent>();

    /**
     * The name of an extent that contains the CMOF model. This extent will be
     * create when this class is instanciated.
     * @see #Repository()
     */
    public static final String CMOF_EXTENT_NAME = BootstrapExtent.CMOF_EXTENT_NAME;

    /**
     * Creates a new repository. The repository will already contain a extent with
     * the CMOF model in it.
     * @see #CMOF_EXTENT_NAME
     */
    public Repository() throws Exception {
        extents.put(CMOF_EXTENT_NAME, cmof.CMOF.createModel());
        //extents.put(CMOF_EXTENT_NAME, Bootstrap.bootstrapMofExtent());
    }

    /**
     * @param name the name of an extent.
     * @return The extent with the given name or Null if no such extent exists in the repository.
     */
    public Extent getExtent(String name) {
        return extents.get(name);
    }

    /**
     * @return The names of all extents in the repository.
     */
    public Collection<String> getExtentNames() {
        return extents.keySet();
    }

    /**
     * Creates an extent for a given meta model.
     * @param name a name for the extent.
     * @return The extent created.
     */
    public Extent createExtent(String name) {
        Extent newextent = new hub.sam.mof.reflection.ExtentImpl(name);
        extents.put(name, newextent);
        return newextent;
    }

    public void deleteExtent(String name) {
        Extent extent = getExtent(name);
        extents.remove(extent);
        ((ExtentImpl)extent).finalize();
        System.gc();        
    }
    /**
     * Gives a factory for the meta-elements in a meta-package. The actual
     * type of the factory depends on the given package.
     * @param forExtent the extent in that the factory will put all elements that it will create
     * @param forPackage the package that contains the meta-elements that the factory will instantiate
     * @return The factory.
     */
    public Factory createFactory(Extent forExtent, cmof.Package forPackage) {
        return hub.sam.mof.reflection.FactoryImpl.createFactory(forExtent, forPackage);
    }

    /**
     * Loads model elements from a XMI file into an extent.
     * @param extent the extent for the elements to load
     * @param metaModel a meta model for the elemnts to load
     * @param xmiFileName the name of a file that contains xmi
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @throws cmofimpl.xmi.BadXmiException
     */
    public void loadXmiIntoExtent(Extent extent, cmof.Package metaModel, String xmiFileName) throws java.io.IOException,
           org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
        hub.sam.mof.xmi.Xmi2Reader.readMofXmi(new java.io.File(xmiFileName), extent, metaModel);
    }  

    /**
     * Loads model elements from a XMI file into an extent. This is only for M2 models created by the Unisys Rose plugin for UML1.x.
     * @param extent the extent for the elements to load
     * @param metaModel a meta model for the elemnts to load
     * @param xmiFileName the name of a file that contains xmi
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @throws cmofimpl.xmi.BadXmiException
     */
    public void loadUnisysXmiIntoExtent(Extent extent, cmof.Package metaModel, String xmiFileName) throws java.io.IOException,
           org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {               
               hub.sam.mof.xmi.Xmi1UnisysUML1Reader.readMofXmi(new java.io.File(xmiFileName), extent, metaModel);
    }

    /**
     * Writes elements to a file as XMI. 
     * @param xmiFileName the name of the file to write
     * @param metamodel is a package containing the metamodel of the extent to be exported.
     * @param model is the extent to be exported. 
     * @throws java.io.IOException
     */
    public void writeExtentToXmi(String xmiFileName, cmof.Package metamodel, Extent model) 
        throws java.io.IOException, hub.sam.mof.instancemodel.MetaModelException, hub.sam.mof.xmi.XmiException, org.jdom.JDOMException {
        hub.sam.mof.xmi.Xmi2Writer.writeMofXmi(new java.io.File(xmiFileName), model, metamodel);
    }

    /**
     * Creates the repository code for a meta model. The repository code will consist of
     * interfaces, implementations, and user-code delegators for all proxy objects, factorys, enumerations. 
     * It also creates a static version of the model.
     * @param extent The extent that contains the meta-model. This extent must be an instance of the CMOF-model.
     * @param path The path to an existing directory in that the generated code will be placed.
     * @throws java.io.IOException
     * @throws GenerationException
     */
    public void generateCode(Extent extent, String path) throws java.io.IOException, GenerationException {
        //new Analyser().analyse(extent);
        
        Collection<cmof.reflection.Object> outermostContainer = new HashSet<cmof.reflection.Object>();        
        for (cmof.reflection.Object obj: extent.objectsOfType(null, true)) {
            outermostContainer.add(obj.getOutermostContainer());
        }
        
        new ResolveJavaCodeClashes().resolveJavaCodeClashes(outermostContainer, 
                (cmof.extension.extensionFactory)createFactory(extent, (cmof.Package)getExtent(CMOF_EXTENT_NAME).
                query("Package:cmof/Package:extension")));
        
        StreamFactory streamFactory = new StreamFactory(path);
        for (cmof.reflection.Object element: extent.objectsOfType(null, true)) {                      
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {                    
                    new PackageGenerator(streamFactory, false).generate(new java.util.Vector<String>(), (cmof.Package)element);    
                }                
            }
        } 
                                    
    }
    
    public void generateStaticModel(Extent extent, String className, String path) throws java.io.IOException, GenerationException {
        String fileName = path + "/" + className.replace(".", "/") + ".java";
        String packageName = null;        
        if (className.lastIndexOf(".") != -1) {
        	 packageName = className.substring(0, className.lastIndexOf("."));	
        	 className = className.substring(className.lastIndexOf(".") + 1, className.length());
        }                
        ExtentImpl.writeStaticModel(fileName, packageName, className, extent);
    }
	
	/**
	 * Generates repository code for a meta-model kept in a XMI file.
	 * @param args a string array of size 2 with the name of a file, containing XMI as the first value and a 
	 * path to a directory, where the generated code will be placed, as 
	 * the second value
	 */
	public static final void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Wrong args");
		} else {
			Repository repository = new Repository();
			cmof.Package m3Model = (cmof.Package) ((ExtentImpl) repository
					.getExtent(CMOF_EXTENT_NAME)).query("Package:CMOF");
			Extent theExtent = repository.createExtent("theExtent");
			repository.loadXmiIntoExtent(theExtent, m3Model, args[0]);
			repository.generateCode(theExtent, args[1]);
		}
	}
}
