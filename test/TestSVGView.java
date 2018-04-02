import cs3500.animator.controller.AppendableController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.DrawableSystem;
import cs3500.animator.view.appendableview.AppendableView;
import org.junit.Assert;
import org.junit.Test;
import util.AnimationFileReader;

import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertEquals;

/**
 * A class for testing SVGAnimationViews.
 */
public class TestSVGView {
  /**
   * Tests that an SVGView correctly outputs what is expected for smalldemo.txt.
   */
  @Test
  public void testSVGOutput() {
    AnimationFileReader reader = new AnimationFileReader();
    DrawableSystem.Builder builder = new DrawableSystem.Builder();
    try {
      reader.readFile("./src/cs3500/animator/resources/smalldemo.txt", builder);
    } catch (FileNotFoundException exception) {
      Assert.fail("File not found!");
    }

    AnimationModel model = builder.build();
    StringBuffer output = new StringBuffer();
    AppendableController controller = new AppendableController(output, 1);
    AppendableView view = new AppendableView(AppendableView.SupportedStringFormat.SVG, controller);
    controller.control(model, view);

    String comparison = String.format("<?xml version=\"1.0\"?>%n<svg width=\"800\" "
            + "height=\"600\" viewPort="
            + "\"0 0 800 600\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">%n"
            + "\t<rect id=\"R\" width=\"50\" height=\"100\" x=\"200\" y=\"200\" "
            + "fill=\"rgb(255,0,0)\" visibility=\"hidden\">%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" begin=\"1.000s\" "
            + "dur=\"0.001s\" fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" begin=\"100.000s\" "
            + "dur=\"0.001s\" fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"x\" from=\"200\" to=\"300\" begin=\"10.000s\" dur=\"40.000s\" "
            + "fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"y\" from=\"200\" to=\"300\" begin=\"10.000s\" dur=\"40.000s\" "
            + "fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"x\" from=\"300\" to=\"200\" begin=\"70.000s\" dur=\"30.000s\" "
            + "fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"y\" from=\"300\" to=\"200\" begin=\"70.000s\" dur=\"30.000s\" "
            + "fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"width\" from=\"50\" to=\"25\" begin=\"51.000s\" dur=\"19.000s\""
            + " fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" "
            + "attributeName=\"height\" from=\"100\" to=\"100\" begin=\"51.000s\" dur=\"19.000"
            + "s\" fill=\"freeze\"/>%n\t</rect>%n\t<ellipse id=\"C\" cx=\"500\" cy=\"100\" "
            + "rx=\"60\" ry=\"30\" fill=\"rgb(0,0,255)\" visibility=\"hidden\">%n\t\t<animate "
            + "attributeType=\"XML\" attributeName=\"visibility\" from=\"hidden\" "
            + "to=\"visible\" begin=\"6.000s\" dur=\"0.001s\" fill=\"freeze\"/>%n\t\t<animate "
            + "attributeType=\"XML\" attributeName=\"visibility\" from=\"visible\" "
            + "to=\"hidden\" begin=\"100.000s\" dur=\"0.001s\" fill=\"freeze\"/>%n\t\t<animate"
            + " attributeType=\"XML\" attributeName=\"cx\" from=\"500\" to=\"500\" "
            + "begin=\"20.000s\" dur=\"50.000s\" fill=\"freeze\"/>%n\t\t<animate "
            + "attributeType=\"XML\" attributeName=\"cy\" from=\"100\" to=\"400\" "
            + "begin=\"20.000s\" dur=\"50.000s\" fill=\"freeze\"/>%n\t\t<animate "
            + "attributeType=\"XML\" attributeName=\"fill\" from=\"rgb(0,0,255)\" "
            + "to=\"rgb(0,255,0)\" begin=\"50.000s\" dur=\"30.000s\" fill=\"freeze\"/>"
            + "%n\t</ellipse>%n</svg>");

    assertEquals(comparison, output.toString());
  }

  @Test(expected = AssertionError.class)
  public void testSVGOutput2() {
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
    AppendableView view = new AppendableView(AppendableView.SupportedStringFormat.SVG, controller);
    controller.control(model, view);

    Assert.assertEquals(String.format("Shapes:%nName: R%nType: rectangle%nLower-left corner: "
            + "(250.0,250.0), Width: 100.0, Height: 50.0, Color: (1.0,0.0,0.0)%nAppears at t=0.5s"
            + "%nDisappears at t=50.0s%n%nName: R2%nType: rectangle%nLower-left corner: (250.0,"
            + "250.0),Width: 20.0, Height: 30.0, Color: (2.0,5.0,6.0)%nAppears at t=0.5s%n"
            + "s%n%nName: C%nType: oval%nCenter: (500.0,100.0), X radius: 60.0"
            + ", Y radius: 30.0, Color: (0.0,0.0,1.0)%nAppears at t=3.0s%nDisappears at t=50.0s%n"
            + "%nShape R moves from (500.0,500.0) to (600.0,600.0) from t=5.0s to t=25.0s%n"
            + "Shape C moves from (350.0,250.0) to (450.0,200.0) from t=10.0s to t=35.0s%n"
            + "Shape C changes color from (0.0,0.0,1.0) to (1.0,1.0,0.0) from t=25.0s to t=40.0s%n"
            + "Shape R2 moves from (300.0,300.0) to (200.0,200.0) from t=35.0s to t=50.0s%nShape R"
            + "2 scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from t=25.5s"
            + " to t=35.0s"), output.toString());
  }
}
