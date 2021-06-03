package etf.openpgp.lf170410d;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.bc.BcPGPSecretKeyRing;
import org.bouncycastle.openpgp.bc.BcPGPSecretKeyRingCollection;

public class SecretKeyRingCollection {
	
private static SecretKeyRingCollection s=null;
	
	private PGPSecretKeyRingCollection secret_ring_collection;
	
	public static SecretKeyRingCollection getInstance() {
		if(s==null) {
			s= new SecretKeyRingCollection();
			return s;
		}
		else return s;
	}
	
    private SecretKeyRingCollection() {
    	try {
			secret_ring_collection=new BcPGPSecretKeyRingCollection(new BufferedInputStream(new FileInputStream("Privatekeyringcollection.asc")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    public BcPGPSecretKeyRing get(String user_id) {
    	try {
			if(secret_ring_collection.getKeyRings(user_id).hasNext()) {
				return (BcPGPSecretKeyRing)secret_ring_collection.getKeyRings(user_id).next();
			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
	
    public void export_key_ring(String user_id,String filename) {
    	BcPGPSecretKeyRing bc_secret = get(user_id);
    	if(bc_secret!=null) {
    		try {
				bc_secret.encode(new BufferedOutputStream(new FileOutputStream("Secret_key_"+user_id+".pkr")));
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
			BcPGPSecretKeyRing bc_secret = new BcPGPSecretKeyRing(new BufferedInputStream(new FileInputStream(filename)));
			BcPGPSecretKeyRingCollection.addSecretKeyRing(secret_ring_collection,bc_secret);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
