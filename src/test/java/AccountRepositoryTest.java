import com.datastax.oss.driver.api.core.CqlIdentifier;
import org.example.Entities.Account;
import org.example.Repositories.AccountRepository;
import org.example.Repositories.EntityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest extends BaseRepositoryTest {
    private EntityRepository<Account> accountRepository;

    @BeforeAll
    public void setUp() {
        super.setUp();
        accountRepository = new AccountRepository(session, CqlIdentifier.fromCql("site"));
    }

    @Test
    void create_ShouldSaveUser() {
        Account user = new Account("test1@example.com", "password123", Account.AccountType.USER);
        accountRepository.create(user);

        Account retrievedAccount = accountRepository.getById(user.getId());
        assertNotNull(retrievedAccount);
        assertEquals("test1@example.com", retrievedAccount.getEmail());
    }

    @Test
    void create_ShouldSaveAdmin() {
        Account admin = new Account("admin1@example.com", "adminpassword123", Account.AccountType.ADMIN);
        accountRepository.create(admin);

        Account retrievedAccount = accountRepository.getById(admin.getId());
        assertNotNull(retrievedAccount);
        assertEquals("admin1@example.com", retrievedAccount.getEmail());
    }

    @Test
    void getById_ShouldReturnUser() {
        Account user = new Account("test2@example.com", "password123", Account.AccountType.USER);
        accountRepository.create(user);

        Account retrievedAccount = accountRepository.getById(user.getId());
        assertNotNull(retrievedAccount);
        assertEquals("test2@example.com", retrievedAccount.getEmail());
    }

    @Test
    void getAll_ShouldReturnListOfAccounts() {
        Account user = new Account("user_1@example.com", "password123", Account.AccountType.USER);
        Account admin = new Account("admin_1@example.com", "adminpassword123", Account.AccountType.ADMIN);

        accountRepository.create(user);
        accountRepository.create(admin);

        List<Account> accounts = accountRepository.getAll();
        assertFalse(accounts.isEmpty());
    }

    @Test
    void update_ShouldUpdateUser() {
        Account user = new Account("update@example.com", "password123", Account.AccountType.USER);
        accountRepository.create(user);

        user.setEmail("updated_user@example.com");
        accountRepository.update(user);

        Account updatedAccount = accountRepository.getById(user.getId());
        assertEquals("updated_user@example.com", updatedAccount.getEmail());
    }

    @Test
    void delete_ShouldDeleteUser() {
        Account user = new Account("delete_user@example.com", "password123", Account.AccountType.USER);
        accountRepository.create(user);

        accountRepository.delete(user);

        Account deletedAccount = accountRepository.getById(user.getId());
        assertNull(deletedAccount);
    }
}
