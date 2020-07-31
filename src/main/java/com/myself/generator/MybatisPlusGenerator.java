package com.myself.generator;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 * @title: MybatisPlusGenerator
 * @projectName springboot-demo
 * @description: TODO
 * @date 2019/9/16 0016下午 14:49
 */
public class MybatisPlusGenerator {
    private static MybatisPlusGenerator single = null;
    /**1.修改存放路径*/

    private static String path = "C:\\workspace\\idea\\me\\garbage-manage\\src\\main\\java"; //文件路径

    private static String packagePath ="com.myself.modules.basics";
    private static String ReturnPath = "/com/myself/modules/basics";            //生成文件路径

    /**2.修改数据库设置*/
    //数据库
    private static String db ="jdbc:mysql://39.99.161.16:3306/garbage-manage?characterEncoding=UTF-8";
    private static String username = "root";  //用户名
    private static String password = "0000";  //数据库密码

    /**3.修改需要生成的表*/
    private static String[] table = {
            /*"aaa",
            "bbb",
            "ccc"*/
            "tb_user"

    };

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MybatisPlusGenerator generator = MybatisPlusGenerator.getSingle();
        generator.autoGeneration();
    }



    private MybatisPlusGenerator() {
        super();
    }

    private static MybatisPlusGenerator getSingle() {
        if (single == null) {
            single = new MybatisPlusGenerator();
        }
        return single;
    }

    //数据源配置
    public void autoGeneration() {
        /**
         * 设置连接到数据库信息
         */
        String dbUrl = db;
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(username)
                .setPassword(password)
                .setDriverName("com.mysql.jdbc.Driver")
                /**
                 * 格式转换
                 */
                // 自定义数据库表字段类型转换【可选】
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        //tinyint转换成Boolean
                        if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                            return DbColumnType.BOOLEAN;
                        }
                        //将数据库中datetime转换成date
                        if ( fieldType.toLowerCase().contains( "datetime" ) ) {
                            return DbColumnType.DATE;
                        }
                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                    }
                });



        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        /**
         * 全局大写命名
         */
        strategyConfig.setCapitalMode(true)
                /**
                 * 实体使用lombok模式
                 */
                .setEntityLombokModel(true)
                //.setDbColumnUnderline(true)
                /**
                 * 数据库表映射到实体的命名策略
                 */
                .setNaming(NamingStrategy.underline_to_camel)
                //.setNaming(NamingStrategy.nochange)
                /**
                 * 数据库字段名
                 */
                //.setColumnNaming(NamingStrategy.nochange)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                /**
                 * controller生成@RestCcontroller
                 */
                .setRestControllerStyle(true)
                /**
                 *  数据库版本控制字段
                 */
//                .setVersionFieldName("version")
                /**
                 *  数据库逻辑删除字段
                 */
                .setLogicDeleteFieldName("is_remove")

                // .setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService")
                /**
                 *  需要生成的表
                 * */
                .setInclude(table);

        /**
         *   排除要生成的表
         *
         * */
//                 .setExclude(new String[]{
//                         "t_b_author",
//                         "t_b_department",
//                         "t_b_function",
//                         "t_b_group",
//                         "t_b_menu",
//                         "t_b_moduler",
//                         "t_b_org",
//                         "t_b_role",
//                         "t_b_role_system",
//                         "t_b_system",
//                         "t_b_user",
//                         "t_b_user_group",
//                         "t_b_user_role",
//                         "t_b_user_system",
//                         "t_b_version"
//                 });


        //全局配置
        GlobalConfig config = new GlobalConfig();
        /**
         * 是否支持ar模式
         */
        config.setActiveRecord(false) //是否支持AR模式
                .setSwagger2(true)    //使用  Swagger
                .setEnableCache(false)
                /**
                 * 指定人员(作者)
                 */
                .setAuthor("xiao")
                /**
                 * 指定输出文件夹位置
                 */
                .setOutputDir(path)
                /**
                 * 是否覆盖
                 */
                .setFileOverride(true)
                /**
                 * 指定service包名称
                 */
                .setServiceName("%sService")
                /**
                 * 指定serviceImpl包名称
                 */
                .setServiceImplName("%sServiceImpl")
                /**
                 * 指定Controller包名称
                 */
                .setControllerName("%sController")
                /**
                 * 指定Dao包名称
                 */
                .setMapperName("%sDao")
                /**
                 * 指定Mapper包名称
                 */
                .setXmlName("%sMapper")
                /**
                 * 指定主键模式
                 */
                .setIdType(IdType.UUID)
                /**
                 * 生成文件后不打开文件夹
                 */
                .setOpen(false)
                /**
                 * XML ResultMap
                 */
                .setBaseResultMap(true)
                /**
                 * XML columList
                 */
                .setBaseColumnList(true);

        // 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setMapper("dao").setService("service")
                .setServiceImpl("service.impl").setController("controller").setEntity("entity");



        //5.自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String templatePath = "/templates/mapper.xml.vm"; // 如果模板引擎是 velocity
        List<FileOutConfig> focList = new ArrayList<>(); // 自定义输出配置
        focList.add(new FileOutConfig(templatePath) { // 自定义配置会被优先输出
            @Override
            public String outputFile(TableInfo tableInfo) {
                // mapper自定义输出文件名
                return path + ReturnPath +"/mapper/" + tableInfo.getEntityName() + "mapper"
                        + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);

        // 6 配置模板 自定义模板/在resources/templates 可以编辑
        TemplateConfig templateConfig = new TemplateConfig();
        // 关闭默认 xml 生成，调整生成 至 根目录
        templateConfig.setEntity("/templates/entity.java").setService("/templates/service.java")
                .setController("/templates/controller.java").setMapper("/templates/mapper.java")
                .setServiceImpl("/templates/serviceImpl.java").setXml(null);

        //7. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config).setDataSource(dataSourceConfig).setStrategy(strategyConfig).setCfg(cfg)
                .setPackageInfo(new PackageConfig()
                        .setParent(packagePath)
                        .setController("controller")
                        .setService("service")
                        .setEntity("entity")
                        .setMapper("dao")
                ).setTemplate(templateConfig);

        //8. 执行
        ag.execute();
    }















}
