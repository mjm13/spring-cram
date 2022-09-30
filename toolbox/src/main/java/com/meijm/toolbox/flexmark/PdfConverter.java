package com.meijm.toolbox.flexmark;

import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PdfConverter {
    static final MutableDataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(Extensions.ALL & ~(Extensions.ANCHORLINKS | Extensions.EXTANCHORLINKS_WRAP), TocExtension.create()).toMutable();
    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    public static void main(String[] args) throws Exception {
        String path = PdfConverter.class.getClassLoader().getResource("HanYiQiHei-55Jian-Regular-2.ttf").getPath();
        String markdown = "" +
                "# Heading\n\n" +
                "=======\n" +
                "\n" +
                "*** ** * ** ***\n" +
                "\n" +
                "paragraph text lazy continuation\n" +
                "\n" +
                "* list itemblock quote lazy continuation\n" +
                "\n" +
                "\\~\\~\\~info with uneven indent with uneven indent indented code \\~\\~\\~\n" +
                "\n" +
                "       with uneven indent\n" +
                "          with uneven indent\n" +
                "    indented code\n" +
                "\n" +
                "1. numbered item 1\n" +
                "2. numbered item 2\n" +
                "3. numbered item 3\n" +
                "   * bullet item 1\n" +
                "   * bullet item 2\n" +
                "   * bullet item 3\n" +
                "     1. numbered sub-item 1\n" +
                "     2. numbered sub-item 2\n" +
                "     3. numbered sub-item 3\n" +
                "\n" +
                "   \\~\\~\\~info with uneven indent with uneven indent indented code \\~\\~\\~\n" +
                "```java \n" +
                "   System.out.println(\"test print\");\n" +
                "``` \n\n" +
                "## 中文测试\n\n" +
                "| 字符 | 说明 | \n" +
                "| --- | --- | \n" +
                "| var 变量名称    | 申明变量，弱类型 |";

        Node document = PARSER.parse(markdown);
        String html = RENDERER.render(document);
        System.out.println(html);
        html = "<!DOCTYPE html><html><head>\n" +
                "</head><body>" + html + "\n" +
                "</body></html>";
        String css =                 "@font-face {\n" +
                "  font-family: 'cnFont';\n" +
                "  src: url('file:" + path + "');\n" +
                "  font-weight: normal;\n" +
                "  font-style: normal;\n" +
                "}\n" +
                "* {\n" +
                "    font-family: 'cnFont';\n" +
                "}\n" ;
        html = PdfConverterExtension.embedCss(html,css);
        OPTIONS.set(PdfConverterExtension.PROTECTION_POLICY, new StandardProtectionPolicy("123", "123", new AccessPermission()));
        PdfConverterExtension.exportToPdf("flexmark-java-landscape.pdf", html, "", OPTIONS);
    }
}