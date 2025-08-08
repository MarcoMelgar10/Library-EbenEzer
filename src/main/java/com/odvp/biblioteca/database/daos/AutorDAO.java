package com.odvp.biblioteca.database.daos;

import com.odvp.biblioteca.objetos.Autor;
import com.odvp.biblioteca.objetos.Log;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.database.ConexionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
Clase para realizar las consultas y operaciones en Autor: crear, leer, actualizar, eliminar
 */

public class AutorDAO {
    private static Logger log = LogManager.getRootLogger();
    LogDAO logDAO = LogDAO.getInstancia();

    // Insertar un nuevo autor
    public void insertar(Autor autor) {
        String qry = "INSERT INTO autor(nombre,resena) VALUES (?,?)";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, autor.getNombre());
            stmt.setString(2, autor.getDescripcion());
            stmt.execute();
            String mensajeInfo = "Autor ingresado ";
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al ingresar autor: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    // Devolver el autor que se busca

    public Autor obtener(int id) {
        String qry = "SELECT id_autor, nombre, resena FROM autor WHERE id_autor = ?";
        Autor autor = null;

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idAutor = rs.getInt("id_autor");
                    String nombreAutor = rs.getString("nombre");
                    String biografia = rs.getString("resena");
                    autor = new Autor(idAutor, nombreAutor, biografia);
                }
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener autor: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
        return autor;
    }

    // Función para modificar autor, pasándole el mismo id pero con los datos modificados

    public void modificar(Autor autor) {
        String  qry = "UPDATE autor SET nombre = ?, resena = ? WHERE id_autor = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, autor.getNombre());
            stmt.setString(2, autor.getDescripcion());
            stmt.setInt(3, autor.getID());
            stmt.executeUpdate();
            String mensajeInfo = "Se modifico el autor " + autor.getNombre();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al modificar autor: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    public Integer getNroLibrosConAutor(int id){
        String qry = "Select COUNT(id_libro) as nroLibros from libro where id_autor = ?";
        int nroLibros = 0;
        try(Connection conn = ConexionDB.getOrCreate().getConexion();
        PreparedStatement ps = conn.prepareStatement(qry)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    nroLibros = rs.getInt("nroLibros");
                    return nroLibros;
                }
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    public void eliminar(int id) {
        String qry = "UPDATE autor SET D_E_L_E_T_E = true where id_autor = ?";
        try(Connection conn = ConexionDB.getOrCreate().getConexion();
        PreparedStatement ps = conn.prepareStatement(qry)){
            ps.setInt(1, id);
            ps.executeUpdate();
            String mensajeInfo = "Se elimino el autor con ID: " + id;
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al eliminar autor: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

    }



    public ArrayList<IDatoVisual> obtenerAutoresAlfabeticamente() {
        String qry = "SELECT id_autor, nombre, resena FROM autor WHERE D_E_L_E_T_E = false ORDER BY nombre";
        ArrayList<IDatoVisual> autores = new ArrayList<>();

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idAutor = rs.getInt("id_autor");
                String nombre = rs.getString("nombre");
                String biografia = rs.getString("resena");

                Autor autor = new Autor(idAutor, nombre, biografia);
                autores.add(autor);
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener lista: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
        return autores;
    }


    public Integer getNextId(){
        String qry = "SELECT MAX(id_autor) AS max_autor from autor";
        int maxId;
        try(Connection conn = ConexionDB.getOrCreate().getConexion();
            PreparedStatement ps = conn.prepareStatement(qry);
            ResultSet rs = ps.executeQuery()){
            if(rs.next()){
                maxId = rs.getInt("max_autor");
                return maxId +1;
            }

        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    public List<IDatoVisual> listaAutoresParametrizado(String textoBusqueda) {
        String qry = "SELECT id_autor, nombre, resena FROM autor WHERE D_E_L_E_T_E = false";
        ArrayList<IDatoVisual> autores = new ArrayList<>();

        if (textoBusqueda != null && !textoBusqueda.isEmpty()) {
            qry += " AND nombre ILIKE ?";
        }

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement pstm = conn.prepareStatement(qry)) {

            if (textoBusqueda != null && !textoBusqueda.isEmpty()) {
                String parametroBusqueda = "%" + textoBusqueda + "%";
                pstm.setString(1, parametroBusqueda);
            }

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    int idAutor = rs.getInt("id_autor");
                    String nombre = rs.getString("nombre");
                    String resena = rs.getString("resena");

                    Autor autor = new Autor(idAutor, nombre,resena);
                    autores.add(autor);
                }
            }
        } catch (SQLException e) {
           log.error("Error al obtner lista: " + e.getMessage());
        }
        return autores;
    }
}