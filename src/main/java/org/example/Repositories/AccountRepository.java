package org.example.Repositories;

import org.example.Entities.Account;
import org.example.Repositories.Interfaces.EntityRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AccountRepository implements EntityRepository<Account> {
    private final Session session;

    public AccountRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Account account) {
        session.save(account);
    }

    @Override
    public Account getById(Long id) {
        return session.get(Account.class, id);
    }

    @Override
    public List<Account> getAll() {
        Query<Account> query = session.createQuery("from Account", Account.class);
        return query.list();
    }

    @Override
    public void update(Account account) {
        session.update(account);
    }

    @Override
    public void delete(Account account) {
        session.delete(account);
    }
}
