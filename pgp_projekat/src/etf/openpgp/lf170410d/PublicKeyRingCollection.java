package etf.openpgp.lf170410d;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.bc.BcPGPPublicKeyRing;
import org.bouncycastle.openpgp.bc.BcPGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;

import java.io.*;

public class PublicKeyRingCollection {
	
	private static PublicKeyRingCollection s=null;
	
	public PGPPublicKeyRingCollection public_ring_collection;
	
	public static PublicKeyRingCollection getInstance() {
		if(s==null) {
			s= new PublicKeyRingCollection();
			return s;
		}
		else return s;
	}
	
    private PublicKeyRingCollection() {
    	try {
			public_ring_collection=new BcPGPPublicKeyRingCollection(new BufferedInputStream(new FileInputStream("publickeyringcollection.asc")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    public BcPGPPublicKeyRing get(String user_id) {
    	try {
			if(public_ring_collection.getKeyRings(user_id).hasNext()) {
				return (BcPGPPublicKeyRing)public_ring_collection.getKeyRings(user_id).next();
			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
	
    public void export_key_ring(String user_id,String filename) {
    	BcPGPPublicKeyRing bc_public = get(user_id);
    	if(bc_public!=null) {
    		try {
				bc_public.encode(new BufferedOutputStream(new FileOutputStream("public_key_"+user_id+".pkr")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public void import_key_ring(String filename) {
    	try {
			BcPGPPublicKeyRing bc_public = new BcPGPPublicKeyRing(new BufferedInputStream(new FileInputStream(filename)));
			BcPGPPublicKeyRingCollection.addPublicKeyRing(public_ring_collection,bc_public);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
