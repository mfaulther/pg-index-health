/*
 * Copyright (c) 2019-2024. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.support.statements;

import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Nonnull;

public class CreateFunctionsStatement extends AbstractDbStatement {

    public CreateFunctionsStatement(@Nonnull final String schemaName) {
        super(schemaName);
    }

    @Override
    public void execute(@Nonnull final Statement statement) throws SQLException {
        statement.execute(String.format("create or replace function %s.add(a integer, b integer) returns integer " +
            "as 'select $1 + $2;' " +
            "language sql " +
            "immutable " +
            "returns null on null input;", schemaName));
        statement.execute(String.format("create or replace function %s.add(a int, b int, c int) returns int " +
            "as 'select $1 + $2 + $3;' " +
            "language sql " +
            "immutable " +
            "returns null on null input;", schemaName));
    }
}
