/*
 * (C) Copyright 2016 - S33A
 * Final Fontasy XVI, Version 1.0.
 * 
 * Contributors:
 *   Pim Janissen
 *   Luud van Keulen
 *   Robin de Kort
 *   Koen Schilders
 *   Guido Thomasse
 *   Joel Verbeek
 */
package com.ffxvi.game.persistance;

import com.badlogic.gdx.utils.Json;
import com.ffxvi.game.models.AmmoType;
import com.ffxvi.game.models.RoomObjectType;
import com.ffxvi.game.models.RoomType;
import com.ffxvi.game.models.WeaponType;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles all input and output of the application which concern JSON. The GSON
 * library is used for this.
 */
public class JSONHandler
{

    /**
     * Instance of the JSONHandler class.
     */
    private final static JSONHandler JSONHANDLER = new JSONHandler();

    /**
     * Initializes a new JSONHandler. This constructor is private to make sure
     * no other instances of this class are created.
     */
    private JSONHandler()
    {

    }

    /**
     * Gets the current instance of JSONHandler.
     *
     * @return the current instance of JSONHandler.
     */
    public static JSONHandler getInstance()
    {
        return JSONHANDLER;
    }

    /**
     * Converts the given JSON String to a HashMap of Strings and AmmoTypes.
     *
     * @param jsonString the JSON String to convert.
     * @return A HashMap containing pairs of Strings and AmmoTypes. Where the
     * String is an identifier for the given AmmoType.
     */
    public HashMap<String, AmmoType> getAmmoTypes(String jsonString)
    {
        Json json = new Json();

        return ((HashMap<String, AmmoType>) json.fromJson(HashMap.class, jsonString));
    }

    /**
     * Converts the given JSON String to a HashMap of Strings and WeaponTypes.
     *
     * @param jsonString the JSON String to convert.
     * @return A HashMap containing pairs of Strings and WeaponTypes. Where the
     * String is an identifier for the given WeaponType.
     */
    public HashMap<String, WeaponType> getWeaponTypes(String jsonString)
    {
        Json json = new Json();

        return ((HashMap<String, WeaponType>) json.fromJson(HashMap.class, jsonString));
    }

    /**
     * Converts the given JSON String to a HashMap of Strings and Room Object
     * Types.
     *
     * @param jsonString the JSON String to convert.
     * @return A HashMap containing pairs of Strings and RoomObjectType. Where
     * the String is an identifier for the given RoomObjectType.
     */
    public HashMap<String, RoomObjectType> getRoomObjectTypes(String jsonString)
    {
        Json json = new Json();

        return ((HashMap<String, RoomObjectType>) json.fromJson(HashMap.class, jsonString));
    }

    /**
     * Converts the given JSON String to an ArrayList of Room Types.
     *
     * @param jsonString the JSON String to convert.
     * @return A ArrayList containing pairs of Strings and RoomType. Where the
     * String is an identifier for the given RoomType.
     */
    public ArrayList<RoomType> getRoomTypes(String jsonString)
    {
        Json json = new Json();

        return ((ArrayList<RoomType>) json.fromJson(ArrayList.class, jsonString));
    }
}
