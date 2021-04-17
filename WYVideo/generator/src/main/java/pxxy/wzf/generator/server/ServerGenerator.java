package pxxy.wzf.generator.server;


import pxxy.wzf.generator.utils.DbUtil;
import pxxy.wzf.generator.utils.Field;
import pxxy.wzf.generator.utils.FreemarkerUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

public class ServerGenerator {
    static String MODULE = "system";
    static String toDtoPath = "server\\src\\main\\java\\pxxy\\wzf\\server\\dto\\";
    static String toVoPath = MODULE+"\\src\\main\\java\\pxxy\\wzf\\"+MODULE+"\\rest\\vo\\param\\";
    static String toServicePath = "server\\src\\main\\java\\pxxy\\wzf\\server\\service\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\pxxy\\wzf\\" + MODULE + "\\rest\\controller\\";
    static String generatorConfigPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception {
        String module = MODULE;
        // 只生成配置文件中的第一个table节点
        File file = new File(generatorConfigPath);
        SAXReader reader=new SAXReader();
        //读取xml文件到Document中
        Document doc=reader.read(file);
        //获取xml文件的根节点
        Element rootElement=doc.getRootElement();
        //读取context节点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //取第一个“table”的节点
        tableElement=contextElement.elementIterator("table").next();
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);

        Map<String,Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("tableNameCn",tableNameCn);
        map.put("module",module);
        map.put("fieldList",fieldList);
        map.put("typeSet",typeSet);
        //生成service代码
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath+Domain+"Service.java",map);
        //生成controller代码
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath+Domain+"Controller.java",map);
        //生成Dto代码
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath+Domain+"Dto.java",map);
        //生成VO代码
        FreemarkerUtil.initConfig("vo.ftl");
        FreemarkerUtil.generator(toVoPath+Domain+"VO.java",map);
    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
