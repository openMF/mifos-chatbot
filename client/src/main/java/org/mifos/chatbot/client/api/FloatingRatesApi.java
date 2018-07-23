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


package org.mifos.chatbot.client.api;

import org.mifos.chatbot.client.ApiCallback;
import org.mifos.chatbot.client.ApiClient;
import org.mifos.chatbot.client.ApiException;
import org.mifos.chatbot.client.ApiResponse;
import org.mifos.chatbot.client.Configuration;
import org.mifos.chatbot.client.Pair;
import org.mifos.chatbot.client.ProgressRequestBody;
import org.mifos.chatbot.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.mifos.chatbot.client.model.GetFloatingRatesFloatingRateIdResponse;
import org.mifos.chatbot.client.model.GetFloatingRatesResponse;
import org.mifos.chatbot.client.model.PostFloatingRatesRequest;
import org.mifos.chatbot.client.model.PostFloatingRatesResponse;
import org.mifos.chatbot.client.model.PutFloatingRatesFloatingRateIdRequest;
import org.mifos.chatbot.client.model.PutFloatingRatesFloatingRateIdResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FloatingRatesApi {
    private ApiClient apiClient;

    public FloatingRatesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public FloatingRatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for createFloatingRate
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call createFloatingRateCall(PostFloatingRatesRequest body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/floatingrates";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call createFloatingRateValidateBeforeCall(PostFloatingRatesRequest body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling createFloatingRate(Async)");
        }
        

        com.squareup.okhttp.Call call = createFloatingRateCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Create a new Floating Rate
     * Creates a new Floating Rate Mandatory Fields: name Optional Fields: isBaseLendingRate, isActive, ratePeriods
     * @param body body (required)
     * @return PostFloatingRatesResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public PostFloatingRatesResponse createFloatingRate(PostFloatingRatesRequest body) throws ApiException {
        ApiResponse<PostFloatingRatesResponse> resp = createFloatingRateWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Create a new Floating Rate
     * Creates a new Floating Rate Mandatory Fields: name Optional Fields: isBaseLendingRate, isActive, ratePeriods
     * @param body body (required)
     * @return ApiResponse&lt;PostFloatingRatesResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<PostFloatingRatesResponse> createFloatingRateWithHttpInfo(PostFloatingRatesRequest body) throws ApiException {
        com.squareup.okhttp.Call call = createFloatingRateValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<PostFloatingRatesResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Create a new Floating Rate (asynchronously)
     * Creates a new Floating Rate Mandatory Fields: name Optional Fields: isBaseLendingRate, isActive, ratePeriods
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call createFloatingRateAsync(PostFloatingRatesRequest body, final ApiCallback<PostFloatingRatesResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = createFloatingRateValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<PostFloatingRatesResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for retrieveAll
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call retrieveAllCall(final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/floatingrates";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call retrieveAllValidateBeforeCall(final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        

        com.squareup.okhttp.Call call = retrieveAllCall(progressListener, progressRequestListener);
        return call;

    }

    /**
     * List Floating Rates
     * Lists Floating Rates
     * @return List&lt;GetFloatingRatesResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<GetFloatingRatesResponse> retrieveAll() throws ApiException {
        ApiResponse<List<GetFloatingRatesResponse>> resp = retrieveAllWithHttpInfo();
        return resp.getData();
    }

    /**
     * List Floating Rates
     * Lists Floating Rates
     * @return ApiResponse&lt;List&lt;GetFloatingRatesResponse&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<GetFloatingRatesResponse>> retrieveAllWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = retrieveAllValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeToken<List<GetFloatingRatesResponse>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * List Floating Rates (asynchronously)
     * Lists Floating Rates
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call retrieveAllAsync(final ApiCallback<List<GetFloatingRatesResponse>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = retrieveAllValidateBeforeCall(progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<GetFloatingRatesResponse>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for retrieveOne
     * @param floatingRateId floatingRateId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call retrieveOneCall(Long floatingRateId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/floatingrates/{floatingRateId}"
            .replaceAll("\\{" + "floatingRateId" + "\\}", apiClient.escapeString(floatingRateId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call retrieveOneValidateBeforeCall(Long floatingRateId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'floatingRateId' is set
        if (floatingRateId == null) {
            throw new ApiException("Missing the required parameter 'floatingRateId' when calling retrieveOne(Async)");
        }
        

        com.squareup.okhttp.Call call = retrieveOneCall(floatingRateId, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieve Floating Rate
     * Retrieves Floating Rate
     * @param floatingRateId floatingRateId (required)
     * @return GetFloatingRatesFloatingRateIdResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public GetFloatingRatesFloatingRateIdResponse retrieveOne(Long floatingRateId) throws ApiException {
        ApiResponse<GetFloatingRatesFloatingRateIdResponse> resp = retrieveOneWithHttpInfo(floatingRateId);
        return resp.getData();
    }

    /**
     * Retrieve Floating Rate
     * Retrieves Floating Rate
     * @param floatingRateId floatingRateId (required)
     * @return ApiResponse&lt;GetFloatingRatesFloatingRateIdResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<GetFloatingRatesFloatingRateIdResponse> retrieveOneWithHttpInfo(Long floatingRateId) throws ApiException {
        com.squareup.okhttp.Call call = retrieveOneValidateBeforeCall(floatingRateId, null, null);
        Type localVarReturnType = new TypeToken<GetFloatingRatesFloatingRateIdResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieve Floating Rate (asynchronously)
     * Retrieves Floating Rate
     * @param floatingRateId floatingRateId (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call retrieveOneAsync(Long floatingRateId, final ApiCallback<GetFloatingRatesFloatingRateIdResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = retrieveOneValidateBeforeCall(floatingRateId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<GetFloatingRatesFloatingRateIdResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for updateFloatingRate
     * @param floatingRateId floatingRateId (required)
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call updateFloatingRateCall(Long floatingRateId, PutFloatingRatesFloatingRateIdRequest body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/floatingrates/{floatingRateId}"
            .replaceAll("\\{" + "floatingRateId" + "\\}", apiClient.escapeString(floatingRateId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call updateFloatingRateValidateBeforeCall(Long floatingRateId, PutFloatingRatesFloatingRateIdRequest body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'floatingRateId' is set
        if (floatingRateId == null) {
            throw new ApiException("Missing the required parameter 'floatingRateId' when calling updateFloatingRate(Async)");
        }
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling updateFloatingRate(Async)");
        }
        

        com.squareup.okhttp.Call call = updateFloatingRateCall(floatingRateId, body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Update Floating Rate
     * Updates new Floating Rate. Rate Periods in the past cannot be modified. All the future rateperiods would be replaced with the new ratePeriods data sent.
     * @param floatingRateId floatingRateId (required)
     * @param body body (required)
     * @return PutFloatingRatesFloatingRateIdResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public PutFloatingRatesFloatingRateIdResponse updateFloatingRate(Long floatingRateId, PutFloatingRatesFloatingRateIdRequest body) throws ApiException {
        ApiResponse<PutFloatingRatesFloatingRateIdResponse> resp = updateFloatingRateWithHttpInfo(floatingRateId, body);
        return resp.getData();
    }

    /**
     * Update Floating Rate
     * Updates new Floating Rate. Rate Periods in the past cannot be modified. All the future rateperiods would be replaced with the new ratePeriods data sent.
     * @param floatingRateId floatingRateId (required)
     * @param body body (required)
     * @return ApiResponse&lt;PutFloatingRatesFloatingRateIdResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<PutFloatingRatesFloatingRateIdResponse> updateFloatingRateWithHttpInfo(Long floatingRateId, PutFloatingRatesFloatingRateIdRequest body) throws ApiException {
        com.squareup.okhttp.Call call = updateFloatingRateValidateBeforeCall(floatingRateId, body, null, null);
        Type localVarReturnType = new TypeToken<PutFloatingRatesFloatingRateIdResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Update Floating Rate (asynchronously)
     * Updates new Floating Rate. Rate Periods in the past cannot be modified. All the future rateperiods would be replaced with the new ratePeriods data sent.
     * @param floatingRateId floatingRateId (required)
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call updateFloatingRateAsync(Long floatingRateId, PutFloatingRatesFloatingRateIdRequest body, final ApiCallback<PutFloatingRatesFloatingRateIdResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = updateFloatingRateValidateBeforeCall(floatingRateId, body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<PutFloatingRatesFloatingRateIdResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
