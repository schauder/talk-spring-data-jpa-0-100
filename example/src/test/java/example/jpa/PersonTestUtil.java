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

import lombok.experimental.UtilityClass;
import org.assertj.core.api.AbstractIntegerAssert;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

/**
 * @author Jens Schauder
 */
@UtilityClass
public class PersonTestUtil {


	static Person createPerson() {

		return
				new Person(
						"Jens",
						new Address("38116", "Braunschweig", "Germany"),
						Gender.MALE,
						asList(new Hobby("Running"),
								new Hobby("Bouldering"),
								new Hobby("Playing Games")
						)
				);
	}

}
