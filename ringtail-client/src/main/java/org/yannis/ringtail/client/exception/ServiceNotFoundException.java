package org.yannis.ringtail.client.exception;

/**
 * Created by dell on 2016/7/6.
 */
public class ServiceNotFoundException extends Exception {

    public ServiceNotFoundException() {
    }

    public ServiceNotFoundException(String message){
        super(message);
    }
}
