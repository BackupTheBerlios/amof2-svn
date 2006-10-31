package cmofimpl.reflection;

public class NamedElement extends ObjectImpl implements cmof.reflection.NamedElement {
    
    private final String name;
    
    protected NamedElement(String name) {
        super(null, null);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        throw new IllegalArgumentException();
    }
}
