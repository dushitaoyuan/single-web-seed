package ${serviceImplFinalPackage};
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taoyuanx.codegen.DTO.PageDTO;
import com.taoyuanx.codegen.vo.PageVo;
import com.taoyuanx.commons.bean.CBeanMapper;
import java.util.Objects;

import ${entityFullName};
import ${mapperFullName};
import ${serviceFullName};
import ${dtoFullName};
@Service
@Transactional
public class ${serviceImplFinalName}  extends ServiceImpl<${mapperFinalName}, ${entityFinalName}> implements ${serviceFinalName}{
    @Autowired
    ${mapperFinalName} ${mapperFinalName?uncapFirst};

    @Override
    public ${dtoFinalName} getById(Long id) {
        ${entityFinalName} ${entityFinalName?uncapFirst} = ${mapperFinalName?uncapFirst}.selectById(id);
        if (Objects.isNull(${entityFinalName?uncapFirst})) {
            return null;
        }
        return CBeanMapper.map(${entityFinalName?uncapFirst}, ${dtoFinalName}.class);
    }

    @Override
    public void delete(Long id) {
        if (count(new LambdaQueryWrapper<${entityFinalName}>().ne(${entityFinalName}::get${table.priKey.fieldName?capFirst}, id)) > 0) {
            removeById(id);
        }
    }

    @Override
    public void save(${dtoFinalName} ${dtoFinalName?uncapFirst}) {
        save(${dtoFinalName?uncapFirst});
    }

    @Override
    public void update(${dtoFinalName} ${dtoFinalName?uncapFirst}) {
        if(count(new LambdaQueryWrapper<UserDO>().ne(${entityFinalName}::get${table.priKey.fieldName?capFirst}, ${dtoFinalName?uncapFirst}.get${table.priKey.fieldName?capFirst}())) > 0) {
            updateById(${dtoFinalName?uncapFirst});
        }
     }
    @Override
    public PageDTO<${dtoFinalName}> list(PageVo<${dtoFinalName}> pageVo) {
        PageHelper.startPage(pageVo.getPageNum(), pageVo.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper(pageVo.getQuery());
        return PageDTO.githubPage((Page) list(queryWrapper));
    }

}




