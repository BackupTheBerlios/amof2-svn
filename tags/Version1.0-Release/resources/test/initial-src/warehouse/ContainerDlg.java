package warehouse;


public class ContainerDlg extends hub.sam.mof.reflection.ObjectDlg implements Container
{
    protected Container self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Container)self;
    }

    public cmof.common.ReflectiveCollection<? extends warehouse.Element> getContent() {
        return (cmof.common.ReflectiveCollection<? extends warehouse.Element>)(java.lang.Object)self.getContent();
    }

    public java.lang.String getIdentifier() {
        return self.getIdentifier();
    }

    public void setIdentifier(java.lang.String value) {
        self.setIdentifier(value);
    }

    public java.lang.String getPosition() {
        return self.getPosition();
    }

    public warehouse.Container getContainer() {
        return (warehouse.Container)(java.lang.Object)self.getContainer();
    }

    public void setContainer(warehouse.Container value) {
        self.setContainer(value);
    }

    public void test()  {
        self.test();
    }

}

