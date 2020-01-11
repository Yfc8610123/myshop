/*
* 函数对象：别人调用不了，类似于Java私有属性
* */
var Validate=function () {

    /*
    * 初始化jquery validation
    * */
    var handlerInitValidate=function () {
        $.validator.addMethod("mobile",function(value,element){
            var length = value.length;
            var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
            return this.optional(element)||(length==11&&mobile.test(value));
        },"手机格式错误");
    };
    /**
     * 增加自定义验证规则
     */
    var handlerInitCustomValidate = function () {
        $("#inputForm").validate({
            errorElement:'span',
            errorClass:'help-block',
            errorPlacement:function(error,element){
                element.parent().parent().attr("class","form-group has-error");
                error.insertAfter(element);
            }
        });
    };
    return{
        /**
         * 初始化
         */
        init:function () {
            handlerInitValidate();
            handlerInitCustomValidate();
        }
    }
}();
$(document).ready(function () {
    Validate.init();
});
