package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.Color;
import java.awt.Font;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import entidades.Usuarios;
import negocio.PrestamosNeg;
import negocioImp.PrestamosNegImp;


@WebServlet("/servletInformePrestamos")
public class servletInformePrestamos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrestamosNeg pNeg = new PrestamosNegImp();

    public servletInformePrestamos() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if(request.getParameter("Param") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/InformePrestamos.jsp");
		    dispatcher.forward(request, response);
		}
		if(request.getParameter("btnGenerarGrafico") != null) {
			//obtengo las fechas
			LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"));
	        LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"));
	        
	        
	        Map<String, Integer> prestamosPorMonto = pNeg.contarPrestamosPorMontosYFechas(fechaInicio, fechaFin);
	        
	        //Crear el conjunto de datos
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        for (Map.Entry<String, Integer> entry : prestamosPorMonto.entrySet()) {
	            dataset.addValue(entry.getValue(), "Cantidad", entry.getKey());
	        }

	        //Crear el gráfico de barras
	        JFreeChart chart = ChartFactory.createBarChart(
	                "Cantidad de Préstamos según Rango de Montos", 
	                "Rango de Montos", 
	                "Cantidad de Préstamos", 
	                dataset, 
	                PlotOrientation.VERTICAL, 
	                false, true, false);

	        // diseño del gráfico
	        CategoryPlot plot = (CategoryPlot) chart.getPlot();
	        plot.setBackgroundPaint(Color.WHITE); 
	        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
	        chart.getTitle().setFont(new Font("Serif", Font.BOLD, 18));

	        // configuro el eje y para que vaya de 1 a 1
	        org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();
	        rangeAxis.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(1.0)); 
	        rangeAxis.setAutoRange(false);
	        rangeAxis.setRange(0, prestamosPorMonto.values().stream().max(Integer::compare).orElse(10)); 
	        
	        // mando el gráfico como imagen 
	        response.setContentType("image/png");
	        OutputStream out = response.getOutputStream();
	        org.jfree.chart.ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
	        out.close();
		}
		
	        
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
