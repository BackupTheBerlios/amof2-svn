package hub.sam.models.sdlplus;

import hub.sam.models.common.NamedElement;

import java.util.Arrays;
import java.util.List;

public class TimerActiveExpressionCustom extends TimerActiveExpressionDlg {

    private static List booleanType = Arrays.asList(new String[]{"predefined", "Boolean"});

    @Override
    public NamedElement determineType() {
        NamedElement type = null;

        for (Object aType : ((cmof.reflection.Object)self).getExtent().getObject()) {
            if (aType instanceof ValueDataTypeDefinition) {
                if (((NamedElement)aType).getQualifiedName().equals(booleanType)) {
                    type = (NamedElement)aType;
                }
            }
        }

        return type;
    }
}
