/*
 * Copyright 2015-2101 gaoxianglong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sharksharding.util.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author gaoxianglong
 *
 * @version 1.3.5
 */
@XmlRootElement(name = "bean")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bean {
	@XmlElement(name = "constructor-arg")
	private ConstructorArg constructor_arg;

	@XmlElement(name = "property")
	private List<Property> property;

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "class")
	private String class_;

	@XmlAttribute(name = "destroy-method")
	private String destroy_method;

	@XmlAttribute(name = "init-method")
	private String init_method;

	public String getInit_method() {
		return init_method;
	}

	public void setInit_method(String init_method) {
		this.init_method = init_method;
	}

	public String getDestroy_method() {
		return destroy_method;
	}

	public void setDestroy_method(String destroy_method) {
		this.destroy_method = destroy_method;
	}

	public ConstructorArg getConstructor_arg() {
		return constructor_arg;
	}

	public void setConstructor_arg(ConstructorArg constructor_arg) {
		this.constructor_arg = constructor_arg;
	}

	public List<Property> getProperty() {
		return property;
	}

	public void setProperty(List<Property> property) {
		this.property = property;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}
}