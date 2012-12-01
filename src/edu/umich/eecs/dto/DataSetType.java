package edu.umich.eecs.dto;

public enum DataSetType {
	RealityMining(0),
	NokiaChallenge(1),
	SampledRealityMining(2);
	
    private final int id;
    private DataSetType(int id) { this.id = id; }
    public int getValue() { return id; }
    
    public static boolean isValid(DataSetType t) {
    	return t == RealityMining || t == NokiaChallenge;
    }
    public int asInt() { return id; }
}
