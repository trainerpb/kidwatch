package com.soham.kidwatch.services;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class ImageUtils {

	public static BufferedImage imageToBufferedImage(Image im) {
		BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}

	public static BufferedImage readImageFromFile(File file) throws IOException {
		return ImageIO.read(file);
	}

	public static void writeImageToPNG(File file, BufferedImage bufferedImage) throws IOException {
		ImageIO.write(bufferedImage, "png", file);
	}

	public static void writeImageToJPG(File file, BufferedImage bufferedImage) throws IOException {
		ImageIO.write(bufferedImage, "jpg", file);
	}

	public static String toBae64(Image image) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			BufferedImage bufferdImage = imageToBufferedImage(image);
			ImageIO.write(bufferdImage, "PNG", baos);
			byte[] data = baos.toByteArray();
			String imageString = Base64.getEncoder().withoutPadding().encodeToString(data);
			return imageString;
		}
	}

	public static BufferedImage generateQRcode(String data, int h, int w) throws WriterException, IOException {
		// the BitMatrix class represents the 2D matrix of bits
		// MultiFormatWriter is a factory class that finds the appropriate Writer
		// subclass for the BarcodeFormat requested and encodes the barcode with the
		// supplied contents.
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(data.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.UTF_8.name()),
				BarcodeFormat.QR_CODE, w, h);
		return MatrixToImageWriter.toBufferedImage(matrix);
	}

	public static String generateQRcodeBase64(String data) throws IOException, WriterException {
		return toBae64(generateQRcode(data, 400, 400));
	}

	public static InetAddress getLocalAddresses() {
		try {
			Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
			while (nics.hasMoreElements()) {
				NetworkInterface nic = nics.nextElement();
				Enumeration<InetAddress> addrs = nic.getInetAddresses();
				while (addrs.hasMoreElements()) {
					InetAddress addr = addrs.nextElement();
					if (addr instanceof Inet4Address) {
						if (false == addr.isLoopbackAddress()) {
							return addr;
						}
					}

				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}
}
