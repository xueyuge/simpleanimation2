package cs3500.animator.view.graphicalview;

import javax.swing.JFrame;

/**
 * A tool used by GraphicalViews to  differentiate different panel implementations.
 */
public interface GraphicalViewInterface {
  /**
   * Initializes this panel implementation.
   *
   * @param frame the parent frame
   * @param callbacks the callback object
   * @param width the width of the parent frame
   * @param height the height of the parent frame
   */
  void initialize(JFrame frame, GraphicalViewCallbacks callbacks, int width, int height);
}
