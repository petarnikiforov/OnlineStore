package org.online.store.error;

public class NotFoundObjectException extends RuntimeException{
    private final String objectClazz;
    private final String id;

    public NotFoundObjectException(String message, String objectClazz, String id){
        super(message);
        this.objectClazz = objectClazz;
        this.id = id;
    }
}
