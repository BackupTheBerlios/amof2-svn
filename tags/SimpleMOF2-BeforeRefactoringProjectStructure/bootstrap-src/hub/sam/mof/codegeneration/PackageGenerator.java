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

package hub.sam.mof.codegeneration;

import cmof.*;
import hub.sam.mof.reflection.*;
import java.util.*;

public class PackageGenerator extends AbstractGenerator {
    private final StreamFactory streamFactory;
    private final boolean interfacesOnly;    
   
    public PackageGenerator(StreamFactory streamFactory, boolean interfacesOnly) {
        super(streamFactory);
        this.streamFactory = streamFactory;
        this.interfacesOnly = interfacesOnly;
    }
    
    class FactoryWrapper {
        final List<String> packageName;
        final cmof.Package thePackage;
        FactoryWrapper(List<String> packageName, cmof.Package thePackage) {
            this.packageName = packageName;
            this.thePackage = thePackage;
        }
        public String getJavaPackage() {
            StringBuffer result = new StringBuffer();            
            for (String singlePackageName: packageName) {                
                result.append(singlePackageName);
                result.append('.');
            }
            result.append(hub.sam.mof.javamapping.JavaMapping.mapping.getJavaIdentifierForName(thePackage.getName()));
            return result.toString();
        }
        public String getJavaFactory() {
            return javaMapping.getFactoryNameForPackage(thePackage);
        }
        public String getJavaFactoryImpl() {
            return javaMapping.getImplFactoryNameForPackage(thePackage);
        }
    }
    
    public void generate(final List<String> packageName, cmof.Package thePackage) throws GenerationException {
        System.out.println("Generate code for package " + thePackage.getQualifiedName());
        try {            
            List<String>myPackageName = new Vector<String>(packageName);
            myPackageName.add(hub.sam.mof.javamapping.JavaMapping.mapping.getJavaIdentifierForName(thePackage.getName()));
                        
            FactoryWrapper wrapper = new FactoryWrapper(packageName, thePackage);
            init(myPackageName, wrapper.getJavaFactory());            
            add("public interface $javaFactory extends " + cmof.reflection.Factory.class.getCanonicalName() + " {");            
            print(wrapper);
            
            AbstractGenerator factoryImplGenerator = null;
            if (!interfacesOnly) {
                factoryImplGenerator = new AbstractGenerator(streamFactory);
                factoryImplGenerator.init(myPackageName, wrapper.getJavaFactoryImpl());
                factoryImplGenerator.add("public class $javaFactoryImpl extends " + FactoryImpl.class.getCanonicalName() + " implements $javaFactory {");
                factoryImplGenerator.add("    public $javaFactoryImpl(" + ExtentImpl.class.getCanonicalName() + " extent, " + cmof.Package.class.getCanonicalName() + " path) {");
                factoryImplGenerator.add("        super(extent, path);");
                factoryImplGenerator.add("    }");
                factoryImplGenerator.print(wrapper);
            }
                                                
            for (Type ownedType: thePackage.getOwnedType()) {
                if (ownedType instanceof UmlClass) {                    
                    UmlClass umlClass = (UmlClass)ownedType;
                    if (!umlClass.isAbstract()) {
                        add("    public " + getFullQualifiedJavaIdentifier(umlClass) + " create" + getJavaIdentifier(umlClass) + "();");
                        print(null);
                        if (!interfacesOnly) {
                            factoryImplGenerator.add("    public " + getFullQualifiedJavaIdentifier(umlClass) + " create" + getJavaIdentifier(umlClass) + "() {");
                            factoryImplGenerator.add("        return (" + getFullQualifiedJavaIdentifier(umlClass) + ") create(\"" + umlClass.getQualifiedName() + "\");");
                            factoryImplGenerator.add("    }");
                            factoryImplGenerator.print(null);
                        }
                    }
                    new ObjectProxyInterfaceGenerator(streamFactory).generate(myPackageName, umlClass);
                    if (!interfacesOnly) {
                        new ObjectProxyImplementationGenerator(streamFactory, "Dlg").generate(myPackageName, umlClass);
                    }
                    if (!umlClass.isAbstract() && !interfacesOnly) {
                        new ObjectProxyImplementationGenerator(streamFactory, "Impl").generate(myPackageName, umlClass);
                    }
                } else if (ownedType instanceof cmof.Enumeration) {
                    new EnumerationGenerator(streamFactory).generate(myPackageName, (cmof.Enumeration)ownedType);
                } else if (ownedType instanceof PrimitiveType) {
                    new PrimitiveTypeGenerator(streamFactory).generate(myPackageName, (cmof.PrimitiveType)ownedType);
                } else if (ownedType instanceof cmof.Association) {
                    // TODO
                } else {
                    throw new RuntimeException("not implemented");
                }
            }
            for (cmof.Package nestedPackage: thePackage.getNestedPackage()) {
                new PackageGenerator(streamFactory, interfacesOnly).generate(myPackageName, nestedPackage);
            }
            if (!interfacesOnly) {
                factoryImplGenerator.add("}");
                factoryImplGenerator.print(wrapper);                
            }
            add("}");
            print(wrapper);
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
            throw new GenerationException(ex);
        }
    }    
}
