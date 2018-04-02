import cs3500.animator.controller.AppendableController;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.DrawableSystem;
import cs3500.animator.view.appendableview.AppendableView;
import util.AnimationFileReader;

import static org.junit.Assert.assertEquals;

/**
 * A class to test TextViews.
 */
public class TestTextView {
  /**
   * Tests that the output of a TextView matches what is expected.
   */
  @Test
  public void testTextViewOutput() {
    AnimationFileReader reader = new AnimationFileReader();
    DrawableSystem.Builder builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/smalldemo.txt", builder);
    } catch (FileNotFoundException exception) {
      Assert.fail("File not found!");
    }

    AnimationModel model = builder.build();
    StringBuffer output = new StringBuffer();
    AppendableController controller = new AppendableController(output, 2);
    AppendableView view = new AppendableView(AppendableView.SupportedStringFormat.DESCRIPTION,
            controller);
    controller.control(model, view);

    assertEquals(String.format("Shapes:%nName: R%nType: rectangle%nLower-left corner: (200.0,200.0"
            + "), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)%nAppears at t=0.5s%n"
            + "Disappears at t=50.0s%n%nName: C%nType: oval%nCenter: (500.0,100.0), X radius: 60.0"
            + ", Y radius: 30.0, Color: (0.0,0.0,1.0)%nAppears at t=3.0s%nDisappears at t=50.0s%n"
            + "%nShape R moves from (200.0,200.0) to (300.0,300.0) from t=5.0s to t=25.0s%n"
            + "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=10.0s to t=35.0s%n"
            + "Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) from t=25.0s to t=40.0s%n"
            + "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=35.0s to t=50.0s%nShape R "
            + "scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from t=25.5s "
            + "to t=35.0s"), output.toString());
  }

  @Test(expected = AssertionError.class)
  public void testTextViewOutput2() {
    AnimationFileReader reader = new AnimationFileReader();
    DrawableSystem.Builder builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/smalldemo2.txt", builder);
    } catch (FileNotFoundException exception) {
      Assert.fail("File not found!");
    }

    AnimationModel model = builder.build();
    StringBuffer output = new StringBuffer();
    AppendableController controller = new AppendableController(output, 2);
    AppendableView view = new AppendableView(AppendableView.SupportedStringFormat.DESCRIPTION,
            controller);
    controller.control(model, view);

    assertEquals(String.format("Shapes:%nName: R%nType: rectangle%nLower-left corner: (250.0,250.0"
            + "), Width: 100.0, Height: 50.0, Color: (1.0,0.0,0.0)%nAppears at t=0.5s%n"
            + "Disappears at t=50.0s%n%nName: R2%nType: rectangle%nLower-left corner: (250.0,250.0"
            + "),Width: 20.0, Height: 30.0, Color: (2.0,5.0,6.0)%nAppears at t=0.5s%n"
            + "s%n%nName: C%nType: oval%nCenter: (500.0,100.0), X radius: 60.0"
            + ", Y radius: 30.0, Color: (0.0,0.0,1.0)%nAppears at t=3.0s%nDisappears at t=50.0s%n"
            + "%nShape R moves from (500.0,500.0) to (600.0,600.0) from t=5.0s to t=25.0s%n"
            + "Shape C moves from (350.0,250.0) to (450.0,200.0) from t=10.0s to t=35.0s%n"
            + "Shape C changes color from (0.0,0.0,1.0) to (1.0,1.0,0.0) from t=25.0s to t=40.0s%n"
            + "Shape R2 moves from (300.0,300.0) to (200.0,200.0) from t=35.0s to t=50.0s%nShape R"
            + "2 scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from t=25.5s"
            + " to t=35.0s"), output.toString());
  }

  @Test
  public void testTextViewOutput3() {
    AnimationFileReader reader = new AnimationFileReader();
    DrawableSystem.Builder builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/smalldemo3.txt", builder);
    } catch (FileNotFoundException exception) {
      Assert.fail("File not found!");
    }

    AnimationModel model = builder.build();
    StringBuffer output = new StringBuffer();
    AppendableController controller = new AppendableController(output, 2);
    AppendableView view = new AppendableView(AppendableView.SupportedStringFormat.DESCRIPTION,
            controller);
    controller.control(model, view);

    assertEquals(String.format("Shapes:%nName: R%nType: rectangle%nLower-left corner: (250.0,250.0"
            + "), Width: 100.0, Height: 50.0, Color: (1.0,0.0,0.0)%nAppears at t=0.5s%n"
            + "Disappears at t=50.0s%n%nName: R2%nType: rectangle%nLower-left corner: (150.0,100.0"
            + "), Width: 20.0, Height: 30.0, Color: (0.0,0.0,1.0)%nAppears at t=0.5s%n"
            + "Disappears at t=50.0s%n%nName: C%nType: oval%nCenter: (300.0,200.0), X radius: 80.0"
            + ", Y radius: 40.0, Color: (1.0,1.0,1.0)%nAppears at t=3.0s%nDisappears at t=50.0s%n"
            + "%nShape R moves from (500.0,500.0) to (600.0,600.0) from t=5.0s to t=25.0s%n"
            + "Shape C moves from (350.0,250.0) to (450.0,200.0) from t=10.0s to t=35.0s%n"
            + "Shape C changes color from (1.0,1.0,1.0) to (0.0,0.0,1.0) from t=25.0s to t=40.0s%n"
            + "Shape R2 moves from (300.0,300.0) to (200.0,200.0) from t=35.0s to t=50.0s%nShape R"
            + "2 scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from t=25.5s"
            + " to t=35.0s"), output.toString());
  }
}
