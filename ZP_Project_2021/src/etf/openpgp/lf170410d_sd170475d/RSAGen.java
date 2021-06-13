package etf.openpgp.lf170410d_sd170475d;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.Features;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.openpgp.examples.*;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;

public class RSAGen
{
    public static void GenKeyRing(String email, String pass,int keyLength)
        throws Exception
    {
     
        PGPKeyRingGenerator krgen = generateKeyRingGenerator
            (email, pass.toCharArray(),keyLength);
        
        
        PGPPublicKeyRing pkr = krgen.generatePublicKeyRing();
        PublicKeyRingCollection.getInstance().putRing(pkr);
       

        
        PGPSecretKeyRing skr = krgen.generateSecretKeyRing();
        SecretKeyRingCollection.getInstance().putRing(skr);
       
    }

    public final static PGPKeyRingGenerator generateKeyRingGenerator
        (String id, char[] pass,int keyLength)
        throws Exception
    { return generateKeyRingGenerator(id, pass,keyLength, 0xc0); }

    

    public final static PGPKeyRingGenerator generateKeyRingGenerator
        (String id, char[] pass,int keyLength ,int s2kcount)
        throws Exception
    {
        
        RSAKeyPairGenerator  kpg = new RSAKeyPairGenerator();

        
        kpg.init
            (new RSAKeyGenerationParameters
             (BigInteger.valueOf(0x10001),
              new SecureRandom(), keyLength, 12));

       
        PGPKeyPair rsakp_sign =
            new BcPGPKeyPair
            (PGPPublicKey.RSA_SIGN, kpg.generateKeyPair(), new Date());
  
        PGPKeyPair rsakp_enc =
            new BcPGPKeyPair
            (PGPPublicKey.RSA_ENCRYPT, kpg.generateKeyPair(), new Date());

        PGPSignatureSubpacketGenerator signhashgen =
            new PGPSignatureSubpacketGenerator();
        
        
        signhashgen.setKeyFlags
            (false, KeyFlags.SIGN_DATA|KeyFlags.CERTIFY_OTHER);
   
        signhashgen.setPreferredSymmetricAlgorithms
            (false, new int[] {
                SymmetricKeyAlgorithmTags.TRIPLE_DES,
                SymmetricKeyAlgorithmTags.IDEA
            });
        signhashgen.setPreferredHashAlgorithms
            (false, new int[] {      
                HashAlgorithmTags.SHA1
            });
        
        signhashgen.setFeature
            (false, Features.FEATURE_MODIFICATION_DETECTION);

 
        PGPSignatureSubpacketGenerator enchashgen =
            new PGPSignatureSubpacketGenerator();
      
        enchashgen.setKeyFlags
            (false, KeyFlags.ENCRYPT_COMMS|KeyFlags.ENCRYPT_STORAGE);


        PGPDigestCalculator sha1Calc =
            new BcPGPDigestCalculatorProvider()
            .get(HashAlgorithmTags.SHA1);
        PGPDigestCalculator sha256Calc =
            new BcPGPDigestCalculatorProvider()
            .get(HashAlgorithmTags.SHA256);

        
        PBESecretKeyEncryptor pske =
            (new BcPBESecretKeyEncryptorBuilder
             (PGPEncryptedData.AES_256, sha256Calc, s2kcount))
            .build(pass);

        PGPKeyRingGenerator keyRingGen =
            new PGPKeyRingGenerator
            (PGPSignature.POSITIVE_CERTIFICATION, rsakp_sign,
             id, sha1Calc, signhashgen.generate(), null,
             new BcPGPContentSignerBuilder
             (rsakp_sign.getPublicKey().getAlgorithm(),
              HashAlgorithmTags.SHA1),
             pske);
        

        keyRingGen.addSubKey
            (rsakp_enc, enchashgen.generate(), null);
        return keyRingGen;
    }
    
    
   
}
