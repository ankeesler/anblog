package com.marshmallow.anblog;

public interface TokenValidator {
  /** Validate token and return subject. */
  String validate(final String token);
}
