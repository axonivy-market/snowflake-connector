package com.snowflakecomputing.org.account.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class V2StatementsBody {

  @JsonProperty("statement")
  private String statement;

  @JsonProperty("timeout")
  private Long timeout;

  @JsonProperty("database")
  private String database;

  @JsonProperty("schema")
  private String schema;

  @JsonProperty("warehouse")
  private String warehouse;

  @JsonProperty("role")
  private String role;

  @JsonProperty("bindings")
  private Object bindings;

  @JsonProperty("parameters")
  private Apiv2statementsParameters parameters;

  public String getStatement() { return statement; }
  public void setStatement(String statement) { this.statement = statement; }

  public Long getTimeout() { return timeout; }
  public void setTimeout(Long timeout) { this.timeout = timeout; }

  public String getDatabase() { return database; }
  public void setDatabase(String database) { this.database = database; }

  public String getSchema() { return schema; }
  public void setSchema(String schema) { this.schema = schema; }

  public String getWarehouse() { return warehouse; }
  public void setWarehouse(String warehouse) { this.warehouse = warehouse; }

  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }

  public Object getBindings() { return bindings; }
  public void setBindings(Object bindings) { this.bindings = bindings; }

  public Apiv2statementsParameters getParameters() { return parameters; }
  public void setParameters(Apiv2statementsParameters parameters) { this.parameters = parameters; }
}
