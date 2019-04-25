package com.yuan.server.util;

import org.apache.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Update;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 对象与map之间转换，方便redis存入hash中
 */
public class EntityUtil {

    static Logger logger = Logger.getLogger(EntityUtil.class);

    /**
     * 某个加了注解的所有字段
     * @param entityClass
     * @param annotationClass
     * @return
     */
    public static List<String> getAnnotationField(Class entityClass, Class annotationClass)
    {
        List<String> result = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field : fields)
        {
            if(!field.isAccessible())
                field.setAccessible(true);
            Annotation annotation = field.getAnnotation(annotationClass);
            if(annotation!=null)
                result.add(field.getName());
        }
        return result;
    }


    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
                if(value==null || value.toString().equals(""))
                    continue;
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }


    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * 将对象转换为mongodb修改时所需要的update，其中已经过滤掉了加了@Id注解的字段
     * @param t
     * @param <T>
     * @return
     */
    public static <T>Update convertMongoUpdate(T t)
    {
        Update update = new Update();
        Class _class = t.getClass();
        Field[] fields = _class.getDeclaredFields();
        for(Field field : fields)
        {
            if(!field.isAccessible())
                field.setAccessible(true);
            /**如果加了Id字段则不放入Update中*/
            Annotation annotation = field.getAnnotation(Id.class);
            if(annotation!=null)
                continue;
            String key = field.getName();
            String methodName = "get" + key.substring(0,1).toUpperCase()+key.substring(1);
            if(field.getType() == Boolean.class || field.getType() == boolean.class)
            {
                methodName = "is" + key.substring(0,1).toUpperCase()+key.substring(1);
            }
            try {
                Method method = _class.getDeclaredMethod(methodName);
                Object value = method.invoke(t);

                update.set(key, value);
            } catch (NoSuchMethodException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            } catch (InvocationTargetException e) {
                logger.error(e);
            }
        }
        return update;
    }

    /**
     * 将一个对象强转为另一个对象
     * @param oldObj
     * @param newObj
     * @param <O>
     * @param <N>
     */
    public static <O,N>void convert(O oldObj, N newObj)
    {
        Field[] newObjFields = newObj.getClass().getDeclaredFields();
        Field oldObjField;
        for(Field newObjField : newObjFields)
        {
            newObjField.setAccessible(true);
            try
            {
                oldObjField = oldObj.getClass().getDeclaredField(newObjField.getName());
                oldObjField.setAccessible(true);
                newObjField.set(newObj, oldObjField.get(oldObj));
            }catch (Exception e)
            {
                logger.error(e);
            }
        }
    }



    /**将request中的参数转换为对象*/
    public static <T>void createObjectByRequest(T t, HttpServletRequest request)
    {
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements())
        {
            Object o = enumeration.nextElement();
            String key = null;
            try {
                key = (String)o;
            }catch (Exception e)
            {
                logger.error(e);
            }
            if(key != null && !key.isEmpty())
            {
                Object originalValue = request.getParameter(key);
                Class StoryNewsClass = t.getClass();
                try {
                    Field field = StoryNewsClass.getDeclaredField(key);
                    field.setAccessible(true);
                    if(field.getType() == Integer.class || field.getType() == int.class)
                    {
                        Integer value = Integer.parseInt(originalValue.toString());
                        field.set(t, value);
                    }else if(field.getType() == Float.class || field.getType() == float.class)
                    {
                        Float value = Float.parseFloat(originalValue.toString());
                        field.set(t, value);
                    }else if(field.getType() == Double.class || field.getType() == double.class)
                    {
                        Double value = Double.parseDouble(originalValue.toString());
                        field.set(t, value);
                    }else if(field.getType() == Boolean.class || field.getType() == boolean.class)
                    {
                        Boolean value = Boolean.parseBoolean(originalValue.toString());
                        field.set(t, value);
                    }else if(field.getType() == String.class)
                    {
                        String value = (String)originalValue;
                        field.set(t, value);
                    }else if(field.getType() == Date.class)
                    {
                        String value = (String)originalValue;
                        field.set(t, DateUtils.parse(value));
                    }
                } catch (Exception e) {
                    logger.debug("key: " + key + "值强转出错！");
                    logger.info(e);
                }
            }
        }
    }
}
