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
package com.vigilanced.client.resource;

/**
 * The types of messages that are allowed.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum ResourceMessageType {

	/**
	 * Standard.
	 */
	NORMAL,
	/**
	 * The resource has become available.
	 */
	STARTUP,
	/**
	 * Results.
	 */
	RESULTS,
	/**
	 * The resource has shutdown (in such a way that permitted this message to
	 * be sent). Use this as more of a signal than a report, which
	 * {@link #RESULTS} is better suited for.
	 */
	SHUTDOWN;

}
