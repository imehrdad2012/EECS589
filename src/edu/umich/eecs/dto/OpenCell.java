package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OpenCell implements Serializable {
	
	@Id
	@GeneratedValue
	int artificalID;
	double latitude;
	double longitude;
	int mcc;
	int mnc;
	int lac;
	int cellid;
	
	OpenCell() {
		super();
	}
	
	
	@Override
	public int hashCode() {
		return  (getCellid()+getMcc()) * 10000 + (int)getLongitude()+(int)getLatitude();
	}
	
	public 	OpenCell( double lat, double lon, int mcc, int mnc, int lac,
			int cellid) {
		super();
		this.latitude = lat;
		this.longitude = lon;
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
		if(c instanceof OpenCell && c != null) {
			return ((OpenCell)c).getCellid() == this.getCellid() && ((OpenCell)c).getMcc() == this.getMcc()
					&& ((OpenCell)c).getLatitude()==this.getLatitude() && ((OpenCell)c).getMnc()==this.getMnc() &&
							((OpenCell)c).getLac()==this.getLac();

		} else {
			return false;
		}
	}




	public double getLatitude() {
		return latitude;
	}



	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public double getLongitude() {
		return longitude;
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
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
	public String toString() {
		return new StringBuilder().append("cellId:"+getCellid()+" ").
				append("mnc:"+getMnc()+" ").append("mcc:"+getMcc()+" ")
				.append("Lac:"+getLac()+" ").toString();
	}

}
