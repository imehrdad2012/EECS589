package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
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
 * 	|-----------------------------------------
 *  |PersonID | Cell from | Weight | Cell to |
 *  |---------|-----------|------------------|
 *  | 100     |  34       |  .1    |   48    |
 *  |---------|-----------|------------------|
 * 	| 100     |    48     |  .2    |  60     |
 *  |---------|-----------|------------------|
 *
 * @author Mehrdad
 * 
 */

@Entity
@Table(name="pathpiece")
public class PathPiece implements Serializable{
	
	// I've used auto generated key to reduce load on database while we do searching.
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	double weight;
	
	@Embedded
	  @AttributeOverrides( {
          @AttributeOverride(name="areaID", column = @Column(name="fromAreaID") ),
          @AttributeOverride(name="cellID", column = @Column(name="fromCellID") )
  } )
	Cell from;
	
	@Embedded
	  @AttributeOverrides( {
        @AttributeOverride(name="areaID", column = @Column(name="toAreaID") ),
        @AttributeOverride(name="cellID", column = @Column(name="toCellID") )
} )
	Cell to;

	PathPiece() {
		
	}

	public PathPiece( double weight, Cell from, Cell to) {

		this.weight = weight;
		this.from = from;
		this.to = to;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Cell getFrom() {
		return from;
	}

	public void setFrom(Cell from) {
		this.from = from;
	}

	public Cell getTo() {
		return to;
	}

	public void setTo(Cell to) {
		this.to = to;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(getFrom().toString()+"<--w:"+ 
	getWeight()+"-->"+getTo().toString()).toString();
				
		
	}

}
