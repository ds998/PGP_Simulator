package etf.openpgp.lf170410d;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.PGPDataEncryptor;
import org.bouncycastle.openpgp.operator.PGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.util.Arrays.Iterator;
import org.bouncycastle.util.encoders.Base64;

public class SendMsgUtil {

	public static void encryptMsg(File file, PGPPublicKey key, boolean radix, boolean zip, int symAlgTag) {

		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file.getName()+".asc"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		out = new ArmoredOutputStream(out);
		String str="hello";
		BouncyCastleProvider bc=new BouncyCastleProvider();
		try {
			signFile(file.getAbsolutePath(), out, str.toCharArray(), bc);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | SignatureException | IOException
				| PGPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			byte[] bytes = compressFile(file.getAbsolutePath(), CompressionAlgorithmTags.ZIP);

			PGPEncryptedDataGenerator encGen = new PGPEncryptedDataGenerator(new JcePGPDataEncryptorBuilder(symAlgTag)
					.setWithIntegrityPacket(true).setSecureRandom(new SecureRandom()).setProvider(bc));

			encGen.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(key).setProvider(bc));

			OutputStream cOut = encGen.open(out, bytes.length);
			
			System.out.println("Pre cOutWrite");
			
			cOut.write(bytes);
			cOut.close();
			System.out.println("Posle cOutWrite");
			out.close();

		} catch (PGPException e) {
			System.err.println(e);
			if (e.getUnderlyingException() != null) {
				e.getUnderlyingException().printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static byte[] compressFile(String fileName, int algorithm) throws IOException {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(algorithm);
		PGPUtil.writeFileToLiteralData(comData.open(bOut), PGPLiteralData.BINARY, new File(fileName));
		comData.close();
		return bOut.toByteArray();
	}

	/*
	 * Create signature
	 * 
	 */
	@SuppressWarnings("deprecation")
	static public void signFile(
	        String          fileName,
	        //InputStream     keyIn,
	        OutputStream    out,
	        char[]          pass,
	        BouncyCastleProvider bc)
	        throws IOException, NoSuchAlgorithmException, NoSuchProviderException, PGPException, SignatureException
	    {	       
	            
			//BouncyCastleProvider bc=new BouncyCastleProvider();
	        PGPSecretKey      pgpSec = SecretKeyRingCollection.getInstance().get("Name1&Mail1").getSecretKey();
	        PGPPrivateKey      pgpPrivKey = pgpSec.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider(bc).build(pass));
	        PGPSignatureGenerator       sGen = new PGPSignatureGenerator(new JcaPGPContentSignerBuilder(pgpSec.getPublicKey().getAlgorithm(), PGPUtil.SHA1).setProvider(bc));
	        
	        sGen.init(PGPSignature.BINARY_DOCUMENT, pgpPrivKey);
	        
	        java.util.Iterator<String>    it = pgpSec.getPublicKey().getUserIDs();
	        if (it.hasNext())
	        {
	            PGPSignatureSubpacketGenerator  spGen = new PGPSignatureSubpacketGenerator();
	            
	            spGen.setSignerUserID(false, (String)it.next());
	            sGen.setHashedSubpackets(spGen.generate());
	        }
	        
	       
	        
	        BCPGOutputStream            bOut = new BCPGOutputStream(out);
	        
	        sGen.generateOnePassVersion(false).encode(bOut);
	        
	        File                        file = new File(fileName);
	        PGPLiteralDataGenerator     lGen = new PGPLiteralDataGenerator();
	        OutputStream                lOut = lGen.open(bOut, PGPLiteralData.BINARY, file);
	        FileInputStream             fIn = new FileInputStream(file);
	        int                         ch;
	        
	        while ((ch = fIn.read()) >= 0)
	        {
	            lOut.write(ch);
	            sGen.update((byte)ch);
	        }

	        lGen.close();
	        fIn.close();

	        sGen.generate().encode(bOut);

	       

	      
	    }

}
