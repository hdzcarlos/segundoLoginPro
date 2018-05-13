package com.cice.controlador;

import com.cice.usuarios.dao.UsuarioDAO;
import com.cice.usuarios.model.Usuario;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/adminUsuario")

public class AdminUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UsuarioDAO usuarioDAO;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
        try {
            usuarioDAO = new UsuarioDAO(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminUsuario() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hola Servlet...");
        String action = request.getParameter("action");
        System.out.println(action);
        try {
            switch (action) {
                case "index":
                    index(request, response);
                    break;
                case "nuevo":
                    nuevo(request, response);
                    break;
                case "retister":
                    System.out.println("Entro");
                    registrar(request, response);
                    break;
                case "showedit":
                    showEditar(request, response);
                    break;
                case "editar":
                    editar(request, response);
                    break;
                case "eliminar":
                    eliminar(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hellow Servlet");
        doGet(request, response);
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Usuario usuario = new Usuario(0, request.getParameter("name"), request.getParameter("pass"));
        usuarioDAO.inserar(usuario);

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);


    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/registrar.jsp");

    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/mostrar.jsp");
        List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();
        request.setAttribute("lista", listaUsuarios);
        dispatcher.forward(request, response);
    }

    private void showEditar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Usuario usuario = usuarioDAO.obtenerporId(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("usuario", usuario);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/editar.jsp");
        dispatcher.forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Usuario usuario = new Usuario(0, request.getParameter("name"), request.getParameter("pass"));
        usuarioDAO.actualizar(usuario);
        index(request, response);

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Usuario usuario = usuarioDAO.obtenerporId(Integer.parseInt(request.getParameter("id")));
        usuarioDAO.eliminar(usuario);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

}
