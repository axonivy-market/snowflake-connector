package com.axonivy.connector.snowflake.auth;

import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.core.MediaType;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.jakarta.rs.json.JacksonJsonProvider;
import ch.ivyteam.ivy.rest.client.mapper.JsonFeature;

public class DataJsonFeature extends JsonFeature {

  @Override
  public boolean configure(FeatureContext context) {
    JacksonJsonProvider provider = new JaxRsClientJson();
    configure(provider, context.getConfiguration());
    context.register(provider, Priorities.ENTITY_CODER);
    return true;
  }

  public static class JaxRsClientJson extends JacksonJsonProvider {
    @Override
    public JsonMapper locateMapper(Class<?> type, MediaType mediaType) {
      configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      return super.locateMapper(type, mediaType);
    }
  }
}
