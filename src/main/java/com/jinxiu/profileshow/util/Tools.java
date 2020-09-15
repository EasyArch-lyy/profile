package com.jinxiu.profileshow.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinxiu.profileshow.common.Constants;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.util.HtmlUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    private static final Logger logger = LoggerFactory.getLogger(Tools.class);

    private static final int CACHE_SIZE = 10 * 1024;
    private static String serverRootPath = null;
    private static final Pattern groupPattern = Pattern.compile("^http(s)?://gitlab(\\d){2}.dtdream.com/(\\w)+$");

    private static ObjectMapper objectMapper = new ObjectMapper();

    private Tools() { }

    /**
     * 获取网站根路径
     *
     * @return 网站根路径
     */
    public static String getServerRootPath() {
        if (StringUtils.isEmpty(serverRootPath)) {
            return null;
        }
        return serverRootPath;
    }

    public static String getResourceDirectory(String... directory) {
        if (serverRootPath == null) {
            return null;
        }
        String path = serverRootPath;
        if (directory != null && directory.length > 0) {
            for (String dir : directory) {
                path = Tools.pathAppend(path, dir);
            }
        }
        return path;
    }

    public static String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

    /**
     * 获取网站跟路径
     *
     * @param serverRootPath 网站个路径
     */
    public static void setServerRootPath(String serverRootPath) {
        Tools.serverRootPath = serverRootPath;
    }

    public static String escapeHtml(String source) {
        return escapeHtml(source, false);
    }

    public static String escapeHtml(String source, boolean bRemainReturn) {
        if (source == null) {
            return "";
        }

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case 10:
                case 13:
                    if (bRemainReturn) {
                        buffer.append(c);
                    }
                    break;
                default:
                    buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public static String getPercentage(double value, double total) {

        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumIntegerDigits(3);
        numberFormat.setMaximumFractionDigits(2);
        Double percentage = (value / total);
        return numberFormat.format(percentage).contains("%") ? numberFormat.format(percentage) : "0%";

    }

    public static List<Integer> formatIntegerList(Set<Integer> list) {
        List<Integer> targetList = new ArrayList<>(list);
        Collections.sort(targetList);
        return targetList;
    }

    public static Date stringToDate(String strTime) {
        //获取第二天的日期
        Date dt = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        dt = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dt);
        strTime = dateString + " " + strTime;

        Date date = null;
        //获取第二天的日期
        try {
            String formatType = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat fm = new SimpleDateFormat(formatType);
            date = fm.parse(strTime);
        } catch (ParseException e) {
            logger.error("stringToDate parse error", e);
        }
        return date;
    }

    public static String getTime() {
        StringBuilder sTime = new StringBuilder();
        Calendar c = Calendar.getInstance();

        sTime.append(c.get(Calendar.YEAR));
        sTime.append(c.get(Calendar.MONTH) + 1);
        sTime.append(c.get(Calendar.DATE));
        sTime.append(c.get(Calendar.HOUR_OF_DAY));
        sTime.append(c.get(Calendar.MINUTE));
        sTime.append(c.get(Calendar.SECOND));

        return sTime.toString();
    }

    public static String dateToStr(Date date) {
        String formatType = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat fm = new SimpleDateFormat(formatType);
        return fm.format(date);
    }

    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    public static boolean isHolidays(Date date) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(date);
        int dayOfWeek = calStart.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == 1 || dayOfWeek == 7);
    }

    public static File checkDirExist(String directoryPath) {
        File file = new File(directoryPath);
        try{
            if (!file.exists() ) {
                file.mkdirs();
            }
        }catch (Exception e){
            logger.error("checkDirExist error: file = {}" , file.getAbsolutePath());
            throw e;
        }
        return file;
    }

    public static String pathAppend(final String sSrcPath, final String sNewFiled) {

        String sTmpSrcPath = sSrcPath;

        if (sTmpSrcPath.lastIndexOf(File.separator) != (sTmpSrcPath.length() - 1))
            sTmpSrcPath += File.separator;
        sTmpSrcPath += sNewFiled;

        return sTmpSrcPath;
    }

    public static synchronized String getMD5Str(String str, String charSet) {
        byte[] byteArray = new byte[0];
        try {
            //md5加密
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            if (charSet == null) {
                messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            } else {
                messageDigest.update(str.getBytes(charSet));
            }

           byteArray = messageDigest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("md5 error:", e);
        }

        StringBuilder md5StrBuff = new StringBuilder();
        for (byte byteInstance : byteArray) {
            String hex = String.format("%02X", 0xFF & byteInstance);
            if (hex.length() == 1) {
                md5StrBuff.append("0").append(hex);
            }
            else {
                md5StrBuff.append(hex);
            }
        }
        return md5StrBuff.toString();
    }

    public static String getBoolResult(boolean bResult) {
        if (bResult)
            return Constants.API_RET_TRUE;
        else
            return Constants.API_RET_FALSE;
    }


    public static String stringArrayToString(String[] strArrays) {
        if (strArrays != null && strArrays.length > 0) {
            return StringUtils.join(strArrays, ",");
        } else {
            return "";
        }
    }

    public static Integer[] integerListToArray(List<Integer> integerList) {
        if (null != integerList) {
            return integerList.toArray(new Integer[0]);
        } else {
            return new Integer[0];
        }
    }

    public static void sleep(long mills, Logger logger) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            if (logger != null) {
                logger.info("InterruptedException", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    public static FreeMarkerConfigurer getFreeMarkerConfigurer(String templateDir, Logger logger) {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:" + templateDir);
        Configuration conf;
        try {
            conf = freeMarkerConfigurer.createConfiguration();
        } catch (IOException e) {
            logger.error("getFreeMarkerConfigurer IOException ", e);
            return null;
        } catch (TemplateException e) {
            logger.error("getFreeMarkerConfigurer TemplateException", e);
            return null;
        }
        conf.setDefaultEncoding("UTF-8");
        conf.setLocale(Locale.CHINA);
        freeMarkerConfigurer.setConfiguration(conf);
        return freeMarkerConfigurer;
    }

    public static String formatDuration(Double time) {
        if (null == time) {
            return Tools.formatDuration(0);
        }
        return Tools.formatDuration(time.longValue() * 1000);
    }

    public static String formatDuration(long time) {

        long ltime = time / 1000;
        long hour = ltime / 3600;
        long minute = ltime % 3600 / 60;
        String strHour = "" + hour;
        String strMinute = "" + minute;
        double second = ((double) (time - (hour * 3600 * 1000) - (minute * 60 * 1000))) / 1000d;
        String strSecond = "" + second;

        strHour = strHour.length() < 2 ? "0" + strHour : strHour;
        strMinute = strMinute.length() < 2 ? "0" + strMinute : strMinute;
        strSecond = strSecond.length() < 2 ? "0" + strSecond : strSecond;

        String strRsult = "";

        if (!"00".equals(strHour)) {
            strRsult += strHour + "小时";
        }

        if (!"00".equals(strMinute)) {
            strRsult += strMinute + "分";
        }

        strRsult += (StringUtils.equals(strSecond, "0.0") ? 0 : strSecond) + "秒";

        return strRsult;
    }

    // 获取response header中Content-Disposition中的filename值
    private static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        filename = param.getValue();
                    } catch (Exception e) {
                        logger.error("getFileName error", e);
                    }
                }
            }
        }
        return filename;
    }

    private static Calendar initNoTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date); //你自己的数据进行类型转换
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static String base64Encode(final String code){
        try{
            if(code == null){
                return "";
            }
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] textByte = code.getBytes("UTF-8");
            // 编码
            final String encodedText = encoder.encodeToString(textByte);
            return encodedText;
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }

    /** 过滤全角空格
     * @param str
     * @return
     */
    public static String trim(String str){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        // 首先把全角空格转化为半角空格，最后去两边空处理
        str = StringUtils.replace(str, "　", " ");
        return str.trim();
    }

    /** node转为String，不包含最外层
     * @param node
     * @return
     */
    public static String nodeChildNodesToString(Node node){
        if(node == null){
            return "";
        }
        String str = nodeToString(node);
        try{
            String nodeName = node.getNodeName();
            str = replaceFirst(str, "<"+nodeName+".*?>", "");
            // 可能获取到的是 <td/>
            if( str.length() >= ("</"+nodeName+">").length() ){
                str = str.substring(0, str.lastIndexOf("</"+nodeName+">"));
            }
        }catch (Exception e){
            logger.error(" nodeChildNodesToString fail:nodeStr={}", nodeToString(node));
        }

        return str;
    }

    /** node转为String
     * @param node
     * @return
     */
    public static String nodeToString(Node node){
        if(node == null){
            return "";
        }
        try{
            DOMSource source = new DOMSource(node);
            StringWriter writer = new StringWriter();
            Result result = new StreamResult(writer);

            TransformerFactory factory = TransformerFactory.newInstance();

            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            transformer.transform(source, result);

            return writer.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            logger.error("---TransformerConfigurationException---", e);
        } catch (TransformerException e) {
            logger.error("---TransformerException---", e);
        }
        return "";
    }

    /** str正则表达式替换第一个
     * @param str
     * @param regex
     * @param replacement
     * @return
     */
    public static String replaceFirst(String str, String regex, String replacement){
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(regex) || replacement == null){
            return "";
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceFirst(replacement);
    }

    public static NodeList findTagsByStr(String xml, String tag) {
        if (StringUtils.isEmpty(xml) || StringUtils.isEmpty(tag)) {
            return null;
        }
        String formatXml = formatConfluenceXml(xml);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setExpandEntityReferences(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(formatXml)));
            Element root = doc.getDocumentElement();
            return root.getElementsByTagName(tag);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatConfluenceXml(String confluenceCaseStr) {
        // 正则表达式
        String temp = "<ac.*?>";
        Pattern pattern = Pattern.compile(temp);
        Matcher matcher = pattern.matcher(confluenceCaseStr);
        confluenceCaseStr = matcher.replaceAll("");

        String temp1 = "</ac.*?>";
        Pattern pattern1 = Pattern.compile(temp1);
        Matcher matcher1 = pattern1.matcher(confluenceCaseStr);
        confluenceCaseStr = matcher1.replaceAll("");


        // 正则表达式<span *>
        String tempSpan = "<span.*?>";
        Pattern patternSpan = Pattern.compile(tempSpan);
        Matcher matcherSpan = patternSpan.matcher(confluenceCaseStr);
        confluenceCaseStr = matcherSpan.replaceAll("");

        // 正则表达式</span>
        String tempSpanEnd = "</span.*?>";
        Pattern patternSpanEnd = Pattern.compile(tempSpanEnd);
        Matcher matcherSpanEnd = patternSpanEnd.matcher(confluenceCaseStr);
        confluenceCaseStr = matcherSpanEnd.replaceAll("");

        long tempNum = new Date().getTime();
        // 处理html中的特殊字符
        confluenceCaseStr = confluenceCaseStr.replaceAll("&amp;", tempNum + "_amp");
        confluenceCaseStr = confluenceCaseStr.replaceAll("&lt;", tempNum + "_lt");
        confluenceCaseStr = confluenceCaseStr.replaceAll("&gt;", tempNum + "_gt");
        confluenceCaseStr = HtmlUtils.htmlUnescape(confluenceCaseStr);
        confluenceCaseStr = confluenceCaseStr.replaceAll("ri:", "");
        confluenceCaseStr = confluenceCaseStr.replaceAll(" ", "");
        confluenceCaseStr = confluenceCaseStr.replaceAll(tempNum + "_amp", "&amp;");
        confluenceCaseStr = confluenceCaseStr.replaceAll(tempNum + "_lt", "&lt;");
        confluenceCaseStr = confluenceCaseStr.replaceAll(tempNum + "_gt", "&gt;");

        String temp2 = "<td>　*?</td>";
        Pattern pattern2 = Pattern.compile(temp2);
        Matcher matcher2 = pattern2.matcher(confluenceCaseStr);
        confluenceCaseStr = matcher2.replaceAll("<td></td>");

        String temp3 = "<td> *?</td>";
        Pattern pattern3 = Pattern.compile(temp3);
        Matcher matcher3 = pattern3.matcher(confluenceCaseStr);
        confluenceCaseStr = matcher3.replaceAll("<td></td>");

        return confluenceCaseStr;
    }

    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (org.springframework.util.StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            logger.error("string2Obj异常：", e);
            return null;
        }
    }


    /** 将map转为String,如：key1:value1,key2:value2
     * @param map
     * @return
     */
    public static String mapToString(Map<String,String> map){
        if(map == null){
            return "";
        }
        StringBuilder str = new StringBuilder("");
        for(Map.Entry<String,String> entry : map.entrySet()){
            if(!str.toString().isEmpty()){
                // 不是第一次，添加分隔符
                str.append(",");
            }
            str.append(entry.getKey()).append(":").append(entry.getValue());
        }
        return str.toString();
    }

    /** 判断字符串是否为空，可多个一起判断
     * @param strs
     * @return
     */
    public static boolean isEmpty(String... strs){
        boolean flag = true;
        for(String str :strs){
            flag = StringUtils.isEmpty(str);
            if(!flag){
                return flag;
            }
        }
        return flag;
    }

}
