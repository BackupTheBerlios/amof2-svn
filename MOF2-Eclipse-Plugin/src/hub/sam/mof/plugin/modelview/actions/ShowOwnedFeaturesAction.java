package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;

public class ShowOwnedFeaturesAction extends ShowOtherFeaturesActions {

	public ShowOwnedFeaturesAction(ModelView view) {
		super(view, "Show only owned features");
	}

	@Override
	protected boolean isShowing(IShowOtherFeaturesContext context, Object obj) {
		return context.isShowingOwnedFeatures(obj);
	}

	@Override
	protected void switchTo(IShowOtherFeaturesContext context, Object obj) {
		context.switchToOwnedFeatures(obj);
	}

}
