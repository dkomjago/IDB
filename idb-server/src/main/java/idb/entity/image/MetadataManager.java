package idb.entity.image;

import com.sun.imageio.plugins.png.PNGMetadata;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageReader;
import javax.imageio.ImageIO;
import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MetadataManager {


    public static byte[] write(BufferedImage buffImg, String key, String value) throws Exception {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();

        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);

        //adding metadata
        IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);

        IIOMetadataNode textEntry = new IIOMetadataNode("modifications");
        textEntry.setAttribute("keyword", key);
        textEntry.setAttribute("value", value);

        IIOMetadataNode text = new IIOMetadataNode("example");
        text.appendChild(textEntry);

        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
        root.appendChild(text);

        metadata.mergeTree("javax_imageio_png_1.0", root);

        //writing the data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
        writer.setOutput(stream);
        writer.write(metadata, new IIOImage(buffImg, null, metadata), writeParam);
        stream.close();

        return baos.toByteArray();
    }

    public static String read(byte[] imageData, String key) throws IOException {
        ImageReader imageReader = ImageIO.getImageReadersByFormatName("png").next();

        imageReader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(imageData)), true);

        // read metadata of first image
        IIOMetadata metadata = imageReader.getImageMetadata(0);

        //this cast helps getting the contents
        PNGMetadata pngmeta = (PNGMetadata) metadata;
        NodeList childNodes = pngmeta.getStandardDimensionNode().getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            String keyword = node.getAttributes().getNamedItem("keyword").getNodeValue();
            String value = node.getAttributes().getNamedItem("value").getNodeValue();
            if(key.equals(keyword)){
                return value;
            }
        }
        return null;
    }

}
