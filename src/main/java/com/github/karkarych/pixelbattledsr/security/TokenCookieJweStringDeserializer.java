package com.github.karkarych.pixelbattledsr.security;

import com.github.karkarych.pixelbattledsr.service.model.auth.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class TokenCookieJweStringDeserializer implements Function<String, Token> {

  private final JWEDecrypter jweDecrypter;

  @Override
  public Token apply(String string) {
    try {
      EncryptedJWT encryptedJWT = EncryptedJWT.parse(string);
      encryptedJWT.decrypt(this.jweDecrypter);
      JWTClaimsSet claimsSet = encryptedJWT.getJWTClaimsSet();
      return new Token(
        UUID.fromString(claimsSet.getJWTID()),
        claimsSet.getSubject(),
        claimsSet.getStringListClaim("authorities"),
        claimsSet.getIssueTime().toInstant(),
        claimsSet.getExpirationTime().toInstant()
      );
    } catch (ParseException | JOSEException exception) {
      log.error(exception.getMessage(), exception);
    }

    return null;
  }
}
