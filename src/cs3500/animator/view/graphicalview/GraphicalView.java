package cs3500.animator.view.graphicalview;

import cs3500.animator.model.drawable.XAnchor;
import cs3500.animator.model.drawable.YAnchor;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.graphicalview.hybridview.HybridViewPanel;
import cs3500.animator.view.graphicalview.visualview.VisualViewPanel;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * An AnimationView that uses Java Swing to display a graphical representation of an
 * AnimationModel.
 */
public class GraphicalView extends JFrame implements AnimationView {
  private static final int    DEFAULT_WINDOW_WIDTH  = 800;
  private static final int    DEFAULT_WINDOW_HEIGHT = 600;
  private static final String DEFAULT_WINDOW_TITLE  = "EasyAnimator";

  private final GraphicalViewInterface panelManager;
  private final GraphicalViewCallbacks callbacks;

  /**
   * Constructs a GraphicalView with a custom panel manager.
   *
   * @param panelManager the panel manager
   * @param callbacks the callback object
   */
  public GraphicalView(GraphicalViewInterface panelManager, GraphicalViewCallbacks callbacks) {
    super();

    this.panelManager  = panelManager;
    this.callbacks     = callbacks;
  }

  /**
   * Constructs a GraphicalView with a built-in graphical format.
   *
   * @param format the format
   * @param callbacks the callback object
   */
  public GraphicalView(SupportedGraphicalFormat format, GraphicalViewCallbacks callbacks) {
    switch (format) {
      case NO_EDITOR:
        this.panelManager = new VisualViewPanel();
        break;

      case HYBRID:
        this.panelManager = new HybridViewPanel();
        break;

      default:
        throw new IllegalArgumentException("That format was not recognized!");
    }
    this.callbacks = callbacks;
  }

  @Override
  public void initialize() {
    this.setTitle(DEFAULT_WINDOW_TITLE);
    this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.panelManager.initialize(this, this.callbacks, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

    this.setVisible(true);
  }

  @Override
  public void view() {
    this.repaint();
  }

  /**
   * Identifiers for built-in graphical formats.
   */
  public enum SupportedGraphicalFormat {
    NO_EDITOR,
    HYBRID
  }

  /**
   * Gets the x-component of the left edge of an object based on its stored
   * coordinate and anchor.
   *
   * @param oldX the stored x of the object
   * @param width the width of the object
   * @param anchor the horizontal anchor
   * @return the left x-coordinate
   * @throws IllegalArgumentException if a bad anchor was provided
   */
  public static int getTrueX(int oldX, int width, XAnchor anchor) throws IllegalArgumentException {
    switch (anchor) {
      case LEFT:
        return oldX;

      case RIGHT:
        return oldX - width;

      case CENTER:
        return oldX - width / 2;

      default:
        throw new IllegalArgumentException("A bad XAnchor was given!");
    }
  }

  /**
   * Gets the y-component of the upper edge of an object based on its stored
   * coordinate and anchor.
   *
   * @param oldY the stored y of the object
   * @param height the height of the object
   * @param anchor the vertical anchor
   * @return the top y-coordinate
   * @throws IllegalArgumentException if a bad anchor was provided
   */
  public static int getTrueY(int oldY, int height, YAnchor anchor) throws IllegalArgumentException {
    switch (anchor) {
      case TOP:
        return oldY;

      case BOTTOM:
        return oldY - height;

      case CENTER:
        return oldY - height / 2;

      default:
        throw new IllegalArgumentException("A bad XAnchor was given!");
    }
  }
}
