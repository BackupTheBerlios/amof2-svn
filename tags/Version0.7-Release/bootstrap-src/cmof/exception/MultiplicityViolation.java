package cmof.exception;


/**
 * Denotes a violation of the multiplicity constraint of a slot. It is thrown 
 * whenever the number of values in a slot does not fulfil the 
 * mulitplicity given by the defining property of the slot.
 */
public class MultiplicityViolation extends ModelException {

	private static final long serialVersionUID = 1L;

	private final core.abstractions.instances.Slot src;
	
	public MultiplicityViolation(core.abstractions.instances.Slot src) {
		super("Multiplicity of property " + src.getDefiningFeature() + 
				" on value " + src.getOwner() + " has been violated");
		this.src = src;
	}
	
	/**
	 * @return The slot that causes this vialation.
	 */
	public core.abstractions.instances.Slot getSource() {
		return src;
	}
}
