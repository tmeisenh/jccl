/* $Id$ */
/*
 * Copyright (c) 2005 - 2007 Travis B. Meisenheimer
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.indexoutofbounds.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p>Simple class which allows other objects to extend in order to take advantage of
 * Commons-Lang's reflection toString(), equals(), and hashCode().</p>
 * 
 * <p>Debugging statements on POJO subclasses should probablly be wrapped in and if
 * statement to avoid the performance cost associated with the reflective toString().</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 * @version $LastChangedRevision$
 */

public class ReflectionObject implements Serializable {
	
	/**
	 * Default (empty) constructor.
	 */
	public ReflectionObject() {
		super();
	}

	/**
	 * toString() which uses Jakarta-Commons ReflecttionToStringBuilder.
	 * 
	 * @return String reprensentation.
	 */
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);			
	}

	/**
	 * equals() which uses Jakarta-Commons EqualsBuilder.
	 * 
	 * @return boolean representing equality.
	 */	
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	/**
	 * hashCode() which uses Jakarta-Commons HashCodeBuilder.
	 * 
	 * @return int containing object's hash code.
	 */	
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
}
