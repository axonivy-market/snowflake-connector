package com.axonivy.connector.snowflake.auth;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

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
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
      ObjectMapper mapper = super.locateMapper(type, mediaType);
      mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      return mapper;
    }
  }
}
