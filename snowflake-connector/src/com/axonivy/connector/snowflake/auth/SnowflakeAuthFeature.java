package com.axonivy.connector.snowflake.auth;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class SnowflakeAuthFeature implements Feature {
  @Override
  public boolean configure(FeatureContext context) {
    context.register(new SnowflakeAuthorizationFilter(), Priorities.AUTHORIZATION);
    return true;
  }
}
