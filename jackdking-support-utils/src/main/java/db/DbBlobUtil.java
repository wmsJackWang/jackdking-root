package db;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbBlobUtil {


    //---------------------------------------------------------------------------
    // protected methods that can be overridden by subclasses
    //---------------------------------------------------------------------------

    /**
     * <p>
     * This method should be overridden by any delegate subclasses that need
     * special handling for BLOBs. The default implementation uses standard
     * JDBC <code>java.sql.Blob</code> operations.
     * </p>
     *
     * @param rs
     *          the result set, already queued to the correct row
     * @param colName
     *          the column name for the BLOB
     * @return the deserialized Object from the ResultSet BLOB
     * @throws ClassNotFoundException
     *           if a class found during deserialization cannot be found
     * @throws IOException
     *           if deserialization causes an error
     */
    protected Object getObjectFromBlob(ResultSet rs, String colName)
            throws ClassNotFoundException, IOException, SQLException {
        InputStream binaryInput = rs.getBinaryStream(colName);

        if(binaryInput == null || binaryInput.available() == 0) {
            return null;
        }

        Object obj = null;

        ObjectInputStream in = new ObjectInputStream(binaryInput);
        try {
            obj = in.readObject();
        } finally {
            in.close();
        }

        return obj;
    }

}
