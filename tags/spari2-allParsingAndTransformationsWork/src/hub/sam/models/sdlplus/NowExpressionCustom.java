package hub.sam.models.sdlplus;

import hub.sam.models.common.NamedElement;

public class NowExpressionCustom extends NowExpressionDlg {

    public static final String timeType = "predefined.Time";

    @Override
    public NamedElement determineType() {
        NamedElement type = null;
        for (Object aType : ((cmof.reflection.Object)self).getExtent().getObject()) {
            if (aType instanceof ValueDataTypeDefinition) {
                if (((NamedElement)aType).getQualifiedName().equals(timeType)) {
                    type = (NamedElement)aType;
                }
            }
        }
        return type;
    }
}
