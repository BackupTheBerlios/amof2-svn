package cmof.extension;


public class extensionFactoryImpl extends hub.sam.mof.reflection.FactoryImpl implements extensionFactory {
    public extensionFactoryImpl(hub.sam.mof.reflection.ExtentImpl extent, cmof.Package path) {
        super(extent, path);
    }

    public cmof.extension.Tag createTag() {
        return (cmof.extension.Tag) create("cmof.extension.Tag");
    }

}

