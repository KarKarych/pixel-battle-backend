package com.github.karkarych.pixelbattledsr.service.exception.model;

public class UserException extends RuntimeException {

  protected Code code;

  protected UserException(Code code, String msg) {
    this(code, null, msg);
  }

  protected UserException(Code code, Throwable e, String msg) {
    super(msg, e);
    this.code = code;
  }

  public Code getCode() {
    return code;
  }

  public enum Code {

    USER_NOT_FOUND("User not found"),
    ;

    private final String codeDescription;

    Code(String codeDescription) {
      this.codeDescription = codeDescription;
    }

    public String getCodeDescription() {
      return codeDescription;
    }

    public UserException get() {
      return new UserException(this, codeDescription);
    }

    public UserException get(String msg) {
      return new UserException(this, codeDescription + " : " + msg);
    }

    public UserException get(Throwable e) {
      return new UserException(this, e, codeDescription + " : " +
        e.getMessage());
    }

    public UserException get(Throwable e, String msg) {
      return new UserException(this, e, codeDescription + " : " + msg);
    }
  }
}
