package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.ReadingAnimationModel;
import cs3500.animator.view.graphicalview.GraphicalView;
import cs3500.animator.view.graphicalview.GraphicalViewCallbacks;

import javax.swing.Timer;

/**
 * A class that handles interactions between GraphicalViews and AnimationModels.
 */
public class GraphicalController implements AnimationController<GraphicalView>,
                                            GraphicalViewCallbacks {
  private static final int DEFAULT_FRAME_TIME = 16;

  private       boolean        quit;
  private final Timer          timer;
  private       float          currentTick;
  private       int            tickRate;
  private       float          tickRateCoefficient;
  private       boolean        paused;
  private       boolean        looping;
  private       int            frameTime;
  private       AnimationModel model;

  /**
   * Constructs a GraphicalController.
   *
   * @param tickRateIn the initial tick rate, in ticks/second
   */
  public GraphicalController(int tickRateIn) {
    this.quit                 = false;
    this.frameTime            = DEFAULT_FRAME_TIME;
    this.tickRate             = tickRateIn;
    this.tickRateCoefficient  = 1.0f;
    this.paused               = false;
    this.looping              = false;
    this.timer                = new Timer(this.frameTime, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (looping && currentTick >= model.getEndTime() + 5) {
          currentTick = 0;
        } else if (!paused) {
          currentTick += (float)frameTime * (float)tickRate * tickRateCoefficient / 1000.0f;
        }
      }
    });
  }

  @Override
  public void control(AnimationModel model, GraphicalView view) {
    this.timer.start();
    this.model = model;

    view.initialize();
    while (!quit) {
      view.view();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //Not needed for this version of the implementation, but could be useful for other
    //GraphicalViewInterfaces
  }

  @Override
  public void setTickRateCoefficient(float coefficient) {
    this.tickRateCoefficient = coefficient;
  }

  @Override
  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  @Override
  public void setLooping(boolean looping) {
    this.looping = looping;
  }

  @Override
  public void setCurrentTick(float currentTick) {
    this.currentTick = currentTick;
  }

  @Override
  public ReadingAnimationModel getModel() {
    return this.model;
  }

  @Override
  public float getCurrentTick() {
    return this.currentTick;
  }

  @Override
  public int getTickRate() {
    return this.tickRate;
  }

  @Override
  public float getTickRateCoefficient() {
    return this.tickRateCoefficient;
  }
}
