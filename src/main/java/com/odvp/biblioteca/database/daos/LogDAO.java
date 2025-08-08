package com.odvp.biblioteca.database.daos;

import com.odvp.biblioteca.objetos.Log;
import com.odvp.biblioteca.database.ConexionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
  Clase para realizar la interaccion con la base de datas, para la tabla libro.
   */public class LogDAO {
    private ConexionDB conexionDB;
    private static Logger logger = LogManager.getRootLogger();
    private static volatile LogDAO instancia;
    private String qry;
    private Log log;

    // Constructor privado
    private LogDAO() {
        this.conexionDB = ConexionDB.getOrCreate();
    }

    // Método estático para obtener la única instancia (con sincronización segura)
    public static LogDAO getInstancia() {
        if (instancia == null) {
            synchronized (LogDAO.class) {
                if (instancia == null) {
                    instancia = new LogDAO();
                }
            }
        }
        return instancia;
    }

    public void insertar(Object log) {
        this.log = (Log) log;
        qry = "INSERT INTO LOGS (tipo, descripcion, fecha) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = conexionDB.getConexion().prepareStatement(qry)) {
            stmt.setString(1, this.log.getTipo());
            stmt.setString(2, this.log.getDescripcion());
            stmt.setDate(3, this.log.getFecha());
            stmt.execute();
            logger.info("Información cargada a la base de datos");

        } catch (SQLException e) {
          logger.error("Error al ingresar los logger: " + e.getMessage());
        }
    }

    public Object obtener(int id) {
        return null;
    }

    public void modificar(Object log) {
        this.log = (Log) log;
    }

    public void eliminar(int id) {
        // Implementación pendiente
    }
}

