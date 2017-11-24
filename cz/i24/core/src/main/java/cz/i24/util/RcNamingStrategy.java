package cz.i24.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class RcNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final long serialVersionUID = 1L;

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return context.getIdentifierHelper().toIdentifier(name.getText(), true);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return context.getIdentifierHelper().toIdentifier(name.getText(), true);
    }
}
