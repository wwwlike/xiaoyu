package ${model.daoImplPackage};
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import ${table.javaBean};
import com.bsi.summer.core.dao.BaseDaoImpl;
import ${model.daoPackage}.${model.daoName};

/**
 * @about: ${table.modelName} 数据访问dao接口层 实现
 * @author: 
 * @create: 
 */
 
@Repository
public class ${model.daoImplName} extends BaseDaoImpl<${model.beanName}, ${model.idName}> implements
		${model.daoName} {
		
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
