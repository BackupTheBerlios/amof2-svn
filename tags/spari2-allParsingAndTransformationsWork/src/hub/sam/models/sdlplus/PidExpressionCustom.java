package hub.sam.models.sdlplus;

import hub.sam.models.common.NamedElement;

import java.util.Arrays;
import java.util.List;

public class PidExpressionCustom extends PidExpressionDlg {

    private static List pidType = Arrays.asList(new String[]{"predefined", "Pid"});

    @Override
    public NamedElement determineType() {
        NamedElement type = null;

        for (Object aType : ((cmof.reflection.Object)self).getExtent().getObject()) {
            if (aType instanceof ValueDataTypeDefinition) {
                if (((NamedElement)aType).getQualifiedName().equals(pidType)) {
                    type = (NamedElement)aType;
                }
            }
        }

        return type;
    }
}
