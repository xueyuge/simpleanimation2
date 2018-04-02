package cs3500.animator.view.graphicalview.visualview;

import cs3500.animator.model.ReadingAnimationModel;
import cs3500.animator.model.drawable.Drawable;
import cs3500.animator.model.drawable.shape.Oval;
import cs3500.animator.model.drawable.shape.Rectangle;
import cs3500.animator.view.graphicalview.GraphicalView;
import cs3500.animator.view.graphicalview.GraphicalViewInterface;
import cs3500.animator.view.graphicalview.GraphicalViewCallbacks;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * The main panel used in a basic GraphicalView.
 */
public class VisualViewPanel extends JPanel implements GraphicalViewInterface {
  protected GraphicalViewCallbacks callbacks;
  protected JFrame                 parentFrame;

  /**
   * Constructs a VisualViewPanel.
   */
  public VisualViewPanel() {
    super();
  }

  @Override
  public void initialize(JFrame frame, GraphicalViewCallbacks callbacks, int width, int height) {
    frame.setLayout(new BorderLayout());
    this.setPreferredSize(new Dimension(width * 2, height * 2));
    frame.add(this, BorderLayout.CENTER);

    JScrollPane pane = new JScrollPane(this);
    frame.add(pane);

    this.callbacks    = callbacks;
    this.parentFrame  = frame;
  }

  @Override
  public void paintComponent(Graphics graphics) {
    ReadingAnimationModel model = this.callbacks.getModel();
    float currentTick = this.callbacks.getCurrentTick();
    for (Drawable drawable : model.getDrawables()) {
      if (!drawable.isVisible()) {
        continue;
      }
      switch (drawable.getType()) {
        case RECTANGLE: {
          cs3500.animator.model.drawable.shape.Rectangle rectangle = (Rectangle)drawable;
          float[] position = rectangle.getPosition(currentTick);
          float[] color = rectangle.getColor(currentTick);
          float[] dimensions = rectangle.getDimensions(currentTick);
          graphics.setColor(new Color(color[0], color[1], color[2]));
          graphics.fillRect(GraphicalView.getTrueX((int)position[0], (int)dimensions[0],
                  rectangle.getXAnchor()), GraphicalView.getTrueY((int)position[1],
                  (int)dimensions[1], rectangle.getYAnchor()), (int)dimensions[0],
                  (int)dimensions[1]);
        }
        break;

        case OVAL: {
          Oval oval = (Oval)drawable;
          float[] position = oval.getPosition(currentTick);
          float[] color = oval.getColor(currentTick);
          float[] dimensions = oval.getDimensions(currentTick);
          graphics.setColor(new Color(color[0], color[1], color[2]));
          graphics.fillOval(GraphicalView.getTrueX((int)position[0], (int)dimensions[0],
                  oval.getXAnchor()), GraphicalView.getTrueY((int)position[1],
                  (int)dimensions[1], oval.getYAnchor()), (int)dimensions[0], (int)dimensions[1]);
        }
        break;

        default:
          throw new IllegalStateException("A Drawable of unknown type was found!");
      }
    }
  }
}
