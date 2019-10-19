package ${serviceFinalPackage};

import com.taoyuanx.codegen.DTO.PageDTO;
import ${dtoFullName};
import com.taoyuanx.codegen.vo.PageVo;
/**
*  @date: ${date}
*/
public interface ${serviceFinalName}{

    ${dtoFinalName} getById(Long id);

    void delete(Long id);

    void save(${dtoFinalName} ${dtoFinalName?uncapFirst});

    void update(${dtoFinalName} ${dtoFinalName?uncapFirst});

    PageDTO<${dtoFinalName}> list(PageVo<${dtoFinalName}> pageVo);
}




