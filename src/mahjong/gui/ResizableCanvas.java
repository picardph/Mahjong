package mahjong.gui;

import com.sun.prism.Graphics;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ResizableCanvas extends Canvas {

	private GraphicsContext context;
	private ICanvasRenderer renderer;

	public ResizableCanvas() {
		context = getGraphicsContext2D();
		renderer = null;
		widthProperty().addListener(evt -> draw());
		heightProperty().addListener(evt -> draw());
	}

	private void draw() {
		if (renderer != null)
			renderer.draw(context);
	}

	public GraphicsContext getContext() {
		return context;
	}

	public void setRenderer(ICanvasRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double prefWidth(double height) {
		return getWidth();
	}

	@Override
	public double prefHeight(double width) {
		return getHeight();
	}
}
