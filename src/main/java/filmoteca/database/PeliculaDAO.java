package filmoteca.database;

import filmoteca.domain.models.Pelicula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    public void crearPelicula(Pelicula pelicula) {
        String query = "INSERT INTO pelicula (titulo, director, anio, genero) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, pelicula.getTitulo());
            pst.setString(2, pelicula.getDirector());
            pst.setInt(3, pelicula.getAnio());
            pst.setString(4, pelicula.getGenero());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pelicula leerPelicula(int id) {
        String query = "SELECT * FROM pelicula WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Pelicula pelicula = new Pelicula(
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getInt("anio"),
                        rs.getString("genero")
                );
                pelicula.setId(rs.getInt("id"));
                return pelicula;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pelicula> leerTodasLasPeliculas() {
        String query = "SELECT * FROM pelicula";
        List<Pelicula> peliculas = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Pelicula pelicula = new Pelicula(
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getInt("anio"),
                        rs.getString("genero")
                );
                pelicula.setId(rs.getInt("id"));
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    public void actualizarPelicula(Pelicula pelicula) {
        String query = "UPDATE pelicula SET titulo = ?, director = ?, anio = ?, genero = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, pelicula.getTitulo());
            pst.setString(2, pelicula.getDirector());
            pst.setInt(3, pelicula.getAnio());
            pst.setString(4, pelicula.getGenero());
            pst.setInt(5, pelicula.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPelicula(int id) {
        String query = "DELETE FROM pelicula WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
