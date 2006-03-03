package hub.sam.mof.xmi;

import hub.sam.mof.instancemodel.*;

import org.jdom.*;
import org.jdom.input.*;

public class XmiReader {
    private final InstanceModel<XmiClassifier,String,String> extent;
    private Namespace xmiNamespace = null;
    private Namespace xsiNamespace = null;
    private final static String XMI_ROOT_ELEMENT = "XMI";
    private String actualNamespacePrefix = null;
    
    public XmiReader(InstanceModel<XmiClassifier,String,String> extent) {
        this.extent = extent;
    }
    
    public void read(java.io.File file) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        Document document = new SAXBuilder().build(file);                
        Element xmi = document.getRootElement();
        if (!xmi.getName().equals(XMI_ROOT_ELEMENT)) {
            throw new XmiException("Unexpected root element \"" + xmi.getName() + "\"");
        }
        xmiNamespace = xmi.getNamespace();
        for (Object oNs: xmi.getAdditionalNamespaces()) {
            Namespace namespace = (Namespace)oNs;
            if (namespace.getPrefix().equals("xmi")) {
                xmiNamespace = namespace;
            } else if (namespace.getPrefix().equals("xsi")) {
                xsiNamespace = namespace; 
            }
        } 
        if (xsiNamespace == null) {
            xsiNamespace = xmiNamespace; // TODO
        }
        if (xmiNamespace == null) {
            xmiNamespace = Namespace.NO_NAMESPACE;
        }
        if (xsiNamespace == null) {
            xsiNamespace = Namespace.NO_NAMESPACE;
        }
        for (Object oChild: xmi.getChildren()) {
            if (oChild instanceof Element) {
                Element child = (Element)oChild;
                readInstance(child);
            }
        }  
    }
    
    public void readInstance(Element child) throws XmiException, MetaModelException {        
        String id = child.getAttributeValue("id", xmiNamespace);
        actualNamespacePrefix = child.getNamespacePrefix();        
        ClassInstance<XmiClassifier,String,String> instance = extent.createInstance(id, 
                new XmiClassifier(child.getName(), actualNamespacePrefix));        
        readContentForInstance(child, instance);
    }
                
    public void readContentForInstance(Element element, ClassInstance<XmiClassifier,String,String> instance) throws XmiException, MetaModelException {        
        for(Object oAttr: element.getAttributes()) {
            Attribute attr = (Attribute)oAttr;
            String namespace = attr.getNamespacePrefix();
            if (namespace.equals("")) {
                String name = attr.getName();
                String value = attr.getValue();
                instance.addValue(name, extent.createUnspecifiedValue(value));
            }
        }
        for (Object oChild: element.getChildren()) {
            Element child = (Element)oChild;
            String name = child.getName();
            String namespace = child.getNamespacePrefix();
            if (!namespace.equals("")) {
                throw new XmiException("Unexpected namespace prefix \"" + child.getNamespacePrefix() + 
                        "\" for element \"" + name + "\"");
            }
            String id = child.getAttributeValue("id", xmiNamespace);             
            String type = child.getAttributeValue("type", xsiNamespace);
            String idref = child.getAttributeValue("idref", xmiNamespace);
            if (child.getAttributes().size() + child.getChildren().size() == 0) {
                instance.addValue(child.getName(), extent.createUnspecifiedValue(child.getText()));
            } else if (idref == null) {         
                InstanceValue<XmiClassifier,String,String> value = null;
                XmiClassifier elementForChild;
                if (type == null) {
                    elementForChild = new XmiClassifier(instance.getClassifier(), name);
                } else {
                    elementForChild = new XmiClassifier(type, actualNamespacePrefix);
                }                
                InstanceValue<XmiClassifier,String,String> instanceValue = 
                        extent.createInstanceValue(extent.createInstance(id, elementForChild));
                value = instanceValue;                
                readContentForInstance(child, instanceValue.getInstance());
                instance.addValue(name, value);
                value.getInstance().setComposite(instance);
            } else {                
                for(ReferenceValue<XmiClassifier,String,String> ref: extent.createReferences(idref)) {
                    instance.addValue(name, ref);
                }
            }                     
        }
    }        
}
