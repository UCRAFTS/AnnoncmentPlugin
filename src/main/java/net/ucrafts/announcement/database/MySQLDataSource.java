package net.ucrafts.announcement.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ucrafts.announcement.Config;
import net.ucrafts.announcement.types.ConfigType;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MySQLDataSource extends AbstractDataSource implements DataSourceInterface
{


    protected String driverClassName = "com.mysql.jdbc.Driver";


    public MySQLDataSource(Config config)
    {
        super(config);

        if (this.dataSource != null) {
            this.close();
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(this.driverClassName);
        hikariConfig.setUsername(this.config.getConfig().getString(ConfigType.DB_USER.getName()));
        hikariConfig.setPassword(this.config.getConfig().getString(ConfigType.DB_PASS.getName()));
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("AnnouncementPlugin");
        hikariConfig.setJdbcUrl(
                String.format(
                        "jdbc:mysql://%s:%s/%s?useSSL=true&verifyServerCertificate=false",
                        this.config.getConfig().getString(ConfigType.DB_HOST.getName()),
                        this.config.getConfig().getInt(ConfigType.DB_PORT.getName()),
                        this.config.getConfig().getString(ConfigType.DB_BASE.getName())
                )
        );

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("logWriter", new PrintWriter(System.out));

        this.dataSource = new HikariDataSource(hikariConfig);
        this.createTables();
    }


    private void createTables()
    {
        String balanceTable = String.format(
            "CREATE TABLE IF NOT EXISTS `%s_ad` (`text` text NOT NULL, `server` varchar(36) NOT NULL, `type` varchar(36) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;",
            this.config.getConfig().getString(ConfigType.DB_TABLES_PREFIX.getName())
        );

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(balanceTable);
            preparedStatement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Cant create plugin table!");
        }
    }
}
