/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.lang.reflect.Constructor;
import javafx.application.HostServices;
import javafx.util.Callback;

/**
 *
 * @author Ratte
 */
public class HostServicesControllerFactory implements Callback<Class<?>,Object> {

    private final HostServices hostServices ;

    public HostServicesControllerFactory(HostServices hostServices) {
        this.hostServices = hostServices ;
    }

    @Override
    public Object call(Class<?> type) {
        try {
            for (Constructor<?> c : type.getConstructors()) {
                if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == HostServices.class) {
                    return c.newInstance(hostServices) ;
                }
            }
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

