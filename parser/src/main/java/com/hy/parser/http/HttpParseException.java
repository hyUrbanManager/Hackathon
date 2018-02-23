package com.hy.parser.http;

/**
 * html解析错误
 *
 * @author hy 2018/2/23
 */
public class HttpParseException extends Exception {

    public HttpParseException(String message) {
        super(message);
    }

    public HttpParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
