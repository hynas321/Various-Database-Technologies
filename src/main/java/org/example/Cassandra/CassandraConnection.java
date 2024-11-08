package org.example.Cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.auth.AuthProvider;
import com.datastax.oss.driver.api.core.auth.ProgrammaticPlainTextAuthProvider;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;

import java.net.InetSocketAddress;

public class CassandraConnection {
    private final CqlSession session;

    public CassandraConnection(String dataCenter) {
        AuthProvider authProvider = new ProgrammaticPlainTextAuthProvider("cassandra", "cassandra");

        this.session = CqlSession.builder()
                .withAuthProvider(authProvider)
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withLocalDatacenter(dataCenter)
                .withKeyspace(CqlIdentifier.fromCql("site"))
                .build();

        CreateKeyspace keyspace = SchemaBuilder.createKeyspace(CqlIdentifier.fromCql("site"))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
        SimpleStatement createKeySpaceStatement = keyspace.build();

        session.execute(createKeySpaceStatement);
    }

    public CqlSession getSession() {
        return session;
    }

    public void close() {
        session.close();
    }
}
