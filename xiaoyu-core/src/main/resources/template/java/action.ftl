package ${model.actionPackage};
import javax.annotation.Resource;
import com.bsi.summer.action.CrudAction;
import ${table.javaBean};
import ${model.servicePackage}.${model.serviceName};
<#if table.modelPath ?exists>   
import org.apache.struts2.convention.annotation.ParentPackage;			
</#if>

/**
 * @about: ${table.modelName} action
 * @author:
 * @create: 
 */
 
 
<#if table.modelPath ?exists>   
@ParentPackage("${table.modelPath}")			
</#if>
public class ${model.actionName} extends CrudAction<${model.beanName}, ${model.serviceName}, ${model.idName}> {

	@Resource
	public void setService(${model.serviceName} service) {
		this.service = service;
	}
	
	public void set${model.beanName}(${model.beanName} ${model.beanName?uncap_first}) {
		setBean(${model.beanName?uncap_first});
	}

	public ${model.beanName} get${model.beanName}() {
		return getBean();
	}
	
	

}
