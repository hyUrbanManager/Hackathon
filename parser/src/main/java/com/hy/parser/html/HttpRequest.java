package com.hy.parser.html;

/**
 * html请求头解析器。
 * <p>
 * <table class="table table-bordered content-table">
 * <thead>
 * <tr>
 * <th>Header</th>
 * <th width="35%">解释</th>
 * <th width="40%">示例</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td>Accept</td>
 * <td>指定客户端能够接收的内容类型</td>
 * <td>Accept: text/plain, text/html</td>
 * </tr>
 * <tr>
 * <td>Accept-Charset</td>
 * <td>浏览器可以接受的字符编码集。</td>
 * <td>Accept-Charset: iso-8859-5</td>
 * </tr>
 * <tr>
 * <td>Accept-Encoding</td>
 * <td>指定浏览器可以支持的web服务器返回内容压缩编码类型。</td>
 * <td>Accept-Encoding: compress, gzip</td>
 * </tr>
 * <tr>
 * <td>Accept-Language</td>
 * <td>浏览器可接受的语言</td>
 * <td>Accept-Language: en,zh</td>
 * </tr>
 * <tr>
 * <td>Accept-Ranges</td>
 * <td>可以请求网页实体的一个或者多个子范围字段</td>
 * <td>Accept-Ranges: bytes</td>
 * </tr>
 * <tr>
 * <td>Authorization</td>
 * <td>HTTP授权的授权证书</td>
 * <td>Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==</td>
 * </tr>
 * <tr>
 * <td>Cache-Control</td>
 * <td>指定请求和响应遵循的缓存机制</td>
 * <td>Cache-Control: no-cache</td>
 * </tr>
 * <tr>
 * <td>Connection</td>
 * <td>表示是否需要持久连接。（HTTP 1.1默认进行持久连接）</td>
 * <td>Connection: close</td>
 * </tr>
 * <tr>
 * <td>Cookie</td>
 * <td>HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器。</td>
 * <td>Cookie: $Version=1; Skin=new;</td>
 * </tr>
 * <tr>
 * <td>Content-Length</td>
 * <td>请求的内容长度</td>
 * <td>Content-Length: 348</td>
 * </tr>
 * <tr>
 * <td>Content-Type</td>
 * <td>请求的与实体对应的MIME信息</td>
 * <td>Content-Type: application/x-www-form-urlencoded</td>
 * </tr>
 * <tr>
 * <td>Date</td>
 * <td>请求发送的日期和时间</td>
 * <td>Date: Tue, 15 Nov&nbsp;2010 08:12:31 GMT</td>
 * </tr>
 * <tr>
 * <td>Expect</td>
 * <td>请求的特定的服务器行为</td>
 * <td>Expect: 100-continue</td>
 * </tr>
 * <tr>
 * <td>From</td>
 * <td>发出请求的用户的Email</td>
 * <td>From: user@email.com</td>
 * </tr>
 * <tr>
 * <td>Host</td>
 * <td>指定请求的服务器的域名和端口号</td>
 * <td>Host: www.zcmhi.com</td>
 * </tr>
 * <tr>
 * <td>If-Match</td>
 * <td>只有请求内容与实体相匹配才有效</td>
 * <td>If-Match: “737060cd8c284d8af7ad3082f209582d”</td>
 * </tr>
 * <tr>
 * <td>If-Modified-Since</td>
 * <td>如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码</td>
 * <td>If-Modified-Since: Sat, 29 Oct 2010 19:43:31 GMT</td>
 * </tr>
 * <tr>
 * <td>If-None-Match</td>
 * <td>如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变</td>
 * <td>If-None-Match: “737060cd8c284d8af7ad3082f209582d”</td>
 * </tr>
 * <tr>
 * <td>If-Range</td>
 * <td>如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag</td>
 * <td>If-Range: “737060cd8c284d8af7ad3082f209582d”</td>
 * </tr>
 * <tr>
 * <td>If-Unmodified-Since</td>
 * <td>只在实体在指定时间之后未被修改才请求成功</td>
 * <td>If-Unmodified-Since: Sat, 29 Oct 2010 19:43:31 GMT</td>
 * </tr>
 * <tr>
 * <td>Max-Forwards</td>
 * <td>限制信息通过代理和网关传送的时间</td>
 * <td>Max-Forwards: 10</td>
 * </tr>
 * <tr>
 * <td>Pragma</td>
 * <td>用来包含实现特定的指令</td>
 * <td>Pragma: no-cache</td>
 * </tr>
 * <tr>
 * <td>Proxy-Authorization</td>
 * <td>连接到代理的授权证书</td>
 * <td>Proxy-Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==</td>
 * </tr>
 * <tr>
 * <td>Range</td>
 * <td>只请求实体的一部分，指定范围</td>
 * <td>Range: bytes=500-999</td>
 * </tr>
 * <tr>
 * <td>Referer</td>
 * <td>先前网页的地址，当前请求网页紧随其后,即来路</td>
 * <td>Referer: http://www.zcmhi.com/archives/71.html</td>
 * </tr>
 * <tr>
 * <td>TE</td>
 * <td>客户端愿意接受的传输编码，并通知服务器接受接受尾加头信息</td>
 * <td>TE: trailers,deflate;q=0.5</td>
 * </tr>
 * <tr>
 * <td>Upgrade</td>
 * <td>向服务器指定某种传输协议以便服务器进行转换（如果支持）</td>
 * <td>Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11</td>
 * </tr>
 * <tr>
 * <td>User-Agent</td>
 * <td>User-Agent的内容包含发出请求的用户信息</td>
 * <td>User-Agent: Mozilla/5.0 (Linux; X11)</td>
 * </tr>
 * <tr>
 * <td>Via</td>
 * <td>通知中间网关或代理服务器地址，通信协议</td>
 * <td>Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)</td>
 * </tr>
 * <tr>
 * <td>Warning</td>
 * <td>关于消息实体的警告信息</td>
 * <td>Warn: 199 Miscellaneous warning</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 *
 * @author hy 2018/2/23
 */
public class HttpRequest {

    // Accept 指定客户端能够接收的内容类型	Accept:text/plain,text/html
    public String Accept;

    // Accept-Charset 浏览器可以接受的字符编码集。    Accept-Charset:iso-8859-5
    public String AcceptCharset;

    // Accept-Encoding 指定浏览器可以支持的web服务器返回内容压缩编码类型。    Accept-Encoding:compress,gzip
    public String AcceptEncoding;

    // Accept-Language 浏览器可接受的语言 Accept-Language:en,zh
    public String AcceptLanguage;

    // Accept-Ranges 可以请求网页实体的一个或者多个子范围字段 Accept-Ranges:bytes
    public String AcceptRanges;

    // Authorization HTTP授权的授权证书 Authorization:Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
    public String Authorization;

    // Cache-Control 指定请求和响应遵循的缓存机制 Cache-Control:no-cache
    public String CacheControl;

    // Connection 表示是否需要持久连接。（HTTP1.1默认进行持久连接）    Connection:close
    public String Connection;

    // Cookie HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器。    Cookie:$Version=1;Skin=new;
    public String Cookie;

    // Content-Length 请求的内容长度 Content-Length:348
    public String ContentLength;

    // Content-Type 请求的与实体对应的MIME信息 Content-Type:application/x-www-form-urlencoded
    public String ContentType;

    // Date 请求发送的日期和时间 Date:Tue,15Nov 2010 08:12:31GMT
    public String Date;

    // Expect 请求的特定的服务器行为 Expect:100-continue
    public String Expect;

    // From 发出请求的用户的Email From:user@email.com
    public String From;

    // Host 指定请求的服务器的域名和端口号 Host:www.zcmhi.com
    public String Host;

    // If-Match 只有请求内容与实体相匹配才有效 If-Match: “737060cd8c284d8af7ad3082f209582d”
    public String IfMatch;

    // If-Modified-Since 如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码 If-Modified-Since:Sat,29Oct 2010 19:43:31GMT
    public String IfModifiedSince;

    // If-None-Match 如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变 If-None-Match: “737060cd8c284d8af7ad3082f209582d”
    public String IfNoneMatch;

    // If-Range 如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag If-Range: “737060cd8c284d8af7ad3082f209582d”
    public String IfRange;

    // If-Unmodified-Since 只在实体在指定时间之后未被修改才请求成功 If-Unmodified-Since:Sat,29Oct 2010 19:43:31GMT
    public String IfUnmodifiedSince;

    // Max-Forwards 限制信息通过代理和网关传送的时间 Max-Forwards:10
    public String MaxForwards;

    // Pragma 用来包含实现特定的指令 Pragma:no-cache
    public String Pragma;

    // Proxy-Authorization 连接到代理的授权证书 Proxy-Authorization:Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
    public String ProxyAuthorization;

    // Range 只请求实体的一部分，指定范围 Range:bytes=500-999
    public String Range;

    // Referer 先前网页的地址，当前请求网页紧随其后,即来路 Referer:http://www.zcmhi.com/archives/71.html
    public String Referer;

    // TE 客户端愿意接受的传输编码，并通知服务器接受接受尾加头信息 TE:trailers,deflate;q=0.5
    public String TE;

    // Upgrade 向服务器指定某种传输协议以便服务器进行转换（如果支持）   Upgrade:HTTP/2.0,SHTTP/1.3,IRC/6.9,RTA/x11
    public String Upgrade;

    // User-Agent User-Agent的内容包含发出请求的用户信息 User-Agent:Mozilla/5.0(Linux;X11)
    public String UserAgent;

    // Via 通知中间网关或代理服务器地址，通信协议 Via:1.0fred,1.1nowhere.com(Apache/1.1)
    public String Via;

    // Warning 关于消息实体的警告信息 Warn:199Miscellaneous warning
    public String Warning;

    // 请求头第一句 GET /video1.wmv HTTP/1.1
    public String head;

    /**
     * 解析html语句。
     *
     * @param raw
     * @return
     */
    public static HttpRequest parseString(String raw) throws HttpParseException {
        if (!raw.endsWith("\r\n\r\n")) {
            throw new HttpParseException("raw is not end with \\r\\n\\r\\n");
        }
        raw = raw.substring(0, raw.length() - 2);

        HttpRequest hr = new HttpRequest();

        String[] map = raw.split("\r\n");
        try {
            hr.head = map[0];
            for (int i = 1; i < map.length; i++) {
                String entry = map[i];
                String[] ss = entry.split(": ");
                String key = ss[0];
                String content = ss[1];
                switch (key) {
                    case "Accept":
                        hr.Accept = content;
                        break;
                    case "Accept-Charset":
                        hr.AcceptCharset = content;
                        break;
                    case "Accept-Encoding":
                        hr.AcceptEncoding = content;
                        break;
                    case "Accept-Language":
                        hr.AcceptLanguage = content;
                        break;
                    case "Accept-Ranges":
                        hr.AcceptRanges = content;
                        break;
                    case "Authorization":
                        hr.Authorization = content;
                        break;
                    case "Cache-Control":
                        hr.CacheControl = content;
                        break;
                    case "Connection":
                        hr.Connection = content;
                        break;
                    case "Cookie":
                        hr.Cookie = content;
                        break;
                    case "Content-Length":
                        hr.ContentLength = content;
                        break;
                    case "Content-Type":
                        hr.ContentType = content;
                        break;
                    case "Date":
                        hr.Date = content;
                        break;
                    case "Expect":
                        hr.Expect = content;
                        break;
                    case "From":
                        hr.From = content;
                        break;
                    case "Host":
                        hr.Host = content;
                        break;
                    case "If-Match":
                        hr.IfMatch = content;
                        break;
                    case "If-Modified-Since":
                        hr.IfModifiedSince = content;
                        break;
                    case "If-None-Match":
                        hr.IfNoneMatch = content;
                        break;
                    case "If-Range":
                        hr.IfRange = content;
                        break;
                    case "If-Unmodified-Since":
                        hr.IfUnmodifiedSince = content;
                        break;
                    case "Max-Forwards":
                        hr.MaxForwards = content;
                        break;
                    case "Pragma":
                        hr.Pragma = content;
                        break;
                    case "Proxy-Authorization":
                        hr.ProxyAuthorization = content;
                        break;
                    case "Range":
                        hr.Range = content;
                        break;
                    case "Referer":
                        hr.Referer = content;
                        break;
                    case "TE":
                        hr.TE = content;
                        break;
                    case "Upgrade":
                        hr.Upgrade = content;
                        break;
                    case "User-Agent":
                        hr.UserAgent = content;
                        break;
                    case "Via":
                        hr.Via = content;
                        break;
                    case "Warning":
                        hr.Warning = content;
                        break;
                    default:
                        throw new HttpParseException("un recognize. key: " + key);
                }
            }
        } catch (Exception e) {
            throw new HttpParseException("illegal raw", e);
        }

        return hr;
    }

    /**
     * 数据模型生成html语句。
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // head不能为空。
        sb = head == null ? sb : sb.append(head).append("\r\n");

        sb = Accept == null ? sb : sb.append("Accept").append(Accept).append("\r\n");
        sb = AcceptCharset == null ? sb : sb.append("Accept-Charset: ").append(AcceptCharset).append("\r\n");
        sb = AcceptEncoding == null ? sb : sb.append("Accept-Encoding: ").append(AcceptEncoding).append("\r\n");
        sb = AcceptLanguage == null ? sb : sb.append("Accept-Language: ").append(AcceptLanguage).append("\r\n");
        sb = AcceptRanges == null ? sb : sb.append("Accept-Ranges: ").append(AcceptRanges).append("\r\n");
        sb = Authorization == null ? sb : sb.append("Authorization: ").append(Authorization).append("\r\n");
        sb = CacheControl == null ? sb : sb.append("Cache-Control: ").append(CacheControl).append("\r\n");
        sb = Connection == null ? sb : sb.append("Connection: ").append(Connection).append("\r\n");
        sb = Cookie == null ? sb : sb.append("Cookie: ").append(Cookie).append("\r\n");
        sb = ContentLength == null ? sb : sb.append("Content-Length: ").append(ContentLength).append("\r\n");
        sb = ContentType == null ? sb : sb.append("Content-Type: ").append(ContentType).append("\r\n");
        sb = Date == null ? sb : sb.append("Date: ").append(Date).append("\r\n");
        sb = Expect == null ? sb : sb.append("Expect: ").append(Expect).append("\r\n");
        sb = From == null ? sb : sb.append("From: ").append(From).append("\r\n");
        sb = Host == null ? sb : sb.append("Host: ").append(Host).append("\r\n");
        sb = IfMatch == null ? sb : sb.append("If-Match: ").append(IfMatch).append("\r\n");
        sb = IfModifiedSince == null ? sb : sb.append("If-Modified-Since: ").append(IfModifiedSince).append("\r\n");
        sb = IfNoneMatch == null ? sb : sb.append("If-None-Match: ").append(IfNoneMatch).append("\r\n");
        sb = IfRange == null ? sb : sb.append("If-Range: ").append(IfRange).append("\r\n");
        sb = IfUnmodifiedSince == null ? sb : sb.append("If-Unmodified-Since: ").append(IfUnmodifiedSince).append("\r\n");
        sb = MaxForwards == null ? sb : sb.append("Max-Forwards: ").append(MaxForwards).append("\r\n");
        sb = Pragma == null ? sb : sb.append("Pragma: ").append(Pragma).append("\r\n");
        sb = ProxyAuthorization == null ? sb : sb.append("Proxy-Authorization: ").append(ProxyAuthorization).append("\r\n");
        sb = Range == null ? sb : sb.append("Range: ").append(Range).append("\r\n");
        sb = Referer == null ? sb : sb.append("Referer: ").append(Referer).append("\r\n");
        sb = TE == null ? sb : sb.append("TE: ").append(TE).append("\r\n");
        sb = Upgrade == null ? sb : sb.append("Upgrade: ").append(Upgrade).append("\r\n");
        sb = UserAgent == null ? sb : sb.append("User-Agent: ").append(UserAgent).append("\r\n");
        sb = Via == null ? sb : sb.append("Via: ").append(Via).append("\r\n");
        sb = Warning == null ? sb : sb.append("Warning: ").append(Warning).append("\r\n");
        sb.append("\r\n");
        return sb.toString();
    }


}

