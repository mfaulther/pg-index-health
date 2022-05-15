/*
 * Copyright (c) 2019-2022. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.model.index;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IndexTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void validation() {
        assertThatThrownBy(() -> Index.of(null, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> Index.of("t", null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> Index.of("", "")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Index.of(" ", " ")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Index.of("t", "")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Index.of("t", " ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getTableAndIndexName() {
        final Index index = Index.of("t", "i");
        assertThat(index).isNotNull();
        assertThat(index.getTableName()).isEqualTo("t");
        assertThat(index.getIndexName()).isEqualTo("i");
    }

    @Test
    void testToString() {
        final Index index = Index.of("t", "i");
        assertThat(index).isNotNull();
        assertThat(index.toString()).isEqualTo("Index{tableName='t', indexName='i'}");
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testEqualsAndHashCode() {
        final Index first = Index.of("t1", "i1");
        final Index second = Index.of("t1", "i2");
        final Index third = Index.of("t2", "i2");

        assertThat(first.equals(null)).isFalse();
        //noinspection EqualsBetweenInconvertibleTypes
        assertThat(first.equals(BigDecimal.ZERO)).isFalse();

        // self
        assertThat(first).isEqualTo(first);
        assertThat(first.hashCode()).isEqualTo(first.hashCode());

        // the same
        assertThat(Index.of("t1", "i1")).isEqualTo(first);

        // others
        assertThat(second).isNotEqualTo(first);
        assertThat(second.hashCode()).isNotEqualTo(first.hashCode());

        assertThat(third).isNotEqualTo(first);
        assertThat(third.hashCode()).isNotEqualTo(first.hashCode());

        assertThat(third).isNotEqualTo(second);
        assertThat(third.hashCode()).isNotEqualTo(second.hashCode());
    }

    @Test
    void equalsHashCodeShouldAdhereContracts() {
        EqualsVerifier.forClass(Index.class)
                .verify();
    }

    @SuppressWarnings({"ConstantConditions", "EqualsWithItself", "ResultOfMethodCallIgnored"})
    @Test
    void compareToTest() {
        final Index first = Index.of("t1", "i1");
        final Index second = Index.of("t1", "i2");
        final Index third = Index.of("t2", "i2");

        assertThatThrownBy(() -> first.compareTo(null)).isInstanceOf(NullPointerException.class);

        // self
        assertThat(first.compareTo(first)).isEqualTo(0);

        // the same
        assertThat(first.compareTo(Index.of("t1", "i1"))).isEqualTo(0);

        // others
        assertThat(first.compareTo(second)).isEqualTo(-1);
        assertThat(second.compareTo(first)).isEqualTo(1);

        assertThat(second.compareTo(third)).isEqualTo(-1);
        assertThat(third.compareTo(second)).isEqualTo(1);
    }
}
