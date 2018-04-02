package cs3500.animator.controller;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.view.AnimationView;

/**
 * A class for handling interactions between AnimationModels and AnimationViews.
 *
 * @param <ViewType> the type of AnimationView being controlled
 */
public interface AnimationController<ViewType extends AnimationView> {
  /**
   * Allows this AnimationController to take control of the given model and view.
   *
   * @param model the model
   * @param view the view
   */
  void control(AnimationModel model, ViewType view);
}
