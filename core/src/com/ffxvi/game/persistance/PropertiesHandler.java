/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gebruiker-pc
 */
public class PropertiesHandler {

    private PropertiesHandler() {
    }

    public static PropertiesHandler getInstance() {
        return PropertiesHandlerHolder.INSTANCE;
    }

    private static class PropertiesHandlerHolder {

        private static final PropertiesHandler INSTANCE = new PropertiesHandler();
    }

    public static String getQueryServerIP(String filePath) {
        String address = getQueryServerAddress(filePath);

        int endIndex = address.indexOf(":");
        return address.substring(0, endIndex);
    }

    public static int getQueryServerPort(String filePath) {
        String address = getQueryServerAddress(filePath);

        int startIndex = address.indexOf(":");
        return Integer.getInteger(address.substring(startIndex));
    }

    private static String getQueryServerAddress(String filePath) {
        Properties properties = new Properties();

        InputStream inStream = null;
        try {
            inStream = new FileInputStream(filePath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            properties.load(inStream);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return properties.getProperty(PropertyKeys.QUERYSERVERADDRESS);
    }
}
