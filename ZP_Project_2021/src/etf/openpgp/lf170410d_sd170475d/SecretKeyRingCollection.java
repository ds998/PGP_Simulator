package etf.openpgp.lf170410d_sd170475d;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.bc.BcPGPPublicKeyRing;
import org.bouncycastle.openpgp.bc.BcPGPSecretKeyRing;
import org.bouncycastle.openpgp.bc.BcPGPSecretKeyRingCollection;
import org.bouncycastle.util.Arrays.Iterator;

public class SecretKeyRingCollection {

	private static SecretKeyRingCollection s = null;

	private PGPSecretKeyRingCollection secret_ring_collection;

	public PGPSecretKeyRingCollection getSecret_ring_collection() {
		return secret_ring_collection;
	}

	public static SecretKeyRingCollection getInstance() {
		if (s == null) {
			s = new SecretKeyRingCollection();
			return s;
		} else
			return s;
	}

	private SecretKeyRingCollection() {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream("secretkeyringcollection.asc"));
			secret_ring_collection = new BcPGPSecretKeyRingCollection(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void putRing(PGPSecretKeyRing pkr) {
		secret_ring_collection = PGPSecretKeyRingCollection.addSecretKeyRing(secret_ring_collection, pkr);
	}

	public void removeRing(String userID) {
		try {
			if (secret_ring_collection.getKeyRings(userID).hasNext()) {
				PGPSecretKeyRing bcskr = secret_ring_collection.getKeyRings(userID).next();
				secret_ring_collection = PGPSecretKeyRingCollection.removeSecretKeyRing(secret_ring_collection, bcskr);
			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public PGPSecretKeyRing get(String user_id) {
		try {
			if (secret_ring_collection.getKeyRings(user_id).hasNext()) {
				// PGPSecretKeyRing skr=secret_ring_collection.getKeyRings(user_id).next();

				return secret_ring_collection.getKeyRings(user_id).next();
			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PGPSecretKey get(String user_id, long keyId) {
		try {
			if (secret_ring_collection.getKeyRings(user_id).hasNext()) {
				java.util.Iterator<PGPSecretKeyRing> iter = secret_ring_collection.getKeyRings(user_id);
				// asd
				PGPSecretKeyRing skr = iter.next();
				if (keyId == 0)
					return skr.getSecretKey();

				while (skr != null) {
					if (skr.getSecretKey(keyId) != null)
						skr.getSecretKey(keyId);
					else
						skr = iter.next();
				}
				return null;
			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PGPSecretKeyRing getRing(String user_id) {
		try {
			if (secret_ring_collection.getKeyRings(user_id).hasNext()) {
				return secret_ring_collection.getKeyRings(user_id).next();
			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void export_key_ring(String user_id, String filename) {
		PGPSecretKeyRing bc_secret = getRing(user_id);
		if (bc_secret != null) {
			try {
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream("secret_key_" + user_id + "_" + filename + ".skr"));
				bc_secret.encode(out);
				out.close();
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
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename + ".skr"));
			PGPSecretKeyRing bc_secret = new BcPGPSecretKeyRing(in);
			in.close();
			secret_ring_collection = PGPSecretKeyRingCollection.addSecretKeyRing(secret_ring_collection, bc_secret);
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

	public void encode() {
		try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("secretkeyringcollection.asc"));
			secret_ring_collection.encode(out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remove(String user_id, long keyId) {
		try {
			if (secret_ring_collection.getKeyRings(user_id).hasNext()) {
				java.util.Iterator<PGPSecretKeyRing> iter = secret_ring_collection.getKeyRings(user_id);
				// asd
				PGPSecretKeyRing skr = iter.next();
				if (keyId == 0) {
					secret_ring_collection = PGPSecretKeyRingCollection.removeSecretKeyRing(secret_ring_collection,
							skr);
					return;
				}

				while (skr != null) {
					if (skr.getSecretKey(keyId) != null) {
						secret_ring_collection = PGPSecretKeyRingCollection.removeSecretKeyRing(secret_ring_collection,
								skr);
						return;

					} else
						skr = iter.next();
				}

			}
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PGPSecretKey get(long key_id) {
		try {
			return secret_ring_collection.getSecretKey(key_id);
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
