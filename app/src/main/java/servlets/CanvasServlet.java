package servlets;

import java.io.IOException;

import beans.PointListCDIBean;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CanvasServlet extends HttpServlet {
    @Inject
    PointListCDIBean pointBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        System.out.println("getServletInfo()");
        pointBean.processAreaRequest();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("getServletInfo()");
        pointBean.processAreaRequest();
    }
}
