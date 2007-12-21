package hub.sam.mof.test.ocl.model;

public class DiagramCustom extends DiagramDlg {

    @Override
    public Figure addFigure(int x, int y, hub.sam.mof.test.ocl.model.FigureType type) {
        modelFactory factory = (modelFactory) self.getExtent().getAdaptor(modelFactory.class);
        Figure figure = factory.createFigure();
        figure.setX(x);
        figure.setY(y);
        figure.setType(type);
        self.getFigure().add(figure);
        return figure;
    }

    @Override
    public void removeFigure(Figure figure) {
        self.getFigure().remove(figure);
        figure.delete();
    }

    @Override
    public boolean containsFigure(int x, int y, hub.sam.mof.test.ocl.model.FigureType type) {
        for (Figure figure: self.getFigure()) {
            if (figure.getX() == x && figure.getY() == y && figure.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

}
