package com.hy.parser.html;

/**
 * html响应头。
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
 * <td>Accept-Ranges</td>
 * <td>表明服务器是否支持指定范围请求及哪种类型的分段请求</td>
 * <td>Accept-Ranges: bytes</td>
 * </tr>
 * <tr>
 * <td>Age</td>
 * <td>从原始服务器到代理缓存形成的估算时间（以秒计，非负）</td>
 * <td>Age: 12</td>
 * </tr>
 * <tr>
 * <td>Allow</td>
 * <td>对某网络资源的有效的请求行为，不允许则返回405</td>
 * <td>Allow: GET, HEAD</td>
 * </tr>
 * <tr>
 * <td>Cache-Control</td>
 * <td>告诉所有的缓存机制是否可以缓存及哪种类型</td>
 * <td>Cache-Control: no-cache</td>
 * </tr>
 * <tr>
 * <td>Content-Encoding</td>
 * <td>web服务器支持的返回内容压缩编码类型。</td>
 * <td>Content-Encoding: gzip</td>
 * </tr>
 * <tr>
 * <td>Content-Language</td>
 * <td>响应体的语言</td>
 * <td>Content-Language: en,zh</td>
 * </tr>
 * <tr>
 * <td>Content-Length</td>
 * <td>响应体的长度</td>
 * <td>Content-Length: 348</td>
 * </tr>
 * <tr>
 * <td>Content-Location</td>
 * <td>请求资源可替代的备用的另一地址</td>
 * <td>Content-Location: /index.htm</td>
 * </tr>
 * <tr>
 * <td>Content-MD5</td>
 * <td>返回资源的MD5校验值</td>
 * <td>Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ==</td>
 * </tr>
 * <tr>
 * <td>Content-Range</td>
 * <td>在整个返回体中本部分的字节位置</td>
 * <td>Content-Range: bytes 21010-47021/47022</td>
 * </tr>
 * <tr>
 * <td>Content-Type</td>
 * <td>返回内容的MIME类型</td>
 * <td>Content-Type: text/html; charset=utf-8</td>
 * </tr>
 * <tr>
 * <td>Date</td>
 * <td>原始服务器消息发出的时间</td>
 * <td>Date: Tue, 15 Nov 2010 08:12:31 GMT</td>
 * </tr>
 * <tr>
 * <td>ETag</td>
 * <td>请求变量的实体标签的当前值</td>
 * <td>ETag: “737060cd8c284d8af7ad3082f209582d”</td>
 * </tr>
 * <tr>
 * <td>Expires</td>
 * <td>响应过期的日期和时间</td>
 * <td>Expires: Thu, 01 Dec 2010 16:00:00 GMT</td>
 * </tr>
 * <tr>
 * <td>Last-Modified</td>
 * <td>请求资源的最后修改时间</td>
 * <td>Last-Modified: Tue, 15 Nov 2010 12:45:26 GMT</td>
 * </tr>
 * <tr>
 * <td>Location</td>
 * <td>用来重定向接收方到非请求URL的位置来完成请求或标识新的资源</td>
 * <td>Location: http://www.zcmhi.com/archives/94.html</td>
 * </tr>
 * <tr>
 * <td>Pragma</td>
 * <td>包括实现特定的指令，它可应用到响应链上的任何接收方</td>
 * <td>Pragma: no-cache</td>
 * </tr>
 * <tr>
 * <td>Proxy-Authenticate</td>
 * <td>它指出认证方案和可应用到代理的该URL上的参数</td>
 * <td>Proxy-Authenticate: Basic</td>
 * </tr>
 * <tr>
 * <td>refresh</td>
 * <td>应用于重定向或一个新的资源被创造，在5秒之后重定向（由网景提出，被大部分浏览器支持）</td>
 * <td>
 * Refresh: 5; url=http://www.atool.org/httptest.php
 * </td>
 * </tr>
 * <tr>
 * <td>Retry-After</td>
 * <td>如果实体暂时不可取，通知客户端在指定时间之后再次尝试</td>
 * <td>Retry-After: 120</td>
 * </tr>
 * <tr>
 * <td>Server</td>
 * <td>web服务器软件名称</td>
 * <td>Server: Apache/1.3.27 (Unix) (Red-Hat/Linux)</td>
 * </tr>
 * <tr>
 * <td>Set-Cookie</td>
 * <td>设置Http Cookie</td>
 * <td>Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1</td>
 * </tr>
 * <tr>
 * <td>Trailer</td>
 * <td>指出头域在分块传输编码的尾部存在</td>
 * <td>Trailer: Max-Forwards</td>
 * </tr>
 * <tr>
 * <td>Transfer-Encoding</td>
 * <td>文件传输编码</td>
 * <td><span style="font-family: monospace;"><span style="font-family: Georgia,'Times New Roman','Bitstream Charter',Times,serif;">Transfer-Encoding:chunked</span></span>
 * </td>
 * </tr>
 * <tr>
 * <td>Vary</td>
 * <td>告诉下游代理是使用缓存响应还是从原始服务器请求</td>
 * <td>Vary: *</td>
 * </tr>
 * <tr>
 * <td>Via</td>
 * <td>告知代理客户端响应是通过哪里发送的</td>
 * <td>Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)</td>
 * </tr>
 * <tr>
 * <td>Warning</td>
 * <td>警告实体可能存在的问题</td>
 * <td>Warning: 199 Miscellaneous warning</td>
 * </tr>
 * <tr>
 * <td>WWW-Authenticate</td>
 * <td>表明客户端请求实体应该使用的授权方案</td>
 * <td>WWW-Authenticate: Basic</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author hy 2018/2/23
 */
public class HtmlResponse {


    // Accept-Ranges	表明服务器是否支持指定范围请求及哪种类型的分段请求	Accept-Ranges: bytes
    public String AcceptRanges;

    // Age	从原始服务器到代理缓存形成的估算时间（以秒计，非负）	Age: 12
    public String Age;

    // Allow	对某网络资源的有效的请求行为，不允许则返回405	Allow: GET, HEAD
    public String Allow;

    // Cache-Control	告诉所有的缓存机制是否可以缓存及哪种类型	Cache-Control: no-cache
    public String CacheControl;

    // Content-Encoding	web服务器支持的返回内容压缩编码类型。	Content-Encoding: gzip
    public String ContentEncoding;

    // Content-Language	响应体的语言	Content-Language: en,zh
    public String ContentLanguage;

    // Content-Length	响应体的长度	Content-Length: 348
    public String ContentLength;

    // Content-Location	请求资源可替代的备用的另一地址	Content-Location: /index.htm
    public String ContentLocation;

    // Content-MD5	返回资源的MD5校验值	Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ==
    public String ContentMD5;

    // Content-Range	在整个返回体中本部分的字节位置	Content-Range: bytes 21010-47021/47022
    public String ContentRange;

    // Content-Type	返回内容的MIME类型	Content-Type: text/html; charset=utf-8
    public String ContentType;

    // Date	原始服务器消息发出的时间	Date: Tue, 15 Nov 2010 08:12:31 GMT
    public String Date;

    // ETag	请求变量的实体标签的当前值	ETag: “737060cd8c284d8af7ad3082f209582d”
    public String ETag;

    // Expires	响应过期的日期和时间	Expires: Thu, 01 Dec 2010 16:00:00 GMT
    public String Expires;

    // Last-Modified	请求资源的最后修改时间	Last-Modified: Tue, 15 Nov 2010 12:45:26 GMT
    public String LastModified;

    // Location	用来重定向接收方到非请求URL的位置来完成请求或标识新的资源	Location: http://www.zcmhi.com/archives/94.html
    public String Location;

    // Pragma	包括实现特定的指令，它可应用到响应链上的任何接收方	Pragma: no-cache
    public String Pragma;

    // Proxy-Authenticate	它指出认证方案和可应用到代理的该URL上的参数	Proxy-Authenticate: Basic
    public String ProxyAuthenticate;

    // refresh	应用于重定向或一个新的资源被创造，在5秒之后重定向（由网景提出，被大部分浏览器支持）
    public String refresh;

    // Refresh: 5; url=http://www.atool.org/httptest.php
    public String Refresh;

    // Retry-After	如果实体暂时不可取，通知客户端在指定时间之后再次尝试	Retry-After: 120
    public String RetryAfter;

    // Server	web服务器软件名称	Server: Apache/1.3.27 (Unix) (Red-Hat/Linux)
    public String Server;

    // Set-Cookie	设置Http Cookie	Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1
    public String SetCookie;

    // Trailer	指出头域在分块传输编码的尾部存在	Trailer: Max-Forwards
    public String Trailer;

    // Transfer-Encoding	文件传输编码	Transfer-Encoding:chunked
    public String TransferEncoding;

    // Vary	告诉下游代理是使用缓存响应还是从原始服务器请求	Vary: *
    public String Vary;

    // Via	告知代理客户端响应是通过哪里发送的	Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)
    public String Via;

    // Warning	警告实体可能存在的问题	Warning: 199 Miscellaneous warning
    public String Warning;

    // WWW-Authenticate	表明客户端请求实体应该使用的授权方案	WWW-Authenticate: Basic
    public String WWWAuthenticate;

}
