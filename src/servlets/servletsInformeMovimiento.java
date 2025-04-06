package servlets;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import entidades.Usuarios;
import negocioImp.MovimientosNegImp;

/**
 * Servlet implementation class servletsInformeMovimiento
 */
@WebServlet("/servletsInformeMovimiento")
public class servletsInformeMovimiento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MovimientosNegImp mNeg = new MovimientosNegImp();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletsInformeMovimiento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Recuperar la sesión actual sin crear una nueva
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if(request.getParameter("Param") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/InformeTipoMovimientos.jsp");
		    dispatcher.forward(request, response);
		}
        if (request.getParameter("btnGenerarGrafico") != null) {
        	// Obtengo las fechas y manejo posibles errores
        	LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"));
        	LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"));

        	// Obtengo los datos
        	Map<String, Integer> porcentajePorMonto = mNeg.porcentajeTiposMovimientosPorFecha(fechaInicio, fechaFin);

        	if (porcentajePorMonto.isEmpty()) {
        	    response.sendError(HttpServletResponse.SC_NO_CONTENT, "No hay datos para el rango de fechas seleccionado.");
        	    return;
        	}

        	// Crear el conjunto de datos para gráfico de pastel
        	DefaultPieDataset dataset = new DefaultPieDataset();
        	for (Map.Entry<String, Integer> entry : porcentajePorMonto.entrySet()) {
        	    dataset.setValue(entry.getKey(), entry.getValue());
        	}

        	// Crear el gráfico de pastel
        	JFreeChart chart = ChartFactory.createPieChart(
        	        "Porcentaje por Tipo de Movimiento",  // Título
        	        dataset,                             // Conjunto de datos
        	        true,                                // Leyenda
        	        true,                                // Tooltips
        	        false                                // URLs
        	);

        	// Personalizar el diseño del gráfico
        	chart.getTitle().setFont(new Font("Serif", Font.BOLD, 18));
        	PiePlot plot = (PiePlot) chart.getPlot();
        	plot.setBackgroundPaint(Color.WHITE);
        	plot.setCircular(true); // Asegura que el gráfico sea circular
        	plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        	plot.setLabelBackgroundPaint(new Color(220, 220, 220));
        	plot.setLabelOutlinePaint(null);
        	plot.setLabelShadowPaint(null);
        	plot.setShadowPaint(new Color(200, 200, 200));

        	plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
        	        "{0}: {1} ({2})",                    // Formato: Nombre, Valor, Porcentaje
        	        new DecimalFormat("0"),              // Formato del valor
        	        new DecimalFormat("0.00%")           // Formato del porcentaje
        	));

        	// Configurar salida del gráfico como imagen PNG
        	response.setContentType("image/png");
        	try (OutputStream out = response.getOutputStream()) {
        	    org.jfree.chart.ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
        	}
        }
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
