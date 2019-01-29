package api.webContext.domain.address;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import api.TestApplication;

@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@ContextConfiguration(classes = TestApplication.class)
@DataJpaTest
public class AddressRepositoryTest {
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Before
	public void setup() {

	}
}
