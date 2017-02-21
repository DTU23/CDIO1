package main;
import control.Ctrl;
import model.*;
import model.storage.FileStorage;
import model.storage.IDataStorage;
import view.TUI;

public class Main{
	
    public static void main(String[] args){
        IDataStorage storage = new FileStorage();
        IDAL dao = new PersistentUserDAO(storage);
        Ctrl controller = new Ctrl(dao);
        TUI tui = new TUI(controller);
        tui.run();
    }
}