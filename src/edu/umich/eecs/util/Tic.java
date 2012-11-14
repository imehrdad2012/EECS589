package edu.umich.eecs.util;

/** Execution time measurement.
 * This is based on Edwin Olson's April software library.
 *  **/
public class Tic
{
    long initTime;
    long startTime;
    boolean verbose;

	/** Includes an implicit call to tic() **/
    public Tic()
    {
    	verbose = false;
        initTime = System.nanoTime();
        startTime = initTime;
    }
    
    public Tic(boolean verbose) {
    	this.verbose = verbose;
        initTime = System.nanoTime();
        startTime = initTime;
    }

    /** Begin measuring time from now. **/
    public void tic()
    {
        startTime = System.nanoTime();
    }

    /** How much time has passed since the most recent call to tic()? **/
    public double toc()
    {
        long endTime = System.nanoTime();
        double elapsedTime = (endTime-startTime)/1000000000f;
        printElapsedTime(elapsedTime);
        return elapsedTime;
    }
    
    public double toc(String msg) {
        long endTime = System.nanoTime();
        double elapsedTime = (endTime-startTime)/1000000000f;
        printElapsedTime(msg, elapsedTime);
        return elapsedTime;   	
    }

    /** Equivalent to toc() followed by tic() **/
    public double toctic()
    {
        long endTime = System.nanoTime();
        double elapsedTime = (endTime-startTime)/1000000000f;
        printElapsedTime(elapsedTime);
        tic();
        return elapsedTime;
    }

    /** How much time has passed since the object was created? **/
    public double totalTime()
    {
        long endTime = System.nanoTime();
        double elapsedTime = (endTime-initTime)/1000000000f;
        if(verbose) {
			System.out.println("Total time: " + elapsedTime + " seconds");
		}
        return elapsedTime;
    }
    
    public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	} 
	
	private void printElapsedTime(String msg, double elapsedTime) {
		if(verbose) {
			System.out.println(msg + ": " + elapsedTime + " seconds");
		}
	}
	
	private void printElapsedTime(double elapsedTime) {
		printElapsedTime("Elapsed time", elapsedTime);
	}
}