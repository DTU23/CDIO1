import control.Ctrl;
import view.TUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main{
    public static void main(String[] args){
        Ctrl controller = new Ctrl();
        TUI tui = new TUI(controller);
        tui.run();
    }
}