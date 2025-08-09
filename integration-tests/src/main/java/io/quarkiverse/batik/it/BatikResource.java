/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package io.quarkiverse.batik.it;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

@Path("/batik")
@ApplicationScoped
public class BatikResource {
    // Watermark constants
    private static final int WATERMARK_LOGO_WIDTH = 75;
    private static final int WATERMARK_LOGO_HEIGHT = 75;
    private static final int WATERMARK_PADDING = 2;
    private static final int WATERMARK_FONT_SIZE = 30;
    private static final String WATERMARK_FONT_NAME = "Courier New";
    private static final String WATERMARK_SUFFIX = " Proprietary";
    private static final int BACKGROUND_COLOR_RGB = 0x002d59; // #002d59

    @GET
    public String getAddWatermark() throws IOException {
        byte[] imagesBytes = null;
        try (InputStream resource = getClass().getResourceAsStream("/image/image.jpg")) {
            if (resource == null) {
                throw new IOException("Unable to read image");
            }
            imagesBytes = resource.readAllBytes();
        }
        addWaterMark(imagesBytes);
        return "Batik added SVG Watermark";
    }

    /**
     * Renders an SVG image to a BufferedImage using the Batik transcoder.
     *
     * @param svgInputStream the SVG data as a byte array
     * @param width the desired width of the output image
     * @param height the desired height of the output image
     * @return the rendered BufferedImage
     * @throws IOException if there's an error reading the SVG data
     * @throws IllegalArgumentException if svgInputStream is null or empty
     */
    public static BufferedImage renderSvgToBufferedImage(byte[] svgInputStream, int width, int height)
            throws IOException {

        if (svgInputStream == null || svgInputStream.length == 0) {
            throw new IllegalArgumentException("SVG input stream cannot be null or empty");
        }

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }

        final BufferedImage[] imagePointer = new BufferedImage[1];

        ImageTranscoder t = new ImageTranscoder() {
            @Override
            public BufferedImage createImage(int w, int h) {
                return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public void writeImage(BufferedImage img, TranscoderOutput out) {
                imagePointer[0] = img;
            }
        };

        t.addTranscodingHint(ImageTranscoder.KEY_WIDTH, (float) width);
        t.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, (float) height);
        try {
            t.transcode(new TranscoderInput(new ByteArrayInputStream(svgInputStream)), null);
        } catch (TranscoderException e) {
            throw new IOException("Error processing SVG logo", e);
        }

        return imagePointer[0];
    }

    /**
     * Adds a watermark to JPEG images using company branding information.
     *
     * <p>
     * The watermark includes the company logo (SVG) and company name with
     * "Proprietary" suffix.
     * The watermark is placed at the top of the image with a branded background
     * color.
     * This method preserves the original image's IIOMetadata in the output JPEG.
     * </p>
     *
     * @param fileBytes the original JPEG file bytes
     * @return the watermarked JPEG file bytes
     * @throws IOException if there's an error reading or writing the image
     * @throws RuntimeException if company profile information is not available
     */
    private byte[] addWaterMark(byte[] fileBytes) throws IOException {
        byte[] logoBytes = null;
        try (InputStream resource = getClass().getResourceAsStream("/image/logo.svg")) {
            if (resource == null) {
                throw new IOException("Unable to read image");
            }
            logoBytes = resource.readAllBytes();
        }

        // Load SVG logo as BufferedImage
        BufferedImage svgLogo = renderSvgToBufferedImage(logoBytes,
                WATERMARK_LOGO_WIDTH,
                WATERMARK_LOGO_HEIGHT);

        // Load original JPEG
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(fileBytes));
        if (original == null) {
            throw new IOException("Unable to read JPEG image");
        }

        int extraHeight = svgLogo.getHeight() + (3 * WATERMARK_PADDING);
        int newWidth = Math.max(original.getWidth(), svgLogo.getWidth());
        int newHeight = original.getHeight() + extraHeight;

        // Create a new image with extra space at top for watermark
        BufferedImage combined = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = combined.createGraphics();

        try {
            // Fill the background with branded color
            g.setColor(new java.awt.Color(BACKGROUND_COLOR_RGB));
            g.fillRect(0, 0, newWidth, newHeight);

            // Draw logo
            g.drawImage(svgLogo, WATERMARK_PADDING, WATERMARK_PADDING, null);

            // Draw text next to the logo
            g.setColor(java.awt.Color.WHITE);
            g.setFont(new Font(WATERMARK_FONT_NAME, Font.BOLD, WATERMARK_FONT_SIZE));

            // Center the text horizontally in the image
            String watermarkText = "Test " + WATERMARK_SUFFIX;
            FontMetrics fontMetrics = g.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(watermarkText);
            int x = (newWidth - textWidth) / 2;
            int y = extraHeight / 2 + WATERMARK_PADDING;
            g.drawString(watermarkText, x, y);

            // Draw original image below watermark
            g.drawImage(original, 0, extraHeight, null);

        } finally {
            g.dispose();
        }

        // Save new image-to-byte array with original metadata
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(combined, "jpg", outputStream);
        return outputStream.toByteArray();
    }

}