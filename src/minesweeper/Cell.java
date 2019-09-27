package minesweeper;

import javax.swing.JButton;

public class Cell {

    private JButton btn;
    private State state;
    private boolean isBomb;
    private int x;
    private int y;
    private Colour colour;

    public Cell(JButton btn, State state, boolean isBomb, int x, int y) {
        this.btn = btn;
        this.state = state;
        this.x = x;
        this.y = y;
        this.colour = Colour.NONE;
    }

    public Cell(JButton btn, State state, boolean isBomb, int x, int y, Colour colour) {
        this.btn = btn;
        this.state = state;
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public JButton getBtn() {
        return btn;
    }

    public void setBtn(JButton btn) {
        this.btn = btn;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
    
}
