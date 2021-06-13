package etf.openpgp.lf170410d_sd170475d;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Date;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
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
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;

public class SendMsgUtil {

	public static void encryptMsg(File file, PGPPublicKey key, boolean radix, boolean zip, boolean encrypt, boolean auth,
			int symAlgTag, PGPPrivateKey pgpPrivKey, PGPSecretKey pgpSec) {

		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file.getName() + ".asc"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// RADIX **********
		if (radix) {
			out = new ArmoredOutputStream(out);
		}
		// ******************************
		String str = "hello";
		BouncyCastleProvider bc = new BouncyCastleProvider();

		int BUFFER_SIZE = 1 << 26;
		OutputStream cOut = out;
		PGPEncryptedDataGenerator encGen=null;
		if (encrypt) {
			encGen = new PGPEncryptedDataGenerator(new JcePGPDataEncryptorBuilder(symAlgTag)
					.setWithIntegrityPacket(true).setSecureRandom(new SecureRandom()).setProvider(bc));

			encGen.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(key).setProvider(bc));

			try {
				cOut = encGen.open(out, new byte[BUFFER_SIZE]);
			} catch (IOException | PGPException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		OutputStream compressedOut = cOut;
		PGPCompressedDataGenerator compressedDataGenerator = null;
		if (zip) {
		compressedDataGenerator = new PGPCompressedDataGenerator(
				CompressionAlgorithmTags.ZIP);
		
		try {
			compressedOut = compressedDataGenerator.open(cOut);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		}
		try {
			/*
			PGPSecretKey pgpSec = SecretKeyRingCollection.getInstance().get("Name1&Mail1").getSecretKey();
			PGPPrivateKey pgpPrivKey = pgpSec
					.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().build(str.toCharArray()));
					*/
			PGPSignatureGenerator sGen=null;
			if (auth) {
			sGen = new PGPSignatureGenerator(
					new JcaPGPContentSignerBuilder(pgpSec.getPublicKey().getAlgorithm(), PGPUtil.SHA1));

			sGen.init(PGPSignature.BINARY_DOCUMENT, pgpPrivKey);

			java.util.Iterator<String> it = pgpSec.getPublicKey().getUserIDs();
			if (it.hasNext()) {
				PGPSignatureSubpacketGenerator spGen = new PGPSignatureSubpacketGenerator();

				spGen.setSignerUserID(false, (String) it.next());
				sGen.setHashedSubpackets(spGen.generate());
			}

			sGen.generateOnePassVersion(false).encode(compressedOut);
			}
			// ************************

			// LITERAL DATA
			PGPLiteralDataGenerator literalDataGenerator = new PGPLiteralDataGenerator();

			OutputStream literalOut = literalDataGenerator.open(compressedOut, PGPLiteralData.BINARY, file.getName(),
					new Date(file.lastModified()), new byte[BUFFER_SIZE]);

			// ******************************

			FileInputStream inputStream = new FileInputStream(file);

			byte[] buf = new byte[BUFFER_SIZE];
			int len;
			while ((len = inputStream.read(buf, 0, buf.length)) > 0) {
				literalOut.write(buf, 0, len);
				if (auth)
				sGen.update(buf, 0, len);
			}

			literalOut.close();
			literalDataGenerator.close();
			if (auth)
			sGen.generate().encode(compressedOut);
			compressedOut.close();
			if (zip) {
			compressedDataGenerator.close();
			}
			
			cOut.close();
			if (encrypt) {
				encGen.close();
			}
			inputStream.close();

		} catch (IOException | PGPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
