package main;
import control.Ctrl;
import view.TUI;

public class Main{
    public static void main(String[] args){
        Ctrl controller = new Ctrl();
        TUI tui = new TUI(controller);
        tui.run();
    }
}