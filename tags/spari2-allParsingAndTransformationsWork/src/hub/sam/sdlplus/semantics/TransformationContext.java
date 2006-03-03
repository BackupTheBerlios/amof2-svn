package hub.sam.sdlplus.semantics;

import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.mopatree.Mof2TreeNode;
import hub.sam.mof.util.MofCopyFluxBox;
import hub.sam.mopa.trees.TreeNode;
import hub.sam.util.AbstractFluxBox;

public class TransformationContext {

    private MofCopyFluxBox fluxBox = null;
    private Iterable<TreeNode> tree = null;

    public TransformationContext(Repository repository, Extent oExtent, Extent iExtent) {
        fluxBox = new MofCopyFluxBox(repository, oExtent, iExtent);
        tree = Mof2TreeNode.createNodes(oExtent.outermostComposites());
    }

    public AbstractFluxBox getFluxBox() {
        return fluxBox;
    }

    public Iterable<TreeNode> getModelTree() {
        return tree;
    }
}
