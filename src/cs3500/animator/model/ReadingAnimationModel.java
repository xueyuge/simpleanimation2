package cs3500.animator.model;

import java.util.List;

import cs3500.animator.model.command.Command;
import cs3500.animator.model.drawable.Drawable;

/**
 * A read-only AnimationModel.
 */
public interface ReadingAnimationModel {
  /**
   * Gets a list of the Commands this AnimationModel uses.
   *
   * @return the Commands
   */
  List<Command> getCommands();

  /**
   * Gets a list of the Drawables this AnimationModel uses.
   *
   * @return the Drawables
   */
  List<Drawable> getDrawables();

  /**
   * Returns the Drawable with the given name.
   *
   * @param name the name
   * @return the Drawable
   */
  Drawable getDrawable(String name);

  /**
   * Calculates the time at which this AnimationModel's Commands end.
   *
   * @return the calculated end time
   */
  int getEndTime();
}
