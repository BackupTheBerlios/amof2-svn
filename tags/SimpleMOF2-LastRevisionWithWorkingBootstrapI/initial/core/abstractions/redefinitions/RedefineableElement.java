package core.abstractions.redefinitions;

import cmof.common.ReflectiveCollection;

public interface RedefineableElement extends core.abstractions.namespaces.NamedElement {

    public ReflectiveCollection<? extends RedefineableElement> getRedefinedElement();
    
    public ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> getRedefinitionContext();
}
