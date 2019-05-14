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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jens Schauder
 */
@Data
@Entity
public class Person {

	Person() {
	}

	Person(String firstName, Address address, Gender gender, Collection<Hobby> hobbies) {

		this.firstName = firstName;
		this.address = address;
		this.gender = gender;

		if (hobbies == null) {
			hobbies = null;
		} else {
			this.hobbies.addAll(hobbies);
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Version
	Long version;

	String firstName;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	Address address;

	@ManyToMany(cascade = CascadeType.ALL)
	Set<Hobby> hobbies = new HashSet<>();

	Gender gender;
}
