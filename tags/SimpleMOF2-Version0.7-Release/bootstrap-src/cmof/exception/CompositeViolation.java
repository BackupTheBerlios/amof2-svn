package cmof.exception;

import cmofimpl.instancemodel.ElementInstance;

/**
 * This exception denotes the violation of a composite association. It is raised 
 * when an instance is assigned as a component to multiple composites.
 */
public class CompositeViolation extends ModelException {
	private static final long serialVersionUID = 1L;
	private final ElementInstance src;
	public CompositeViolation(ElementInstance src) {
		super("The instance " + src + " caused a composite violation.");
		this.src = src;
	}
	/**
	 * @return The component that caused this violation.
	 */
	public ElementInstance getSource() {
		return src;
	}
}
