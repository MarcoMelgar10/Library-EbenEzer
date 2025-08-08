package com.odvp.biblioteca.database.daos;

import com.odvp.biblioteca.database.ConexionDB;
import com.odvp.biblioteca.objetos.Log;
import com.odvp.biblioteca.objetos.Prestamo;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.PrestamoCardData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
  Clase para realizar la interaccion con la base de datas, para la tabla prestamo.
   */
public class PrestamoDAO{
    private static Logger log = LogManager.getRootLogger();
    LogDAO logDAO = LogDAO.getInstancia();

    public void eliminar(int id) {
        String qry = "Update prestamo set D_E_L_E_T_E = TRUE WHERE id_prestamo = ?";
        try(Connection cnn = ConexionDB.getOrCreate().getConexion();
            PreparedStatement stmt = cnn.prepareStatement(qry)){
                stmt.setInt(1, id);
                stmt.execute();
            String mensajeInfo = "Se eliminó el prestamo con ID: " + id ;
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al elminar prestamo: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

    }
    public ArrayList<IDatoVisual> listaPrestamosCardData() {
        String qry =  "SELECT p.id_prestamo, p.fecha_prestamo, p.fecha_vencimiento, p.fecha_devolucion, " +
                "u.nombre, u.apellido_paterno, u.apellido_materno, " +
                "l.titulo AS libro, p.estado " +
                "FROM prestamo p " +
                "JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "JOIN libro l ON p.id_libro = l.id_libro " +
                "WHERE P.D_E_L_E_T_E = FALSE " +
                "ORDER BY " +
                "  CASE p.estado " +
                "    WHEN 'activo' THEN 1 " +
                "    WHEN 'vencido' THEN 2 " +
                "    WHEN 'devuelto' THEN 3 " +
                "  END," +
                "p.id_prestamo;";
        ArrayList<IDatoVisual> prestamos = new ArrayList<>();
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement ps = conn.prepareStatement(qry);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idPrestamo = rs.getInt("id_prestamo");
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                String usuario = rs.getString("nombre");
                usuario += " ";
                usuario += rs.getString("apellido_paterno");
                usuario += " ";
                usuario += rs.getString("apellido_materno");
                String libro = rs.getString("libro");
                String estado = rs.getString("estado");
                PrestamoCardData prestamo = new PrestamoCardData(idPrestamo, fechaPrestamo, usuario, libro, estado);
                prestamos.add(prestamo);
                log.info("Se obtuvo la lista de prestamos");
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener la lista de prestamo: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

        return prestamos;
    }

    public String getLibro(int codigoPrestamo) {
        String qry = "SELECT l.titulo FROM prestamo p JOIN libro l on l.id_libro = p.id_libro WHERE id_prestamo = ? AND l.D_E_L_E_T_E = false";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setInt(1, codigoPrestamo); // Mover esto antes de ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
            log.info("Se obtuvo el libro con ID: " + codigoPrestamo);
        } catch (SQLException e) {
            String mensajeError = "Error al obtener la lista de prestamo: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
        return null;
    }


    public String getUsuario(int codigoPrestamo) {
        String qry = "SELECT u.nombre, u.apellido_paterno, u.apellido_materno FROM prestamo p JOIN usuario u on u.id_usuario = p.id_usuario WHERE id_prestamo = ? AND p.D_E_L_E_T_E = false";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
                PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setInt(1, codigoPrestamo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString(1);
                    nombre += " ";
                    nombre += rs.getString(2);
                    nombre += " ";
                    nombre += rs.getString(3);
                    return nombre;
                }
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener usuario del prestamo: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
        return null;
    }

    public List<IDatoVisual> listaPrestamosBusqueda(String textoBusqueda, String tipoBusqueda) {
        List<IDatoVisual> prestamos = new ArrayList<>();
        String qry = "SELECT p.id_prestamo, p.fecha_prestamo, p.fecha_vencimiento, p.fecha_devolucion, " +
                "u.nombre, u.apellido_paterno, u.apellido_materno, " +
                "l.titulo AS libro, p.estado " +
                "FROM prestamo p " +
                "JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "JOIN libro l ON p.id_libro = l.id_libro " +
                "WHERE p.D_E_L_E_T_E = FALSE ";

        // Agrega la condición según el tipo de búsqueda
        if (tipoBusqueda.equals("BUSQUEDA_POR_TITULO")) {
            qry += "AND l.titulo ILIKE ? ";
        } else {
            qry += "AND u.id_usuario = ? ";
        }

        qry += "ORDER BY " +
                "  CASE p.estado " +
                "    WHEN 'activo' THEN 1 " +
                "    WHEN 'vencido' THEN 2 " +
                "    WHEN 'devuelto' THEN 3 " +
                "  END, p.id_prestamo";

        try (Connection cnn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = cnn.prepareStatement(qry)) {

            if (tipoBusqueda.equals("BUSQUEDA_POR_TITULO")) {
                stmt.setString(1, "%" + textoBusqueda + "%");
            } else {
                if(!textoBusqueda.isEmpty()) {
                    int id = Integer.parseInt(textoBusqueda);
                    stmt.setInt(1, id);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idPrestamo = rs.getInt("id_prestamo");
                    Date fechaPrestamo = rs.getDate("fecha_prestamo");
                    String usuario = rs.getString("nombre") + " " +
                            rs.getString("apellido_paterno") + " " +
                            rs.getString("apellido_materno");
                    String libro = rs.getString("libro");
                    String estado = rs.getString("estado");

                    PrestamoCardData prestamo = new PrestamoCardData(idPrestamo, fechaPrestamo, usuario, libro, estado);
                    prestamos.add(prestamo);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            String mensajeError = "Error al buscar préstamos:"  + e.getMessage();
            JOptionPane.showMessageDialog(null,mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

        return prestamos;
    }


    public Prestamo obtener(int id) {
        String qry = "Select * from prestamo Where id_prestamo = ? ";
        try(Connection cnn =ConexionDB.getOrCreate().getConexion();
        PreparedStatement stmt = cnn.prepareStatement(qry)){
            stmt.setInt(1, id);
            try(ResultSet rs =  stmt.executeQuery()){
             if(rs.next()){
                 int idPrestamo = rs.getInt("id_prestamo");
                 Date fechaPrestamo = rs.getDate("fecha_prestamo");
                 Date fechaVencimiento = rs.getDate("fecha_vencimiento");
                 Date fechaDevolucion = rs.getDate("fecha_devolucion");
                 int idUsuario = rs.getInt("id_usuario");
                 int idLibro = rs.getInt("id_libro");
                 String estado = rs.getString("estado");

                 return new Prestamo(idPrestamo, fechaPrestamo, fechaVencimiento, fechaDevolucion,idUsuario, idLibro, estado );
             }
            }
        }catch (SQLException e){
            String mensajeError = "Error al obtener préstamos:"  + e.getMessage();
            JOptionPane.showMessageDialog(null,mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
        return null;
    }

    public void modificar(Prestamo prestamo) {

        String qry = "UPDATE prestamo SET " +
                "fecha_prestamo = ?, " +
                "fecha_vencimiento = ?, " +
                "fecha_devolucion = ?, " +
                "estado = ?, " +
                "id_libro = ?, " +
                "id_usuario = ? " +
                "WHERE id_prestamo = ?";

        try (Connection cnn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = cnn.prepareStatement(qry)) {

            stmt.setDate(1, prestamo.getFecha());
            stmt.setDate(2, prestamo.getFechaVencimiento());
            stmt.setDate(3, prestamo.getFechaDevolucion());
            stmt.setString(4, prestamo.getEstado());
            stmt.setInt(5, prestamo.getIdLibro());
            stmt.setInt(6, prestamo.getIdUsuario());
            stmt.setInt(7, prestamo.getId());

            int affectedRows = stmt.executeUpdate();
            String mensajeInfo;
            if (affectedRows > 0) {
                 mensajeInfo = "Se modificó el prestamo con ID: " + prestamo.getId();
                log.info(mensajeInfo);
                Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
                logDAO.insertar(logEntrada);
            }


        } catch (SQLException e) {
            String mensajeError = "Error al modificar prestamo: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

    }
    public void agregar(Prestamo nuevoPrestamo) {
        String qry = "CALL realizar_prestamo(?,?)";

        try(Connection cnn = ConexionDB.getOrCreate().getConexion();
            PreparedStatement stmt = cnn.prepareStatement(qry)){
            stmt.setInt(1, nuevoPrestamo.getIdUsuario());
            stmt.setInt(2, nuevoPrestamo.getIdLibro());
            stmt.execute();
            String mensajeInfo = "Se agrego el prestamo con ID: " + nuevoPrestamo.getId() ;
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);

        } catch (SQLException e) {
            String mensajeError = "Error al agregar prestamo: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getNextId() {
        String qry = "Select Max(id_prestamo) as id_max from Prestamo";
        try(Connection cnn = ConexionDB.getOrCreate().getConexion();
        PreparedStatement stmt = cnn.prepareStatement(qry);
        ResultSet rs = stmt.executeQuery()){
            if(rs.next()) {
                return rs.getInt("id_max");
            }
        }catch (SQLException e){
            log.error("Error al obtener el siguiente id en prestamos: " + e.getMessage());
        }
        return -1;
    }


    public void realizarDevolucion(int idPrestamo) {
        String qry = "call realizar_devolucion(?)";
        try (Connection cnn = ConexionDB.getOrCreate().getConexion();
             CallableStatement stmt = cnn.prepareCall(qry)) {
            stmt.setInt(1, idPrestamo);
            stmt.execute();
        } catch (SQLException e) {
            String mensajeInfo = "Se eliminó el prestamo con ID: " + idPrestamo ;
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
            JOptionPane.showMessageDialog(null, e.getMessage(), mensajeInfo, JOptionPane.ERROR_MESSAGE);
        }
    }
}
