package com.can.tree.qrGenarate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

public class QrGenarate {

	private static final String DIRECTORY = "/home";
    private static final String DEFAULT_FILE_NAME = "output.pdf";
	public void generate(int count,String lastValue) throws DocumentException {
		Rectangle pagesize = new Rectangle(
    	    	PageSize.A4.getWidth() ,
    	    	PageSize.A4.getHeight());
    	    	Document qr_code_Example = new Document(pagesize);
    	    	qr_code_Example.setMargins(0, 0, 40, 0);
    	    	PdfWriter writer=null;
				try {
					writer = PdfWriter.getInstance(qr_code_Example, new FileOutputStream(DIRECTORY+"/"+DEFAULT_FILE_NAME));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	qr_code_Example.open();            
    	    	PdfContentByte cb = writer.getDirectContent();
    	    	PdfPTable table = new PdfPTable(7);
    	    	qr_code_Example.add(new Paragraph("\n\n"));
    	    	table.setTotalWidth(new float[]{160f,40f,40f, 160f,40f,40f, 160f});
    	    	table.setWidths(new float[]{160f,40f,40f, 160f,40f,40f, 160f});
    	        PdfPCell cell = new PdfPCell();
    	        PdfPCell cell2 = new PdfPCell();
    	        PdfPCell cellqr = new PdfPCell();
    	        cell2 = new PdfPCell(new Phrase("      "));
    	        cell2.setBorder(Rectangle.NO_BORDER);
    	        cellqr = new PdfPCell(new Phrase("      "));
    	        cellqr.setUseAscender(true);
    	        cellqr.setBorder(Rectangle.RIGHT);
    	        BigInteger lastNumber = new BigInteger(lastValue);
    	        BigInteger addOne = new BigInteger("1");
    	        int qrCount=0;
    	        if(count%3==0)
    	        	qrCount=count/3;
    	        else
    	        	qrCount=count/3+1;
    	        for(int i = 0 ;i<qrCount ;i++) {
    	        table.flushContent();
    	        String  multiple = Integer.toString(3*i);
    	        String val=lastNumber.add(new BigInteger(multiple)).toString();
    	        BigInteger qrNumber1 = new BigInteger(val).add(addOne);
    	        BigInteger qrNumber2 = new BigInteger(qrNumber1.toString()).add(addOne);
    	        BigInteger qrNumber3 = new BigInteger(qrNumber2.toString()).add(addOne);
    	        
    	        BarcodeQRCode code1 = new BarcodeQRCode(qrNumber1.toString(), 10, 5, null);
    	        Image qr_image1 = code1.getImage();
    	        BarcodeQRCode code2 = new BarcodeQRCode(qrNumber2.toString(), 10, 5, null);
    	        Image qr_image2 = code2.getImage();
    	        BarcodeQRCode code3 = new BarcodeQRCode(qrNumber3.toString(), 10, 5, null);
    	        Image qr_image3 = code3.getImage();
    	        cell = new PdfPCell(qr_image1, true);
    	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	        cell.setUseBorderPadding(true);
    	        table.addCell(cell);
    	        
    	        table.addCell(cellqr);
    	        table.addCell(cellqr);
    	        
    	        cell = new PdfPCell(qr_image2, true);
    	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	        cell.setUseBorderPadding(true);
    	        table.addCell(cell);
    	        
    	        table.addCell(cellqr);
    	        table.addCell(cellqr);
    	        
    	        cell = new PdfPCell(qr_image3, true);
    	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	        cell.setUseBorderPadding(true);
    	        table.addCell(cell);
    	        
    	        
    	        
    	    	qr_code_Example.add(table);
    	    	qr_code_Example.add(new Paragraph("\n"));
    	        table.flushContent();
    	        
    	        PdfPCell cell3 = new PdfPCell(new Phrase(qrNumber1.toString()));
    	        cell3.setUseAscender(true);
    	        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	        cell3.setBorder(Rectangle.NO_BORDER);
    	        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

    	    	table.addCell(cell3);
    	    	
    	    	table.addCell(cell2);
    	    	table.addCell(cell2);
    	    	cell3 = new PdfPCell(new Phrase(qrNumber2.toString()));
    	        cell3.setUseAscender(true);
    	        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	        cell3.setBorder(Rectangle.NO_BORDER);
    	        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
    	    	table.addCell(cell3);
    	    	
    	    	table.addCell(cell2);
    	    	table.addCell(cell2);
    	    	cell3 = new PdfPCell(new Phrase(qrNumber3.toString()));
    	        cell3.setUseAscender(true);
    	        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	        cell3.setBorder(Rectangle.NO_BORDER);
    	        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);;
    	    	table.addCell(cell3);
    	    	
    	    	qr_code_Example.add(table);
    	    	DottedLineSeparator separator = new DottedLineSeparator();
    	        separator.setPercentage(59500f / 523f);
    	        Chunk linebreak = new Chunk(separator);
    	        qr_code_Example.add(new Paragraph("\n\n"));
    	        qr_code_Example.add(linebreak);
    	    	qr_code_Example.add(new Paragraph("\n\n"));
    	    	
    	    	}qr_code_Example.close();
    	    	writer.close();
	}
	
	public ResponseEntity<Object> qrPdfDownload() throws FileNotFoundException {
		String filename = DIRECTORY+"/"+DEFAULT_FILE_NAME;
		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment;filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, mustrevalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		ResponseEntity<Object> responseEntity =	ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/pdf")).body(resource);
		return responseEntity;
		
	}
}
