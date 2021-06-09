package etf.openpgp.lf170410d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;

@SuppressWarnings("serial")
public class PkrTableModel extends AbstractTableModel {
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	//private PublicKeyRingCollection ringCollection;
	private List<List<String>> data;
	
	// Column Names
	String[] columnNames = { "Name", "Email", "KeyId", "TimeStamp", "PublicKey" };

	public PkrTableModel() {
		PublicKeyRingCollection pkrCollection=PublicKeyRingCollection.getInstance();
        PGPPublicKeyRingCollection collection=pkrCollection.getPublic_ring_collection();
        
        
        
        data= new ArrayList<List<String>>();
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
                List<String> rowInfo= new ArrayList<String>();
                //k.getKeyID();
                String[] str= id.split("&");
                rowInfo.add(str[0]);
                rowInfo.add(str[1]);
                rowInfo.add(""+k.getKeyID());
                rowInfo.add(k.getCreationTime().toString());
                try {
                	byte[] bytes=k.getEncoded();
                	char[] hexChars = new char[bytes.length * 2];
                    for (int j = 0; j < bytes.length; j++) {
                        int v = bytes[j] & 0xFF;
                        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
                    }
					rowInfo.add(new String(hexChars));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                data.add(rowInfo);
                }
            }
        }
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.

		return false;

	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
		return data.get(arg0).get(arg1);
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

}
