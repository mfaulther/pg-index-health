/*
 * Copyright (c) 2019-2022. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.utils;

import io.github.mfvanek.pg.common.maintenance.AbstractCheckOnHost;
import io.github.mfvanek.pg.common.maintenance.Diagnostic;
import io.github.mfvanek.pg.connection.PgHost;
import io.github.mfvanek.pg.model.PgContext;
import io.github.mfvanek.pg.model.table.TableNameAware;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;

import javax.annotation.Nonnull;

@SuppressWarnings(("PMD.LinguisticNaming"))
public class AbstractCheckOnHostAssert<E extends TableNameAware> extends AbstractAssert<AbstractCheckOnHostAssert<E>, AbstractCheckOnHost<E>> {

    protected AbstractCheckOnHostAssert(@Nonnull final AbstractCheckOnHost<E> abstractCheckOnHost) {
        super(abstractCheckOnHost, AbstractCheckOnHostAssert.class);
    }

    public <T> AbstractCheckOnHostAssert<E> hasType(@Nonnull final Class<T> type) {
        isNotNull();
        if (!actual.getType().isAssignableFrom(type)) {
            failWithMessage("Expected type %s but was %s", type, actual.getType());
        }
        return this;
    }

    public AbstractCheckOnHostAssert<E> hasDiagnostic(@Nonnull final Diagnostic diagnostic) {
        isNotNull();
        if (actual.getDiagnostic() != diagnostic) {
            failWithMessage("Expected diagnostic %s but was %s", diagnostic, actual.getDiagnostic());
        }
        return this;
    }

    public AbstractCheckOnHostAssert<E> hasHost(@Nonnull final PgHost host) {
        isNotNull();
        if (!actual.getHost().equals(host)) {
            failWithMessage("Expected host %s but was %s", host, actual.getHost());
        }
        return this;
    }

    public ListAssert<E> executing() {
        isNotNull();
        return Assertions.assertThat(actual.check());
    }

    public ListAssert<E> executing(@Nonnull final PgContext pgContext) {
        isNotNull();
        return Assertions.assertThat(actual.check(pgContext));
    }

    public static <T extends TableNameAware> AbstractCheckOnHostAssert<T> assertThat(@Nonnull final AbstractCheckOnHost<T> actual) {
        return new AbstractCheckOnHostAssert<>(actual);
    }
}
