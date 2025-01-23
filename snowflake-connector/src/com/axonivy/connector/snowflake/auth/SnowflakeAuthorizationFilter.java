package com.axonivy.connector.snowflake.auth;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import ch.ivyteam.ivy.rest.client.FeatureConfig;

public class SnowflakeAuthorizationFilter implements ClientRequestFilter {
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER_TOKEN = "Bearer %s";
  private static final String AUTHORIZATION_TOKEN_TYPE_HEADER = "x-snowflake-authorization-token-type";
  private static final String KEYPAIR_JWT_TOKEN_TYPE = "KEYPAIR_JWT";

  public static interface Property {
    String JWT_TOKEN = "AUTH.JWTToken";
  }

  @Override
  public void filter(ClientRequestContext ctxt) throws IOException {
    if (ctxt.getHeaders().containsKey(AUTHORIZATION)) {
      return;
    }

    var config = new FeatureConfig(ctxt.getConfiguration(), SnowflakeAuthFeature.class);
    String token = config.readMandatory(Property.JWT_TOKEN);
    ctxt.getHeaders().add(AUTHORIZATION, String.format(BEARER_TOKEN, token));
    ctxt.getHeaders().add(AUTHORIZATION_TOKEN_TYPE_HEADER, KEYPAIR_JWT_TOKEN_TYPE);
  }
}
