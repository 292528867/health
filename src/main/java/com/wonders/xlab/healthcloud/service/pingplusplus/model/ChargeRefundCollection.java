package com.wonders.xlab.healthcloud.service.pingplusplus.model;

import com.wonders.xlab.healthcloud.service.pingplusplus.Pingpp;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.APIConnectionException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.APIException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.AuthenticationException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.InvalidRequestException;
import com.wonders.xlab.healthcloud.service.pingplusplus.net.APIResource;

import java.util.Map;

public class ChargeRefundCollection extends PingppColllectionAPIResource<Refund> {
	public ChargeRefundCollection all(Map<String, Object> params)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return all(params, null);
	}

	public ChargeRefundCollection all(Map<String, Object> params,
			String apiKey) throws AuthenticationException,
			InvalidRequestException, APIConnectionException,
			APIException {
		String url = String.format("%s%s", Pingpp.getApiBase(), this.getURL());
		return request(APIResource.RequestMethod.GET, url, params,
				ChargeRefundCollection.class, apiKey);
	}

	public Refund retrieve(String id)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return retrieve(id, null);
	}

	public Refund retrieve(String id, String apiKey) throws AuthenticationException,
			InvalidRequestException, APIConnectionException,
			APIException {
		String url = String.format("%s%s/%s", Pingpp.getApiBase(), this.getURL(), id);
		return request(APIResource.RequestMethod.GET, url, null,
				Refund.class, apiKey);
	}

	public Refund create(Map<String, Object> params)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return create(params, null);
	}

	public Refund create(Map<String, Object> params,
			String apiKey) throws AuthenticationException,
			InvalidRequestException, APIConnectionException,
			APIException {
		String url = String.format("%s%s", Pingpp.getApiBase(), this.getURL());
		return request(APIResource.RequestMethod.POST, url, params,
				Refund.class, apiKey);
	}
}
