/*
 * Copyright (c) 2019-2022. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.common.health.logger;

import io.github.mfvanek.pg.common.maintenance.DatabaseChecks;
import io.github.mfvanek.pg.connection.ConnectionCredentials;
import io.github.mfvanek.pg.connection.HighAvailabilityPgConnectionFactoryImpl;
import io.github.mfvanek.pg.connection.PgConnectionFactoryImpl;
import io.github.mfvanek.pg.connection.PrimaryHostDeterminerImpl;
import io.github.mfvanek.pg.embedded.PostgresDbExtension;
import io.github.mfvanek.pg.embedded.PostgresExtensionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StandardHealthLoggerTest extends HealthLoggerTestBase {

    @RegisterExtension
    static final PostgresDbExtension POSTGRES = PostgresExtensionFactory.database();

    private final HealthLogger logger;

    StandardHealthLoggerTest() {
        super(POSTGRES.getTestDatabase());
        final ConnectionCredentials credentials = ConnectionCredentials.ofUrl(
                POSTGRES.getUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        this.logger = new StandardHealthLogger(
                credentials,
                new HighAvailabilityPgConnectionFactoryImpl(new PgConnectionFactoryImpl(), new PrimaryHostDeterminerImpl()),
                DatabaseChecks::new);
    }

    @ParameterizedTest
    @ValueSource(strings = {"public", "custom"})
    void logAll(final String schemaName) {
        executeTestOnDatabase(schemaName,
                dbp -> dbp.withReferences().withData().withInvalidIndex().withNullValuesInIndex().withTableWithoutPrimaryKey().withDuplicatedIndex().withNonSuitableIndex().withStatistics(), ctx -> {
                    waitForStatisticsCollector();
                    final List<String> logs = logger.logAll(Exclusions.empty(), ctx);
                    assertThat(logs)
                            .isNotNull()
                            .hasSize(10)
                            .containsExactlyInAnyOrder(
                                    "invalid_indexes:1",
                                    "duplicated_indexes:2",
                                    "foreign_keys_without_index:1",
                                    "tables_without_primary_key:1",
                                    "indexes_with_null_values:1",
                                    "indexes_bloat:11",
                                    "tables_bloat:2",
                                    "intersected_indexes:5",
                                    "unused_indexes:7",
                                    "tables_with_missing_indexes:0");
                });
    }

    @Test
    void logAllWithDefaultSchema() {
        final List<String> logs = logger.logAll(Exclusions.empty());
        assertThat(logs)
                .isNotNull()
                .hasSize(10);
        for (final SimpleLoggingKey key : SimpleLoggingKey.values()) {
            assertThat(logs)
                    .filteredOn(ofKey(key))
                    .hasSize(1)
                    .containsExactly(key.getSubKeyName() + ":0");
        }
    }
}
