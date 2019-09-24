package ${package};
<#assign  name="${tableClass.shortClassName?substring(0,tableClass.shortClassName?lastIndexOf('DO'))}" >
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ${servicePackage}.${name}Service;
import ${daoPackage}.${name}Dao;
@Service
@Transactional
public class ${name}${mapperSuffix} implements ${name}Service{
    @Autowired
    ${name}Dao ${name?uncapFirst}Dao;
}




