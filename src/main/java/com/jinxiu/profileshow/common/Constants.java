package com.jinxiu.profileshow.common;

import com.jinxiu.profileshow.util.MyProperities;
import java.io.File;

/**
 * 定义项目的全局常量
 */
public class Constants {

    //define api response
    public static final String API_RET_SUCCESS = "success";
    public static final String API_RET_FAIL = "fail";
    public static final String API_RET_ERROR = "error";
    public static final String API_RET_EXCEP = "exception";

    public static final String API_RET_TRUE = "true";
    public static final String API_RET_FALSE = "false";

    public static final String API_RET_SUCCESS_JSON = "{\"result\":\"success\"}";
    public static final String API_RET_ERROR_JSON = "{\"result\":\"error\"}";
    public static final String API_RET_EMPTY_JSON = "{}";

    public static final String NOW_USER = "nowUser";
    public static final String NOW_USER_ACCOUNT = "nowUserAccount";
    public static final String NOW_USER_PWD = "nowUserPwd";

    //define case source
    public static final int CASE_SOURCE_CONFLUENCE = 1;
    public static final int CASE_SOURCE_EXCEL = 2;
    public static final int CASE_SOURCE_JAR = 3;
    public static final int CASE_SOURCE_WEB = 4;

    //define jobStatus
    public static final String JOB_STATUS_SUCCESS = "1"; // 成功
    public static final String JOB_STATUS_FAILED = "2"; // 失败
    public static final String JOB_STATUS_ABORTED = "3"; // 中止
    public static final String JOB_STATUS_UNSTABLE = "4"; // 不稳定

    //define case type
    public static final int CASE_TYPE_FUNC = 1;
    public static final int CASE_TYPE_COMB = 2;
    public static final int CASE_TYPE_PRESS = 3;
    public static final int CASE_TYPE_PERF = 4;
    public static final int CASE_TYPE_EXCP = 5;
    public static final int CASE_TYPE_COMP = 6;
    public static final int CASE_TYPE_RELIABILITY = 7;
    public static final int CASE_TYPE_EASY = 8;
    public static final int CASE_TYPE_SECURITY = 9;
    public static final int CASE_TYPE_DEPLOYMENT = 10;


    //case priority L-1 M-2 H-3 S-4
    public static final int CASE_PRIORITY_L = 1;
    public static final int CASE_PRIORITY_M = 2;
    public static final int CASE_PRIORITY_H = 3;
    public static final int CASE_PRIORITY_S = 4;

    //case or task  status normal-1 delete-2 ready-10 running-11 stop-12
    public static final int CASE_STATUS_NORMAL = 1;
    public static final int CASE_STATUS_DELETE = 2;
    public static final int CASE_STATUS_READY = 10;
    public static final int CASE_STATUS_RUNNING = 11;
    public static final int CASE_STATUS_STOP = 12;
    public static final int CASE_STATUS_CLOSE = 13;
    public static final int CASE_STATUS_COMPLETE = 14;  //完成状态
    public static final int JOB_STATUS_RELEASED = 17;

    // define result of case execution
    public static final String CASE_EXECUTE_OK = "OK";
    public static final String CASE_EXECUTE_POK = "POK";
    public static final String CASE_EXECUTE_FAIL = "Fail";
    public static final String CASE_EXECUTE_BLOCK = "Block";
    public static final String CASE_EXECUTE_NOT_SUPPORT = "Not Support";
    public static final String CASE_EXECUTE_NOT_TEST = "Not Test";
    public static final String CASE_EXECUTE_NOT_ARRANGE = "Not Arrange";
    public static final String CASE_EXECUTE_PASS = "Pass";
    public static final String CASE_EXECUTE_SKIP = "Skip";

    public static final String HTTP_CONTENT_TYPE_APPLICATION_JSON = "application/json";

    //上传/下载文件夹名称
    public static final String UPLOADDIR = "upload" + File.separator;
    public static final String DOWNLOADDIR = "download" + File.separator;

    //Excel模板中优先级
    public static final String EXCEL_PRIORITY_HIGH = "high";
    public static final String EXCEL_PRIORITY_MIDDLE = "middle";
    public static final String EXCEL_PRIORITY_LOW = "low";

    //Task Type
    public static final int TASK_TYPE_MANU = 1;
    public static final int TASK_TYPE_AUTO = 2;
    public static final int TASK_TYPE_WHITEBOX = 3;
    public static final int TASK_TYPE_CI = 4;

    public static final String newCompileJobRadio = "newCompileJob";
    public static final String importCompileJobRadio = "importCompileJob";
    public static final String baseBranchIssueRadio = "baseBranchIssue";
    public static final String userDefinedIssueRadio = "userDefinedIssue";

    final static public int Confluence_Page_Module = 1;
    final static public int AT_Module = 2;
    final static public int Not_Valid_Module = 3;
    final static public int Not_Confluence_Module = 4;

    final static public String AT_Case_Table = "AT";
    final static public String PT_Case_Table = "PT";

    final static public String File_Repository = "file-repository";

    public static final int OVER_DAY = Integer.valueOf(MyProperities.findValueByKey("OVER_DAY"));
    public static final String Local_Server_Url = MyProperities.findValueByKey("LOCAL_SERVER");
    public static final String Module_Blacklist_PageID = MyProperities.findValueByKey("Module_Blacklist_PageID");

    public static final String CI_EDITIONJOB_NAME_PREFIX = "MyJob_testCiEdition_";

    public static final String AUTO_TASK_NAME_PREFIX = "MyJob_AutoTask_";

    public static final int EDITION_COMPILE_RATE = 30;

    public static final int EDITION_SCAN_RATE = 10;

    public static final int EDITION_PACK_RATE = 20;

    public static final int EDITION_DEPLOY_RATE = 30;

    public static final int EDITION_AT_RATE = 10;

    public static final String COMMON_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int CREATE_JOB_RETRY_TIMES = 3;

    public static final String MVN_CLEAN_TEST_SHELL = MyProperities.findValueByKey("RUN_TEST_SCRIPT");

    public static final String RUN_TEST_SCRIPTMVN = MyProperities.findValueByKey("RUN_TEST_SCRIPTMVN");

    //用户类型
    public static final int USER_LDAP = 1;
    public static final int USER_LOCAL = 2;

    public static final String CHARSET_UTF_8 = "UTF-8";

    public static final String CONF_DESCRIPTION_SUFFIX = ".description";

    //任务类型
    public static final String AUTO_TASK_TYPE_WEB = "WEB";
    public static final String AUTO_TASK_TYPE_API = "API";
    public static final String AUTO_TASK_TYPE_THREE_PART = "ThreePart";

    public static final String CONFIG_TYPE_BEFORE_SUITE = "@BeforeSuite: ";
    public static final String CONFIG_TYPE_AFTER_SUITE = "@AfterSuite: ";
    public static final String CONFIG_TYPE_BEFORE_TEST = "@BeforeTest: ";
    public static final String CONFIG_TYPE_AFTER_TEST = "@AfterTest: ";
    public static final String CONFIG_TYPE_BEFORE_GROUPS = "@BeforeGroups: ";
    public static final String CONFIG_TYPE_AFTER_GROUPS = "@AfterGroups: ";
    public static final String CONFIG_TYPE_BEFORE_CLASS = "@BeforeClass: ";
    public static final String CONFIG_TYPE_AFTER_CLASS = "@AfterClass: ";
    public static final String CONFIG_TYPE_BEFORE_METHOD = "@BeforeMethod: ";
    public static final String CONFIG_TYPE_AFTER_METHOD = "@AfterMethod: ";

    public static final String THREE_PART_AUTO_TASK_LOG = "report.report";

    public static final String PATH_TASK_PREFIX = "Task_";

    public static final String JENKINS_JOB_RESULT_SUCCESS = "SUCCESS";
    public static final String JENKINS_JOB_RESULT_FAILED = "FAILED";
    public static final String JENKINS_JOB_RESULT_FAILURE = "FAILURE";

    public static final String COMPILE_STATUS_WARNING = "warning";
    public static final String COMPILE_STATUS_DANGER = "danger";

    public static String GIT_PROTOCOL = MyProperities.findValueByKey("GIT_PROTOCOL");
    public static String JAVA_HOME = MyProperities.findValueByKey("JAVA_HOME");
    public static String MAVEN_HOME = MyProperities.findValueByKey("MAVEN_HOME");

    // 超时时间基数
    public static final int TIME_OUT = 1000;
    public static final int BLACK_ROW_NUM = 500;

    private Constants(){}
}
