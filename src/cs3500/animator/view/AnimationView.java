package cs3500.animator.view;

/**
 * A means by which an AnimationModel can be displayed or viewed.
 */
public interface AnimationView {
  void initialize();

  /**
   * Runs this AnimationView.
   */
  void view();
}
