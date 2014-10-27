package com.flores.nico.database;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flores.nico.utils.VolleyClient;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by nicoflores on 03-09-14.
 */
public class Movement extends SugarRecord<Movement> {
    // Params Name
    @Ignore
    public static final String SERIALIZED_AMOUNT = "amount";
    @Ignore
    public static final String SERIALIZED_CATEGORY = "category";
    @Ignore
    public static final String SERIALIZED_COMMENT = "comment";
    @Ignore
    public static final String SERIALIZED_DATE = "date";
    @Ignore
    public static final String SERIALIZED_INCOME = "isExpense";

    // Fields
    private double amount;
    private Category category;
    private boolean income;
    private Date movement_date;
    private String description;
    private String filePath;

    public Movement () {
    }

    public Movement (double amount, Category category, boolean income, Date movement_date,
                     String description) {
        this.amount = amount;
        this.category = category;
        this.income = income;
        this.movement_date = movement_date;
        this.description = description;
        this.filePath = "";
    }

    public Movement (double amount, Category category, boolean income, Date movement_date,
                     String description, String filePath) {
        this.amount = amount;
        this.category = category;
        this.income = income;
        this.movement_date = movement_date;
        this.description = description;
        this.filePath = filePath;
    }

    public static void syncMovements (final Context context) {
        VolleyClient client = VolleyClient.getInstance(context);
        client.getMovements(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject response) {
                /*TODO make async task to compare de local data with de server data to add the
                lost elements*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                Toast.makeText(context, "Sync failed", Toast.LENGTH_SHORT).show();
            }
        });
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

    public String getFilePath () {
        return filePath;
    }

    public void setFilePath (String filePath) {
        this.filePath = filePath;
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
