/*
 * Copyright (c) 2019-2024. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.common.maintenance;

import io.github.mfvanek.pg.model.DbObject;

import javax.annotation.Nonnull;

/**
 * Allows getting information about original generic type.
 *
 * @param <T> represents an object in a database associated with a table
 * @author Ivan Vakhrushev
 * @since 0.6.0
 */
public interface RawTypeAware<T extends DbObject> {

    /**
     * Gets original java type.
     *
     * @return java type representing database object
     */
    @Nonnull
    Class<T> getType();
}
