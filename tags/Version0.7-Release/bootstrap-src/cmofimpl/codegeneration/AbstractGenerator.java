package cmofimpl.codegeneration;

public class AbstractGenerator {
    private CodeGenerator cg = null;    
    private final StreamFactory streamFactory; 
    protected int indent = 0;
    protected cmofimpl.javamapping.JavaMapping javaMapping = new cmofimpl.javamapping.JavaMapping(); 
    public AbstractGenerator(StreamFactory streamFactory) {
        this.streamFactory = streamFactory;
    }
    
    protected void print(Object o) throws Throwable {
        cg.print(o);
    }
    
    protected void add(String code) {
        cg.add(indent, code);
    }
    
    protected void init(java.util.List<String> packageName, String className) throws Throwable {
        cg = new CodeGenerator(streamFactory.createStream(packageName, className, "java"));
        if (packageName.size() != 0) {
            String packageIdentifier = "";           
            for (String singleName: packageName) {
                packageIdentifier += singleName + ".";
            }
            packageIdentifier = packageIdentifier.substring(0, packageIdentifier.length()-1);
            add("package " + packageIdentifier + ";");
            add("");
            print(this);
        }        
    }
    
    protected String getJavaIdentifier(cmof.NamedElement element) {
        return javaMapping.getJavaIdentifier(element);
    }
    
    protected String getFullQualifiedJavaIdentifier(cmof.NamedElement element) {
        return javaMapping.getFullQualifiedJavaIdentifier(element);
    }
}
