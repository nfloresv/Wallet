package com.flores.nico.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flores.nico.database.Movement;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicoflores on 15-10-14.
 */
public class VolleyClient {
    // Volley
    private static VolleyClient volleyClient;
    // URLs
    private final String BASE_URL = "data.magnet.cl/";
    private final String USER_URL = BASE_URL + "users/";
    private final String MOVEMENT_URL = BASE_URL + "transactions/";
    // Params Name
    private final String SERIALIZED_EMAIL = "email";
    private final String SERIALIZED_FIRST_NAME = "first_name";
    private final String SERIALIZED_LAST_NAME = "last_name";
    private final String SERIALIZED_PASSWORD = "password";
    private RequestQueue queue;

    private VolleyClient (Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static VolleyClient getInstance (Context context) {
        if (volleyClient == null) {
            volleyClient = new VolleyClient(context);
        }
        return volleyClient;
    }

    /* TODO Maybe the generic is not necessary */
    public void signin (String email, String first_name, String last_name, String password,
                        Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<String, String>(4);
        params.put(SERIALIZED_EMAIL, email);
        params.put(SERIALIZED_FIRST_NAME, first_name);
        params.put(SERIALIZED_LAST_NAME, last_name);
        params.put(SERIALIZED_PASSWORD, password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, USER_URL,
                new JSONObject(params), successListener, errorListener);
        queue.add(request);
    }

    public void login (String email, String password, Response.Listener<JSONObject>
            successListener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<String, String>(2);
        params.put(SERIALIZED_EMAIL, email);
        params.put(SERIALIZED_PASSWORD, password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, USER_URL,
                new JSONObject(params), successListener, errorListener);
        queue.add(request);
    }

    /* TODO Maybe it is better to transform all to string */
    public void sendMovement (double amount, String category, String comment, Date date,
                              boolean income, Response.Listener<JSONObject> successListener,
                              Response.ErrorListener errorListener) {
        Map<String, Object> params = new HashMap<String, Object>(5);
        params.put(Movement.SERIALIZED_AMOUNT, amount);
        params.put(Movement.SERIALIZED_CATEGORY, category);
        params.put(Movement.SERIALIZED_COMMENT, comment);
        params.put(Movement.SERIALIZED_DATE, date);
        params.put(Movement.SERIALIZED_INCOME, income);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MOVEMENT_URL,
                new JSONObject(params), successListener, errorListener);
        queue.add(request);
    }

    public void getMovements (Response.Listener<JSONObject> successListener,
                              Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MOVEMENT_URL, null,
                successListener, errorListener);
        queue.add(request);
    }
}
