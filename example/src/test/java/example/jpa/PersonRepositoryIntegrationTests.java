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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static example.jpa.PersonTestUtil.*;
import static org.assertj.core.api.Assertions.*;

/**
 * @author Jens Schauder
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class PersonRepositoryIntegrationTests {

	@Autowired
	PersonRepository persons;

	Person p = null;

	@Before
	public void before() {
		p = persons.save(createPerson());
	}


	@Test
	public void crud() {


		assertThat(persons.findAll())
				.containsExactly(p);

		persons.deleteAll();

		assertThat(persons.findAll())
				.hasSize(0);

	}

	@Test
	public void derivedQueriesSimple() {

		assertThat(persons.findByFirstName("Jens"))
				.containsExactly(p);

	}

	@Test
	public void derivedQueriesInvolved() {

		assertThat(persons.existsByAddress_CityContainsIgnoreCase("nschw"))
				.isTrue();

	}

	@Test
	public void queryAnnotation() {

		assertThat(persons.addressListByNullableCity(null)).containsExactly("Jens\n38116 Braunschweig");
		assertThat(persons.addressListByNullableCity("")).containsExactly("Jens\n38116 Braunschweig");
		assertThat(persons.addressListByNullableCity("New York")).isEmpty();
	}

	@Test
	public void queryByExample() {

		Person pattern = new Person(null, new Address(null, null, "germany"), Gender.MALE, null);

		Example<Person> strict = Example.of(pattern);

		assertThat(persons.findAll(strict))
				.isEmpty();


		Example<Person> lenient = Example.of(pattern, ExampleMatcher.matchingAll().withIgnoreCase());

		assertThat(persons.findAll(lenient))
				.extracting("firstName")
				.containsExactly("Jens");

	}

	@Test
	public void queryBySpecification() {

		assertThat(
				persons.findAll(notFrom("new"))
		).extracting("firstName")
				.containsExactly("Jens");


		assertThat(
				persons.findAll(notFrom("schweig"))
		).isEmpty();
	}

	private Specification<Person> notFrom(String cityPart) {

		return (Specification<Person>) (root, cq, cb) ->
				cb.not(
						cb.like(
								root.get("address").get("city"),
								"%" + cityPart + "%"
						)
				);
	}
}
