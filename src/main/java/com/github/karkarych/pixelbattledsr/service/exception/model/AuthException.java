package com.github.karkarych.pixelbattledsr.service.exception.model;

public class AuthException extends RuntimeException {

  protected Code code;

  protected AuthException(Code code, String msg) {
    this(code, null, msg);
  }

  protected AuthException(Code code, Throwable e, String msg) {
    super(msg, e);
    this.code = code;
  }

  public Code getCode() {
    return code;
  }

  public enum Code {

    AUTH_PASSWORD_DOES_NOT_MATCH("Password does not match"),
    AUTH_LOGIN_EXISTS("Login exists"),
    ;

    private final String codeDescription;

    Code(String codeDescription) {
      this.codeDescription = codeDescription;
    }

    public String getCodeDescription() {
      return codeDescription;
    }

    public AuthException get() {
      return new AuthException(this, codeDescription);
    }

    public AuthException get(String msg) {
      return new AuthException(this, codeDescription + " : " + msg);
    }

    public AuthException get(Throwable e) {
      return new AuthException(this, e, codeDescription + " : " +
        e.getMessage());
    }

    public AuthException get(Throwable e, String msg) {
      return new AuthException(this, e, codeDescription + " : " + msg);
    }
  }
}
