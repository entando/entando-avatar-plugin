package org.entando.plugin.avatar.service.impl;

public class AvatarUploadException extends RuntimeException {

    public AvatarUploadException() {
    }

    public AvatarUploadException(String message) {
        super(message);
    }

    public AvatarUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvatarUploadException(Throwable cause) {
        super(cause);
    }

    public AvatarUploadException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
