package com.odvp.biblioteca.database.daos;

import com.odvp.biblioteca.main.modulos.defaulltComponents.IFiltro;
import com.odvp.biblioteca.main.modulos.libros.ModeloLibros;
import com.odvp.biblioteca.objetos.Log;
import com.odvp.biblioteca.objetosVisuales.CategoryData;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetos.Libro;
import com.odvp.biblioteca.objetosVisuales.LibroCardData;
import com.odvp.biblioteca.database.ConexionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
  Clase para realizar la interacción con la base de datos, para la tabla libro.
 */
public class LibroDAO{
    private static Logger log = LogManager.getRootLogger();
    LogDAO logDAO = LogDAO.getInstancia();

        // Inserta nuevos libros en la BD
    public void insertar(Libro libro) {
        String qry = "CALL agregar_libro(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getObservacion());
            if (libro.getPublicacion() == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, libro.getPublicacion());
            }
            stmt.setInt(4, libro.getStock());
            stmt.setInt(5, libro.getIdAutor());
            stmt.setInt(6, libro.getIdCategoria());
            stmt.setInt(7, libro.getIdSubCategoria());
            stmt.execute();
            String mensajeInfo = "Se agrego el libro: " + libro.getTitulo();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al agregar libro: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    // Obtiene el siguiente ID disponible para un libro
    public Integer getNextId() {
        String qry = "SELECT MAX(id_libro) AS id_libro FROM libro WHERE d_e_l_e_t_e = 'false'";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(qry);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id_libro") + 1;
            }
        } catch (SQLException e) {
            log.error("Error al obtener el siguiente id: " + e.getMessage());
        }
        return 0;
    }

    // Busca y devuelve un libro específico por ID
    public Libro obtener(int id) {
        String qry = "SELECT l.id_libro, l.titulo, l.observacion, l.fecha_publicacion, " +
                "l.stock, l.stock_disponible, a.nombre AS autor, " +
                "c.nombre AS categoria, s.nombre AS sub_categoria " +
                "FROM libro l " +
                "JOIN autor a ON l.id_autor = a.id_autor " +
                "JOIN categoria c ON l.id_categoria = c.id_categoria " +
                "JOIN sub_categoria s ON l.id_sub_categoria = s.id_sub_categoria " +
                "WHERE l.id_libro = ? and l.d_e_l_e_t_e = 'false'";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Libro.Builder()
                            .ID(rs.getInt("id_libro"))
                            .titulo(rs.getString("titulo"))
                            .observacion(rs.getString("observacion"))
                            .publicacion(rs.getDate("fecha_publicacion"))
                            .stock(rs.getInt("stock"))
                            .stockDisponible(rs.getInt("stock_disponible"))
                            .autor(rs.getString("autor"))
                            .categoria(rs.getString("categoria"))
                            .subCategoria(rs.getString("sub_categoria"))
                            .build();
                }
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener libro: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
        return null;
    }

    // Modifica un libro existente
    public void modificar(Libro libro) {
        String qry = "UPDATE libro SET titulo = ?, observacion = ?, fecha_publicacion = ?, stock = ?, " +
                "stock_disponible = ?, id_autor = ?, id_categoria = ?, id_sub_categoria = ? " +
                "WHERE id_libro = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getObservacion());
            stmt.setDate(3, libro.getPublicacion());
            stmt.setInt(4, libro.getStock());
            stmt.setInt(5, libro.getStockDisponible());
            stmt.setInt(6, libro.getIdAutor());
            stmt.setInt(7, libro.getIdCategoria());
            stmt.setInt(8, libro.getIdSubCategoria());
            stmt.setInt(9, libro.getID());
            stmt.executeUpdate();
            String mensajeInfo = "Se modificó el libro: " + libro.getTitulo();
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al modificar libro: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    // Elimina un libro (marcándolo como eliminado)
    public void eliminar(int id) {
        String qry = "UPDATE libro SET d_e_l_e_t_e = 'true' WHERE id_libro = ?";
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            String mensajeInfo = "Se elimino el libro con ID: " + id;
            log.info(mensajeInfo);
            Log logEntrada = new Log("INFO", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        } catch (SQLException e) {
            String mensajeError = "Error al eliminar libro: " + e.getMessage();
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
    }

    // Obtiene los nombres de las columnas de la tabla libro
    public ArrayList<String> nombreColumnas() {
        String qry = "SELECT column_name FROM information_schema.columns WHERE table_name = 'libro'";
        ArrayList<String> columnas = new ArrayList<>();
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                columnas.add(rs.getString("column_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnas;
    }

    // Obtiene una lista de todos los libros
    public ArrayList<Libro> listaLibros() {
        String qry = "SELECT l.id_libro, l.titulo, l.observacion, l.fecha_publicacion, l.stock, " +
                "l.stock_disponible, l.id_autor, l.id_categoria, l.id_sub_categoria, a.nombre " +
                "FROM libro l JOIN autor a ON l.id_autor = a.id_autor";
        ArrayList<Libro> libros = new ArrayList<>();
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                libros.add(new Libro.Builder()
                        .ID(rs.getInt("id_libro"))
                        .titulo(rs.getString("titulo"))
                        .observacion(rs.getString("observacion"))
                        .publicacion(rs.getDate("fecha_publicacion"))
                        .stock(rs.getInt("stock"))
                        .stockDisponible(rs.getInt("stock_disponible"))
                        .idAutor(rs.getInt("id_autor"))
                        .idCategoria(rs.getInt("id_categoria"))
                        .idSubCategoria(rs.getInt("id_sub_categoria"))
                        .autor(rs.getString("nombre"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    // Obtiene una lista de libros para visualización (sin detalles completos)
    public ArrayList<IDatoVisual> listaLibrosVisual() {
        String qry = "SELECT l.id_libro, l.titulo, l.stock, l.stock_disponible, a.nombre " +
                "FROM libro l JOIN autor a ON l.id_autor = a.id_autor " +
                "WHERE l.d_e_l_e_t_e = 'false'" +
                "ORDER BY l.id_libro";
        ArrayList<IDatoVisual> libros = new ArrayList<>();
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             PreparedStatement stmt = conn.prepareStatement(qry);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                libros.add(new LibroCardData(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getInt("stock_disponible")
                ));
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener la lista de libros";
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }
        return libros;
    }

    // Obtiene una lista de libros con filtros aplicados
    public ArrayList<IDatoVisual> listaLibrosVisualParametrizada(String textoBusqueda, List<CategoryData> categoriasSeleccionadas, List<IFiltro> filtrosSeleccionados, String tipoBusqueda) {
        StringBuilder query = new StringBuilder(
                "SELECT l.id_libro, l.titulo, l.stock, l.stock_disponible, a.nombre " +
                        "FROM libro l JOIN autor a ON l.id_autor = a.id_autor " +
                        "WHERE l.d_e_l_e_t_e = 'false'"
        );

        // Agregar condición de búsqueda por título o autor
        if (textoBusqueda != null && !textoBusqueda.isEmpty()) {
            if (ModeloLibros.BUSQUEDA_POR_TITULO.equals(tipoBusqueda)) {
                query.append(" AND l.titulo ILIKE '%").append(textoBusqueda.replace("'", "''")).append("%'");
            } else if (ModeloLibros.BUSQUEDA_POR_AUTOR.equals(tipoBusqueda)) {
                query.append(" AND a.nombre ILIKE '%").append(textoBusqueda.replace("'", "''")).append("%'");
            }
        }

        // Agregar filtro de categorías
        if (categoriasSeleccionadas != null && !categoriasSeleccionadas.isEmpty()) {
            query.append(" AND l.id_categoria IN (");
            for (CategoryData categoria : categoriasSeleccionadas) {
                query.append(categoria.getId()).append(",");
            }
            query.deleteCharAt(query.length() - 1); // Eliminar la última coma
            query.append(")");
        }

        if (filtrosSeleccionados != null && !filtrosSeleccionados.isEmpty()) {
            for (IFiltro filtro : filtrosSeleccionados) {
                query.append(filtro.getQry());
            }
        }

        ArrayList<IDatoVisual> libros = new ArrayList<>();
        try (Connection conn = ConexionDB.getOrCreate().getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {

            while (rs.next()) {
                libros.add(new LibroCardData(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getInt("stock_disponible")
                ));
            }
        } catch (SQLException e) {
            String mensajeError = "Error al obtener la lista de libros parametrizada";
            log.error(mensajeError);
            Log logEntrada = new Log("ERROR", mensajeError, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
        }

        return libros;
    }


}
