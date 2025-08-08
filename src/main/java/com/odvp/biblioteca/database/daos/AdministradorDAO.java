package com.odvp.biblioteca.database.daos;

import com.odvp.biblioteca.objetos.Administrador;
import com.odvp.biblioteca.database.ConexionDB;
import com.odvp.biblioteca.objetos.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
  Clase para realizar la interaccion con la base de datas, para la tabla administrador.
   */
public class AdministradorDAO {
    private static Logger log = LogManager.getRootLogger();
    LogDAO logDAO = LogDAO.getInstancia();

    public Administrador obtener(String usuario) {
        String qry = "SELECT * FROM administrador WHERE administrador_usuajpackage ^\n" +
                "  --input target ^\n" +
                "  --name BibliotecaEbenEzer ^\n" +
                "  --main-jar Biblioteca-1.0-SNAPSHOT.jar ^\n" +
                "  --main-class com.odvp.biblioteca.MainApp ^\n" +
                "  --type exe ^\n" +
                "  --icon src\\main\\resources\\icono_biblioteca.ico ^\n" +
                "  --java-options \"-Xmx512m\" ^\n" +
                "  --dest dist\nrio LIKE ?";
        String usuarioBusqueda = "%" + usuario + "%";

        try (Connection cnn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stms = cnn.prepareStatement(qry)) {

            stms.setString(1, usuarioBusqueda);
            ResultSet rs = stms.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String contrasena = rs.getString("contrasena");
                String administradorUsuario = rs.getString("administrador_usuario");

                return new Administrador(id, contrasena, administradorUsuario);
            }

        } catch (SQLException e) {
            String mensajeError = "Error al obtener administrador: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

        return null;
    }


    public void modificar(Administrador administrador) {
        String qry = "UPDATE administrador SET administrador_usuario = ?, contrasena = ? WHERE id_administrador = ? AND D_E_L_E_T_E = false";

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {

            stmt.setString(1, administrador.getUsuario());
            stmt.setString(2, administrador.getContrasena());
            stmt.setInt(3, administrador.getId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                log.error("No se encontró el administrador con ID " + administrador.getId());
            } else {
                String mensajeInfo = "Se modifico el administrador con ID: " + administrador.getId();
                log.info(mensajeInfo);
                Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
                logDAO.insertar(logEntrada);
            }

        } catch (SQLException e) {
            String mensajeError = "Error al modificar administrador: " + e.getMessage();
            log.error(mensajeError);

            // Crear manualmente el objeto Log para insertar
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    public void eliminar(int id) {
        String qry = "UPDATE administrador SET D_E_L_E_T_E = true WHERE id_administrador = ?";

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {

            stmt.setInt(1, id);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                log.error("No se encontró el administrador con ID " + id);
            } else {
                String mensajeInfo = "Se elimino el administrador con ID: " + id;
                log.info(mensajeInfo);
                Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
                logDAO.insertar(logEntrada);
            }

        } catch (SQLException e) {
            String mensajeError = "Error al eliminar el administrador: " + e.getMessage();
            log.error(mensajeError);

            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    public void insertar(Administrador administrador) {
        String qry = "CALL agregar_administrador(?,?)";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, administrador.getUsuario());
            stmt.setString(2, administrador.getContrasena());
            stmt.execute();
            String mensajeInfo = "Se agrego administrador: " + administrador.getUsuario();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
            JOptionPane.showMessageDialog(null, "Se agrego un nuevo administrador!", "Inforamcion", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            String mensajeError = "Error al insertar administrador: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);

        }

    }

    public boolean comprobarContrasena(String usuario, String contrasena) {
        String sql = "SELECT contrasena FROM administrador WHERE administrador_usuario = ?";
        try (PreparedStatement stmt = ConexionDB.getOrCreate().getConexion().prepareStatement(sql)) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {

                JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String contrasenaGuardada = rs.getString("contrasena");

            if (BCrypt.checkpw(contrasena, contrasenaGuardada)) {
                return true;
            } else {

                JOptionPane.showMessageDialog(null, "Contraseña incorrecta " + usuario, "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                String mensajeInfo = "Contraseña incorrecta";
                log.info(mensajeInfo);
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Usuario no autenticado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            String mensajeError = "Error de autenticacion: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

        return false;
    }

    public ObservableList<Administrador> listaAdministradores() {
        ObservableList<Administrador> resultado = FXCollections.observableArrayList();

        String query = "SELECT id_administrador, administrador_usuario, fecha_creacion " +
                "FROM administrador WHERE D_E_L_E_T_E = ? ORDER BY fecha_creacion DESC";

        try (PreparedStatement ps = ConexionDB.getOrCreate().getConexion().prepareStatement(query)) {
            ps.setBoolean(1, false); // Solo administradores no eliminados

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(new Administrador(
                            rs.getInt("id_administrador"),
                            rs.getString("administrador_usuario"),
                            "********",
                            rs.getDate("fecha_creacion")));
                }
            }
        } catch (SQLException ex) {
            String mensajeError = "Error al obtener la lista de administradores: " + ex.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

        return resultado;
    }
}
