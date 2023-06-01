/**
 * Copyright 2018 Dingfan Zhao
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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

//
//package org.mifos.chatbot.client.api;
//
//import org.mifos.chatbot.client.ApiException;
////import org.mifos.chatbot.client.model.AccountSummaryCollectionData;
////import org.mifos.chatbot.client.model.ClientData;
//import org.mifos.chatbot.client.model.CommandProcessingResult;
//import org.junit.Test;
//import org.junit.Ignore;
//
////import java.util.ArrayList;
////import java.util.HashMap;
////import java.util.List;
////import java.util.Map;
//
///**
// * API tests for ClientApiApi
// */
//@Ignore
//public class ClientApiApiTest {
//
//    private final ClientApiApi api = new ClientApiApi();
//
//
//    /**
//     * Activate a Client | Close a Client | Reject a Client | Withdraw a Client | Reactivate a Client | UndoReject a Client | UndoWithdraw a Client | Assign a Staff | Unassign a Staff | Update Default Savings Account | Propose a Client Transfer | Withdraw a Client Transfer | Reject a Client Transfer | Accept a Client Transfer | Propose and Accept a Client Transfer |
//     *
//     *  [Reject a Client] Mandatory Fields : rejectionDate, rejectionReasonId  [Withdraw a Client] Mandatory Fields : withdrawalDate, withdrawalReasonId  [Reactivate a Client] Mandatory Fields : reactivationDate  [UndoReject a Client] Mandatory Fields : reopenedDate  [UndoWithdraw a Client]Mandatory Fields :  reopenedDate
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void activateTest() throws ApiException {
//        Long clientId = null;
//        String body2 = null;
//        String command = null;
//        String body = null;
//        String response = api.activate(clientId, body2, command, body);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Create a Client
//     *
//     * Note: 1. You can enter either:firstname/middlename/lastname - for a person (middlename is optional) OR fullname - for a business or organisation (or person known by one name).  2.If address is enable(enable-address&#x3D;true), then additional field called address has to be passed.
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void createTest() throws ApiException {
//        ClientData body = null;
//        CommandProcessingResult response = api.create(body);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Delete a Client
//     *
//     * If a client is in Pending state, you are allowed to Delete it. The delete is a &#39;hard delete&#39; and cannot be recovered from. Once clients become active or have loans or savings associated with them, you cannot delete the client but you may Close the client if they have left the program.
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void deleteTest() throws ApiException {
//        Long clientId = null;
//        CommandProcessingResult response = api.delete(clientId);
//
//        // TODO: test validations
//    }
//
//    /**
//     * List Clients
//     *
//     * Example Requests:  clients  clients?fields&#x3D;displayName,officeName,timeline  clients?offset&#x3D;10&amp;limit&#x3D;50  clients?orderBy&#x3D;displayName&amp;sortOrder&#x3D;DESC
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void retrieveAllTest() throws ApiException {
//        String sqlSearch = null;
//        Long officeId = null;
//        String externalId = null;
//        String displayName = null;
//        String firstName = null;
//        String lastName = null;
//        String underHierarchy = null;
//        Integer offset = null;
//        Integer limit = null;
//        String orderBy = null;
//        String sortOrder = null;
//        Boolean orphansOnly = null;
//        ClientData response = api.retrieveAll(sqlSearch, officeId, externalId, displayName, firstName, lastName, underHierarchy, offset, limit, orderBy, sortOrder, orphansOnly);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Retrieve client accounts overview
//     *
//     * An example of how a loan portfolio summary can be provided. This is requested in a specific use case of the community application. It is quite reasonable to add resources like this to simplify User Interface development.  Example Requests:   clients/1/accounts  clients/1/accounts?fields&#x3D;loanAccounts,savingsAccounts
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void retrieveAssociatedAccountsTest() throws ApiException {
//        Long clientId = null;
//        AccountSummaryCollectionData response = api.retrieveAssociatedAccounts(clientId);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Retrieve a Client
//     *
//     * Example Requests:  clients/1   clients/1?template&#x3D;true   clients/1?fields&#x3D;id,displayName,officeName
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void retrieveOneTest() throws ApiException {
//        Long clientId = null;
//        Boolean staffInSelectedOfficeOnly = null;
//        ClientData response = api.retrieveOne(clientId, staffInSelectedOfficeOnly);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Retrieve Client Details Template
//     *
//     * This is a convenience resource. It can be useful when building maintenance user interface screens for client applications. The template data returned consists of any or all of:  Field Defaults Allowed Value ListsExample Request:  clients/template
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void retrieveTemplateTest() throws ApiException {
//        Long officeId = null;
//        String commandParam = null;
//        Boolean staffInSelectedOfficeOnly = null;
//        ClientData response = api.retrieveTemplate(officeId, commandParam, staffInSelectedOfficeOnly);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Update a Client
//     *
//     * You can update any of the basic attributes of a client (but not its associations) using this API.  Changing the relationship between a client and its office is not supported through this API. An API specific to handling transfers of clients between offices is available for the same.  The relationship between a client and a group must be removed through the Groups API.
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void updateTest() throws ApiException {
//        Long clientId = null;
//        ClientData body = null;
//        CommandProcessingResult response = api.update(clientId, body);
//
//        // TODO: test validations
//    }
//
//}
