package cmofimpl.codegeneration;

import cmof.*;

public class PrimitiveTypeGenerator extends AbstractGenerator {
    public PrimitiveTypeGenerator(StreamFactory streamFactory) {
        super(streamFactory);
    }

    public void generate(java.util.List<String> packageName, PrimitiveType type) throws GenerationException {
        try {
            String typeName = getJavaIdentifier(type);
            init(packageName, typeName);
            add("public interface " + typeName + " extends " + PrimitiveType.class.getCanonicalName() + " {");
            
            add("}");
            print(null);            
        } catch (Throwable ex) {
            throw new GenerationException(ex);
        }
    }
}
