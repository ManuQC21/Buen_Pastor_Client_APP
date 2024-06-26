package Buen.Pastor.app.Activity.ui.Filtros.escaneo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessor {

    private final Context context;

    public ImageProcessor(Context context) {
        this.context = context;
    }

    public void rotateImage(File imageFile, int degrees) {
        Bitmap bitmap = null;
        Bitmap rotatedBitmap = null;
        FileOutputStream out = null;
        try {
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            if (bitmap == null) {
                Toast.makeText(context, "Failed to decode image file", Toast.LENGTH_SHORT).show();
                return;
            }

            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            out = new FileOutputStream(imageFile);
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
        } catch (IOException e) {
            Toast.makeText(context, "Error rotating image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
            if (rotatedBitmap != null) {
                rotatedBitmap.recycle();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Toast.makeText(context, "Error closing file output stream: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
