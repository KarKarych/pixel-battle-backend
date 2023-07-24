package com.github.karkarych.pixelbattledsr.service.exception.model;

public class FieldException extends RuntimeException {

  protected Code code;

  protected FieldException(Code code, String msg) {
    this(code, null, msg);
  }

  protected FieldException(Code code, Throwable e, String msg) {
    super(msg, e);
    this.code = code;
  }

  public Code getCode() {
    return code;
  }

  public enum Code {

    FIELD_ROW_OUT_OF_RANGE("Field row out of range"),
    FIELD_COLUMN_OUT_OF_RANGE("Field column out of range"),
    FIELD_TOO_MANY_REQUEST_UPDATE("Too many requests for field update"),
    FIELD_NOT_FOUND("Field not found. Internal error"),
    ;

    private final String codeDescription;

    Code(String codeDescription) {
      this.codeDescription = codeDescription;
    }

    public String getCodeDescription() {
      return codeDescription;
    }

    public FieldException get() {
      return new FieldException(this, codeDescription);
    }

    public FieldException get(String msg) {
      return new FieldException(this, codeDescription + " : " + msg);
    }

    public FieldException get(Throwable e) {
      return new FieldException(this, e, codeDescription + " : " +
        e.getMessage());
    }

    public FieldException get(Throwable e, String msg) {
      return new FieldException(this, e, codeDescription + " : " + msg);
    }
  }
}
