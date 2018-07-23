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

/**
 * GetSavingsStatus
 */

public class GetSavingsStatus {
  @SerializedName("id")
  private Integer id = null;

  @SerializedName("code")
  private String code = null;

  @SerializedName("value")
  private String value = null;

  @SerializedName("submittedAndPendingApproval")
  private Boolean submittedAndPendingApproval = null;

  @SerializedName("approved")
  private Boolean approved = null;

  @SerializedName("rejected")
  private Boolean rejected = null;

  @SerializedName("withdrawnByApplicant")
  private Boolean withdrawnByApplicant = null;

  @SerializedName("active")
  private Boolean active = null;

  @SerializedName("closed")
  private Boolean closed = null;

  public GetSavingsStatus id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "100", value = "")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public GetSavingsStatus code(String code) {
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
  @ApiModelProperty(example = "savingsAccountStatusType.submitted.and.pending.approval", value = "")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public GetSavingsStatus value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(example = "Submitted and pending approval", value = "")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public GetSavingsStatus submittedAndPendingApproval(Boolean submittedAndPendingApproval) {
    this.submittedAndPendingApproval = submittedAndPendingApproval;
    return this;
  }

   /**
   * Get submittedAndPendingApproval
   * @return submittedAndPendingApproval
  **/
  @ApiModelProperty(example = "true", value = "")
  public Boolean isSubmittedAndPendingApproval() {
    return submittedAndPendingApproval;
  }

  public void setSubmittedAndPendingApproval(Boolean submittedAndPendingApproval) {
    this.submittedAndPendingApproval = submittedAndPendingApproval;
  }

  public GetSavingsStatus approved(Boolean approved) {
    this.approved = approved;
    return this;
  }

   /**
   * Get approved
   * @return approved
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean isApproved() {
    return approved;
  }

  public void setApproved(Boolean approved) {
    this.approved = approved;
  }

  public GetSavingsStatus rejected(Boolean rejected) {
    this.rejected = rejected;
    return this;
  }

   /**
   * Get rejected
   * @return rejected
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean isRejected() {
    return rejected;
  }

  public void setRejected(Boolean rejected) {
    this.rejected = rejected;
  }

  public GetSavingsStatus withdrawnByApplicant(Boolean withdrawnByApplicant) {
    this.withdrawnByApplicant = withdrawnByApplicant;
    return this;
  }

   /**
   * Get withdrawnByApplicant
   * @return withdrawnByApplicant
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean isWithdrawnByApplicant() {
    return withdrawnByApplicant;
  }

  public void setWithdrawnByApplicant(Boolean withdrawnByApplicant) {
    this.withdrawnByApplicant = withdrawnByApplicant;
  }

  public GetSavingsStatus active(Boolean active) {
    this.active = active;
    return this;
  }

   /**
   * Get active
   * @return active
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public GetSavingsStatus closed(Boolean closed) {
    this.closed = closed;
    return this;
  }

   /**
   * Get closed
   * @return closed
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean isClosed() {
    return closed;
  }

  public void setClosed(Boolean closed) {
    this.closed = closed;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetSavingsStatus getSavingsStatus = (GetSavingsStatus) o;
    return Objects.equals(this.id, getSavingsStatus.id) &&
        Objects.equals(this.code, getSavingsStatus.code) &&
        Objects.equals(this.value, getSavingsStatus.value) &&
        Objects.equals(this.submittedAndPendingApproval, getSavingsStatus.submittedAndPendingApproval) &&
        Objects.equals(this.approved, getSavingsStatus.approved) &&
        Objects.equals(this.rejected, getSavingsStatus.rejected) &&
        Objects.equals(this.withdrawnByApplicant, getSavingsStatus.withdrawnByApplicant) &&
        Objects.equals(this.active, getSavingsStatus.active) &&
        Objects.equals(this.closed, getSavingsStatus.closed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, value, submittedAndPendingApproval, approved, rejected, withdrawnByApplicant, active, closed);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetSavingsStatus {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    submittedAndPendingApproval: ").append(toIndentedString(submittedAndPendingApproval)).append("\n");
    sb.append("    approved: ").append(toIndentedString(approved)).append("\n");
    sb.append("    rejected: ").append(toIndentedString(rejected)).append("\n");
    sb.append("    withdrawnByApplicant: ").append(toIndentedString(withdrawnByApplicant)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    closed: ").append(toIndentedString(closed)).append("\n");
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

