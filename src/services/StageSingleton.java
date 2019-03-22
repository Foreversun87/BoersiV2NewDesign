package services;

import javafx.stage.Stage;

/**
 *
 * @author Ratte
 */
public class StageSingleton {

    private static Stage instance;

    private StageSingleton() {
    }

    public static Stage getInstance() {
        if (instance == null) {
            instance = new Stage();
        }
        return instance;
    }
}
