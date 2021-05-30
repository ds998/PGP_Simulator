package etf.openpgp.lf170410d;

import java.io.IOException;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;

import java.io.*;

public class PublicKeyRingCollection {
	
	public PGPPublicKeyRingCollection public_ring_collection;
	
    public PublicKeyRingCollection() {
    	try {
			public_ring_collection=new PGPPublicKeyRingCollection(new FileInputStream("publickeyringcollection.asc"),new BcKeyFingerprintCalculator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
	

}
