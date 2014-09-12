package com.flores.nico.database;

import android.net.Uri;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by nicoflores on 03-09-14.
 */
public class Movement extends SugarRecord<Movement> {
    private double amount;
    private Category category;
    private boolean income;
    private Date movement_date;
    private String description;
    private Uri fileUri;

    public Movement () {
    }

    public Movement (double amount, Category category, boolean income, Date movement_date,
                     String description, Uri fileUri) {
        this.amount = amount;
        this.category = category;
        this.income = income;
        this.movement_date = movement_date;
        this.description = description;
        this.fileUri = fileUri;
    }

    public double getAmount () {
        return amount;
    }

    public void setAmount (double amount) {
        this.amount = amount;
    }

    public Category getCategory () {
        return category;
    }

    public void setCategory (Category category) {
        this.category = category;
    }

    public boolean isIncome () {
        return income;
    }

    public void setIncome (boolean income) {
        this.income = income;
    }

    public Date getMovement_date () {
        return movement_date;
    }

    public void setMovement_date (Date movement_date) {
        this.movement_date = movement_date;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public Uri getFileUri () {
        return fileUri;
    }

    public void setFileUri (Uri fileUri) {
        this.fileUri = fileUri;
    }

    @Override
    public String toString () {
        return "Movement{" +
                "amount=" + amount +
                ", income=" + income +
                ", description='" + description + '\'' +
                '}';
    }
}
