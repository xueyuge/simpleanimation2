package cs3500.animator.view.appendableview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiFunction;

import cs3500.animator.model.ReadingAnimationModel;
import cs3500.animator.model.command.ColorCommand;
import cs3500.animator.model.command.Command;
import cs3500.animator.model.command.MoveCommand;
import cs3500.animator.model.command.ScaleCommand;
import cs3500.animator.model.drawable.Drawable;
import cs3500.animator.model.drawable.DrawableBaseType;
import cs3500.animator.model.drawable.shape.Oval;
import cs3500.animator.model.drawable.shape.Rectangle;
import cs3500.animator.view.AnimationView;

import static cs3500.animator.model.drawable.DrawableBaseType.SHAPE;

/**
 * An AnimationView that appends a String of a specified format to some Appendable.
 */
public class AppendableView implements AnimationView {
  private final BiFunction<ReadingAnimationModel, Integer, String> renderer;
  private final AppendableViewCallbacks                            callbacks;

  /**
   * Constructs an AppendableView from a custom formatting function.
   *
   * @param renderer the function to format the AnimationModel into a String
   * @param callbacks the callback object
   */
  public AppendableView(BiFunction<ReadingAnimationModel, Integer, String> renderer,
                        AppendableViewCallbacks callbacks) {
    this.renderer   = renderer;
    this.callbacks  = callbacks;
  }

  /**
   * Constructs an AppendableView from a pre-existing formatting function.
   *
   * @param format the supported format
   * @param callbacks the callback object
   */
  public AppendableView(SupportedStringFormat format, AppendableViewCallbacks callbacks) {
    switch (format) {
      case DESCRIPTION:
        this.renderer = descriptionRenderer;
        break;

      case SVG:
        this.renderer = svgRenderer;
        break;

      default:
        throw new IllegalArgumentException("An unrecognized String format was requested!");
    }
    this.callbacks = callbacks;
  }

  @Override
  public void initialize() {
    //Nothing to initialize!
  }

  @Override
  public void view() {
    ReadingAnimationModel model       = this.callbacks.getModel();
    int                   tickRate    = this.callbacks.getTickRate();
    Appendable            appendable  = this.callbacks.getAppendable();
    try {
      appendable.append(this.renderer.apply(model, tickRate));
    } catch (IOException exception) {
      throw new IllegalStateException("This View's Appendable was unable to be appended to!");
    }
  }

  /**
   * Describes one of the built-in String conversion formats.
   */
  public enum SupportedStringFormat {
    DESCRIPTION,
    SVG
  }

  /* **********************************************************************************************
   *
   * TEXT VIEW
   *
   * *********************************************************************************************/
  private static final BiFunction<ReadingAnimationModel, Integer, String> descriptionRenderer =
      (ReadingAnimationModel model, Integer tickRate) -> {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.addAll(model.getDrawables());

        ArrayList<Command> commands = new ArrayList<>();
        commands.addAll(model.getCommands());

        StringBuilder resultBuilder = new StringBuilder();

        for (DrawableBaseType baseType : DrawableBaseType.values()) {
          if (baseType == SHAPE) {
            resultBuilder.append(String.format("Shapes:%n"));
          } else {
            throw new IllegalStateException("A DrawableBaseType was not recognized!");
          }

          for (Drawable drawable : drawables) {
            if (drawable.getBaseType() != baseType || !drawable.isVisible()) {
              continue;
            }
            switch (drawable.getType()) {
              case RECTANGLE: {
                Rectangle rectangle = (Rectangle) drawable;
                float[] position = rectangle.getPosition();
                float[] color = rectangle.getColor();
                float[] dimensions = rectangle.getDimensions();
                resultBuilder.append(String.format("Name: %s%nType: rectangle%nLower-left "
                        + "corner: (%.1f,%.1f), Width: %.1f, Height: %.1f, Color: (%.1f,%.1f,"
                        + "%.1f)%nAppears at t=%.1fs%nDisappears at t=%.1fs%n",
                        rectangle.getName(), position[0], position[1], dimensions[0],
                        dimensions[1], color[0], color[1], color[2],
                        (float) rectangle.getAppearTime() / (float)tickRate,
                        (float) rectangle.getDisappearTime() / (float)tickRate));
              }
              break;

              case OVAL: {
                Oval oval = (Oval) drawable;
                float[] position = oval.getPosition();
                float[] color = oval.getColor();
                float[] dimensions = oval.getDimensions();
                resultBuilder.append(String.format("Name: %s%nType: oval%nCenter: (%.1f,%.1f), "
                        + "X radius: %.1f, Y radius: %.1f, Color: (%.1f,%.1f,%.1f)%nAppears "
                        + "at t=%.1fs%nDisappears at t=%.1fs%n", oval.getName(), position[0],
                        position[1], dimensions[0] / 2.0f, dimensions[1] / 2.0f, color[0],
                        color[1], color[2], (float) oval.getAppearTime() / (float)tickRate,
                        (float) oval.getDisappearTime() / (float)tickRate));
              }
              break;

              default:
                throw new IllegalStateException("A DrawableType was not recognized!");
            }
            resultBuilder.append(String.format("%n"));
          }
        }

        for (int i = 0; i < commands.size(); i++) {
          Command command   = commands.get(i);
          Drawable operand  = command.getOperand();
          float start       = (float)command.getStartTime() / (float)tickRate;
          float end         = (float)command.getEndTime() / (float)tickRate;

          String operandBaseTypeName;
          if (operand.getBaseType() == SHAPE) {
            operandBaseTypeName = "Shape";
          } else {
            throw new IllegalStateException("A DrawableBaseType was not recognized!");
          }

          switch (command.getType()) {
            case MOVE: {
              MoveCommand moveCommand = (MoveCommand)command;
              float[] initialData = moveCommand.getInitialData();
              float[] finalData   = moveCommand.getFinalData();
              resultBuilder.append(String.format("%s %s moves from (%.1f,%.1f) to (%.1f,%.1f) from"
                      + " t=%.1fs to t=%.1fs", operandBaseTypeName, operand.getName(),
                      initialData[0], initialData[1], finalData[0], finalData[1], start, end));
            }
            break;

            case COLOR: {
              ColorCommand moveCommand = (ColorCommand)command;
              float[] initialData = moveCommand.getInitialData();
              float[] finalData   = moveCommand.getFinalData();
              resultBuilder.append(String.format("%s %s changes color from (%.1f,%.1f,%.1f) to "
                      + "(%.1f,%.1f,%.1f) from t=%.1fs to t=%.1fs",
                      operandBaseTypeName, operand.getName(), initialData[0], initialData[1],
                      initialData[2], finalData[0], finalData[1], finalData[2], start, end));
            }
            break;

            case SCALE: {
              ScaleCommand scaleCommand = (ScaleCommand)command;
              float[] initialData = scaleCommand.getInitialData();
              float[] finalData   = scaleCommand.getFinalData();

              String horizontal;
              String vertical;
              switch (operand.getType()) {
                case RECTANGLE:
                  horizontal = "Width";
                  vertical = "Height";
                  break;

                case OVAL:
                  horizontal = "X radius";
                  vertical = "Y radius";
                  break;

                default:
                  throw new IllegalArgumentException("A CommandType was not recognized!");
              }

              resultBuilder.append(String.format("%s %s scales from %s: %.1f, %s: %.1f to %s: %.1f"
                      + ", %s: %.1f from t=%.1fs to t=%.1fs", operandBaseTypeName,
                      operand.getName(), horizontal, initialData[0], vertical, initialData[1],
                      horizontal, finalData[0], vertical, finalData[1], start, end));
            }
            break;

            default:
              throw new IllegalStateException("A Command of unknown type was found!");
          }
          if (i != commands.size() - 1) {
            resultBuilder.append(String.format("%n"));
          }
        }

        return resultBuilder.toString();
      };

  /* **********************************************************************************************
   *
   * SVG VIEW
   *
   * *********************************************************************************************/
  private static final BiFunction<ReadingAnimationModel, Integer, String> svgRenderer =
      (ReadingAnimationModel model, Integer tickRate) -> {
        StringBuilder resultBuilder = new StringBuilder();

        resultBuilder.append(String.format("<?xml version=\"1.0\"?>%n<svg width=\"800\" height="));
        resultBuilder.append("\"600\" viewPort=\"0 0 800 600\" version=\"1.1\" ");
        resultBuilder.append(String.format("xmlns=\"http://www.w3.org/2000/svg\">%n"));

        for (Drawable drawable : model.getDrawables()) {
          if (!drawable.isVisible()) {
            continue;
          }
          switch (drawable.getType()) {
            case RECTANGLE: {
              Rectangle rectangle = (Rectangle)drawable;
              float[] position = rectangle.getPosition();
              float[] color = rectangle.getColor();
              float[] dimensions = rectangle.getDimensions();
              resultBuilder.append(String.format("\t<rect id=\"%s\" width=\"%d\" height=\"%d\" "
                      + "x=\"%d\" y=\"%d\" fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\">",
                      rectangle.getName(), (int)dimensions[0], (int)dimensions[1], (int)position[0],
                      (int)position[1], (int)(color[0] * 255.0f), (int)(color[1] * 255.0f),
                      (int)(color[2] * 255.0f)));
            }
            break;

            case OVAL: {
              Oval oval = (Oval)drawable;
              float[] position = oval.getPosition();
              float[] color = oval.getColor();
              float[] dimensions = oval.getDimensions();
              resultBuilder.append(String.format("\t<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" "
                      + "rx=\"%d\" ry=\"%d\" fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\">",
                      oval.getName(), (int)position[0], (int)position[1],
                      (int)(dimensions[0] / 2.0f), (int)(dimensions[1] / 2.0f),
                      (int)(color[0] * 255.0f), (int)(color[1] * 255.0f),
                      (int)(color[2] * 255.0f)));
            }
            break;

            default:
              throw new IllegalStateException("A DrawableType was not recognized!");
          }
          resultBuilder.append(String.format("%n"));

          resultBuilder.append(String.format("\t\t<animate attributeType=\"XML\" attributeName="
                  + "\"visibility\" from=\"hidden\" to=\"visible\" begin=\"%.3fs\" dur=\"0.001s\" "
                  + "fill=\"freeze\"/>%n", (float)drawable.getAppearTime() / (float)tickRate));
          resultBuilder.append(String.format("\t\t<animate attributeType=\"XML\" attributeName="
                  + "\"visibility\" from=\"visible\" to=\"hidden\" begin=\"%.3fs\" dur=\"0.001s\" "
                  + "fill=\"freeze\"/>%n", (float)drawable.getDisappearTime() / (float)tickRate));

          for (Command command : drawable.getCommands()) {
            switch (command.getType()) {
              case MOVE: {
                MoveCommand moveCommand = (MoveCommand)command;
                String horizontal;
                String vertical;
                switch (command.getOperand().getType()) {
                  case RECTANGLE:
                    horizontal  = "x";
                    vertical    = "y";
                    break;

                  case OVAL:
                    horizontal  = "cx";
                    vertical    = "cy";
                    break;

                  default:
                    throw new IllegalStateException("A bad DrawableType was found!");
                }
                resultBuilder.append(String.format("\t\t<animate attributeType=\"XML\" "
                        + "attributeName=\"%s\" from=\"%d\" to=\"%d\" begin=\"%.3fs\" dur=\"%.3fs\""
                        + " fill=\"freeze\"/>%n\t\t<animate attributeType=\"XML\" attributeName="
                        + "\"%s\" from=\"%d\" to=\"%d\" begin=\"%.3fs\" dur=\"%.3fs\" "
                        + "fill=\"freeze\"/>", horizontal, (int)moveCommand.getInitialData()[0],
                        (int)moveCommand.getFinalData()[0],
                        (float)command.getStartTime() / (float)tickRate,
                        (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate,
                        vertical, (int)moveCommand.getInitialData()[1],
                        (int)moveCommand.getFinalData()[1],
                        (float)command.getStartTime() / (float)tickRate,
                        (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate));
              }
              break;

              case COLOR: {
                ColorCommand colorCommand = (ColorCommand)command;
                resultBuilder.append(String.format("\t\t<animate attributeType=\"XML\" "
                        + "attributeName=\"fill\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\""
                        + " begin=\"%.3fs\" dur=\"%.3fs\" fill=\"freeze\"/>",
                        (int)(colorCommand.getInitialData()[0] * 255.0f),
                        (int)(colorCommand.getInitialData()[1] * 255.0f),
                        (int)(colorCommand.getInitialData()[2] * 255.0f),
                        (int)(colorCommand.getFinalData()[0] * 255.0f),
                        (int)(colorCommand.getFinalData()[1] * 255.0f),
                        (int)(colorCommand.getFinalData()[2] * 255.0f),
                        (float)command.getStartTime() / (float)tickRate,
                        (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate));
              }
              break;

              case SCALE: {
                ScaleCommand scaleCommand = (ScaleCommand)command;
                String horizontal;
                String vertical;
                float initialWidth;
                float initialHeight;
                float finalWidth;
                float finalHeight;
                switch (command.getOperand().getType()) {
                  case RECTANGLE:
                    horizontal    = "width";
                    vertical      = "height";
                    initialWidth  = scaleCommand.getInitialData()[0];
                    initialHeight = scaleCommand.getInitialData()[1];
                    finalWidth    = scaleCommand.getFinalData()[0];
                    finalHeight   = scaleCommand.getFinalData()[1];
                    break;

                  case OVAL:
                    horizontal  = "rx";
                    vertical    = "ry";
                    initialWidth  = scaleCommand.getInitialData()[0] / 2.0f;
                    initialHeight = scaleCommand.getInitialData()[1] / 2.0f;
                    finalWidth    = scaleCommand.getFinalData()[0] / 2.0f;
                    finalHeight   = scaleCommand.getFinalData()[1] / 2.0f;
                    break;

                  default:
                    throw new IllegalStateException("A bad DrawableType was found!");
                }
                resultBuilder.append(String.format("\t\t<animate attributeType=\"XML\" "
                        + "attributeName=\"%s\" from=\"%d\" to=\"%d\" begin=\"%.3fs\""
                        + " dur=\"%.3fs\" fill=\"freeze\"/>%n\t\t<animate "
                        + "attributeType=\"XML\" attributeName=\"%s\" from=\"%d\" to=\"%d\" "
                        + "begin=\"%.3fs\" dur=\"%.3fs\" fill=\"freeze\"/>",
                        horizontal, (int)initialWidth, (int)finalWidth,
                        (float)command.getStartTime() / (float)tickRate,
                        (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate,
                        vertical, (int)initialHeight, (int)finalHeight,
                        (float)command.getStartTime() / (float)tickRate,
                        (float)(command.getEndTime() - command.getStartTime()) / (float)tickRate));
              }
              break;

              default:
                throw new IllegalStateException("A CommandType was not recognized!");
            }
            resultBuilder.append(String.format("%n"));
          }

          switch (drawable.getType()) {
            case RECTANGLE:
              resultBuilder.append("\t</rect>");
              break;

            case OVAL:
              resultBuilder.append("\t</ellipse>");
              break;

            default:
              throw new IllegalStateException("A DrawableType was not recognized!");
          }
          resultBuilder.append(String.format("%n"));
        }
        resultBuilder.append("</svg>");

        return resultBuilder.toString();
      };
}
