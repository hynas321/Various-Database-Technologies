import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Entities.User;
import org.example.Repositories.AccountRepository;
import org.example.Repositories.EntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest extends BaseRepositoryTest {
    private EntityRepository<Account> accountRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();

        accountRepository = new AccountRepository(database);
    }


    @Test
    void create_ShouldSaveUser() {
        User user = new User("test1@example.com", "password123");
        accountRepository.create(user);

        Account retrievedAccount = accountRepository.getById(user.getId());
        assertNotNull(retrievedAccount);
        assertTrue(retrievedAccount instanceof User);
        assertEquals("test1@example.com", retrievedAccount.getEmail());
    }

    @Test
    void create_ShouldSaveAdmin() {
        Admin admin = new Admin("admin1@example.com", "adminpassword123");
        accountRepository.create(admin);

        Account retrievedAccount = accountRepository.getById(admin.getId());
        assertNotNull(retrievedAccount);
        assertTrue(retrievedAccount instanceof Admin);
        assertEquals("admin1@example.com", retrievedAccount.getEmail());
    }

    @Test
    void getById_ShouldReturnUser() {
        User user = new User("test2@example.com", "password123");
        accountRepository.create(user);

        Account retrievedAccount = accountRepository.getById(user.getId());
        assertNotNull(retrievedAccount);
        assertTrue(retrievedAccount instanceof User);
        assertEquals("test2@example.com", retrievedAccount.getEmail());
    }

    @Test
    void getById_ShouldReturnAdmin() {
        Admin admin = new Admin("admin2@example.com", "adminpassword123");
        accountRepository.create(admin);

        Account retrievedAccount = accountRepository.getById(admin.getId());
        assertNotNull(retrievedAccount);
        assertTrue(retrievedAccount instanceof Admin);
        assertEquals("admin2@example.com", retrievedAccount.getEmail());
    }

    @Test
    void getAll_ShouldReturnListOfAccounts() {
        User user = new User("user_1@example.com", "password123");
        Admin admin = new Admin("admin_1@example.com", "adminpassword123");

        accountRepository.create(user);
        accountRepository.create(admin);

        List<Account> accounts = accountRepository.getAll();
        assertFalse(accounts.isEmpty());
        assertTrue(accounts.stream().anyMatch(account -> account instanceof User));
        assertTrue(accounts.stream().anyMatch(account -> account instanceof Admin));
    }

    @Test
    void update_ShouldUpdateUser() {
        User user = new User("update@example.com", "password123");
        accountRepository.create(user);

        user.setEmail("updated_user@example.com");
        accountRepository.update(user);

        Account updatedAccount = accountRepository.getById(user.getId());
        assertEquals("updated_user@example.com", updatedAccount.getEmail());
    }

    @Test
    void update_ShouldUpdateAdmin() {
        Admin admin = new Admin("admin_update@example.com", "adminpassword123");
        accountRepository.create(admin);

        admin.setEmail("updated_admin@example.com");
        accountRepository.update(admin);

        Account updatedAccount = accountRepository.getById(admin.getId());
        assertEquals("updated_admin@example.com", updatedAccount.getEmail());
    }

    @Test
    void delete_ShouldDeleteUser() {
        User user = new User("delete_user@example.com", "password123");
        accountRepository.create(user);

        accountRepository.delete(user);

        Account deletedAccount = accountRepository.getById(user.getId());
        assertNull(deletedAccount);
    }

    @Test
    void delete_ShouldDeleteAdmin() {
        Admin admin = new Admin("delete_admin@example.com", "adminpassword123");
        accountRepository.create(admin);

        accountRepository.delete(admin);

        Account deletedAccount = accountRepository.getById(admin.getId());
        assertNull(deletedAccount);
    }
}
