package main;
import control.Ctrl;
import model.FileStorage;
import model.IDataStorage;
import model.UserDAO;
import view.TUI;

public class Main{
	
    public static void main(String[] args){
        IDataStorage storage = new FileStorage();
        UserDAO dao = new UserDAO(storage);
        Ctrl controller = new Ctrl(dao);
        TUI tui = new TUI(controller);
        tui.run();
    }
}