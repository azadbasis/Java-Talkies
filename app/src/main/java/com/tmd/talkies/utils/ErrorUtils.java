package com.tmd.talkies.utils;

import com.tmd.talkies.service.network.APIError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ErrorUtils {

    public static String getErrorMessages(final retrofit2.Response<?> response) {
        try {
            ResponseBody responseBody = response.errorBody();
            return responseBody == null ? response.message() : responseBody.string();
        } catch (IOException e) {
            return response.message();
        }
    }
    public static APIError getApiError(int responseCode, Response<?> response) {
        APIError   apiError=  new APIError();
        try {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response.errorBody().string());
                String errorMessage = jsonObject.getString("error");
                apiError.setMessage(errorMessage);
                apiError.setStatusCode(responseCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiError;

    }
    public static String  getErrorMessage(Response<?> response) {
        String errorMessage = null;
        try {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response.errorBody().string());
                errorMessage = jsonObject.getString("error");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorMessage;
    }

}

