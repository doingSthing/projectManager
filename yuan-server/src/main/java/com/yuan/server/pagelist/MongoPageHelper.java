package com.yuan.server.pagelist;

import com.yuan.server.util.EntityUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 此切面可以配置全局的翻页列表
 */
@Component
@Aspect
public class MongoPageHelper {

    Logger logger = Logger.getLogger(MongoPageHelper.class);

    @Autowired(required = false)
    HttpServletRequest request;

    @Autowired
    MongoTemplate mongoTemplate;


    @Around("execution( * com.yuan.server.service.*Service.pageList*(..))")
    public PageListResult list(ProceedingJoinPoint pjp)
    {
        PageListResult pageListResult = new PageListResult();
        try {
            Class entityClass = (Class)request.getAttribute("entityClass");
            if(entityClass == null)
                return null;
            int start = 1;
            try
            {
                start = Integer.parseInt(request.getParameter("start"));
            }catch (Exception e){logger.info(e);}

            int size = 10;
            try
            {
                size = Integer.parseInt(request.getParameter("size"));
            }catch (Exception e){logger.info(e);}

            start = (start-1) * size;
            String queryStr = request.getParameter("queryStr");
            Criteria condition = (Criteria) request.getAttribute("condition");
            List data = getData(start, size, queryStr, condition, entityClass);
            long count = getCount(queryStr, condition, entityClass);
            pageListResult.setData(data);
            pageListResult.setCount(count);
        } catch (Exception e) {
            logger.info(e);
        }
        return pageListResult;
    }


    /**
     * 获取分页数据
     * @param start
     * @param size
     * @param queryStr  //前台模糊查询的字段
     * @param condition
     * @param entityClass
     * @return
     */
    public List getData(int start, int size,
                        String queryStr, Criteria condition,
                        Class entityClass)
    {
        Query query = new Query();
        query.skip(start);
        query.limit(size);
        query.with(Sort.by(
                    new Sort.Order(Sort.Direction.DESC, "updateDate"),
                    new Sort.Order(Sort.Direction.DESC, "id"),
                    new Sort.Order(Sort.Direction.DESC, "_id")
                ));
        if(queryStr==null || queryStr.isEmpty())
        {
            if(condition!=null)
                query.addCriteria(condition);
            logger.debug("PageHelper  start:......");
            logger.debug(query.getQueryObject().toJson());
            logger.debug("PageHelper  end:......");
            return mongoTemplate.find(query, entityClass);
        }
        if(condition!=null)
        {
            Criteria criteria = new Criteria();
            Criteria queryCriteria = getQuery(queryStr, entityClass);
            criteria.andOperator(condition, queryCriteria);
            query.addCriteria(criteria);
        }else
        {
            Criteria queryCriteria = getQuery(queryStr, entityClass);
            query.addCriteria(queryCriteria);
        }
        logger.debug("PageHelper  start:......");
        logger.debug(query.getQueryObject().toJson());
        logger.debug("PageHelper  end:......");
        return mongoTemplate.find(query, entityClass);
    }


    /**
     * 获取总数量
     * @param queryStr
     * @param entityClass
     * @return
     */
    public long getCount(String queryStr,
                         Criteria condition,
                         Class entityClass)
    {
        Query query = new Query();
        if(queryStr==null || queryStr.isEmpty())
        {
            if(condition!=null)
                query.addCriteria(condition);
            return mongoTemplate.count(query, entityClass);
        }

        if(condition!=null)
        {
            Criteria criteria = new Criteria();
            Criteria queryCriteria = getQuery(queryStr, entityClass);
            criteria.andOperator(condition, queryCriteria);
            query.addCriteria(criteria);
        }else
        {
            Criteria queryCriteria = getQuery(queryStr, entityClass);
            query.addCriteria(queryCriteria);
        }
        return mongoTemplate.count(query, entityClass);
    }

    /**
     * 获取模糊查询的条件
     * @param queryStr
     * @param entityClass
     * @return
     */
    public Criteria getQuery(String queryStr, Class entityClass)
    {
        Pattern pattern =Pattern.compile("^.*" + queryStr + ".*$",Pattern.CASE_INSENSITIVE);
        Criteria criteria = new Criteria();
        List<String> allField = EntityUtil.getAnnotationField(entityClass, MongoFuzzyField.class);
        if(allField!=null && !allField.isEmpty())
        {
            Criteria[] allCriteria = new Criteria[allField.size()];
            for(int i = 0; i < allField.size(); i++)
            {
                String field = allField.get(i);
                allCriteria[i] = Criteria.where(field).regex(pattern);
            }
            criteria.orOperator(allCriteria);
        }
        return criteria;
    }
}
