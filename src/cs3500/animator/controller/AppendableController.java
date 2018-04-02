package cs3500.animator.controller;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.ReadingAnimationModel;
import cs3500.animator.view.appendableview.AppendableView;
import cs3500.animator.view.appendableview.AppendableViewCallbacks;

/**
 * A class for handing interactions between AnimationModels and AppendableViews.
 */
public class AppendableController implements AnimationController<AppendableView>,
                                             AppendableViewCallbacks {
  private final Appendable      appendable;
  private final int             tickRate;
  private       AnimationModel  model;

  /**
   * Constructs an AppendableController.
   *
   * @param appendable the Appendable to append to
   * @param tickRate the rate at which the animation ticks, in ticks/second
   */
  public AppendableController(Appendable appendable, int tickRate) {
    this.appendable = appendable;
    this.tickRate   = tickRate;
  }

  @Override
  public void control(AnimationModel model, AppendableView view) {
    this.model = model;
    view.initialize();
    view.view();
  }

  @Override
  public ReadingAnimationModel getModel() {
    return this.model;
  }

  @Override
  public int getTickRate() {
    return this.tickRate;
  }

  @Override
  public Appendable getAppendable() {
    return this.appendable;
  }
}
