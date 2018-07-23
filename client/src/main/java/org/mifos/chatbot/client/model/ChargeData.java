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
 * ChargeData
 */

public class ChargeData {
  @SerializedName("penalty")
  private Boolean penalty = null;

  @SerializedName("overdueInstallmentCharge")
  private Boolean overdueInstallmentCharge = null;

  public ChargeData penalty(Boolean penalty) {
    this.penalty = penalty;
    return this;
  }

   /**
   * Get penalty
   * @return penalty
  **/
  @ApiModelProperty(value = "")
  public Boolean isPenalty() {
    return penalty;
  }

  public void setPenalty(Boolean penalty) {
    this.penalty = penalty;
  }

  public ChargeData overdueInstallmentCharge(Boolean overdueInstallmentCharge) {
    this.overdueInstallmentCharge = overdueInstallmentCharge;
    return this;
  }

   /**
   * Get overdueInstallmentCharge
   * @return overdueInstallmentCharge
  **/
  @ApiModelProperty(value = "")
  public Boolean isOverdueInstallmentCharge() {
    return overdueInstallmentCharge;
  }

  public void setOverdueInstallmentCharge(Boolean overdueInstallmentCharge) {
    this.overdueInstallmentCharge = overdueInstallmentCharge;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChargeData chargeData = (ChargeData) o;
    return Objects.equals(this.penalty, chargeData.penalty) &&
        Objects.equals(this.overdueInstallmentCharge, chargeData.overdueInstallmentCharge);
  }

  @Override
  public int hashCode() {
    return Objects.hash(penalty, overdueInstallmentCharge);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChargeData {\n");
    
    sb.append("    penalty: ").append(toIndentedString(penalty)).append("\n");
    sb.append("    overdueInstallmentCharge: ").append(toIndentedString(overdueInstallmentCharge)).append("\n");
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

