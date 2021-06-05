package etf.openpgp.lf170410d;

import org.bouncycastle.gpg.keybox.PublicKeyRingBlob;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.bc.BcPGPPublicKeyRing;
import org.bouncycastle.openpgp.bc.BcPGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;

import java.io.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PublicKeyRingCollection {
	
	private static PublicKeyRingCollection s=null;
	
	private PGPPublicKeyRingCollection public_ring_collection;
	
	public PGPPublicKeyRingCollection getPublic_ring_collection() {
		return public_ring_collection;
	}

	public static PublicKeyRingCollection getInstance() {
		if(s==null) {
			s= new PublicKeyRingCollection();
			return s;
		}
		else return s;
	}
	
    private PublicKeyRingCollection() {
    	try {
    		BufferedInputStream in = new BufferedInputStream(new FileInputStream("publickeyringcollection.asc"));
			public_ring_collection=new BcPGPPublicKeyRingCollection(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    public void putRing(PGPPublicKeyRing pkr) {
    	public_ring_collection=PGPPublicKeyRingCollection.addPublicKeyRing(public_ring_collection, pkr);
    }
    
    public void removeRing(String userID) {
    	try {
			if(public_ring_collection.getKeyRings(userID).hasNext()) {
				PGPPublicKeyRing bcpkr= public_ring_collection.getKeyRings(userID).next();
				public_ring_collection = PGPPublicKeyRingCollection.removePublicKeyRing(public_ring_collection, bcpkr);
			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public PGPPublicKey get(String user_id) {
    	/*try {
			if(public_ring_collection.getKeyRings(user_id).hasNext()) {
				System.out.println("aaa");
				PGPPublicKeyRing keyRing = public_ring_collection.getKeyRings(user_id).next();
				Iterator<PGPPublicKey> kIt = (Iterator<PGPPublicKey>) keyRing.getPublicKeys();
				PGPPublicKey key = null;
				 while (key == null && kIt.hasNext()) {
		                PGPPublicKey k = kIt.next();
		 
		                if (k.isEncryptionKey()) {
		                    key = k;
		                }
		            }
				return key;
						
			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;*/
    	
    	PublicKeyRingCollection pkrCollection=PublicKeyRingCollection.getInstance();
        PGPPublicKeyRingCollection collection=pkrCollection.getPublic_ring_collection();
        
        
        
       // collection.getKeyRings();

        Iterator<PGPPublicKeyRing> rIt = (Iterator<PGPPublicKeyRing>) collection.getKeyRings();
        
        while (rIt.hasNext()) {
        	
            PGPPublicKeyRing kRing = rIt.next();
            
            Iterator<PGPPublicKey> kIt = (Iterator<PGPPublicKey>) kRing.getPublicKeys();
            while (kIt.hasNext()) {
                PGPPublicKey k = kIt.next();
                //k.get
                Iterator<String> iter=k.getUserIDs();
                String id;
                if (iter.hasNext()) {
                id= iter.next();
                System.out.println(id);
                if (id.equals(user_id)) {
                	return kIt.next();
                }
                
                }
            }
        }
    	
    	return null;
    }
	
    public void export_key_ring(String user_id,String filename) {
    	/*PGPPublicKeyRing bc_public = get(user_id);
    	if(bc_public!=null) {
    		try {
    			BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream("public_key_"+user_id+"_"+filename+".pkr"));
				bc_public.encode(out);
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}*/
    }
    
    public void import_key_ring(String filename) {
    	try {
    		BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename+".pkr"));
		    PGPPublicKeyRing bc_public = new BcPGPPublicKeyRing(in);
			in.close();
			PGPPublicKeyRingCollection.addPublicKeyRing(public_ring_collection,bc_public);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void encode() {
    	try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("publickeyringcollection.asc"));
			public_ring_collection.encode(out);
			out.close();
			System.out.println(public_ring_collection.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
