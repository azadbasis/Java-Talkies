package com.tmd.talkies.service.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable public final String message;
    public final int statusCode;

    private Resource(@NonNull Status status, @Nullable T data,
                     @Nullable String message, int statusCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null,200);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data, int statusCode) {
        return new Resource<>(Status.ERROR, data, msg,statusCode);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null,0);
    }



    public enum Status { SUCCESS, ERROR, LOADING }
}