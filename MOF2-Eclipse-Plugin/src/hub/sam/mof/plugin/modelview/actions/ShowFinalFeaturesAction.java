package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;

public class ShowFinalFeaturesAction extends ShowOtherFeaturesActions {

	public ShowFinalFeaturesAction(ModelView view) {
		super(view, "Show all final features");
	}

	@Override
	protected boolean isShowing(IShowOtherFeaturesContext context, Object obj) {
		return context.isShowingFinalFeatures(obj);
	}

	@Override
	protected void switchTo(IShowOtherFeaturesContext context, Object obj) {
		context.switchToFinalFeatures(obj);
	}

}
