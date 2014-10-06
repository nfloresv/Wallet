package com.flores.nico.database;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by nicoflores on 01-10-14.
 */
public class Debt extends SugarRecord<Debt> {
    private double amount;
    private boolean income;
    private String receiver;
    private Date reminder;
    private String description;

    public Debt () {
    }

    public Debt (double amount, boolean income, String receiver, Date reminder, String description) {
        this.amount = amount;
        this.income = income;
        this.receiver = receiver;
        this.reminder = reminder;
        this.description = description;
    }

    public double getAmount () {
        return amount;
    }

    public void setAmount (double amount) {
        this.amount = amount;
    }

    public boolean isIncome () {
        return income;
    }

    public void setIncome (boolean income) {
        this.income = income;
    }

    public String getReceiver () {
        return receiver;
    }

    public void setReceiver (String receiver) {
        this.receiver = receiver;
    }

    public Date getReminder () {
        return reminder;
    }

    public void setReminder (Date reminder) {
        this.reminder = reminder;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }
}
