package com.axonivy.connector.snowflake.auth;

import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;

public class SnowflakeAuthFeature implements Feature {
  @Override
  public boolean configure(FeatureContext context) {
    context.register(new SnowflakeAuthorizationFilter(), Priorities.AUTHORIZATION);
    return true;
  }
}
