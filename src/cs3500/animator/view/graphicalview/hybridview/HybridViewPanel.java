package cs3500.animator.view.graphicalview.hybridview;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs3500.animator.controller.AppendableController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.drawable.Drawable;
import cs3500.animator.view.appendableview.AppendableView;
import cs3500.animator.view.graphicalview.GraphicalViewCallbacks;
import cs3500.animator.view.graphicalview.visualview.VisualViewPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The main panel used in a hybrid type GraphicalView.
 */
public class HybridViewPanel extends VisualViewPanel {
  private final ImageIcon playImage;
  private final ImageIcon pauseImage;
  private final ImageIcon loopImage;
  private final ImageIcon restartImage;

  /**
   * Constructs a HypridViewPanel.
   */
  public HybridViewPanel() {
    super();

    ImageIcon playOriginal = new ImageIcon("./play.png");
    this.playImage = new ImageIcon(playOriginal.getImage().getScaledInstance(32, 32,
                                                                        Image.SCALE_DEFAULT));

    ImageIcon pauseOriginal = new ImageIcon("./pause.png");
    this.pauseImage = new ImageIcon(pauseOriginal.getImage().getScaledInstance(32, 32,
                                                                          Image.SCALE_DEFAULT));

    ImageIcon loopOriginal = new ImageIcon("./loop.png");
    this.loopImage = new ImageIcon(loopOriginal.getImage().getScaledInstance(32, 32,
                                                                        Image.SCALE_DEFAULT));

    ImageIcon restartOriginal = new ImageIcon("./restart.png");
    this.restartImage = new ImageIcon(restartOriginal.getImage().getScaledInstance(32, 32,
                                                                            Image.SCALE_DEFAULT));
  }

  @Override
  public void initialize(JFrame frame, GraphicalViewCallbacks callbacks, int width, int height) {
    super.initialize(frame, callbacks, width, height);

    //Add some buttons and stuff...
    JPanel interfacePanel = new JPanel();
    interfacePanel.setLayout(new FlowLayout());

    JLabel speedLabel = new JLabel("Tick Rate x");
    interfacePanel.add(speedLabel);

    JComboBox<Float> speedBox = new JComboBox<>(
            new Float[] {0.25f, 0.5f, 1.0f, 1.5f, 2.0f, 5.0f, 10.0f});
    speedBox.setSelectedItem(1.0f);
    speedBox.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        callbacks.setTickRateCoefficient((float)speedBox.getSelectedItem());
      }
    });
    interfacePanel.add(speedBox);

    JButton pausePlayButton = new JButton();
    pausePlayButton.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (pausePlayButton.getName().equals("Pause")) {
          pausePlayButton.setIcon(playImage);
          pausePlayButton.setName("Play");
          callbacks.setPaused(true);
        } else {
          pausePlayButton.setIcon(pauseImage);
          pausePlayButton.setName("Pause");
          callbacks.setPaused(false);
        }
      }
    });
    pausePlayButton.setIcon(this.pauseImage);
    pausePlayButton.setName("Pause");
    interfacePanel.add(pausePlayButton);

    JButton restartButton = new JButton();
    restartButton.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        callbacks.setCurrentTick(0.0f);
      }
    });
    restartButton.setIcon(restartImage);
    interfacePanel.add(restartButton);

    JButton fileSaveButton = new JButton();
    fileSaveButton.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(interfacePanel);
        if (result == JFileChooser.APPROVE_OPTION) {
          File file = chooser.getSelectedFile();
          FileWriter writer;
          try {
            writer = new FileWriter(file);
          } catch (IOException exception) {
            throw new IllegalStateException("Selected a file that could not be written to!");
          }
          AppendableController controller = new AppendableController(writer,
                  (int)((float)callbacks.getTickRate() * callbacks.getTickRateCoefficient()));
          AppendableView view = new AppendableView(AppendableView.SupportedStringFormat.SVG,
                  controller);
          controller.control((AnimationModel)callbacks.getModel(), view);
          try {
            writer.close();
          } catch (IOException exception) {
            throw new IllegalStateException("Could not close SVG File!");
          }
        } else {
          throw new IllegalStateException("Selected a file that could not be written to!");
        }
      }
    });
    fileSaveButton.setText("Export");
    interfacePanel.add(fileSaveButton);

    JButton loopButton = new JButton();
    Color baseColor = loopButton.getBackground();
    loopButton.setAction(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (loopButton.getName().equals("Looping")) {
          loopButton.setBackground(baseColor);
          loopButton.setName("Not Looping");
          callbacks.setLooping(false);
        } else {
          loopButton.setBackground(Color.GREEN);
          loopButton.setName("Looping");
          callbacks.setLooping(true);
        }
      }
    });
    loopButton.setIcon(loopImage);
    loopButton.setName("Not Looping");
    interfacePanel.add(loopButton);

    frame.add(interfacePanel, BorderLayout.SOUTH);

    JPanel selectionPanel = new JPanel();
    selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
    selectionPanel.setPreferredSize(new Dimension(100,
            24 * callbacks.getModel().getDrawables().size()));
    for (Drawable drawable : callbacks.getModel().getDrawables()) {
      JCheckBox box = new JCheckBox();
      box.setAction(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (box.isSelected()) {
            callbacks.getModel().getDrawable(box.getText()).setVisibility(true);
          } else {
            callbacks.getModel().getDrawable(box.getText()).setVisibility(false);
          }
        }
      });
      box.setSelected(true);
      box.setText(drawable.getName());
      box.setName("on");
      selectionPanel.add(box);
    }
    frame.add(selectionPanel, BorderLayout.WEST);
    JScrollPane pane = new JScrollPane(selectionPanel);
    frame.add(pane, BorderLayout.WEST);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
  }
}
