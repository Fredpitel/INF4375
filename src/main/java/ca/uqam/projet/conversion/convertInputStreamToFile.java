package ca.uqam.projet.conversion;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


/**
 * Created by deasel on 2016-06-13.
 */
public class convertInputStreamToFile {

    public class InputStreamToFileApp {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        public OutputStream createCSVFile(URL url) {
            try {
                // read this file into InputStream
                inputStream = url.openStream();

                // write the inputStream to a FileOutputStream
                outputStream =
                        new FileOutputStream("tempFile");

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
