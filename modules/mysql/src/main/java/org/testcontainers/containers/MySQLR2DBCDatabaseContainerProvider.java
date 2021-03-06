package org.testcontainers.containers;

import com.google.auto.service.AutoService;
import dev.miku.r2dbc.mysql.MySqlConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.testcontainers.r2dbc.R2DBCDatabaseContainer;
import org.testcontainers.r2dbc.R2DBCDatabaseContainerProvider;

@AutoService(R2DBCDatabaseContainerProvider.class)
public class MySQLR2DBCDatabaseContainerProvider implements R2DBCDatabaseContainerProvider {

    static final String DRIVER = MySqlConnectionFactoryProvider.MYSQL_DRIVER;

    @Override
    public boolean supports(ConnectionFactoryOptions options) {
        return DRIVER.equals(options.getRequiredValue(ConnectionFactoryOptions.DRIVER));
    }

    @Override
    public R2DBCDatabaseContainer createContainer(ConnectionFactoryOptions options) {
        String image = MySQLContainer.IMAGE + ":" + options.getRequiredValue(IMAGE_TAG_OPTION);
        MySQLContainer<?> container = new MySQLContainer<>(image)
            .withDatabaseName(options.getRequiredValue(ConnectionFactoryOptions.DATABASE));

        if (Boolean.TRUE.equals(options.getValue(REUSABLE_OPTION))) {
            container.withReuse(true);
        }
        return new MySQLR2DBCDatabaseContainer(container);
    }
}
