package cmofimpl.bootstrap;


public class B1PrimitiveType extends B1Class implements cmof.PrimitiveType {

    public B1PrimitiveType(String name) {
        super(name);
        init(new cmof.Property[]{});
    }

}
