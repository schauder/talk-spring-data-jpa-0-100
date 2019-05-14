package example.jpa;

import org.assertj.core.api.AbstractIntegerAssert;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static example.jpa.PersonTestUtil.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonIntegrationTests {

	@Autowired
	PersonService personService;

	@Autowired
	EntityManager em;

	@Autowired
	JdbcTemplate template;

	@Test
	@Transactional
	public void saving() {

		Person p = createPerson();
		em.persist(p);

		Person reloaded = em.find(Person.class, p.getId());

		assertThat(reloaded).isNotNull();
		assertThat(reloaded.getId()).isEqualTo(p.getId());

		assertPersonCount().isEqualTo(0);

		Person reloadedAgain = em
				.createQuery("SELECT p  FROM Person p " +
						"WHERE p.id = :id", Person.class)
				.setParameter("id", p.getId())
				.getSingleResult();

		assertPersonCount().isEqualTo(1);

		// Flush has the same effect as a query.
		// em.flush();

		// JPA guarantees that within a single session/transaction every instance is only loaded once.
		assertThat(p)
				.isSameAs(reloaded)
				.isSameAs(reloadedAgain);

	}

	@Test
	public void fetching() {

		Long id = personService.savePerson(createPerson());

		Person person = personService.loadPerson(id);

		System.out.println(person.getAddress());

		assertThatExceptionOfType(LazyInitializationException.class)
				.isThrownBy(
						() -> person.getHobbies().forEach(System.out::println)
				);
	}


	AbstractIntegerAssert<?> assertPersonCount() {

		return assertThat(
				template.queryForObject("SELECT COUNT(*) FROM PERSON", Integer.class)
		);
	}


}
