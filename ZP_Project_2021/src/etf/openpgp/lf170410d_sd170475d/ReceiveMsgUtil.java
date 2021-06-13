package etf.openpgp.lf170410d_sd170475d;

import java.io.InputStream;
import java.security.NoSuchProviderException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.jcajce.*;
import org.bouncycastle.util.io.Streams;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;

import java.util.Iterator;

import java.io.*;

public class ReceiveMsgUtil {
	public static void decryptFile( String file, char[] password )
            throws IOException, NoSuchProviderException
    {
        InputStream in = new BufferedInputStream( new FileInputStream( file ) );
        in = PGPUtil.getDecoderStream(in);
        BouncyCastleProvider bc=new BouncyCastleProvider();

        try
        {
            JcaPGPObjectFactory pgpObjectFactory = new JcaPGPObjectFactory( in );
            PGPEncryptedDataList encryptedDataList;

            Object object = pgpObjectFactory.nextObject();

            if ( object instanceof PGPEncryptedDataList )
            {
                encryptedDataList = ( PGPEncryptedDataList ) object;
            }
            else
            {
                encryptedDataList = ( PGPEncryptedDataList ) pgpObjectFactory.nextObject();
            }

            Iterator<PGPEncryptedData> it = encryptedDataList.getEncryptedDataObjects();
            PGPPrivateKey privateKey = null;
            PGPSecretKey secretKey = null;
            PGPPublicKeyEncryptedData publicKeyEncryptedData = null;

            while ( privateKey == null && it.hasNext() )
            {
                publicKeyEncryptedData = (PGPPublicKeyEncryptedData)it.next();

                secretKey = SecretKeyRingCollection.getInstance().get(publicKeyEncryptedData.getKeyID() );
                privateKey = secretKey.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider(bc).build(password));
            }

            if (privateKey == null)
            {
                throw new IllegalArgumentException("Private key for message not found.");
            }

            InputStream clear = publicKeyEncryptedData.getDataStream( new JcePublicKeyDataDecryptorFactoryBuilder().setProvider(bc).build(privateKey) );
            JcaPGPObjectFactory plainFact = new JcaPGPObjectFactory( clear );

            Object message = plainFact.nextObject();

            if ( message instanceof PGPCompressedData )
            {
                PGPCompressedData cData = ( PGPCompressedData ) message;
                plainFact = new JcaPGPObjectFactory( cData.getDataStream() );

                message = plainFact.nextObject();
            }
            
            PGPLiteralData lit_data;
            PGPOnePassSignatureList opsList = null;
            PGPOnePassSignature ops = null;
            PGPPublicKey signingKey = null;

            if ( message instanceof PGPLiteralData )
            {
            	lit_data=(PGPLiteralData) message;
            }
            else if (message instanceof PGPOnePassSignatureList)
            {
            	 opsList = (PGPOnePassSignatureList) message;
                 ops = opsList.get(0);
                 signingKey = PublicKeyRingCollection.getInstance().get(ops.getKeyID());
                 // TODO warn on no public keys set
                 if (signingKey != null) {
                   ops.init(new JcaPGPContentVerifierBuilderProvider().setProvider(bc), signingKey);
                 }
                 
                 lit_data = (PGPLiteralData) plainFact.nextObject();
            }
            else
            {
                throw new PGPException("message is not a simple encrypted file - type unknown.");
            }
            
            InputStream in_stream = lit_data.getInputStream();
            
            String out_name = file.substring(0,file.lastIndexOf('.'));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(out_name));

            int ch;
            while ((ch = in_stream.read()) >= 0) {
              if (signingKey != null) {
                ops.update((byte)ch);
              }
              out.write(ch);
            }

            out.close();

            if (signingKey != null) {
              PGPSignatureList sigList = (PGPSignatureList) plainFact.nextObject();
              if (!ops.verify(sigList.get(0))) {
                throw new PGPException("Signature not verified.");
              }
            }
        }
        catch (PGPException e)
        {
            System.err.println(e);
            if (e.getUnderlyingException() != null)
            {
                e.getUnderlyingException().printStackTrace();
            }
        }
        finally {
            in.close();
        }

    }

}
