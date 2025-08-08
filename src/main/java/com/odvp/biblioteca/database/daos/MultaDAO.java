package com.odvp.biblioteca.database.daos;

import com.odvp.biblioteca.objetos.Log;
import com.odvp.biblioteca.objetos.Multa;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.MultaCardData;
import com.odvp.biblioteca.database.ConexionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/*
  Clase para realizar la interaccion con la base de datas, para la tabla multaCardData.
   */
public class MultaDAO {
    private static Logger log = LogManager.getRootLogger();
    LogDAO logDAO = LogDAO.getInstancia();

    public void insertar(Multa multa) {
        String qry = "CALL agregar_multa(?,?,?)";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, multa.getDescripcion());
            stmt.setInt(2, multa.getMonto());
            stmt.setInt(3, multa.getIdPrestamo());
            stmt.execute();
            String mensajeInfo = "Se agregó la multa por el prestamo: " + multa.getIdPrestamo();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al agregar multa: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);

        }
    }

    /*
    estado = false, significa "Cancelada", al contrario "Activa"
     */
    public void modificar(Multa multa) {
        String qry = "UPDATE multa SET descripcion = ?, monto =?, fecha_multa= ?, estado = ? WHERE id_multa = ? AND D_E_L_E_T_E = FALSE";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement ps = conn.prepareStatement(qry)) {
            ps.setString(1, multa.getDescripcion());
            ps.setInt(2, multa.getMonto());
            ps.setDate(3, multa.getFechaMulta());
            ps.setBoolean(4, multa.isEstado());
            ps.setInt(5, multa.getIdMulta());
            ps.execute();
            String mensajeInfo = "Se modificó la multa por el prestamo: " + multa.getIdPrestamo();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al modificar multa: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);

        }
    }

    public Multa obtener(int id) {
        String qry = "SELECT * FROM MULTA WHERE id_multa = ?";

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement ps = conn.prepareStatement(qry)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Usa if en lugar de while, porque esperamos solo una multaCardData
                    String descripcion = rs.getString("descripcion");
                    int monto = rs.getInt("monto");
                    Date fechaMulta = rs.getDate("fecha_multa");
                    boolean estado = rs.getBoolean("estado");
                    Date fechaCancelacion = rs.getDate("fecha_cancelacion");
                    boolean D_E_L_E_T_E = rs.getBoolean("D_E_L_E_T_E"); // Mejor cambiar el nombre en la BD
                    int idPrestamo = rs.getInt("id_prestamo");

                    String mensajeInfo = "Se eliminó al usuario con ID: " + id;
                    log.info(mensajeInfo);
                    return new Multa(id, descripcion, monto, fechaMulta, estado, fechaCancelacion, D_E_L_E_T_E, idPrestamo);

                }
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener la multa: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void eliminar(int id) {
        String qry = "UPDATE multa SET D_E_L_E_T_E = TRUE WHERE id_multa = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement st = conn.prepareStatement(qry)) {
            st.setInt(1, id);
            st.execute();
            String mensajeInfo = "Se eliminó la multa con el ID: " + id ;
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al eliminar multa: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);

        }


    }


    public ArrayList<MultaCardData> listaMultas() {
        String qry = """
                    SELECT m.id_multa, m.descripcion, m.monto, m.fecha_multa, m.estado, 
                           m.fecha_cancelacion, m.id_prestamo, u.nombre, u.apellido_paterno, u.apellido_materno
                    FROM multa m 
                    JOIN prestamo p ON p.id_prestamo = m.id_prestamo
                    JOIN usuario u ON p.id_usuario = u.id_usuario 
                    WHERE  m.D_E_L_E_T_E = FALSE
                    ORDER BY m.estado DESC, m.id_multa ASC
                """;

        ArrayList<MultaCardData> multas = new ArrayList<>();

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idMulta = rs.getInt("id_multa");
                int idPrestamo = rs.getInt("id_prestamo");
                int monto = rs.getInt("monto");
                Date fechaMulta = rs.getDate("fecha_multa");
                boolean estado = rs.getBoolean("estado");
                String nombreUsuario = rs.getString("nombre"); // Datos de usuario
                nombreUsuario += " ";
                nombreUsuario += rs.getString("apellido_paterno");
                nombreUsuario += " ";
                nombreUsuario += rs.getString("apellido_materno");

                // Crear objeto DTO
                MultaCardData multaCardData = new MultaCardData(idMulta,nombreUsuario, monto, fechaMulta, estado, idPrestamo);
                multas.add(multaCardData);
            }
        } catch (SQLException e) {
           log.error("Error al obtener la lista de multas: " + e.getMessage());
        }
        return multas;
    }


    //Ejecutar en la linea principal del programa, para que cada vez que se inicie se actualicen los prestamos acorde a las fechas limites
    public void validarPrestamosVencidos() {
        String qry = "SELECT actualizar_prestamos_y_multas()";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.execute();
            String mensajeInfo = "Estado de prestamos y multas actualizados ";
            log.info(mensajeInfo);

        } catch (SQLException e) {
            String mensajeError = "Error al actualizar el estado de las multas y los prestamos: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }


    public List<IDatoVisual> listaMultasVisual() {
        String qry = """
                    SELECT m.id_multa, m.monto, m.fecha_multa, m.estado, m.id_prestamo, 
                    u.nombre, u.apellido_materno, u.apellido_paterno
                    FROM multa m 
                    JOIN prestamo p on p.id_prestamo = m.id_prestamo
                    JOIN usuario u ON p.id_usuario = u.id_usuario 
                    WHERE m.D_E_L_E_T_E = FALSE
                    ORDER BY m.estado DESC, m.id_multa ASC
                """;

        ArrayList<IDatoVisual> multas = new ArrayList<>();

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idMulta = rs.getInt("id_multa");
                int idPrestamo = rs.getInt("id_prestamo");
                int monto = rs.getInt("monto");
                boolean estado = rs.getBoolean("estado");
                Date fechaMulta = rs.getDate("fecha_multa");
                String nombreUsuario = rs.getString("nombre");
                nombreUsuario += " ";
                nombreUsuario += rs.getString("apellido_paterno");
                nombreUsuario += " ";
                nombreUsuario += rs.getString("apellido_materno");


                MultaCardData multaCardData = new MultaCardData(idMulta, nombreUsuario, monto, fechaMulta, estado, idPrestamo);

                multas.add(multaCardData);
            }
        } catch (SQLException e) {
            log.error("Error al obtener la lista de multas: " + e.getMessage());
        }
        return multas;

    }

    public int getNextId() {
        String qry = "Select MAX(id_multa) from multa";
        int idMulta = 0;
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                idMulta = rs.getInt(1);
                idMulta += 1;

            }
        } catch (SQLException e) {
            log.error("Error al obtener el siguiente id en las multas: "+ e.getMessage());
        }
        return idMulta;
    }

    public void cancelarMulta(int idMulta) {
        String qry = "UPDATE multa SET estado = false, fecha_cancelacion = ? WHERE id_multa = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement st = conn.prepareStatement(qry)) {
            st.setDate(1, Date.valueOf(LocalDate.now()));
            st.setInt(2, idMulta);
            st.execute();
            String mensajeInfo = "Se cancelo la multa con ID: " + idMulta;
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al cancelar multa multa: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

    }
    public List<IDatoVisual> listaMultasVisual(String textoBusqueda) {
        String qry = """
            SELECT m.id_multa, m.monto, m.fecha_multa, m.estado, m.id_prestamo,
                   u.apellido_paterno, u.apellido_materno, u.nombre 
            FROM multa m 
            JOIN prestamo p ON p.id_prestamo = m.id_prestamo
            JOIN usuario u ON p.id_usuario = u.id_usuario 
            WHERE unaccent(CONCAT(u.nombre, ' ', u.apellido_paterno, ' ', u.apellido_materno)) ILIKE unaccent(?)
               OR unaccent(CONCAT(u.apellido_paterno, ' ', u.apellido_materno)) ILIKE unaccent(?) AND m.D_E_L_E_T_E = FALSE
            ORDER BY m.estado DESC, m.id_multa ASC
        """;

        ArrayList<IDatoVisual> multas = new ArrayList<>();

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {

            String filtro = "%" + textoBusqueda + "%"; // Permitir coincidencias parciales

            stmt.setString(1, filtro); // Buscar por nombre completo sin tildes
            stmt.setString(2, filtro); // Buscar por apellidos sin tildes

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idMulta = rs.getInt("id_multa");
                    int idPrestamo = rs.getInt("id_prestamo");
                    int monto = rs.getInt("monto");
                    boolean estado = rs.getBoolean("estado");
                    Date fechaMulta = rs.getDate("fecha_multa");

                    // Concatenar nombre y apellidos
                    String nombreUsuario = rs.getString("nombre") + " " +
                            rs.getString("apellido_paterno") + " " +
                            rs.getString("apellido_materno");

                    MultaCardData multaCardData = new MultaCardData(idMulta, nombreUsuario, monto, fechaMulta, estado, idPrestamo);
                    multas.add(multaCardData);
                }
            }
        } catch (SQLException e) {
           log.error("Error al obtener la lista de multas por busqueda especifica");
        }
        return multas;
    }




}
