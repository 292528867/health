package com.wonders.xlab.healthcloud.service.pingplusplus.model;

import com.wonders.xlab.healthcloud.service.pingplusplus.exception.APIConnectionException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.APIException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.AuthenticationException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.InvalidRequestException;

import java.util.Map;

/**
 * Common interface for Pingpp objects that can store metadata.
 */
public interface MetadataStore<T> {
	Map<String, String> getMetadata();
	void setMetadata(Map<String, String> metadata);

	MetadataStore<T> update(Map<String, Object> params) throws AuthenticationException, InvalidRequestException,
		APIConnectionException, APIException;
	MetadataStore<T> update(Map<String, Object> params, String apiKey) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException;
}
