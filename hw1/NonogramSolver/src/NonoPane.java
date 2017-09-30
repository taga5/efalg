import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class NonoPane extends BorderPane {
	
	protected Nonogram nonogram;
	protected int gridWidth;
	protected int gridHeight;
	protected Canvas canvas;
	
	public NonoPane(Nonogram nonogram) {
		canvas = new Canvas();
		setCenter(canvas);
		canvas.widthProperty().bind(widthProperty());
		canvas.heightProperty().bind(heightProperty());
		widthProperty().addListener(e -> draw());
		heightProperty().addListener(e -> draw());
		setNonogram(nonogram);
	}
	
	protected void setNonogram(Nonogram nonogram) {
		this.nonogram = nonogram;
		gridHeight = nonogram.height + nonogram.colHints.stream().max((l1, l2) -> l1.size() - l2.size()).get().size();
		gridWidth = nonogram.width + nonogram.rowHints.stream().max((l1, l2) -> l1.size() - l2.size()).get().size();
		draw();
	}
	
	protected double scaleX(double x) {
		return getWidth() / gridWidth * x;
	}
	
	protected double scaleY(double y) {
		return getHeight() / gridHeight * y;	
	}
	
	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font("Arial", getWidth() >= getHeight() ? scaleY(1) : scaleX(.8)));
		for (int i = 0; i < nonogram.colHints.size(); i++) {
			int hints = nonogram.colHints.get(i).size();
			for (int j = 0; j < hints; j++) {
				double x = i + gridWidth - nonogram.width + .5;
				double y = gridHeight - nonogram.height - j - .5;
				gc.fillText(nonogram.colHints.get(i).get(hints - j - 1).toString(), scaleX(x), scaleY(y));
			}
		}
		for (int i = 0; i < nonogram.rowHints.size(); i++) {
			int hints = nonogram.rowHints.get(i).size();
			for (int j = 0; j < hints; j++) {
				double x = gridWidth - nonogram.width - j - 1 + .5;
				double y = i + gridHeight - nonogram.height + 1 - .5;
				gc.fillText(nonogram.rowHints.get(i).get(hints - j - 1).toString(), scaleX(x), scaleY(y));
			}
		}
		for (int i = 0; i < nonogram.height; i++) {
			for (int j = 0; j < nonogram.width; j++) {
				Color color = Color.LIGHTGRAY;
				if (nonogram.get(i, j) == Nonogram.FILLED) color = Color.BLACK;
				else if (nonogram.get(i, j) == Nonogram.EMPTY) color = Color.DARKORCHID;
				if (nonogram.predictions[i][j] == Nonogram.FILLED) color = Color.GRAY;
				else if (nonogram.predictions[i][j] == Nonogram.EMPTY) color = Color.ORCHID;
				gc.setFill(color);
				double x = j + gridWidth - nonogram.width;
				double y = i + gridHeight - nonogram.height;
				gc.fillRect(scaleX(x), scaleY(y), scaleX(x+1), scaleY(y+1));
			}
		}
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		for (int i = gridHeight - nonogram.height; i < gridHeight; i++) {
			gc.strokeLine(0, scaleY(i), scaleX(gridWidth), scaleY(i));
		}
		for (int j = gridWidth - nonogram.width; j < gridWidth; j++) {
			gc.strokeLine(scaleX(j), 0, scaleX(j), scaleY(gridHeight));
		}
	}
}
