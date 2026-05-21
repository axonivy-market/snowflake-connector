package com.snowflakecomputing.org.account.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Apiv2statementsParameters {

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("query_tag")
  private String queryTag;

  @JsonProperty("binary_output_format")
  private String binaryOutputFormat;

  @JsonProperty("date_output_format")
  private String dateOutputFormat;

  @JsonProperty("time_output_format")
  private String timeOutputFormat;

  @JsonProperty("timestamp_output_format")
  private String timestampOutputFormat;

  @JsonProperty("timestamp_ltz_output_format")
  private String timestampLtzOutputFormat;

  @JsonProperty("timestamp_ntz_output_format")
  private String timestampNtzOutputFormat;

  @JsonProperty("timestamp_tz_output_format")
  private String timestampTzOutputFormat;

  @JsonProperty("multi_statement_count")
  private Integer multiStatementCount;

  public String getTimezone() { return timezone; }
  public void setTimezone(String timezone) { this.timezone = timezone; }

  public String getQueryTag() { return queryTag; }
  public void setQueryTag(String queryTag) { this.queryTag = queryTag; }

  public String getBinaryOutputFormat() { return binaryOutputFormat; }
  public void setBinaryOutputFormat(String binaryOutputFormat) { this.binaryOutputFormat = binaryOutputFormat; }

  public String getDateOutputFormat() { return dateOutputFormat; }
  public void setDateOutputFormat(String dateOutputFormat) { this.dateOutputFormat = dateOutputFormat; }

  public String getTimeOutputFormat() { return timeOutputFormat; }
  public void setTimeOutputFormat(String timeOutputFormat) { this.timeOutputFormat = timeOutputFormat; }

  public String getTimestampOutputFormat() { return timestampOutputFormat; }
  public void setTimestampOutputFormat(String timestampOutputFormat) { this.timestampOutputFormat = timestampOutputFormat; }

  public String getTimestampLtzOutputFormat() { return timestampLtzOutputFormat; }
  public void setTimestampLtzOutputFormat(String timestampLtzOutputFormat) { this.timestampLtzOutputFormat = timestampLtzOutputFormat; }

  public String getTimestampNtzOutputFormat() { return timestampNtzOutputFormat; }
  public void setTimestampNtzOutputFormat(String timestampNtzOutputFormat) { this.timestampNtzOutputFormat = timestampNtzOutputFormat; }

  public String getTimestampTzOutputFormat() { return timestampTzOutputFormat; }
  public void setTimestampTzOutputFormat(String timestampTzOutputFormat) { this.timestampTzOutputFormat = timestampTzOutputFormat; }

  public Integer getMultiStatementCount() { return multiStatementCount; }
  public void setMultiStatementCount(Integer multiStatementCount) { this.multiStatementCount = multiStatementCount; }
}
