/**************************************************************************************** 
 Copyright © 2014-2018 Changsha ZTEsoft Corporation. All rights reserved. Reproduction or <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.ztesoft.zsmart.oss.opb.itf.rest;

import java.util.HashMap;
import java.util.List;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import com.ztesoft.zsmart.core.exception.BaseAppException;
import com.ztesoft.zsmart.oss.opb.itf.dao.ItfConfigDataDAO;
import com.ztesoft.zsmart.oss.opb.util.GeneralDAOFactory;
import com.ztesoft.zsmart.oss.opb.util.JdbcUtil;
import com.ztesoft.zsmart.sso.utils.StringUtil;


/** 
 * Description: <br> 
 *  
 * @author bluewind<br>
 * @version 8.0<br>
 * @taskId <br>
 * @CreateDate 2016年4月1日 <br>
 * @since V8<br>
 * @see com.ztesoft.zsmart.oss.opb.itf.rest <br>
 */

public class RestService extends ResourceConfig {
    
    /**
     * DEscription: <br>
     * 
     * @author bluewind <br>
     * @version 8.0 <br>
     * @throws BaseAppException <br>
     * @CreateDate 2016年4月1日 <br>
     * 
     * @since V8<br>
     */
    public RestService() throws BaseAppException {
        //服务类所在的包路径， 注册opb公共资源包
        String sResource = "com.ztesoft.zsmart.oss.opb.itf.rest.resource";
        packages(sResource);
        
        //查询是否存在不同的资源包，如果有，就把所有的资源包都注册上去
        ItfConfigDataDAO configDao = (ItfConfigDataDAO) GeneralDAOFactory.create(ItfConfigDataDAO.class, 
            JdbcUtil.getDbIdentifier(JdbcUtil.OSS_OPB));
        
        List<HashMap<String, String>> lstResource = configDao.selectRestResourceByAll();
        
        if (null != lstResource) {
            HashMap<String, String> has = null;
            for (int i = lstResource.size() - 1; i >= 0; i--) {
                has = lstResource.get(i);
                sResource = has.get("RESOURCE_PKG");
                if (!StringUtil.isEmpty(sResource)) {
                    packages(sResource);
                }
            }
        }
        
        
        // 注册Json转换器
        register(JacksonJsonProvider.class);
    }

}
