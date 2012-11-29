package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Location implements Serializable {
	
	@Id
	int id;
	double lat;
	double lon;
	int mcc;
	int mnc;
	int lac;
	int cellid;
	
	Location() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public int getMcc() {
		return mcc;
	}
	public void setMcc(int mcc) {
		this.mcc = mcc;
	}
	public int getMnc() {
		return mnc;
	}
	public void setMnc(int mnc) {
		this.mnc = mnc;
	}
	public int getLac() {
		return lac;
	}
	public void setLac(int lac) {
		this.lac = lac;
	}
	public int getCellid() {
		return cellid;
	}
	public void setCellid(int cellid) {
		this.cellid = cellid;
	}
	
	@Override
	public int hashCode() {
		return  (getCellid()+getMcc()) * 10000 + (int)lon+(int)lat;
	}
	
	public Location(int id, double lat, double lon, int mcc, int mnc, int lac,
			int cellid) {
		super();
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.mcc = mcc;
		this.mnc = mnc;
		this.lac = lac;
		this.cellid = cellid;
	}

	@Override
	public boolean equals(Object c) {
		if(c == this) {
			return true;
		}
		if(c instanceof Location && c != null) {
			return ((Location)c).getCellid() == this.getCellid() && ((Location)c).getMcc() == this.getMcc()
					&& ((Location)c).getLat()==this.getLat() && ((Location)c).getMnc()==this.getMnc() &&
							((Location)c).getLac()==this.getLac();

		} else {
			return false;
		}
	}
	

}
