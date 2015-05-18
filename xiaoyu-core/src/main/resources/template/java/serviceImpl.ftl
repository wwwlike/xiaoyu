package ${model.serviceImplPackage};

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bsi.summer.core.service.BaseServiceImpl;
import ${table.javaBean};
import ${model.daoPackage}.${model.daoName};
import ${model.servicePackage}.${model.serviceName};

/**
 * @about: ${table.modelName} service 实现
 * @author: 
 * @create: 
 */
@Service
public class ${model.serviceImplName} extends BaseServiceImpl<${model.beanName}, ${model.idName}, ${model.daoName}>
		implements ${model.serviceName} {

	@Resource
	public void setDao(${model.daoName} dao) {
		this.dao = dao;
	}

}
