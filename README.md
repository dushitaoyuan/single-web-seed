# single-web-seed
单体种子项目

## 项目介绍
整合 mybatis-plus 和 通用mapper,另增加数据库字段自动加解密,借助mybatis typehandler实现,具体参见:
com.taoyuanx.commons.mybatis.encrypt,加密方式可自定义,默认实现为aes

springboot-seed springboot版本

springmvc 版本(最小化,其他插件自行集成)


seed-commons 公共依赖
code-gen  代码生成

## 代码生成

目前只支持mysql,其他数据库可自行编码完成
### 实现流程
1.根据数据库查询结构

2.选中数据库,选中表

3.配置代码生成相关配置,包名,实体名,列到实体属性的映射等,配置完毕保存到数据库,配置表结构为
key-value,type形式,配置key按照规则自定约定和解析,不严格来说可存储大部分配置

4.查询表结构,联合配置进行解析成 tableInfo 

5.根据freemarker模板+数据生成代码,如果需要支持多种模板,可自行实现IRender

6. 列名称到属性的映射中,全局配置高于局部配置,全局配置用于转换通用列到属性的映射,常见场景为(版号,时间,序列等)

## 核心类
ITableHandler,MysqlTableHandler,IRender,CodeGenCommonService,CodeGenController,TypeHander

TypeHander 负责进行 数据表数据类型(columnType)到 java类型 以及mybatis jdbcType的转换,用户可在
application.properties 配置 typeMapping,jdbcMapping 自定义转换
## 代码生成介绍

一款基于web的自由灵活的轻型代码生成器

## 系统截图

![avatar](https://github.com/dushitaoyuan/single-web-seed/blob/master/imgs/db.png)

![avatar](https://github.com/dushitaoyuan/single-web-seed/blob/master/imgs/table.png)



![avatar](https://github.com/dushitaoyuan/single-web-seed/blob/master/imgs/global.png)




![avatar](https://github.com/dushitaoyuan/single-web-seed/blob/master/imgs/table_edit.png)

