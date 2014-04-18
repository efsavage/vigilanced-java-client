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

import lombok.Data;
import lombok.extern.java.Log;

import org.apache.http.HttpResponse;

/**
 * The result of any API operation.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@Log
public class VigilancedClientResult {

	private boolean success;
	private Throwable error;

	/**
	 * Handles exceptions that might occur as a result of calling the API.
	 * 
	 * @param _error
	 *            The error.
	 */
	public void handle(Throwable _error) {
		this.error = _error;
	}

	/**
	 * Handles a response.
	 * 
	 * @param response
	 *            The response of the API call.
	 */
	public void handle(HttpResponse response) {
		log.finest(response.getStatusLine().toString());
		if (response.getStatusLine().getStatusCode() == 200) {
			setSuccess(true);
		}
	}

}
