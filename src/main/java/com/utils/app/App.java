package com.utils.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

/**
 *
 */
public class App 
{

    public static final String DESTINO = "./target/salida.pdf";
    public static final String ORIGEN = "./src/main/java/com/utils/app/prueba.pdf";

    public static void main( String[] args ) throws IOException
    {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(ORIGEN), new PdfWriter(new ByteArrayOutputStream()));
        IEventListener listener = new MyImageRenderListener();
        PdfCanvasProcessor parser = new PdfCanvasProcessor(listener);
        parser.processPageContent(pdfDoc.getLastPage());
        pdfDoc.close();     

        float posY = ((MyImageRenderListener)listener).posYMin;
        
        prueba(posY);
        
    }


    public static void prueba(float posY) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfReader(ORIGEN), new PdfWriter(DESTINO));
        PdfPage page = pdf.getLastPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(36, posY - 100, 100, 100);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, pdf, rectangle);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        Text title = new Text("The Strange Case of Dr. Jekyll and Mr. Hyde").setFont(bold);
        Text author = new Text("Robert Louis Stevenson").setFont(font);
        Paragraph p = new Paragraph().add(title).add(" by ").add(author);
        canvas.add(p);
        canvas.close();
        pdf.close();        
    }
}
