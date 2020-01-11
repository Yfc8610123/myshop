package com.yfc.my.shop.commoms.validator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.*;
/**
 * JSR303 Validator(Hibernate Validator)工具类
 * <p>title:BeanValidator</p>
 * <p>description:</p>
 * 
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/1 18:28
*/
public class BeanValidator {

    public static Validator validator;

    public static void setValidator(Validator validator) {
        BeanValidator.validator = validator;
    }

    /**
     * 调用JSR303的validate方法，验证失败时抛出ConstraintViolationException
     * @param validator
     * @param object
     * @param groups
     */
    private static void validateWithException(Validator validator,Object object,Class<?>... groups) throws ConstraintViolationException
    {
        Set constraintViolations = validator.validate(object,groups);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    /**
     * 辅助方法，转换ConstraintViolationException中的Set<constranintViolations> 为List<message>
     * @param e
     * @return
     */
    private static List<String> extractMessage(ConstraintViolationException e){
        return extractMessage(e.getConstraintViolations());
    }

    /**
     * 辅助方法,转换Set<constranintViolations> 为List<message>
     * @param constraintViolations
     * @return
     */
    private static List<String> extractMessage(Set<? extends ConstraintViolation> constraintViolations){
        List<String> errortMessages = new ArrayList<>();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            errortMessages.add(constraintViolation.getMessage());
        }
        return errortMessages;
    }
    /**
     * 辅助方法，转换ConstraintViolationException中的Set<constranintViolations> 为Map<property,message>
     * @param e
     * @return
     */
    private static Map<String,String> extractPropertyAndMessage(ConstraintViolationException e){
        return extractPropertyAndMessage(e.getConstraintViolations());
    }
    /**
     * 辅助方法,转换Set<constranintViolations> 为Map<property,message>
     * @param constraintViolations
     * @return
     */
    private static Map<String,String> extractPropertyAndMessage(Set<? extends ConstraintViolation> constraintViolations){
        Map<String,String> errortMessages= new HashMap<>();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            errortMessages.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
        }
        return errortMessages;
    }
    /**
     * 辅助方法，转换ConstraintViolationException中的Set<constranintViolations>转化为 List<propertyPath message>
     * @param e
     * @return
     */
    private static List<String> extractPropertyAndMessageAsList(ConstraintViolationException e){
        return extractPropertyAndMessageAsList(e.getConstraintViolations(),"");
    }
    /**
     * 辅助方法,转换Set<constranintViolations> 为List<propertyPath message>
     * @param constraintViolations
     * @return
     */
    private static List<String> extractPropertyAndMessageAsList(Set<? extends ConstraintViolation> constraintViolations){
        return extractPropertyAndMessageAsList(constraintViolations,"");
    }
    /**
     * 辅助方法，转换ConstraintViolationException中的Set<constranintViolations>转化为 List<propertyPath+separator+message>
     * @param e
     * @return
     */
    private static List<String> extractPropertyAndMessageAsList(ConstraintViolationException e,String separator){
        return extractPropertyAndMessageAsList(e.getConstraintViolations(),"");
    }

    /**
     * 辅助方法,转换Set<constranintViolations> 为List<propertyPath+separator+message>
     * @param constraintViolations
     * @param separator
     * @return
     */
    private static List<String> extractPropertyAndMessageAsList( Set<? extends ConstraintViolation> constraintViolations,String separator){
        List<String> errortMessages = new ArrayList<>();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            errortMessages.add(constraintViolation.getPropertyPath()+separator+constraintViolation.getMessage());
        }
        return errortMessages;
    }

    /**
     * 服务端参数有效性验证
     * @param object 验证的对象
     * @param groups 验证组
     * @return 验证成功，放回null，验证失败，返回错误信息
     */
    public static String validator(Object object,Class<?>... groups){
        try {
            validateWithException(validator,object,groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = extractMessage(ex);
            list.add(0,"数据验证失败：");
            //封装错误消息为字符串
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<list.size();i++){
                String exMsg = list.get(i);
                if(i!=0){
                    sb.append(String.format("%s%s",i,exMsg)).append(list.size()>1?"<br/>":"");
                }else {
                    sb.append(exMsg).append(list.size()>1?"<br/>":"");
                }
            }
            return sb.toString();
        }
        return null;
    }
}
