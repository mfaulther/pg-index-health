/*
 * Copyright (c) 2019-2023. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.connection;

import javax.annotation.Nonnull;

/**
 * Allows getting information about database host.
 *
 * @author Ivan Vakhrushev
 */
public interface HostAware {

    /**
     * Gets information about host in the cluster.
     *
     * @return {@code PgHost}
     * @see PgHost
     */
    @Nonnull
    PgHost getHost();
}
