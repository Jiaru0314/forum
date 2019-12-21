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

}
