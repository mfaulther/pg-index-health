/*
 * Copyright (c) 2019-2023. Ivan Vakhrushev and others.
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

public class AddLinksBetweenAccountsAndClientsStatement extends AbstractDbStatement {

    public AddLinksBetweenAccountsAndClientsStatement(@Nonnull final String schemaName) {
        super(schemaName);
    }

    @Override
    public void execute(@Nonnull final Statement statement) throws SQLException {
        statement.execute(String.format("alter table if exists %1$s.accounts " +
                        "add constraint c_accounts_fk_client_id foreign key (client_id) references %1$s.clients (id);",
                schemaName));
    }
}
