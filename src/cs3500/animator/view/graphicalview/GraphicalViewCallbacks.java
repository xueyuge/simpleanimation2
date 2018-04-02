package cs3500.animator.view.graphicalview;

import cs3500.animator.model.ReadingAnimationModel;

import java.awt.event.ActionListener;

/**
 * Represents all methods needed by GraphicalViews to callback to their controllers.
 */
public interface GraphicalViewCallbacks extends ActionListener {
  /**
   * Sets a multiplier for the rate at which the controller ticks time.
   *
   * @param coefficient the multiplier
   */
  void setTickRateCoefficient(float coefficient);

  /**
   * Sets whether the controller should pause time.
   *
   * @param paused whether the controller should be paused
   */
  void setPaused(boolean paused);

  /**
   * Sets whether the controller should be looping the animation.
   *
   * @param looping whether the controller should be looping
   */
  void setLooping(boolean looping);

  /**
   * Sets the current tick the controller's time should be on.
   *
   * @param currentTick the current tick
   */
  void setCurrentTick(float currentTick);

  /**
   * Gets the multiplier for the rate at which the controller ticks time.
   *
   * @return the multiplier
   */
  float getTickRateCoefficient();

  /**
   * Gets the read-only runtime model used by the controller.
   *
   * @return the model
   */
  ReadingAnimationModel getModel();

  /**
   * Gets the current tick of the controller's time.
   *
   * @return the current tick
   */
  float getCurrentTick();

  /**
   * Gets the base rate at which the controller ticks time.
   *
   * @return the tick rate
   */
  int getTickRate();
}
