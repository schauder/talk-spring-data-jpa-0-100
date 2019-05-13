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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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


	@Test
	public void crud() {

		Person p = persons.save(createPerson());

		assertThat(persons.findAll())
				.containsExactly(p);

		persons.deleteAll();


		assertThat(persons.findAll())
				.hasSize(0);

	}

	@Test
	public void derivedQueriesSimple() {

		Person p = persons.save(createPerson());

		assertThat(persons.findByFirstName("Jens"))
				.containsExactly(p);

	}
	@Test
	public void derivedQueriesInvolved() {

		Person p = persons.save(createPerson());

		assertThat(persons.existsByAddress_CityContainsIgnoreCase("nschw"))
				.isTrue();

	}
}
