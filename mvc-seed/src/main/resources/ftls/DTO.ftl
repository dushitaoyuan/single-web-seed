package ${package};

<#assign  name="${tableClass.shortClassName?substring(0,tableClass.shortClassName?lastIndexOf('DO'))}" >
import ${tableClass.fullClassName};

import lombok.Data;
@Data
public class ${name}${mapperSuffix} extends ${tableClass.shortClassName} {

}




