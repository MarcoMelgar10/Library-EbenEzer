package com.odvp.biblioteca.database.daos;
import com.odvp.biblioteca.objetos.Log;
import com.odvp.biblioteca.objetosVisuales.SubCategoryData;
import com.odvp.biblioteca.database.ConexionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
  Clase para realizar la interaccion con la base de datas, para la tabla subcategoria.
   */
public class SubCategoriaDAO{
    private static Logger log = LogManager.getRootLogger();
    LogDAO logDAO = LogDAO.getInstancia();
    public void insertar(SubCategoryData subCategoryData) {
        String qry = "CALL agregar_sub_categoria(?,?)";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, subCategoryData.getNombre());
            stmt.setString(2, subCategoryData.getDescripcion());
            stmt.setString(3, subCategoryData.getCategoria());
            stmt.execute();
            String mensajeInfo = "Se agrego la subcategoria con ID: " + subCategoryData.getId();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);

        } catch (SQLException e) {
            String mensajeError = "Error al agregar la subcategoria: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public SubCategoryData obtener(int id) {
        String qry = "SELECT id_sub_categoria, nombre, descripcion, id_categoria FROM sub_categoria WHERE id_sub_categoria = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    SubCategoryData subCategoryData = new SubCategoryData(
                            rs.getInt("id_sub_categoria"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getInt("id_categoria"));
                    return subCategoryData;
                }
            }
            String mensajeInfo = "Se obtuvo la subcategoria con ID: " + id;
            log.info(mensajeInfo);
        } catch (SQLException e) {
            String mensajeError = "Error al obtener la subcategoria: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }


    public void modificar(SubCategoryData categorySubData) {

    }


    public void eliminar(int id) {

    }

    public ArrayList<SubCategoryData> obtenerSubCategoriasPorCategoria(int categoriaId) {
        String qry = "SELECT * " +
                "FROM sub_categoria " +
                "WHERE id_categoria = '"+ categoriaId +"'";
        ArrayList<SubCategoryData> subCategorias = new ArrayList<>();

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement ps = conn.prepareStatement(qry);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_sub_categoria");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                int idCategoria = rs.getInt("id_categoria");
                subCategorias.add(new SubCategoryData(id, nombre, descripcion, idCategoria));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subCategorias;
    }

    public ArrayList<SubCategoryData> obtenerSubCategoriasPorCategoriaAlfabeticamente(int categoriaId) {
        String qry = "SELECT * " +
                "FROM sub_categoria " +
                "WHERE id_categoria = '"+ categoriaId +"' order by nombre";
        ArrayList<SubCategoryData> subCategorias = new ArrayList<>();

        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement ps = conn.prepareStatement(qry);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_sub_categoria");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                int idCategoria = rs.getInt("id_categoria");
                subCategorias.add(new SubCategoryData(id, nombre, descripcion, idCategoria));
            }String mensajeInfo = "Se obtuvo la subcategoria de la categoria: " + categoriaId;
            log.info(mensajeInfo);

        } catch (SQLException e) {
            String mensajeError = "Error al agregar la subcategoria: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return subCategorias;
    }

}
