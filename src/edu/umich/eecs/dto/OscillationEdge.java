package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class is persisted into our database as a simple model of each users' paths.
 * We have used auto-inc key for our table.
 * what we persist is:
 * 	-----------------------------------------------
 *  | Cell first | TotOsc| TotSwitch | Cell second |
 *  |-----------|--------------------|--------------
 *  |    34     |  100   |   48      |     70       |
 *  |-----------|  ------|-----------|--------------
 *  |    48     |  200   |  60       |      81      |
 *  |---------  |--------|-----------|--------------
 *
 * @author Mehrdad
 * 
 */

@Entity
@Table(name = "osc_edges")
public class OscillationEdge implements Serializable {

	@Id
	private OscillationEdgeKey key;
	private int totSwitch;
	private int totOscillation;

	public OscillationEdge(Cell first, Cell second, int totSwitch,
			int totOscillation) {

		this.key = new OscillationEdgeKey(first, second);
		this.key.setSecond(second);
		this.totSwitch = totSwitch;

		this.totOscillation = totOscillation;

	}

	OscillationEdge() {

	}

	public int getTotSwitch() {
		return totSwitch;
	}

	public void setTotSwitch(int totSwitch) {
		this.totSwitch = totSwitch;
	}

	public int getTotOscillation() {
		return totOscillation;
	}

	public void setTotOscillation(int totOscillation) {
		this.totOscillation = totOscillation;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(
				"First Cell: " + this.key.getFirst().toString()
						+ "  TotalNumberofOscillation: " + getTotOscillation()
						+ "   TotalNumberofSwitches:" + getTotSwitch()
						+ "  Second Cell: " + this.key.getSecond().toString())
				.toString();

	}

}

@Embeddable
class OscillationEdgeKey implements Serializable {

	@AttributeOverrides({
			@AttributeOverride(name = "areaID", column = @Column(name = "secondAreaID")),
			@AttributeOverride(name = "cellID", column = @Column(name = "secondCellID")) })
	private Cell second;

	@AttributeOverrides({
			@AttributeOverride(name = "areaID", column = @Column(name = "firstAreaID")),
			@AttributeOverride(name = "cellID", column = @Column(name = "firstCellID")) })
	private Cell first;

	public OscillationEdgeKey() {
	}

	public Cell getFirst() {
		return first;
	}

	public OscillationEdgeKey(Cell first, Cell second) {
		super();
		this.second = second;
		this.first = first;
	}

	public void setFirst(Cell first) {
		this.first = first;
	}

	public Cell getSecond() {
		return second;
	}

	public void setSecond(Cell second) {
		this.second = second;
	}

}
