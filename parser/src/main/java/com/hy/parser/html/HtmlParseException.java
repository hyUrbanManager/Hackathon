package com.hy.parser.html;

/**
 * html解析错误
 *
 * @author hy 2018/2/23
 */
public class HtmlParseException extends Exception {

    public HtmlParseException(String message) {
        super(message);
    }

    public HtmlParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
