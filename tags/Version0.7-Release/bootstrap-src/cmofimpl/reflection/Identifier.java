package cmofimpl.reflection;

public class Identifier {
    private static int idSource = 0;
    private final Integer id;
    
    public static Identifier getUniqueIdentifier() {
        return new Identifier();
    }
    
    public Identifier(int id) {
        this.id = id;
        if (idSource < id) {
            idSource = id + 1;
        }
    }
    private Identifier() {
        this.id = idSource++;
    }
    public String toString() {
        return id.toString();
    }
    public int hashCode() {
        return id.hashCode();
    }
    public boolean equals(Object id) {
        if (id instanceof Identifier) {
            return ((Identifier)id).id.equals(this.id);
        } else {
            return false;
        }
    }
}
