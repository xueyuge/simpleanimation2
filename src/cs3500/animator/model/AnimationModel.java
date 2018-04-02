package cs3500.animator.model;

import cs3500.animator.model.command.Command;
import cs3500.animator.model.drawable.Drawable;

/**
 * Represents a general model for an animation.
 */
public interface AnimationModel extends ReadingAnimationModel {
  /**
  * Adds a Drawable to this AnimationModel.
  *
  * @param drawable the Drawable
  */
  void addDrawable(Drawable drawable, String name);

  /**
   * Adds a Command to this AnimationModel.
   *
   * @param command the Command
   */
  void addCommand(Command command, String name);
}
