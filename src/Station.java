import java.util.Date;

public class Station {
	private int vc;
	private int station_id;
	private String station_name;
	private Date datatime;
	public int getVc() {
		return vc;
	}
	public void setVc(int vc) {
		this.vc = vc;
	}
	public int getStation_id() {
		return station_id;
	}
	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public Date getDatatime() {
		return datatime;
	}
	public void setDatatime(Date datatime) {
		this.datatime = datatime;
	}
	
	
}
