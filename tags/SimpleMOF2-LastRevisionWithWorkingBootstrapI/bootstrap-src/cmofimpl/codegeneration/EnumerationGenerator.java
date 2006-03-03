package cmofimpl.codegeneration;

import cmof.*;
import cmof.common.ReflectiveSequence;

public class EnumerationGenerator extends AbstractGenerator {

    public EnumerationGenerator(StreamFactory streamFactory) {
        super(streamFactory);
    }

    public void generate(java.util.List<String> packageName, Enumeration enumeration) throws GenerationException {
        try {
            String enumerationName = getJavaIdentifier(enumeration);
            init(packageName, enumerationName);
            add("public enum " + enumerationName + " {");
            indent += 2;
            ReflectiveSequence<? extends EnumerationLiteral> literals = enumeration.getOwnedLiteral();
            for (int i = 0; i < literals.size(); i++) {
                add(literals.get(i).getName().toUpperCase() + ((i + 1 < literals.size()) ? "," : ""));
            }
            indent -= 2;
            add("}");
            print(null);            
        } catch (Throwable ex) {
            throw new GenerationException(ex);
        }
    }
}
