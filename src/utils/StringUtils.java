/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author agabel
 */
public class StringUtils {
    
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
