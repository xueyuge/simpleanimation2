package cs3500.animator.view.appendableview;

import cs3500.animator.model.ReadingAnimationModel;

/**
 * Describes the callback functions needed for AppendableViews to be implemented by any controllers
 * that want to control an AppendableView.
 */
public interface AppendableViewCallbacks {
  /**
   * Returns a read-only version of of the model.
   *
   * @return the model
   */
  ReadingAnimationModel getModel();

  /**
   * Returns the rate at which the controller ticks time.
   *
   * @return the tick rate
   */
  int getTickRate();

  /**
   * Gets the Appendable being used by the controller.
   *
   * @return the appendable
   */
  Appendable getAppendable();
}
