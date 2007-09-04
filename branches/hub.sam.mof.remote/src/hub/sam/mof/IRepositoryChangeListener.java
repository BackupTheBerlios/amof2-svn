package hub.sam.mof;

import cmof.reflection.Extent;

public interface IRepositoryChangeListener {
    void extendAdded(Extent extent);
    void extendAboutToBeRemoved(String name, Extent extent);
}
