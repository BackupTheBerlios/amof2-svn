package cmof.exception;

public abstract class ModelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ModelException(String msg) {
		super(msg);
	}
}
