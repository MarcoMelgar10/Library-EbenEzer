package com.odvp.biblioteca.database.daos;

import com.odvp.biblioteca.objetos.Log;
import com.odvp.biblioteca.objetosVisuales.CategoryData;
import com.odvp.biblioteca.database.ConexionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO{
    private static Logger log = LogManager.getRootLogger();
    LogDAO logDAO = LogDAO.getInstancia();

    public void insertar(CategoryData categoryData) {
        String qry = "CALL agregar_categoria(?, ?)";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, categoryData.getNombre());
            stmt.setString(2, categoryData.getDescripcion());
            stmt.execute();
            String mensajeInfo = "Se agrego la categoria: " + categoryData.getNombre();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al insertar categoria: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

    }

    public CategoryData obtener(int id) {
        String sql = "SELECT id_categoria, nombre, descripcion FROM categoria WHERE id_categoria = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new CategoryData(
                            rs.getInt("id_categoria"),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    );
                }
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener categoria: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
        return null;
    }

    public void modificar(CategoryData categoryData) {
        String qry = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id_categoria = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, categoryData.getNombre());
            stmt.setString(2, categoryData.getDescripcion());
            stmt.setInt(3, categoryData.getId());
            stmt.executeUpdate();
            String mensajeInfo = "Se modifico la categoria: " + categoryData.getNombre();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al insertar categoria: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    public void eliminar(int id) {
        String qry = "DELETE FROM categoria WHERE id_categoria = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            String mensajeInfo = "Se elimino la categoria: " + id;
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al eliminar categoria: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    public int getIdCategoria(String nombre) {
        String qry = "SELECT buscar_categoria(?)";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // No encontrado
    }

    public List<CategoryData> listaCategorias() {
        List<CategoryData> categorias = new ArrayList<>();
        String qry = "SELECT id_categoria, nombre, descripcion FROM categoria";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(new CategoryData(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return categorias;
    }

    public List<CategoryData> listaCategoriasAlfabeticamente() {
        List<CategoryData> categorias = new ArrayList<>();
        String qry = "SELECT id_categoria, nombre, descripcion FROM categoria ORDER BY nombre";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(new CategoryData(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            log.error("Error al obtener la categoria: " + e.getMessage());
        }
        return categorias;
    }
}
