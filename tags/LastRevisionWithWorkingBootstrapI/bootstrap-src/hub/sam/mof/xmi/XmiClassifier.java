package hub.sam.mof.xmi;

public class XmiClassifier {
    private String name = null;
    private String namespacePrefix = null;
    private XmiClassifier contextClass = null;
    private String contextProperty = null;
    private final boolean isDefinedByContext;
    
    public XmiClassifier(String name, String namespacePrefix) {
        this.name = name;
        this.namespacePrefix = namespacePrefix;
        isDefinedByContext = false;
    }
    
    public XmiClassifier(XmiClassifier contextClass, String contextProperty) {
        this.contextClass = contextClass;
        this.contextProperty = contextProperty;
        isDefinedByContext = true;
    }

    public String getName() {
        return name;
    }

    public String getNamespacePrefix() {
        return namespacePrefix;
    }
    
    public String toString() {
        if (isDefinedByContext()) {
            return "(classified by context: " + contextClass + "::" + contextProperty + ")";
        } else {
            if (namespacePrefix != null) {
                return namespacePrefix + "." + name;
            } else {
                return name;
            }        
        }
    }
    
    public XmiClassifier getContextClass() {
        return contextClass;
    }
    
    public String getContextProperty() {
        return contextProperty;
    }
    
    public boolean isDefinedByContext() {
        return isDefinedByContext;
    }
}
