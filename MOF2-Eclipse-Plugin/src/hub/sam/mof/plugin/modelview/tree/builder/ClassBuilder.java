package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.ImageImageDescriptor;
import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.OverlayIcon;
import hub.sam.mof.plugin.modelview.actions.IShowOtherFeaturesContext;
import hub.sam.mof.plugin.modelview.tree.IBuilderFactory;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeParent;
import hub.sam.mof.util.ListImpl;
import hub.sam.mof.util.SetImpl;
import hub.sam.util.MultiMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import core.abstractions.namespaces.NamedElement;
import core.abstractions.redefinitions.RedefinableElement;

import cmof.Operation;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;

public class ClassBuilder extends ClassifierBuilder implements IShowOtherFeaturesContext {

	@Override
	public TreeObject create(Object obj, TreeParent parent, IBuilderFactory factory) {
		TreeObject to = super.create(obj, parent, factory);
		switchToOwnedFeatures(to);
		return to;
	}
	
	private void addRedefinition(MultiMap<RedefinableElement, RedefinableElement> redefinitions, ReflectiveCollection<RedefinableElement> keys, NamedElement member) {
		if (member instanceof RedefinableElement) {
			for (RedefinableElement redefined: ((RedefinableElement)member).getRedefinedElement()) {
				redefinitions.put(redefined, (RedefinableElement)member);	
			}			
			keys.add(member);
		}
	}
	
	private void addMemberChildren(NamedElement member, IChildManager mgr, boolean isInherited, boolean isRedefined) {
		TreeObject child = mgr.addChild(member);
				
		Image baseImage = null;
		if (member instanceof Property) {
			baseImage = Images.getDefault().getProperty();
		} else if (member instanceof Operation) {
			baseImage = Images.getDefault().getOperation();
		} else {
			baseImage = child.getImage();
		}
		if (isInherited || isRedefined) {
			Rectangle bounds = baseImage.getBounds();
			ImageDescriptor newImage = null;
			if (isInherited && !isRedefined) {
				newImage = new OverlayIcon(			
						new ImageImageDescriptor(baseImage), 
						new ImageDescriptor[][] {new ImageDescriptor[] {new ImageImageDescriptor(Images.getDefault().getImported_deco())}, null, null, null},
						new Point(bounds.height, bounds.width));
			} else if (!isInherited && isRedefined){
				newImage = new OverlayIcon(			
						new ImageImageDescriptor(baseImage), 
						new ImageDescriptor[][] {null, new ImageDescriptor[] {new ImageImageDescriptor(Images.getDefault().getRedefined_deco())}, null, null},
						new Point(bounds.height, bounds.width));
			} else {
				newImage = new OverlayIcon(			
						new ImageImageDescriptor(baseImage), 
						new ImageDescriptor[][] {new ImageDescriptor[] {new ImageImageDescriptor(Images.getDefault().getImported_deco())}, new ImageDescriptor[] {new ImageImageDescriptor(Images.getDefault().getRedefined_deco())}, null, null},
						new Point(bounds.height, bounds.width));
			}
			child.setImage(Images.getDefault().get(newImage));
		} else {
			child.setImage(baseImage);
		}
		
		if (isInherited || isRedefined) {
			String text = child.getText();
			core.abstractions.namespaces.Namespace ns = member.getNamespace();
			if (ns != null && ns != mgr.getParent().getElement()) {
				child.setText("(from " + ns.getQualifiedName() + ") " + text);
			} else {
				child.setText("(from <unknown>) " + text);
			}	
		}		
	}

	@Override
	public void addChildren(Object obj, IChildManager mgr) {
		TreeObject to = mgr.getParent();
		UmlClass theClass = (UmlClass)obj;
		
		ReflectiveCollection<? extends NamedElement> inheritedMember = null;
		ReflectiveCollection<RedefinableElement> keys = null;

		MultiMap<RedefinableElement, RedefinableElement> redefinitions = null;
		if (to.optionIsSet(SHOW_FINAL_FEATURES) || to.optionIsSet(SHOW_INHERITED_FEATURES)) {
			redefinitions = new MultiMap<RedefinableElement, RedefinableElement>();
			keys = new ListImpl<RedefinableElement>();
			inheritedMember = new SetImpl<NamedElement>();
			inheritedMember.addAll(theClass.getInheritedMember());
			for (NamedElement member: inheritedMember) {
				addRedefinition(redefinitions, keys, member);
			}
			for (NamedElement member: theClass.getOwnedMember()) {
				addRedefinition(redefinitions, keys, member);				
			}
		}
		
		if (to.optionIsSet(SHOW_FINAL_FEATURES)) {
			for (RedefinableElement member: keys) {
				if (redefinitions.get(member).size() == 0) {
					addMemberChildren(member, mgr, inheritedMember.contains(member), false);
				}
			}
		} else {
			for(Property property: theClass.getOwnedAttribute()) {
				mgr.addChild(property);
			}
		
			for(Operation op: theClass.getOwnedOperation()) {
				mgr.addChild(op);
			}
			
			if (to.optionIsSet(SHOW_INHERITED_FEATURES)) {
				loop: for(NamedElement member: theClass.getInheritedMember()) {
					if (member instanceof RedefinableElement) {
						addMemberChildren(member, mgr, true, redefinitions.get((RedefinableElement)member).size() != 0);
					} else {
						addMemberChildren(member, mgr, true, false);
					}
				}
			} 
		}
			
		super.addChildren(obj, mgr);
	}
	
	@Override
	public String getText(Object element) {
		UmlClass theClass = (UmlClass)element;
		String result = theClass.getName();
		if (theClass.isAbstract()) {
			result += ", abstract";
		}
		return result;
	}

	@Override
	public Image getImage(Object element) {
		return Images.getDefault().getClassIcon();
	}
	
	private final static int SHOW_OWNED_FEATURES = 2;
	private final static int SHOW_INHERITED_FEATURES = 3;
	private final static int SHOW_FINAL_FEATURES = 4;
	
	public boolean isShowingFinalFeatures(Object obj) {
		return ((TreeObject)obj).optionIsSet(SHOW_FINAL_FEATURES);
	}

	public boolean isShowingInheritedFeatures(Object obj) {
		return ((TreeObject)obj).optionIsSet(SHOW_INHERITED_FEATURES);
	}

	public boolean isShowingOwnedFeatures(Object obj) {
		return ((TreeObject)obj).optionIsSet(SHOW_OWNED_FEATURES);
	}

	public void switchToFinalFeatures(Object obj) {
		TreeObject to = (TreeObject)obj;
		to.setOption(SHOW_FINAL_FEATURES);
		to.unsetOption(SHOW_INHERITED_FEATURES);
		to.unsetOption(SHOW_OWNED_FEATURES);		
	}

	public void switchToInheritedFeatures(Object obj) {
		TreeObject to = (TreeObject)obj;
		to.unsetOption(SHOW_FINAL_FEATURES);
		to.setOption(SHOW_INHERITED_FEATURES);
		to.unsetOption(SHOW_OWNED_FEATURES);
	}

	public void switchToOwnedFeatures(Object obj) {
		TreeObject to = (TreeObject)obj;
		to.unsetOption(SHOW_FINAL_FEATURES);
		to.unsetOption(SHOW_INHERITED_FEATURES);
		to.setOption(SHOW_OWNED_FEATURES);	
	}
}
