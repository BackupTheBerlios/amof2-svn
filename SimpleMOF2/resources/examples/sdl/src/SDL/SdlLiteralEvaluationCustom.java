package SDL;

public class SdlLiteralEvaluationCustom extends SdlLiteralEvaluationDlg {
    @Override
    public SdlDataValue getValue() {
        SdlDataType type = (SdlDataType)self.getMetaClassifierSdlLiteralExpression().getLiteral().getOwner();
        String typeName = type.getName();
        if (typeName.equals("Integer")) {
            int intValue = new Integer(self.getMetaClassifierSdlLiteralExpression().getValue());
            IntValue value = (IntValue)type.metaGCreateSdlDataValue(IntValue.class.getName());
            value.setValue(intValue);
            return value;
        } else if (typeName.equals("Boolean")) {
            boolean booleanValue = new Boolean(self.getMetaClassifierSdlLiteralExpression().getValue());
            BooleanValue value = (BooleanValue)type.metaGCreateSdlDataValue(BooleanValue.class.getName());
            value.setValue(booleanValue);
            return value;
        } else {
            // TODO general literals
            return null;
        }
    }
}
