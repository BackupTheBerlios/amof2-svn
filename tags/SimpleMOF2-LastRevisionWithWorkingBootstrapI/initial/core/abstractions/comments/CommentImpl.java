package core.abstractions.comments;


public class CommentImpl extends cmofimpl.reflection.ObjectImpl implements Comment
{
    public CommentImpl(hub.sam.mof.instancemodel.ClassInstance instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public CommentImpl(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getAnnotatedElement() {
        java.lang.Object value = get("annotatedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public String getBody() {
        java.lang.Object value = get("body");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setBody(String value) {
        set("body", value);
    }

    public core.abstractions.ownerships.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.ownerships.Element)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.comments.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.comments.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

}

