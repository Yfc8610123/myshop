package com.yfc.my.shop.commoms.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jackson工具类
 * <p>title:MapperUtils</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/6 21:38
*/
public class MapperUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static ObjectMapper getInstance(){
        return objectMapper;
    }

    /**
     * 对象转json字符串
     * @param object
     * @return
     * @throws Exception
     */
    public static String obj2json(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * 对象(属性中的非空)转json字符串
     * @param object
     * @return
     * @throws Exception
     */
    public static String obj2jsonIgnoreNull(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    /**
     * json转换为javaBean
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T json2pojo(String jsonString,Class<T> clazz) throws Exception {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
        return objectMapper.readValue(jsonString,clazz);
    }

    /**
     *将指定节点的json转换为javaBean
     * @param jsonString
     * @param treeNode
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T json2pojoByTree(String jsonString,String treeNode,Class<T> clazz) throws Exception{
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        JsonNode data = jsonNode.findPath(treeNode);
        return json2pojo(data.toString(),clazz);
    }
    /**
     * jsonString（属性中的非空） 转换为Map<String,Object>
     * @param jsonString
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Map<String,Object> json2mapIgnoreNull(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString,Map.class);
    }

    /**
     * json字符串转换为Map<String,T>
     * @throws Exception
     */
    public static <T> Map<String,T> json2map(String jsonString,Class<T> clazz) throws Exception {
        Map<String,Map<String,Object>> map = objectMapper.readValue(jsonString, new TypeReference<Map<String,T>>(){});
        Map<String,T> result = new HashMap<String,T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(),map2pojo(entry.getValue(),clazz));
        }
        return result;
    }

    /**
     * 深度转换json成map
     * @param json
     * @return
     * @throws Exception
     */
    public static Map<String,Object> json2mapDeeply(String json)throws Exception{
        return json2mapRecursion(json,objectMapper);
    }

    /**
     * 把json解析成list，如果list内部的元素存在jsonSting，继续解析
     * @param json
     * @param mapper
     * @return
     */
    private static List<Object> json2listRecursion(String json,ObjectMapper mapper)throws Exception{
        if(json==null){
            return null;
        }
        List<Object> list = mapper.readValue(json, List.class);
        for (Object obj : list) {
            if(obj!=null && obj instanceof String){
                String str = (String) obj;
                if(str.startsWith("[")){
                    obj=json2listRecursion(str,mapper);
                }else if(obj.toString().startsWith("{")){
                    obj=json2mapRecursion(str,mapper);
                }
            }
        }
        return list;
    }

    /**
     * 把json解析成map，如果map内部的value存在jsonSting，继续解析
     * @param json
     * @param mapper
     * @return
     * @throws Exception
     */
    private static Map<String,Object> json2mapRecursion(String json,ObjectMapper mapper)throws Exception{
        if(json==null){
            return null;
        }
        Map<String,Object> map = mapper.readValue(json, Map.class);
        for (Map.Entry<String, Object>  entry : map.entrySet()) {
            Object obj = entry.getValue();
            if(obj!=null && obj instanceof String){
                String str = (String) obj;
                if(str.startsWith("[")){
                    List<?> list = json2listRecursion(str,mapper);
                    map.put(entry.getKey(),list);
                }else if(obj.toString().startsWith("{")){
                    Map<String, Object> mapRecursion = json2mapRecursion(str, mapper);
                    map.put(entry.getKey(),mapRecursion);
                }
            }
        }
        return map;
    }

    /**
     * 将json数组转换为集合
     * @param jsonArrayStr
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> json2list(String jsonArrayStr,Class<T> clazz)throws Exception{
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> list = objectMapper.readValue(jsonArrayStr,javaType);
        return list;
    }

    /**
     * 将指定节点的json数组转换为集合
     * @param json
     * @param treeNode json中的节点
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> List<T> json2listByTree(String json,String treeNode,Class<T> clazz)throws Exception{
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode data = jsonNode.findPath(treeNode);
        return json2list(data.toString(),clazz);
    }

    /**
     * 获取泛型的collection type
     * @param collectionClass 泛型的collection
     * @param elementClasses 元素类
     * @return JavaType java类型
     * @throws Exception
     */
    public static JavaType getCollectionType(Class<?> collectionClass,Class<?>... elementClasses )throws Exception{
        return objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
    }

    /**
     * 将map转换为javaBean
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T map2pojo(Map map,Class<T> clazz){
        return objectMapper.convertValue(map,clazz);
    }

    /**
     * 将map转换为json
     * @param map
     * @return
     */
    public static String map2json(Map map){
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将json对象转换为javaBean
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2pojo(Object object,Class<T> clazz){
        return objectMapper.convertValue(object,clazz);
    }
}
