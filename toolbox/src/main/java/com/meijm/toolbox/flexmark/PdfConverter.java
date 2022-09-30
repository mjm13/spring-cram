package com.meijm.toolbox.flexmark;

import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

public class PdfConverter {

    static final MutableDataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
                    Extensions.ALL & ~(Extensions.ANCHORLINKS | Extensions.EXTANCHORLINKS_WRAP)
                    , TocExtension.create()).toMutable();
//            .set(TocExtension.LIST_CLASS, PdfConverterExtension.DEFAULT_TOC_LIST_CLASS) ;


    public static void main(String[] args) throws Exception {
        String path = PdfConverter.class.getClassLoader().getResource("HanYiQiHei-55Jian-Regular-2.ttf").getPath();
        System.out.println(path);

        String nonLatinFonts = "" +
                "<style>\n" +
                "@font-face {\n" +
                "  font-family: 'mjm';\n" +
//                "  src: url('file:/C:/Windows/Fonts/simsun.ttc');\n" +
                "  src: url('file:"+path+"');\n" +
                "  font-weight: normal;\n" +
                "  font-style: normal;\n" +
                "}\n" +
                "body {\n" +
                "    font-family: 'mjm';\n" +
                "    overflow: hidden;\n" +
                "    word-wrap: break-word;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "</style>\n" ;
        String html = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">\n" +
                "" +  // add your stylesheets, scripts styles etc.
                // uncomment line below for adding style for custom embedded fonts
                 nonLatinFonts +
                "</head><body>测试" +
                "</body></html>";

        PdfConverterExtension.exportToPdf("test1.pdf", html, "", OPTIONS);

        System.out.println(nonLatinFonts);
    }
}