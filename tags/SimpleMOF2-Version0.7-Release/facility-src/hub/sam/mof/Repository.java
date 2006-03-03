package hub.sam.mof;

import cmof.reflection.*;
import cmofimpl.codegeneration.*;
import cmofimpl.reflection.*;
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
	public static final String CMOF_EXTENT_NAME = "CMOF";
	
    /**
     * Creates a new repository. The repository will already contain a extent with
     * the CMOF model in it.
     * @see #CMOF_EXTENT_NAME
     */
    public Repository() {
        extents.put(CMOF_EXTENT_NAME, cmof.cmofb4.createModel("CMOF"));
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
	 * @param metaModel the meta model that shall be instanciated.
	 * @return The extent created.
	 */
    public Extent createExtent(String name, cmof.Package metaModel) {
        return new cmofimpl.reflection.ExtentImpl(name);
    }
    
	/**
	 * Gives a factory for the meta-elements in a meta-package. The actual
	 * type of the factory depends on the given package.
	 * @param forExtent the extent in that the factory will put all elements that it will create
	 * @param forPackage the package that contains the meta-elements that the factory will instantiate
	 * @return The factory.
	 */
    public Factory createFactory(Extent forExtent, cmof.Package forPackage) {
        return cmofimpl.reflection.FactoryImpl.createFactory((ExtentImpl)forExtent, forPackage);
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
            org.xml.sax.SAXException, cmofimpl.xmi.BadXmiException {
        cmofimpl.xmi.XmiReader reader = new cmofimpl.xmi.XmiReader(extent, metaModel);        
        reader.readXmi(new java.io.FileInputStream(xmiFileName));        
    }  
    
	/**
	 * Writes elements to a file as XMI. 
	 * @param xmiFileName the name of the file to write
	 * @param a collection of elements; those elements and all their components, and their components, etc. will be exported.
	 * @param metaModel the meta-model for the elements to export 
	 * @throws java.io.IOException
	 */
    public void writeExtentToXmi(String xmiFileName, cmof.common.ReflectiveCollection<? extends cmof.reflection.Object> elements, Extent metaModel) 
            throws java.io.IOException {
        cmofimpl.xmi.XmiWriter writer = new cmofimpl.xmi.XmiWriter(metaModel);
        writer.writeXmi(elements);
        writer.toFile(new java.io.FileOutputStream(xmiFileName));
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
        new Analyser().analyse(extent);
        StreamFactory streamFactory = new StreamFactory(path);
        for (Element element: extent.objectsOfType(null, true)) {                      
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {                    
                    new PackageGenerator(streamFactory, false).generate(new java.util.Vector<String>(), (cmof.Package)element);    
                }                
            }
        }            
        //System.out.println("Write the static model");            
        cmofimpl.reflection.ExtentImpl.writeStaticModel(path + "/" + extent.getName() + ".java", null, extent);
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
			Extent theExtent = repository.createExtent("theExtent", m3Model);
			repository.loadXmiIntoExtent(theExtent, m3Model, args[0]);
			repository.generateCode(theExtent, args[1]);
		}
	}
}
