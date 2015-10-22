import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JFreeCharTest extends HttpServlet {

	public JFreeCharTest() {
		super();
	}

	public void init() throws ServletException {
		
	}
	
	public void destroy() {
		super.destroy(); 
	}

    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException,  
            IOException {  
        doGet(request, response);  
    }  

	@Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
  
        //OutputStream out = response.getOutputStream();  
        //Creating parameters
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        System.out.println(startTime);
        //System.out.println(endTime);
        
        //Formating the time parameters into "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String newStartTime = sd.format(new Date(startTime));
        System.out.println(newStartTime);
        
        //Formating the time parameters into "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String newEndTime = sd1.format(new Date(endTime));
        System.out.println(newEndTime);
        
        
        //Creating json format ready to pass the parameters
        List<Station> listStation = getDataSet(newStartTime,newEndTime); 
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("listStation", listStation);  
        
        JSONArray jsonArray = new JSONArray();  
        jsonArray.add(jsonObject);  
        PrintWriter out = response.getWriter();  
        out.write(jsonArray.toString());  
    }  
  

  
    private List<Station> getDataSet(String startTime,String endTime) {
    	List<Station> listStation = new ArrayList<Station>();
    	try{
	    	//Connecting the database
	    	Connection cn = DB.getConn();
	    	System.out.println("Searching..");
	    	String sql = "select AVG(nbBikes) as vc,BIXI.station_id,BIXI_STATIONS.station_name,BIXI.datetime from BIXI INNER JOIN BIXI_STATIONS ON BIXI.station_id = BIXI_STATIONS.station_id WHERE BIXI.datetime >= timestamp('"+startTime+"') and BIXI.datetime <= timestamp('"+endTime+"')GROUP BY station_name order by vc desc limit 10";
	    	PreparedStatement ps = cn.prepareStatement(sql);
	    	ResultSet rs = ps.executeQuery();
	    	System.out.println("End");
	    	while(rs.next()){
	    		Station station = new Station();
	    		station.setVc(rs.getInt(1));
	    		station.setStation_id(rs.getInt(2));
	    		station.setStation_name(rs.getString(3));
	    		station.setDatatime(new java.util.Date(rs.getDate(4).getTime()));
	    		listStation.add(station);
	    		
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
 
        return listStation;  
    }  
	

}