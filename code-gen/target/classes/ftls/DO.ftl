package ${entityFinalPackage};


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
<#if table.imports??><#list table.imports as item>import ${item};</#list></#if>

/**
*
*  @date: ${date}
*  @desc: ${table.entityComment!}
*/
@Data
@TableName("${table.tableName}")
public class ${entityFinalName}{
<#list table.entityFields as field>
    <@str value="${field.fieldComment}">
        /*
        * ${field.fieldComment}
        */
    </@str>
    <#if field.isKey??>
        @TableId("${field.columnName}")
        private ${field.javaType} ${field.fieldName};

    <#else>
        @TableField("${field.columnName}")
        private ${field.javaType} ${field.fieldName};

    </#if>
</#list>
}




