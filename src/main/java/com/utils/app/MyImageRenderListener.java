package com.utils.app;

import java.util.Set;

import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;

public class MyImageRenderListener implements IEventListener {

    public float posYMin = 9999;

    public MyImageRenderListener() {
    }

    public void eventOccurred(IEventData data, EventType type) {
        switch (type) {
            case RENDER_IMAGE:
                try {
                    ImageRenderInfo renderInfo = (ImageRenderInfo) data;
                    PdfImageXObject image = renderInfo.getImage();
                    if (image == null || image.getHeight() > 1700) {
                        // Es la imagen del background
                        return;
                    }
                    
                    float posY = renderInfo.getImageCtm().get(Matrix.I32);// + renderInfo.getImageCtm().get(Matrix.I22);
                    System.out.println("POSICION Y: " + posY);
                    if (posY < posYMin) {
                        posYMin = posY;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            default:
                break;
        }
    }

    public Set<EventType> getSupportedEvents() {
        return null;
    }

}
