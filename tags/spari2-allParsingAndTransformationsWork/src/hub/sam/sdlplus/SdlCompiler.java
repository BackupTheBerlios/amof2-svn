package hub.sam.sdlplus;

import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.sdlplus.parser.ParseException;
import hub.sam.sdlplus.parser.SdlplusParser;
import hub.sam.sdlplus.semantics.SemanticAnalysis;

import java.io.InputStream;

/**
 * The main compiler class. It is responsible for the repository, command line arguments, calls parser and semantic analysis,
 * and provides unique string representations for model elements, used for consistent error reporting.
 */
public class SdlCompiler {
    private static SdlCompiler theCompiler = new SdlCompiler();

    /**
     * Returns a default compiler instance.
     */
    public static SdlCompiler getCompiler() {
        return theCompiler;
    }

    private Extent sdlMetaExtent = null;
    private Extent sdlModelExtent = null;
    private Repository repository = null;
    private cmof.Package sdlPlusPackage = null;

    /**
     * Initializes the repository. It creates all nessasary extends for all needed meta-models and models.
     * These are two M2 extents and two M1 extents; one for the normal sdl model the other for the "sdl with concrete
     * syntax extensions.
     */
    private void initializeRepository() throws Exception {
        repository = Repository.getLocalRepository();
        cmof.Package cmofPackage = (cmof.Package)repository.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
        sdlModelExtent = repository.createExtent("sdl-model-extent");
        sdlMetaExtent = repository.createExtent("sdl-neta-extent");

        repository.loadXmiIntoExtent(sdlMetaExtent, cmofPackage, "resources/models/sdlr-wcse-mof2.xml");
        sdlPlusPackage = (cmof.Package)sdlMetaExtent.query("Package:SdlPlus");
    }

    /**
     * A utility function that adds values for line and column two a model element. This can be used to attach the location
     * of an item in the source file to a corresponding model element. The method can be called for any RefObject that as a
     * single value Integer attribute of name "line" and "column".
     */
    public void addContextM1(cmof.reflection.Object aObject, int line, int column) {
        aObject.set("line", line);
        aObject.set("column", column);
    }

    /**
     * A utility function that returns a descriptive string about the position of the given model element in the source
     * file.
     */
    public String getContextM1(cmof.reflection.Object aObject) {
        return "[" + aObject.get("line") + "," + aObject.get("column") + "]";
    }

    /**
     * A utility function that returns a descriptive string about the given model element in the source
     * file. The string includes the elements type, name and position in the source file.
     */
    public String getContextInformation(cmof.reflection.Object context) {
        return context.getMetaClass() + ":" + context.toString() + " at " + SdlCompiler.getCompiler().getContextM1(context);
    }

    /**
     * The main function. It analyses the command line arguments, initializes the input stream, initializes the repository,
     * calls the parser and semantic analysis, finally it writes the analysed model.
     */
    public void compile(String[] args) {
        InputStream in;
        // analyse the given arguments
        if (args.length == 0) {
            System.out.println("Reading from standard input.");
            in = System.in;
        } else if (args.length == 1) {
            System.out.println("Reading from file " + args[0] + ".");
            try {
                in = new java.io.FileInputStream(args[0]);
            } catch (java.io.FileNotFoundException e) {
                System.out.println("File " + args[0] + " not found.");
                return;
            }
        } else {
            System.out.println("Wrong usage.");
            return;
        }

        try {
            System.out.println("Initialiasing repository.");
            initializeRepository();

            SdlplusParser parser = new SdlplusParser(in);
            System.out.println("Start parsing.");
            parser.parse(repository, sdlMetaExtent, sdlModelExtent);

            System.out.println("Start semantic analysis.");
            boolean success = new SemanticAnalysis().doSemanticAnalysis(repository, sdlMetaExtent, sdlModelExtent);
            if (success) {
                System.out.println("Write output.");
                repository.writeExtentToXmi("resources/test-files/test-out.xml", sdlPlusPackage, sdlModelExtent);
            }
        } catch (ParseException e) {
            System.out.println("Encountered errors during parse:");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Gets the default compiler instance and runs it.
     */
    public static void main(String[] args) {
        SdlCompiler.getCompiler().compile(args);
    }
}
