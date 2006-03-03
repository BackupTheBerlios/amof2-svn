package cmof.exception;

public class IllegalArgumentException extends ModelException {
	private final java.lang.Object src;
	
	public IllegalArgumentException(java.lang.Object src) {
		super("Illegal argument " + src);
		this.src = src;
	}

	private static final long serialVersionUID = 1L;

	public java.lang.Object getSource() {
		return src;
	}
}
