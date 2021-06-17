package com.sbdc.sbdcweb.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mortennobel.imagescaling.AdvancedResizeOp.UnsharpenMask;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * 이미지 파일 관리
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-17
 * 
 */
@Service("imageManager")
public class ImageManager {
	private static final Logger logger = LoggerFactory.getLogger(ImageManager.class);

	/**
	 * 이미지 파일 가져오는 메소드
	 * 
	 * @param saveFilename		서버에 저장된 파일이름
	 * @param originalFilename	클라이언트가 업로드한 파일이름
	 * @param pathname			파일이 저장된 경로
	 * @param response			응답할 HttpServletResponse 객체
	 * @return					Encode 완료한 String
	 */
	public String doImageDownload(String saveFilename, String originalFilename, String pathname, HttpServletResponse response) {
		String encodeFileString = null;
		String fullpathname = pathname + File.separator + saveFilename;
		try {
    		if(originalFilename == null || originalFilename.equals(""))
    			originalFilename = saveFilename;
				originalFilename = new String(originalFilename.getBytes("euc-kr"),"8859_1");
        } catch (UnsupportedEncodingException e) {
        }

	    try {
	        File file = new File(fullpathname);

	        if (file.exists()){
	            response.setContentType("image/jpeg");
				response.setHeader(
						"Content-disposition",
						"attachment;filename=\"" + originalFilename + "\"");

				encodeFileString = encodeFileToBase64Binary(file);
	            return encodeFileString;
	        }

	    } catch(Exception e) {
	    }
	    
	    return encodeFileString;
	}

	/**
	 * Base64 Encode 메소드
	 * 
	 * @param 	file		Base64 Encode를 적용할 파일 객체
	 * @return	encodedfile	Encode 완료한 String
	 */
    public String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
            fileInputStreamReader.close();
        } catch (FileNotFoundException e) {
            logger.error("encodeFileToBase64Binary FileNotFoundException 에러", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("encodeFileToBase64Binary IOException 에러", e.getMessage(), e);
        }
        return encodedfile;
    }

	/**
	 * Image Resize 메소드
	 * 
	 * @param 	file			Resize 할 file 객체
	 * @param 	filePath		file 경로
	 * @param 	guidName		file guid 이름
	 * @param 	fileEXT			file 확장자
	 * @return	fileCompression	Resize 로직 적용 한 file 객체
	 */
    public File ImageResize(File file, String filePath, String guidName, String fileEXT) {
        int oldWidth = 0;
        int oldHeight = 0;
        int newWidth = 0;
        int newHeight = 0;
        double ratio = 0.0d;

        // 변경할 Width, Height 기준값
        int standardWidth = 500;
        int standardHeight = 500;

        // 파일서버 이관 변경
        String compressionFilePath = filePath + "thumb" + File.separator;
//        String compressionFilePath = filePath + "compression" + File.separator;

        File fileCompression = new File(compressionFilePath + guidName);

        if (fileCompression.exists()) {
        	return fileCompression;
        }

		File isDir = new File(compressionFilePath);
		if (!isDir.exists()) {
			isDir.mkdirs();
        }

		try {
			BufferedImage image = ImageIO.read(file);

			oldWidth = image.getWidth();
			oldHeight = image.getHeight();

			if (standardWidth < oldWidth) {
				ratio = (double)standardWidth/(double)oldWidth;
				newWidth = (int)(oldWidth * ratio);
				newHeight = (int)(oldHeight * ratio);
			} else if(standardHeight < oldHeight) {
				ratio = (double)standardHeight/(double)oldHeight;
				newWidth = (int)(oldWidth * ratio);
				newHeight = (int)(oldHeight * ratio);
			} else {
				newWidth = oldWidth;
				newHeight = oldHeight;
			}

			ResampleOp resampleOp = new ResampleOp(newWidth, newHeight);
			resampleOp.setUnsharpenMask(UnsharpenMask.Soft);

			BufferedImage rescaledImage = resampleOp.filter(image, null);
            ImageIO.write(rescaledImage, fileEXT, fileCompression);
		} catch (Exception e) {
            logger.error("ImageResize 에러", e.getMessage(), e);
		}
        return fileCompression;
    }

}
