package com.co.uniquindio.microservicios.util;

import java.text.MessageFormat;

public final class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final ServiceError serviceError;

  public ServiceException(final ServiceError serviceError) {
    super(serviceError.getMessage());
    this.serviceError = serviceError;
  }

  public static ServiceException badRequest(final String message, final Object... args) {
    return new ServiceException(ServiceError
        .create(400)
        .message(MessageFormat.format(message, args))
        .build());
  }

  public static ServiceException notFound(final String message, final Object... args) {
    return new ServiceException(ServiceError
        .create(404)
        .message(MessageFormat.format(message, args))
        .build());
  }

  public static ServiceException conflict(final String message, final Object... args) {

    return new ServiceException(ServiceError
        .create(409)
        .message(MessageFormat.format(message, args))
        .build());
  }

  public static ServiceException internalError(final String message, final Object... args) {
    return new ServiceException(ServiceError
        .create(500)
        .message(MessageFormat.format(message, args))
        .build());
  }

  public ServiceError serviceError() {
    return this.serviceError;
  }

  @Override
  public String toString() {
    return "ServiceException{" +
            "serviceError=" + serviceError +
            '}';
  }
}
