/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.vigilanced.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.PathVariable;

import com.ajah.thread.ThreadUtils;
import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;
import com.vigilanced.client.resource.ResourceMessageLevel;
import com.vigilanced.client.resource.ResourceMessageType;
import com.vigilanced.client.resource.ResourceStateType;

/**
 * Main client for the Vigilanced API.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class VigilancedClient {

	private static final String BASE_URL = "http://api.vigilanced.com/";

	private String apiKey;
	private HttpClient http;
	private long lastRequest;

	public VigilancedClientResult addMessage(String resourceKey, String message, ResourceMessageLevel level, ResourceMessageType type) {
		VigilancedClientResult result = new VigilancedClientResult();
		try {
			AjahUtils.requireParam(resourceKey, "resourceKey");
			AjahUtils.requireParam(message, "message");

			HttpPost post = new HttpPost(BASE_URL + "/api/1.0/resource/" + resourceKey + "/message");
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("message", message));
			if (level != null) {
				nvps.add(new BasicNameValuePair("level", level.name()));
			}
			if (type != null) {
				nvps.add(new BasicNameValuePair("type", type.name()));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps));

			HttpResponse response = this.http.execute(throttle(post));
			result.handle(response);

		} catch (Throwable t) {
			result.handle(t);
		}
		return result;
	}

	/**
	 * Keeps requests to no more than one every 100ms.
	 * 
	 * @param request
	 *            The request to throttle, if necessary.
	 * @return The request that was passed in.
	 */
	private synchronized HttpUriRequest throttle(HttpUriRequest request) {
		ThreadUtils.sleepUntil(this.lastRequest + 100);
		this.lastRequest = System.currentTimeMillis();
		return request;
	}

	public VigilancedClientResult updateState(@PathVariable final String resourceKey, final ResourceStateType type, final String state, final String detail) {
		VigilancedClientResult result = new VigilancedClientResult();
		try {
			AjahUtils.requireParam(resourceKey, "resourceKey");
			AjahUtils.requireParam(state, "state");

			HttpPost post = new HttpPost(BASE_URL + "/api/1.0/resource/" + resourceKey + "/detail");
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("state", state));
			if (type != null) {
				nvps.add(new BasicNameValuePair("type", type.name()));
			}
			if (!StringUtils.isBlank(detail)) {
				nvps.add(new BasicNameValuePair("detail", detail));
			}

			HttpResponse response = this.http.execute(throttle(post));
			result.handle(response);

		} catch (Throwable t) {
			result.handle(t);
		}
		return result;

	}

	public VigilancedClientResult updateMetric(@PathVariable final String resourceKey, final String name, final BigDecimal value, final String detail) {
		VigilancedClientResult result = new VigilancedClientResult();
		try {
			AjahUtils.requireParam(resourceKey, "resourceKey");
			AjahUtils.requireParam(name, "name");
			AjahUtils.requireParam(value, "value");

			HttpPost post = new HttpPost(BASE_URL + "/api/1.0/resource/" + resourceKey + "/metric/" + name);
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("value", value.toString()));
			if (!StringUtils.isBlank(detail)) {
				nvps.add(new BasicNameValuePair("detail", detail));
			}

			HttpResponse response = this.http.execute(throttle(post));
			result.handle(response);

		} catch (Throwable t) {
			result.handle(t);
		}
		return result;

	}

}
