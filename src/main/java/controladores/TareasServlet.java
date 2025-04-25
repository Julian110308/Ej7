/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Personal
 */
public class TareasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<Tarea> listaTareas = (List<Tarea>)session.getAttribute("tareas");
        
        if(listaTareas == null){
            listaTareas = new ArrayList<>();
            session.setAttribute("tareas", listaTareas);
        }
        
        String pathInfo = request.getPathInfo();
        
        if(pathInfo == null || "/".equals(pathInfo)){
            request.getRequestDispatcher("/WEB-INF/views/listarTareas.jsp").forward(request,response);
        } else if("/nueva".equals(pathInfo)){
            request.getRequestDispatcher("WEB/INF/views/nuevaTarea.jsp").forward(request, response);
        } else if(pathInfo.startsWith("/completar/")){
            try{
                int id = Integer.parseInt(pathInfo.substring("/completar/".length()));
                for(Tarea tarea : listaTareas){
                    if(tarea.getId() == id){
                        tarea.setCompletada(true);
                        break;
                    }
                }
            }catch(NumberFormatException e){
                
            }
            response.sendRedirect(request.getContextPath() + "/tareas");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Tarea> listaTareas = (List<Tarea>)session.getAttribute("tareas");
        
        if(listaTareas == null){
            listaTareas = new ArrayList<>();
            session.setAttribute("tareas", listaTareas);
        }
        
        String pathInfo = request.getPathInfo();
        
        if("/guardar".equals(pathInfo)){
            String descripcion = request.getParameter("descripcion");
            
            if(descripcion != null && !descripcion.trim().isEmpty()){
                int nuevoId = 1;
                if(!listaTareas.isEmpty()){
                    nuevoId = listaTareas.get(listaTareas.size() - 1).getId() + 1;
                }
                Tarea nuevaTarea = new Tarea(nuevoId, descripcion);
                listaTareas.add(nuevaTarea);
            }
            response.sendRedirect(request.getContextPath() +  "/tareas");
        }
    }
}
