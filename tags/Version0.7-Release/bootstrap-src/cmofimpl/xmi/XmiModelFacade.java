package cmofimpl.xmi;

import java.util.*;
import cmof.*;
import cmof.reflection.*;
import cmof.extension.*;
import cmofimpl.util.*;
import org.jdom.Namespace;

public class XmiModelFacade {

    private Map<String, Map<String, UmlClass>> namespaces = new HashMap<String, Map<String, UmlClass>>();
    private Map<Element, Namespace > xmiNamespaces = new HashMap<Element, Namespace>();
    private cmof.common.ReflectiveCollection<? extends cmof.reflection.Object> allElements = new SetImpl<cmof.reflection.Object>();
    
    public XmiModelFacade(Extent model) {        
        allElements.addAll(model.objectsOfType(null, false));
        initialize();
    }
    
    public XmiModelFacade(cmof.Package model) {
        collectAllElements(model);        
        initialize();
    }
    
    private void collectAllElements(cmof.Package thePackage) {
        for (Element e: thePackage.getOwnedElement()) {            
            allElements.add(e);
            if (e instanceof PackageImport) {
                PackageImport packageImport = (PackageImport)e;
                collectAllElements(packageImport.getImportedPackage());
            }
        }
    }
    
    private void initialize() {
        for (cmof.reflection.Object o: allElements) {
            if (o instanceof Tag) {
                Tag tag = (Tag)o;
                if (tag.getName().equals("org.omg.xmi.nsPrefix")) {
                    String prefix = tag.getValue();
                    for (Element taggedElement: tag.getElement()) {
                        xmiNamespaces.put(taggedElement, getNamespace(prefix));
                    }
                }
            }
        }
        
        for (cmof.reflection.Object o: allElements) {
            if (o instanceof UmlClass) {
                UmlClass umlClass = (UmlClass)o;
                if (!umlClass.isAbstract()) {
                    // find nsPrefix for class
                    Namespace xmiNamespace = null;
                    String prefix = null;
                    try {
                        for (cmof.Namespace ns = umlClass; ns != null && xmiNamespace == null; ns = ns.getNamespace()) {
                            xmiNamespace = xmiNamespaces.get(ns);
                        }
                    } catch (Exception e) {
                        // this does not work in bootstrapping
                    }
                    if (xmiNamespace == null) {
                        prefix = "";
                    } else {
                        prefix = xmiNamespace.getPrefix();
                    }
                    Map<String, UmlClass> classForNames = namespaces.get(prefix);
                    if (classForNames == null) {
                        classForNames = new HashMap<String, UmlClass>();
                        namespaces.put(prefix, classForNames);
                    }
                    classForNames.put(umlClass.getName(), umlClass);    
                }
            }
        }        
    }
               
    public UmlClass getClassForTagName(String xmiName, String ns) {        
        Map<String, UmlClass> classForName = namespaces.get(ns);
        if (classForName == null) {
            return null;
        } else {
            if (classForName.get(xmiName) == null) {
                if (ns.equals("")) {
                    // look in all namespaces if ns not specified
                    int foundCount = 0;
                    UmlClass result = null;
                    for (String otherNs: namespaces.keySet()) {
                        classForName = namespaces.get(otherNs);
                        if (classForName.get(xmiName) != null) {
                            foundCount++; result = classForName.get(xmiName);
                        }
                    }
                    if (foundCount != 1) {
                        return null;
                    } else {
                        return result;
                    }
                } else {
                    return null;
                }
            } else {
                return classForName.get(xmiName);
            }
        }
    }
    
    public String getTagNameForMetaClass(UmlClass metaClass) {
        
        Namespace xmiNamespace = null;
        String prefix = null;
        try {
            for (cmof.Namespace ns = metaClass; ns != null && xmiNamespace == null; ns = ns.getNamespace()) {
                xmiNamespace = xmiNamespaces.get(ns);
            }
        } catch (Exception e) {
            // this does not work in bootstrapping
        }
        if (xmiNamespace == null) {            
            return getXmiNameForMetaClass(metaClass);
        } else {
            return xmiNamespace.getPrefix() + ":" + getXmiNameForMetaClass(metaClass);
        }         
    }
    
    public String getXmiNameForMetaClass(UmlClass metaClass) { 
        return metaClass.getName(); 
    }
    
    public Namespace getXmiNamespaceForMetaClass(UmlClass metaClass) {
        return xmiNamespaces.get(metaClass);
    }
    
    public Collection<Namespace> getNamespaces() {
        return new HashSet<Namespace>(xmiNamespaces.values());
    }
    
    private org.jdom.Namespace getNamespace(String prefix) {
        return org.jdom.Namespace.getNamespace(prefix, "http://www.abcdefguuh.de/URL");
    }
}
