package hub.sam.mof.java;

import java.io.IOException;

import org.jdom.JDOMException;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.merge.MergeException;
import hub.sam.mof.xmi.XmiException;
import hub.sam.util.AbstractFluxBox;

import cmof.Package;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.common.ReflectiveCollection;
import cmof.reflection.Extent;

import com.sun.javadoc.*;

public class JavaModelGenerationDoclet {
	
	private final Repository repository;
	private final Extent workingExtent;
	private final Package m3Model;
	private final cmofFactory factory;
	private final AbstractFluxBox<ClassDoc, UmlClass, Object> fluxBox;
	
	private JavaModelGenerationDoclet() { 
		repository = Repository.getLocalRepository();
		workingExtent = repository.createExtent("working extent");
		m3Model = (Package)repository.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
		factory = (cmofFactory)repository.createFactory(workingExtent, m3Model);
		
		fluxBox = new AbstractFluxBox<ClassDoc, UmlClass, Object>() {
			@Override
			protected UmlClass createValue(ClassDoc key, Object params) {				
				return factory.createUmlClass();
			}			
		};		
	}
		
	private void forClass(ClassDoc javaClass, Package owningPackage) {
		UmlClass mofClass = factory.createUmlClass();
		if (owningPackage != null) {
			mofClass.setPackage(owningPackage);
		}
		
		mofClass.setName(javaClass.name());
		if (javaClass.isAbstract() || javaClass.isInterface()) {
			mofClass.setIsAbstract(true);
		}
		
		ReflectiveCollection<? extends UmlClass> superClasses = mofClass.getSuperClass();
		superClasses.add(fluxBox.getObject(javaClass.superclass(), null));
		for(ClassDoc superInterface: javaClass.interfaces()) {
			superClasses.add(fluxBox.getObject(superInterface, null));
		}		
	}
	
	private void forPackage(PackageDoc javaPackage, Package owningPackage) {
		Package mofPackage = factory.createPackage();
		if (owningPackage != null) {
			mofPackage.setNestingPackage(owningPackage);
		}
		mofPackage.setName(javaPackage.name());
		for (ClassDoc javaType: javaPackage.allClasses()) {					
			if (javaType.isException()) {
				System.out.println("found a exception " + javaType);
			} else if (javaType.isEnum()) {
				System.out.println("found a enum " + javaType);
			} else if (javaType.isInterface()) {
				forClass(javaType, mofPackage);
			} else if (javaType.isClass()) {
				forClass(javaType, mofPackage);
			} else {
				throw new RuntimeException("Unexpected doc element kind for doclet: " + javaType);
			}
		}
	}
	
	private void writeOutPut() throws IOException, MetaModelException, XmiException, JDOMException {		
		repository.writeExtentToXmi("resources/models/work/JavaGenTest.xml", m3Model, workingExtent);		
	}
	
	public static boolean start(RootDoc root) {
		JavaModelGenerationDoclet doclet = new JavaModelGenerationDoclet();
		for(PackageDoc aPackage: root.specifiedPackages()) {
			doclet.forPackage(aPackage, null);
		}
		try {
			doclet.writeOutPut();
		} catch (IOException e) {
			System.err.println("IO Error: " + e.getMessage());
			return false;
		} catch (Exception e) {
			System.err.println("Unexpected Error: " + e.getMessage());
			e.printStackTrace(System.err);
			return false;
		}
		return true;
	}
}
