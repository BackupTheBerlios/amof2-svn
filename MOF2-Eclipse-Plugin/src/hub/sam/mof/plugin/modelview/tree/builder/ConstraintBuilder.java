package hub.sam.mof.plugin.modelview.tree.builder;

import cmof.Constraint;
import cmof.Element;
import cmof.NamedElement;
import cmof.OpaqueExpression;
import cmof.ValueSpecification;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

public class ConstraintBuilder extends ElementBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr) {
		Constraint constraint = (Constraint)obj;
		
		ValueSpecification text = constraint.getSpecification();
		if (text instanceof OpaqueExpression) {
			TreeObject to = new TreeObject(obj, mgr.getParent());
			to.setText(((OpaqueExpression)text).getBody());
			to.setImage(null);
			mgr.addChild(to);
		}
		for (Element constrainedElement: constraint.getConstrainedElement()) {
			TreeObject to = mgr.addChild(constrainedElement);
			if (constrainedElement instanceof NamedElement)
			to.setText("(from " + ((NamedElement)constrainedElement).getNamespace().getQualifiedName() +") " + to.getText());
		}
		super.addChildren(obj, mgr);
	} 

}
