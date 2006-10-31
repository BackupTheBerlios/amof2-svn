package cmofimpl.codegeneration;

public class GenerationException extends Exception {
    public GenerationException(String msg) {
        super(msg);
    }
    public GenerationException(Throwable ex) {
        super(ex);
    }
}
