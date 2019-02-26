/******************************************************************************
 * Implementation of UUID version 4 (the one that uses random/pseudo-random
 * numbers) 
 * By: Ashraf Amrou
 * Old Dominion University
 * Aug 14, 2003
 *****************************************************************************/
package gov.lanl.util.uuid;

import java.security.SecureRandom;

/**
 * Factory class for generating UUID version 4. All what this class does is
 * creating UUID version 4 objects using crypto-quality random numbers.
 *  
 */
public final class UUIDFactory {

    /**
     * Random number generator
     */
    private java.util.Random rand = null;

    /**
     * an instance
     */
    private static UUIDFactory generator = new UUIDFactory();

    ///////////////////////////////////////////////////
    /**
     * private constructor (Singleton class)
     */
    private UUIDFactory() {
        // crypto-quality random number generator
        rand = new SecureRandom();
    }

    ///////////////////////////////////////////////////
    /**
     * Customers of this class call this method to generete new UUID objects
     */
    public synchronized static UUID generateUUID() {
        return new UUID(generator.rand.nextLong(), generator.rand.nextLong());
    }
    ///////////////////////////////////////////////////

}
