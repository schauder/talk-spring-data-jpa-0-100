/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.jpa;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author Jens Schauder
 */
@Data
@Entity
public class Address {

	Address(){}

	Address(String zipCode, String city, String state) {

		this.zipCode = zipCode;
		this.city = city;
		this.state = state;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Version
	Long version;

	String zipCode;
	String city;
	String state;
}
