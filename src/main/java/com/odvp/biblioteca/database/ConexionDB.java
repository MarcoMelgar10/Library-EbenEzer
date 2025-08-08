package com.odvp.biblioteca.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;


public class ConexionDB {
    private static volatile ConexionDB instancia;
    private final HikariDataSource dataSource;

    private static Logger logger = LogManager.getRootLogger();

    private ConexionDB() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://ep-jolly-lab-a8wz5ags-pooler.eastus2.azure.neon.tech:5432/neondb");
        config.setUsername("neondb_owner");
        config.setPassword("npg_XgQ6PsVu3OtR");

        // Configuración del pool de conexiones
        config.setMaximumPoolSize(20);  // 20 conexiones simultáneas (ajustar según carga real)
        config.setMinimumIdle(2);       // Mantener 5 conexiones listas (evita latencia)
        config.setIdleTimeout(30000);   // 30s antes de cerrar conexiones inactivas
        config.setMaxLifetime(1800000); // 30 minutos de vida útil (correcto)
        config.setConnectionTimeout(5000); // 5s de timeout para obtener una conexión

        this.dataSource = new HikariDataSource(config);
        logger.info("Conexion con la base de datos creada");
    }

    public static ConexionDB getOrCreate() {
        if (instancia == null) {
            synchronized (ConexionDB.class) {
                if (instancia == null) {
                    instancia = new ConexionDB();
                }
            }
        }
        return instancia;
    }

    public Connection getConexion() throws SQLException {
        return dataSource.getConnection(); // Obtiene una conexión del pool
    }

    public void cerrarPool() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
