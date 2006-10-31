package cmofimpl.reflection;

public class ArgumentImpl implements cmof.reflection.Argument {
	
	private final String name;
	private final Object value;
	public ArgumentImpl(String name, java.lang.Object value) {
		this.name = name;
		this.value = value;
	}
	
    public String getName() {
        return name;
    }

    public java.lang.Object getValue() {
        return value;
    }    
	
	public boolean equals(java.lang.Object other) {
		if (other instanceof ArgumentImpl) {
			ArgumentImpl otherArgument = (ArgumentImpl)other;
			return (name.equals(otherArgument.name) && value.equals(otherArgument.value));
		} else {
			return false;
		}
	}
}
