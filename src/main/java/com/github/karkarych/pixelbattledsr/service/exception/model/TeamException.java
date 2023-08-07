package com.github.karkarych.pixelbattledsr.service.exception.model;

public class TeamException extends RuntimeException {

  protected Code code;

  protected TeamException(Code code, String msg) {
    this(code, null, msg);
  }

  protected TeamException(Code code, Throwable e, String msg) {
    super(msg, e);
    this.code = code;
  }

  public Code getCode() {
    return code;
  }

  public enum Code {

    TEAM_NOT_FOUND("Team not found"),
    TEAM_OWNER_MISMATCH("Team owner is different from user in update request"),
    TEAM_USER_IS_MEMBER("User is already a member of the team"),
    TEAM_USER_IS_NOT_MEMBER("User is not a member of the team"),
    ;

    private final String codeDescription;

    Code(String codeDescription) {
      this.codeDescription = codeDescription;
    }

    public String getCodeDescription() {
      return codeDescription;
    }

    public TeamException get() {
      return new TeamException(this, codeDescription);
    }

    public TeamException get(String msg) {
      return new TeamException(this, codeDescription + " : " + msg);
    }

    public TeamException get(Throwable e) {
      return new TeamException(this, e, codeDescription + " : " +
        e.getMessage());
    }

    public TeamException get(Throwable e, String msg) {
      return new TeamException(this, e, codeDescription + " : " + msg);
    }
  }
}
