package example.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JpaApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	public void contextLoads() {

		Person p = new Person(
				"Jens",
				new Address("38116", "Braunschweig", "Germany"),
				Gender.MALE
		);

		em.persist(p);

	}

}
