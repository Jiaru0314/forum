package com.jit.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * @program: forum
 * @description: Markdown转HTML工具类
 * @author: XZQ
 * @create: 2019-11-23 21:33
 **/
public class MarkdownUtils {

    /**
     * markdown格式转换成HTML格式
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    public static void main(String[] args) {
        String md_str = "###我和我的祖国\n" +
                "#一刻也不分离\n" +
                "***#####徐志强*******\n" +
                "\n" +
                "------------\n" +
                "\n" +
                "# > ";
        System.out.println(markdownToHtml(md_str));
    }
}
