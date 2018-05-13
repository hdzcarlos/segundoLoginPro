package com.cice.usuarios.dao;

import com.cice.usuarios.model.Conexion;
import com.cice.usuarios.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Conexion con;
    private Connection connection;

    public UsuarioDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        System.out.println(jdbcURL);
        con = new Conexion(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public boolean inserar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios ( id, nombre, pass) VALUES (?,?,?)";
        System.out.println("El usuario es " + usuario.getNombre());
        con.conectar();
        connection = con.getJdbcConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, null);
        statement.setString(1, usuario.getNombre());
        statement.setString(1, usuario.getPass());


        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        con.desconectr();
        return rowInserted;
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuarios";
        con.conectar();
        connection = con.getJdbcConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            String pass = resultSet.getString("pass");
            Usuario usuario = new Usuario(id, nombre, pass);
            listaUsuarios.add(usuario);

        }
        con.desconectr();
        return listaUsuarios;
    }

    public Usuario obtenerporId(int id) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        con.conectar();
        connection = con.getJdbcConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet res = statement.executeQuery();
        if (res.next()){
            usuario = new Usuario(res.getInt("id"), res.getString("nombre"),res.getString("pass"));
        }
        res.close();
        con.desconectr();

            return usuario;
    }
    public boolean eliminar (Usuario usuario) throws SQLException {
        boolean rowEliminar = false;
        String sql ="DELETE FROM usuarios WHERE id=?";
        con.conectar();
        connection = con.getJdbcConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,usuario.getId());
        rowEliminar = statement.executeUpdate() > 0;
        statement.close();
        con.desconectr();

        return rowEliminar;
    }

    public boolean actualizar (Usuario usuario) throws SQLException {
        boolean rowActualizar = false;
        String sql = " UPDATE usuarios SET name=?, pass=? WHERE id=?";
        con.conectar();
        connection = con.getJdbcConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,usuario.getNombre());
        statement.setString(1,usuario.getPass());
        System.out.println(usuario.getNombre());
        rowActualizar = statement.executeUpdate() > 0;
        statement.close();
        con.desconectr();
        return rowActualizar;
    }


}