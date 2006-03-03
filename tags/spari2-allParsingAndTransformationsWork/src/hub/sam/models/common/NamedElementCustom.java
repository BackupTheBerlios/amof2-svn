package hub.sam.models.common;

import cmof.common.ReflectiveSequence;
import hub.sam.mof.util.ListImpl;

public class NamedElementCustom extends NamedElementDlg {

    @Override
    public String getQualifiedName() {
        ReflectiveSequence<String> result = new ListImpl<String>();
        result.add(getName());
        Namespace container = (Namespace)getContainer();
        while (container != null) {
            result.add(0, container.getName());
            container = (Namespace)container.getContainer();
        }
        StringBuffer resultString = new StringBuffer();
        boolean first = true;
        for (String part : result) {
            if (!first) {
                resultString.append(".");
            } else {
                first = false;
            }
            resultString.append(part);
        }
        return resultString.toString();
    }
}
