package etf.openpgp.lf170410d;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.operator.PGPDataEncryptor;
import org.bouncycastle.openpgp.operator.PGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.util.encoders.Base64;

public class SendMsgUtil {

	public static void encryptMsg(File file, PGPPublicKey key) {

		OutputStream out=null;
		InputStream in=null;
		try {
			out = new FileOutputStream(file.getName() + ".skr");
			in = new FileInputStream(file);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Security.addProvider(new BouncyCastleProvider());

		;

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();

		PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);
		OutputStream dummy= bOut;

		if (true) {
			try {
				dummy=comData.open(bOut);
			} catch (IOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		}

		try {
			org.bouncycastle.openpgp.PGPUtil.writeFileToLiteralData(dummy, PGPLiteralData.TEXT, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BcPGPDataEncryptorBuilder encBuilder=new BcPGPDataEncryptorBuilder(SymmetricKeyAlgorithmTags.TRIPLE_DES);
		encBuilder.setWithIntegrityPacket(true);
		
		PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(
	           encBuilder);
		
		cPk.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(key));
		
		byte[] bytes = bOut.toByteArray();
		OutputStream cOut=null;
        try {
			cOut = cPk.open(out, bytes.length);
			cOut.write(bytes);  
			
		} catch (IOException | PGPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        if (false) {
		
		Base64 radix = new Base64();
		//radix.encode(cOut.to, arg1);
		
        }
		try {
			comData.close();
			out.close();
			in.close();
			//cOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
