/*
 * Apache Fineract API Documentation
 * Apache Fineract is a secure, multi-tenanted microfinance platform. <br />              The goal of the Apache Fineract API is to empower developers to build apps on top of the Apache Fineract Platform. The reference app [  https://demo.openmf.org  ] (username: mifos, password: password) works on the same demo tenant as the interactive links in this documentation.              <br/>The API                 is organized around REST [ https://en.wikipedia.org/wiki/Representational_state_transfer ]               <br/> Find out more about Apache Fineract on [ https://demo.openmf.org/api-docs/apiLive.htm#top ]              <br/> You can Try The API From Your Browser itself at [ https://demo.openmf.org/api-docs/apiLive.htm#interact ]              <br/> The Generic Options are available at [ https://demo.openmf.org/api-docs/apiLive.htm#genopts ]              <br/> Find out more about Updating Dates and Numbers at [ https://demo.openmf.org/api-docs/apiLive.htm#dates_and_numbers ]              <br/> For the Authentication and the Basic of HTTP and HTTPS refer [ https://demo.openmf.org/api-docs/apiLive.htm#authentication_overview ]              <br/> Check about ERROR codes at [ https://demo.openmf.org/api-docs/apiLive.htm#errors ]               <br/> <br/> Please refer to the old documentation for any documentation queries [ https://demo.openmf.org/api-docs/apiLive.htm ]              <br/>             ______________________________________________________________________________________________________________________________          
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.mifos.chatbot.client.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import org.mifos.chatbot.client.model.EnumOptionData;

import java.math.BigDecimal;

/**
 * JournalEntryData
 */

public class JournalEntryData {
  @SerializedName("id")
  private Long id = null;

  @SerializedName("officeId")
  private Long officeId = null;

  @SerializedName("glAccountId")
  private Long glAccountId = null;

  @SerializedName("glAccountType")
  private EnumOptionData glAccountType = null;

  @SerializedName("entryType")
  private EnumOptionData entryType = null;

  @SerializedName("amount")
  private BigDecimal amount = null;

  @SerializedName("transactionId")
  private String transactionId = null;

  public JournalEntryData id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public JournalEntryData officeId(Long officeId) {
    this.officeId = officeId;
    return this;
  }

   /**
   * Get officeId
   * @return officeId
  **/
  @ApiModelProperty(value = "")
  public Long getOfficeId() {
    return officeId;
  }

  public void setOfficeId(Long officeId) {
    this.officeId = officeId;
  }

  public JournalEntryData glAccountId(Long glAccountId) {
    this.glAccountId = glAccountId;
    return this;
  }

   /**
   * Get glAccountId
   * @return glAccountId
  **/
  @ApiModelProperty(value = "")
  public Long getGlAccountId() {
    return glAccountId;
  }

  public void setGlAccountId(Long glAccountId) {
    this.glAccountId = glAccountId;
  }

  public JournalEntryData glAccountType(EnumOptionData glAccountType) {
    this.glAccountType = glAccountType;
    return this;
  }

   /**
   * Get glAccountType
   * @return glAccountType
  **/
  @ApiModelProperty(value = "")
  public EnumOptionData getGlAccountType() {
    return glAccountType;
  }

  public void setGlAccountType(EnumOptionData glAccountType) {
    this.glAccountType = glAccountType;
  }

  public JournalEntryData entryType(EnumOptionData entryType) {
    this.entryType = entryType;
    return this;
  }

   /**
   * Get entryType
   * @return entryType
  **/
  @ApiModelProperty(value = "")
  public EnumOptionData getEntryType() {
    return entryType;
  }

  public void setEntryType(EnumOptionData entryType) {
    this.entryType = entryType;
  }

  public JournalEntryData amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public JournalEntryData transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

   /**
   * Get transactionId
   * @return transactionId
  **/
  @ApiModelProperty(value = "")
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JournalEntryData journalEntryData = (JournalEntryData) o;
    return Objects.equals(this.id, journalEntryData.id) &&
        Objects.equals(this.officeId, journalEntryData.officeId) &&
        Objects.equals(this.glAccountId, journalEntryData.glAccountId) &&
        Objects.equals(this.glAccountType, journalEntryData.glAccountType) &&
        Objects.equals(this.entryType, journalEntryData.entryType) &&
        Objects.equals(this.amount, journalEntryData.amount) &&
        Objects.equals(this.transactionId, journalEntryData.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, officeId, glAccountId, glAccountType, entryType, amount, transactionId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JournalEntryData {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    officeId: ").append(toIndentedString(officeId)).append("\n");
    sb.append("    glAccountId: ").append(toIndentedString(glAccountId)).append("\n");
    sb.append("    glAccountType: ").append(toIndentedString(glAccountType)).append("\n");
    sb.append("    entryType: ").append(toIndentedString(entryType)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

