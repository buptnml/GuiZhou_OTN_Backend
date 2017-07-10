package com.bupt.util.mybatis.languagedriver;

import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 韩宪斌 on 2017/7/3.
 * 实现数据库的下划线命名法自动转换为驼峰命名法
 */
public class UnderScoreCaseLanguageDriver extends XMLLanguageDriver
        implements LanguageDriver {
    
    //SQL正则,忽略大小写
    private final Pattern SQLPattern = Pattern.compile("(select.*from)|(set.*(?=(where.*)))|(where.*)|(set.*$)", 0x02);
    
    //变量正则
    private final Pattern camelCasePattern = Pattern.compile("([a-z]+([A-Z]{1}[a-z]+)+)|(\\#\\{([a-z]+([A-Z]{1}[a-z]+)+)\\})");
    
    private final Pattern wordPattern = Pattern.compile("[A-Z]+");
    
    public static void main(String args[]) {
        UnderScoreCaseLanguageDriver temp = new UnderScoreCaseLanguageDriver();
        System.out.println(temp.SQLPattern);
        System.out.println(temp.camelCasePattern);
        String[] testCase = {"select equipId ,equipName,x,y from res_equip_cordinate where " +
                "equipSystemName=#{equipSystemName}", "delete from sys_user where userName=#{userName}", "update sys_user " +
                "set userName='' where password=#{password}", "UPDATE Person SET firstName = #{firstName} WHERE " +
                "lastName = #{lastName}", "UPDATE publishers SET city = #{city}, state = #{state}", "UPDATE sys_user, " +
                "month SET sys_user.priceToKnow=month.price WHERE sys_user.id=month.id;"};
        for (String stringCase : testCase) {
            System.out.println("Original case is: " + stringCase);
            System.out.println("Result is: " + temp.SQLStringHandler(stringCase));
        }
    }
    
    
    private String SQLStringHandler(String SQLString) {
        String result = new String(SQLString);
        Matcher SQLMatcher = SQLPattern.matcher(SQLString);
        while (SQLMatcher.find()) {
            System.out.println("Match SQL part is: " + SQLMatcher.group());
            sentenceHandler(SQLMatcher.group());
        }
        return result.toString();
    }
    
    /**
     * 处理正则SQL筛选出的短句
     *
     * @param sentence
     * @return
     */
    private String sentenceHandler(String sentence) {
        String result = new String();
        if (sentence.toLowerCase().indexOf("select") >= 0) {
            result = this.selectHandler(sentence);
        }
        if (sentence.toLowerCase().indexOf("where") >= 0) {
            result = this.whereHandler(sentence);
        }
        if (sentence.toLowerCase().indexOf("set") >= 0) {
            result = this.setHandler(sentence);
        }
        return result;
    }
    
    /**
     * select from类型短句处理
     *
     * @param selectString
     * @return
     */
    private String selectHandler(String selectString) {
        StringBuilder result = new StringBuilder();
        Matcher wordMatcher = camelCasePattern.matcher(selectString);
        int lastStart=0;
        while (wordMatcher.find()) {
            result.append(selectString.substring(lastStart,wordMatcher.start()));
            result.append(wordMatcher.group()+" as "+toUnderScoreCase(wordMatcher.group()));
            lastStart=wordMatcher.end();
        }
        System.out.println("result: "+result);
        return result.toString();
    }
    
    /**
     * set类型短句处理
     *
     * @param setString
     * @return
     */
    private String setHandler(String setString) {
        StringBuilder result = new StringBuilder(setString);
        Matcher wordMatcher = camelCasePattern.matcher(setString);
        while (wordMatcher.find()) {
            System.out.println(wordMatcher.group());
        }
        return result.toString();
    }
    
    /**
     * where类型短句处理
     *
     * @param whereString
     * @return
     */
    private String whereHandler(String whereString) {
        StringBuilder result = new StringBuilder(whereString);
        Matcher wordMatcher = camelCasePattern.matcher(whereString);
        while (wordMatcher.find()) {
            System.out.println(wordMatcher.group());
        }
        return result.toString();
    }
    
    
    /**
     * 将驼峰命名法转化为下划线命名
     *
     * @param camelCaseString
     * @return
     */
    private String toUnderScoreCase(String camelCaseString) {
        StringBuilder result = new StringBuilder(camelCaseString);
        Matcher bigCharMatcher = wordPattern.matcher(camelCaseString);
        while (bigCharMatcher.find() && bigCharMatcher.group().length() == 1) {
            result.replace(bigCharMatcher.start(), bigCharMatcher.end(), "_" + bigCharMatcher.group().toLowerCase());
        }
        return result.toString();
    }


//    //将下划线命名法转换为驼峰命名法
//    private String toCamelCase(String underScoreCaseString){
//        StringBuilder camelCaseString=new StringBuilder();
//        String[] stringArray=underScoreCaseString.split("_");
//        for(int i=0;i< stringArray.length;i++){
//            if(i==0){
//                camelCaseString.append(stringArray[i]);
//            }else{
//                camelCaseString.append(this.firstWordToUpperCase(stringArray[i]));
//            }
//
//        }
//        return camelCaseString.toString();
//    }
//
//    //首字母转换为大写
//    @NotNull
//    private String firstWordToUpperCase(String inputString){
//        String firstLetter=inputString.substring(0,1).toUpperCase();
//        return firstLetter+inputString.substring(1);
//    }
    
}
