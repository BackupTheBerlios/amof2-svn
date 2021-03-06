package warehouse;


/**
 * <b>Item</b>, superClass = {warehouse.Element}
 */
public interface Item extends warehouse.Element
{

    /**
     * <b>weight</b>, multiplicity=(1,1)
     */
    public int getWeight();

    public void setWeight(int value);

    /**
     * <b>visibility</b>, multiplicity=(1,1)
     */
    public warehouse.VisibilityKind getVisibility();

    public void setVisibility(warehouse.VisibilityKind value);

}

