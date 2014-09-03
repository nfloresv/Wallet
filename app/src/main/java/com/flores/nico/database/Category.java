package com.flores.nico.database;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by nicoflores on 03-09-14.
 */
public class Category extends SugarRecord<Category> {
    private String name;
    private String description;

    public Category () {
    }

    public Category (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

}
