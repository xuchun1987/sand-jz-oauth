package oauth.util;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ProxyServer;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * 第三方类包AsyncHttpClient，工具类
 * @author xuchun 2017-04-07
 *
 */
public class AsyncHttpClientUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(AsyncHttpClientUtil.class);
	
    private static final String DEFAULT_CHARSET = "UTF-8";

    public static final String CONTENT_TYPE_JSON="application/json;charset=UTF-8";

    public static final String CONTENT_TYPE_XML="text/xml;charset=UTF-8";

    public static final String CONTENT_TYPE_html="text/html;charset=utf-8";


    public static final String CONTENT_TYPE_FORM="application/x-www-form-urlencoded";
    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers) {
        AsyncHttpClient http = new AsyncHttpClient();
        try {
            AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
            builder.setBodyEncoding(DEFAULT_CHARSET);
            if (params != null && !params.isEmpty()) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    builder.addQueryParameter(key, params.get(key));
                }
            }

            if (headers != null && !headers.isEmpty()) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    builder.addHeader(key, params.get(key));
                }
            }
            Future<Response> f = builder.execute();
            String body = f.get().getResponseBody(DEFAULT_CHARSET);

            return body;
        } catch (Exception e) {
            logger.error("error:"+e);
            return null;
        } finally {
            if(http!=null){
                http.close();
            }
        }
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws UnsupportedEncodingException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: POST 请求
     */
    public static String post(String url, Map<String, String> params) {
        AsyncHttpClient http = new AsyncHttpClient();
        try {
            AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
            builder.setBodyEncoding(DEFAULT_CHARSET);
            if (params != null && !params.isEmpty()) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    builder.addParameter(key, null==params.get(key)?"":params.get(key));
                }
            }
            logger.info("----------------AsyncHttpClientUtil-post--------------");
            logger.info("----------------请求url:"+url);
            logger.info("----------------请求param:"+WebUtil.parseFastJson(params));
            Future<Response> f = builder.execute();
            logger.info("----------------请求返回状态码："+f.get().getStatusCode());
            String body = f.get().getResponseBody(DEFAULT_CHARSET);
            logger.info("----------------请求返回内容："+body);
            return body;
        } catch (Exception e) {
            logger.error("error:"+e);
            return null;
        } finally {
            if(http!=null){
                http.close();
            }
        }
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: POST 请求
     */
    public static String postPingAnBank(String url, Map<String, String> params) {
        AsyncHttpClient http = new AsyncHttpClient();
        try {
            AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
            builder.setBodyEncoding(DEFAULT_CHARSET);
            builder.addHeader("Content-type", CONTENT_TYPE_FORM);
            if (params != null && !params.isEmpty()) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    builder.addParameter(key, null==params.get(key)?"":params.get(key).toString());
                }
            }
            logger.info("----------------AsyncHttpClientUtil-post--------------");
            logger.info("----------------请求url:"+url);
            logger.info("----------------请求param:"+WebUtil.parseFastJson(params));
            Future<Response> f = builder.execute();
            logger.info("----------------请求返回状态码："+f.get().getStatusCode());
            String body = f.get().getResponseBody(DEFAULT_CHARSET);
            logger.info("----------------请求返回内容："+body);
            return body;
        } catch (Exception e) {
            logger.error("error:"+e);
            return null;
        } finally {
            if(http!=null){
                http.close();
            }
        }
    }
    
    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: POST 请求
     */
    public static String post(String url, Map<String, String> params,Boolean isProxy,Map<String,String> proxyMap){
        AsyncHttpClient http = new AsyncHttpClient();
        try {
            AsyncHttpClient.BoundRequestBuilder builder =http.preparePost(url);
            //设置代理配置
            if(isProxy){
                ProxyServer proxyServer=
                        new ProxyServer(
                                proxyMap.get("proxyIp"),
                                Integer.valueOf(proxyMap.get("proxyPort")),
                                proxyMap.get("userName"),
                                proxyMap.get("password"));
                builder.setProxyServer(proxyServer);
            }

            builder.setBodyEncoding(DEFAULT_CHARSET);
            if (params != null && !params.isEmpty()) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    builder.addParameter(key, params.get(key)==null?"":params.get(key));
                }

            }
            //此header千万不能加上，因为会导致接收方，获取不到请求参数
            //builder.addHeader("Content-type", "text/html;charset=utf-8");
            logger.info("----------------AsyncHttpClientUtil-post--------------");
            logger.info("----------------是否使用代理服务器："+isProxy+"--------------");
            logger.info("----------------请求url:"+url);
            logger.info("----------------请求param:"+WebUtil.parseFastJson(params));
            Future<Response> f = builder.execute();
            logger.info("----------------请求返回状态码："+f.get().getStatusCode());
            String body = f.get().getResponseBody(DEFAULT_CHARSET);
            logger.info("----------------请求返回内容："+body);

            return body;
        } catch (Exception e) {
            logger.error("error:"+e);
            return null;
        }finally {
            if(http!=null){
                http.close();
            }
        }
    }




    public static String post(String url, String s) {
        AsyncHttpClient http = new AsyncHttpClient();
        try {
            AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
            builder.setBodyEncoding(DEFAULT_CHARSET);
            builder.addHeader("Content-type", "application/json;charset=UTF-8");
            builder.setBody(s);
            Future<Response> f = builder.execute();
            String body = f.get().getResponseBody(DEFAULT_CHARSET);
            return body;
        } catch (Exception e) {
            logger.error("error:"+e);
            return null;
        } finally {
            if(http!=null){
                http.close();
            }
        }
    }


    public static String postBody(String url, String s,String contentType) {
        logger.info("------------url:"+url);
        AsyncHttpClient http = new AsyncHttpClient();
        try {
            AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
            builder.setBodyEncoding(DEFAULT_CHARSET);
            builder.addHeader("Content-type", contentType);
            builder.setBody(s);
            Future<Response> f = builder.execute();
            String body = f.get().getResponseBody(DEFAULT_CHARSET);
            return body;
        } catch (Exception e) {
            logger.error("error:"+e);
            return null;
        } finally {
            if(http!=null){
                http.close();
            }
        }
    }





    /**
     * io流下载文件
     * xuchun
     * 2017-12-26
     * @param url
     * @param contentType
     * @param output
     * @return
     */
    public static boolean getToFile(String url, String contentType, FileOutputStream output) {
        logger.info("------------下载文件url:"+url);
        AsyncHttpClient http = new AsyncHttpClient();
        try {
            AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
            builder.setBodyEncoding(DEFAULT_CHARSET);
            builder.addHeader("Content-type", contentType);
            Future<Response> f = builder.execute();
            /*String str=f.get().getResponseBody(DEFAULT_CHARSET);
            logger.info(str);*/
            logger.info("----------------请求返回状态码："+f.get().getStatusCode());
            if(f.get().getStatusCode()==200){
                InputStream instream = f.get().getResponseBodyAsStream();
                if(instream!=null){
                    try {
                        byte[] tmp = new byte[4096];
                        int l;
                        while((l = instream.read(tmp)) != -1) {
                            output.write(tmp, 0, l);
                        }
                    } finally {
                        if(instream!=null){
                            instream.close();
                        }
                        if(output!=null){
                            output.close();
                        }

                    }
                }
            }

            logger.info("------------文件下载完成，请查看文件目录");
            return true;

        } catch (Exception e) {
            logger.error("error:"+e);
            return false;
        } finally {
            if(http!=null){
                http.close();
            }
        }
    }


}