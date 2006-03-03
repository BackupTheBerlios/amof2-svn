package cmof.exception;

import cmofimpl.instancemodel.MofStructureSlot;

/**
 * Denotes a violation of the multiplicity constraint of a slot. It is thrown 
 * whenever the number of values in a slot does not fulfil the 
 * mulitplicity given by the defining property of the slot.
 */
public class MultiplicityViolation extends ModelException {

	private static final long serialVersionUID = 1L;

	private final MofStructureSlot src;
	
	public MultiplicityViolation(MofStructureSlot src) {
		super("Multiplicity of property " + src.getDefiningFeature() + 
				" on value " + src.getOwningInstance() + " has been violated");
		this.src = src;
	}
	
	/**
	 * @return The slot that causes this vialation.
	 */
	public MofStructureSlot getSource() {
		return src;
	}
}
