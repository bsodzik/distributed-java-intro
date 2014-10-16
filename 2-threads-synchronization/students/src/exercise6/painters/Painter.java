package exercise6.painters;

import exercise6.equipment.Brush;
import exercise6.equipment.Paint;

public abstract class Painter implements Runnable {

    protected final Paint paint;
    protected final Brush brush;

    public Painter(Paint paint, Brush brush) {
        this.paint = paint;
        this.brush = brush;
    }
}
