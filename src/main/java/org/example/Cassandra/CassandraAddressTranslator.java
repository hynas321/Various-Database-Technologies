package org.example.Cassandra;

import com.datastax.oss.driver.api.core.addresstranslation.AddressTranslator;
import com.datastax.oss.driver.api.core.context.DriverContext;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.net.InetSocketAddress;

public class CassandraAddressTranslator implements AddressTranslator {

    public CassandraAddressTranslator(DriverContext context) {

    }

    @NonNull
    @Override
    public InetSocketAddress translate(@NonNull InetSocketAddress inetSocketAddress) {
        String hostAddress = inetSocketAddress.getAddress().getHostAddress();

        return switch (hostAddress)
            {
                case "172.24.0.2" -> new InetSocketAddress("cassandra1", 9042);
                case "172.24.0.3" -> new InetSocketAddress("cassandra2", 9043);
                default -> throw new RuntimeException("Incorrect address");
            };
    }

    @Override
    public void close() {

    }
}
