package com.example.trasstarea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class Formas extends View {


    private int color;

    public enum ShapeType {
        CIRCULO,
        TRIANGULO,
        CUADRADO,
        ESTRELLA
    }

    public Formas(Context context, ShapeType shapeType) {
        super(context);
        this.tipoForma = shapeType;
        posicionRandom(); // Inicializar posición aleatoria
        init(); // Inicializar otros atributos
        elegirColorAleatorioRGB(); // Inicializar color aleatorio

    }

    public Formas(Context context, AttributeSet attrs, ShapeType shapeType) {
        super(context, attrs);
        this.tipoForma = shapeType;
        posicionRandom(); // Inicializar posición aleatoria
        init(); // Inicializar otros atributos
        elegirColorAleatorioRGB(); // Inicializar color aleatorio

    }

    private void posicionRandom() {
        // Generar coordenadas aleatorias dentro del área visible de la pantalla
        Random random = new Random();
        x = random.nextFloat() * screenWidth;
        y = random.nextFloat() * screenHeight;
    }
    private Paint paint;
    private float x, y; // Coordenadas de la forma
    private float tamano; // Tamaño de la forma
    private float dx, dy; // Velocidades de movimiento
    private int screenWidth, screenHeight; // Dimensiones de la pantalla
    private ShapeType tipoForma;

    private void elegirColorAleatorioRGB() {
        Random random = new Random();
        // Generar valores aleatorios para los componentes rojo, verde y azul
        int r = random.nextInt(256); // Valor entre 0 y 255 para el componente rojo
        int g = random.nextInt(256); // Valor entre 0 y 255 para el componente verde
        int b = random.nextInt(256); // Valor entre 0 y 255 para el componente azul
        // Combinar los componentes para formar un color ARGB
        color = Color.rgb(r, g, b);
    }
    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED); // Color predeterminado
        x = 100; // Posición inicial en x
        y = 100; // Posición inicial en y
        tamano = 50; // Tamaño predeterminado

        dx = 5; // Velocidad horizontal
        dy = 5; // Velocidad vertical
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        posicionRandom(); // Actualizar posición aleatoria al cambiar el tamaño de la vista
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color); // Establecer el color aleatorio antes de dibujar la forma
        switch (tipoForma) {
            case CIRCULO:
                canvas.drawCircle(x, y, tamano, paint);
                break;
            case TRIANGULO:
                drawTriangle(canvas);
                break;
            case CUADRADO:
                drawSquare(canvas);
                break;
            case ESTRELLA:
                drawStar(canvas);
                break;
        }
        moveShape();
        invalidate();
    }

    private void drawTriangle(Canvas canvas) {
        Path path = new Path();
        path.moveTo(x, y - tamano); // Punto superior
        path.lineTo(x - tamano, y + tamano); // Punto inferior izquierdo
        path.lineTo(x + tamano, y + tamano); // Punto inferior derecho
        path.lineTo(x, y - tamano); // Volver al punto superior para cerrar el triángulo
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawSquare(Canvas canvas) {
        float halfSize = tamano;
        canvas.drawRect(x - halfSize, y - halfSize, x + halfSize, y + halfSize, paint);
    }

    private void drawStar(Canvas canvas) {
        Path path = new Path();
        path.moveTo(x, y - tamano); // Punto superior

        // Puntos del pentágono externo
        float angleStep = (float) (Math.PI * 2 / 5);
        float angle = (float) Math.PI / 2; // Comenzar desde la parte superior
        for (int i = 0; i < 5; i++) {
            path.lineTo((float) (x + Math.cos(angle) * tamano),
                    (float) (y - Math.sin(angle) * tamano));
            angle += angleStep;
            path.lineTo((float) (x + Math.cos(angle - angleStep / 2) * tamano * 0.4),
                    (float) (y - Math.sin(angle - angleStep / 2) * tamano * 0.4));
            angle += angleStep;
        }
        path.close();
        canvas.drawPath(path, paint);
    }

    private void moveShape() {
        x += dx;
        y += dy;

        // Verificar si la forma alcanza los bordes de la pantalla y hacerla rebotar
        if (x + tamano > screenWidth || x - tamano < 0) {
            dx = -dx; // Cambiar la dirección horizontal
        }
        if (y + tamano > screenHeight || y - tamano < 0) {
            dy = -dy; // Cambiar la dirección vertical
        }
    }

    // Métodos getter y setter para los atributos necesarios

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTamano() {
        return tamano;
    }

    public void setTamano(float tamano) {
        this.tamano = tamano;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
