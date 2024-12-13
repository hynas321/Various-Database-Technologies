package org.example.Cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.auth.AuthProvider;
import com.datastax.oss.driver.api.core.auth.ProgrammaticPlainTextAuthProvider;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

import java.net.InetSocketAddress;

public class CassandraConnection {
    private CqlSession session;

    public CassandraConnection(String dataCenter, String keyspace) {
        AuthProvider authProvider = new ProgrammaticPlainTextAuthProvider("cassandra", "cassandra");

        this.session = CqlSession.builder()
                .withAuthProvider(authProvider)
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withLocalDatacenter(dataCenter)
                .build();

        createKeyspaceIfNotExists(keyspace);

        this.session.close();
        this.session = CqlSession.builder()
                .withAuthProvider(authProvider)
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withLocalDatacenter(dataCenter)
                .withKeyspace(CqlIdentifier.fromCql(keyspace))
                .build();
    }

    private void createKeyspaceIfNotExists(String keyspace) {
        SimpleStatement keyspaceStatement = SchemaBuilder.createKeyspace(CqlIdentifier.fromCql(keyspace))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true)
                .build();

        session.execute(keyspaceStatement);
    }

    public CqlSession getSession() {
        return session;
    }

    public void close() {
        session.close();
    }
}
