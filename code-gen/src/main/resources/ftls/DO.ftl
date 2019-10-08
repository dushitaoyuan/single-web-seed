package ${table.packageName};

import ${table.entityName};

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
<#if table.imports??>
<#list table.imports as item>
import ${item};
</#list>
</#if>
@Data
public class ${table.entityName}{
<#list table.entityFields as field>
    <#if field.fieldComment??>
        /*
         * ${field.fieldComment}
         */
    </#if>
    <#if field.isKey??>
        @TableId("${field.columnName}")
        private ${field.javaType} ${field.fieldName};
    <#else>
        @TableField("${field.columnName}")
        private ${field.javaType} ${field.fieldName};
    </#if>
</#list>
}




