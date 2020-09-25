package utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;

public class Converters {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public static byte[] readBytesFromFile(File file) {
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {
			bytesArray = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytesArray;
	}

	public static String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
			Base64.Encoder encoder = Base64.getEncoder();
			imageString = encoder.encodeToString(imageBytes);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}

	public static Fmd importImageString(String image) throws UnsupportedEncodingException, IOException, UareUException {
		byte[] bytes = Base64.getDecoder().decode(image.getBytes("UTF-8"));
		if (null != bytes && bytes.length > 0) {
			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			BufferedImage buf = ImageIO.read(stream);
			if (null != buf) {
				return UareUGlobal.GetEngine().CreateFmd(toBytes(buf), buf.getWidth(), buf.getHeight(), 500, 0, 1,
						Fmd.Format.ISO_19794_2_2005);
			}
		}
		return null;
	}

	public static Fmd importImageBytes(byte[] bytes) throws UnsupportedEncodingException, IOException, UareUException {
		if (null != bytes && bytes.length > 0) {
			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			BufferedImage buf = ImageIO.read(stream);
			if (null != buf) {
				return UareUGlobal.GetEngine().CreateFmd(toBytes(buf), buf.getWidth(), buf.getHeight(), 500, 0, 1,
						Fmd.Format.ISO_19794_2_2005);
			}
		}
		return null;
	}

	public static byte[] toBytes(BufferedImage image) throws IOException {
		WritableRaster raster = image.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
		return (data.getData());
	}

	public static BufferedImage createImageFromBytes(byte[] imageData) {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
		try {
			return ImageIO.read(bais);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
