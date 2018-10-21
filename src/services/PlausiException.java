package services;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Control;

public class PlausiException extends Exception {
    private List<String> messages;
    private Control control;
    
    public PlausiException(String fehler) {
        super(fehler);        
    }
    
    public PlausiException(String fehler,Control con) {
        super(fehler);  
        setControl(con);
    }
    
    public PlausiException(List<String> messages) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        if (messages == null) {
            this.messages = new ArrayList<String>();
        }
        else {
            this.messages = messages;
        }
    }
    
    public PlausiException(List<String> messages,Control con) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        
        if (messages == null) {
            this.messages = new ArrayList<String>();
        }
        else {
            this.messages = messages;
        }
        
        setControl(con);
    }
    
    public List<String> getMessages() {
        return messages;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }
}