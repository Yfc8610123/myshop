var App=function () {
    //iCheck
    var _masterCheckbox;
    var _checkbox;
    //用于存放id数组
    var _idArray;
    //默认的Dropzone参数
    var defaultDropzoneOpts ={
        url:"",
        paramName:"dropFile",
        maxFiles:1,//一次性上传的文件数量上限
        maxFilesize:2,//文件大小，单位：MB
        acceptedFiles:".jpg,.gif,.png,.jpeg",//上传的类型
        addRemoveLinks:true,
        parallelUploads:1,//一次上传的文件数量
        //previewsContainer:"#preview"，//上传图片的预览窗口
        dictDefaultMessage:"拖动文件至此或点击这里上传",
        dictMaxFilesExceeded:"您最多只能上传"+this.maxFiles+"个文件！",
        dictResponseError:"上传文件失败！",
        dictInvalidFileType:"文件类型只能为*.jpg,*.gif,*.png,*.jpeg",
        dictFallbackMessage:"浏览器不受支持",
        dictFileTooBig:"文件过大，超过文件上传最大支持",
        dictRemoveFile:"删除",
        dictCancelUpload:"取消"
    };
    /**
     * 私有方法，删除多行数据
     * @param url
     */
    var handlerDeleteMulti =function (url) {
        _idArray = new Array();
        _checkbox.each(function () {
            var _id= $(this).attr("id");
            //将选中的id放入数组
            if(_id!=null&&_id!="undefine"&&$(this).is(":checked") )
            {
                _idArray.push(_id);
            }
        });
        //判断用户是否选择了数据项
        if(_idArray.length===0){
            $("#modal-message").html("您还没有选择任何选项，请至少选择一项");
        }else {
            $("#modal-message").html("您确定要删除吗？");
        }
        //点击删除按钮时弹出模态框
        $("#modal-default").modal("show");
        //如果用户选择了数据项，给模态框的确定按钮绑定删除函数
        $("#btn_modal").bind("click",function () {
           handlerDeleteData(url);
        });
    };
    /**
     * 单个删除
     * @param url
     * @param id
     * @param msg
     */
    var handlerDeleteSingle = function(url,id,msg){
        //可选参数
        if(!msg) msg=null;
        _idArray = new Array();
        _idArray.push(id);
        $("#modal-message").html(msg==null?"您确定要删除数据项吗？":msg);
        $("#modal-default").modal("show");
        $("#btn_modal").bind("click",function () {
            handlerDeleteData(url);
        });
    };
    /**
     * Ajax异步删除
     * @param url
     * @param id
     * @param msg
     */
    var handlerDeleteData = function (url) {
        //点击确定删除按钮后隐藏模态框
        $("#modal-default").modal("hide");
        //ajax请求后台删除数据
        if(_idArray.length>0){
            setTimeout(function () {
                $.ajax({
                    "url":url,
                    "type":"POST",
                    "data":{"ids":_idArray.toString()},
                    "dataType":"JSON",
                    "success":function (data) {
                        //请求成功后，解绑模态框确定按钮点击事件
                        $("#btn_modal").unbind("click");
                        //无论删除成功与否，都弹出模态框，显示消息。当成功时刷新页面
                        if(data.status==200){
                            $("#btn_modal").bind("click",function () {
                                window.location.reload();
                            });
                        }
                        //删除失败时隐藏模态框
                        else {
                            $("#btn_modal").bind("click",function () {
                                $("#modal-default").modal("hide");
                            });
                        }
                        //显示模态框，以及后端传回来的消息。
                        $("#modal-message").html(data.message);
                        $("#modal-default").modal("show");
                    }
                });
            },500);
        }
    };
    /**
     * 初始化DataTables
      */
    var handlerInitDataTables=function (url,columns) {
        var _dataTable = $('#Tbuser_table').DataTable({
            "paging": true,
            "info": true,
            "lengthChange": false,
            "ordering": false,
            "searching": false,
            "processing": true,
            "serverSide": true,
            "deferRender": true,
            "ajax": {
                "url": url
            },
            "columns": columns,
            "language":{
                "sDecimal":        "",
                "sEmptyTable":     "空表",
                "sInfo":           "显示第_START_至_END_项结果，共_TOTAL_项",
                "sInfoEmpty":      "显示0条信息",
                "sInfoFiltered":   "有_MAX_项结果过滤",
                "sInfoPostFix":    "",
                "sThousands":      ",",
                "sLengthMenu":     "显示_MENU_项结果",
                "sLoadingRecords": "正在加载...",
                "sProcessing":     "处理中...",
                "sSearch":         "搜索:",
                "sZeroRecords":    "没有查到任何结果",
                "oPaginate": {
                    "sFirst":      "首页",
                    "sLast":       "尾页",
                    "sNext":       "下页",
                    "sPrevious":   "上页"
                },
                "oAria": {
                    "sSortAscending":  ": 升序",
                    "sSortDescending": ": 降序"
                }
            },
            "drawCallback": function( settings ) {
                handlerInitChebox();
                handlerCheckboxAll();
            }
        });
        return _dataTable;
    };
    /**
     * 私有方法,初始化iCheck
     */
    var handlerInitChebox=function () {
        //激活iChek盒子样式
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass   : 'iradio_minimal-blue'
        });
        //控制端的checkbox
        _masterCheckbox= $('input[type="checkbox"].minimal.icheck_master');
        //获取全部的chekbox集合
        _checkbox =$('input[type="checkbox"].minimal');
    };
    //全选
    var handlerCheckboxAll = function () {
        _masterCheckbox.on("ifClicked", function (e) {
            //true表示未选中状态
            if (e.target.checked) {
                _checkbox.iCheck("uncheck");
            }
            //false表示选中状态
            else {
                _checkbox.iCheck("check");
            }
        });
    };
    //初始化dropzone
    var handlerInitDropzone = function (opts) {
        //关闭Dropzone的自动发现功能
        Dropzone.autoDiscover=false;
        //继承(defaultDropzoneOptsu继承了opts)
        $.extend(defaultDropzoneOpts,opts);
        var myDropzone = new Dropzone(defaultDropzoneOpts.id,defaultDropzoneOpts

        );
    };
    /**
     * 查看详情
     * @param url
     */
    var handlerShowDetail = function (url) {
        //这里是通过Ajax请求html的方式，将jsp装载进模态框中
        $.ajax({
            url:url,
            type:"get",
            dataType:"html",
            success:function (data) {
                $("#modal-detail-body").html(data);
                $("#modal-detail").modal("show");
            }
        });
    };
    /**
     * 初始化Ztree
     * @param url
     * @param autoParam
     * @param callback
     */
    var handlerZtree = function (url,autoParam,callback) {
        var setting={
            view: {
                selectedMulti: false
            },
            async:{
                enable:true,
                url:url,
                autoParam:autoParam
            }
        };
        $.fn.zTree.init($("#myTree"), setting);
        $("#btn_modal").bind("click",function () {
            var zTree = $.fn.zTree.getZTreeObj("myTree");
            var nodes = zTree.getSelectedNodes();
            //未选择
            if(nodes.length==0){
                alert("请选择一个节点");
            }
            //已选择一个节点
            else {
                callback(nodes);
            }
        });
    };
    return{
        /**
         * 初始化
         */
        init:function(){
            handlerInitChebox();
            handlerCheckboxAll();
        },
        /**
         * 初始化批量删除
         * @returns {*}
         */
        deleteMulti:function (url) {
            handlerDeleteMulti(url);
        },
        /**
         * 初始化DataTables
         * @param url
         * @param columns
         * @returns {jQuery|*}
         */
        initDataTables:function (url,columns) {
           return handlerInitDataTables(url,columns);
        },
        /**
         *显示详情页
         * @param url
         */
        showDetail:function (url) {
            handlerShowDetail(url);
        },
        /**
         * 初始化Ztree
         * @param url
         * @param autoParam
         * @param callback
         */
        initZtree:function (url,autoParam,callback) {
            handlerZtree(url,autoParam,callback);
        },
        /**
         * 初始化Dropzone（用于给外部调用）
         * @param opts
         */
        initDropzone:function (opts) {
            handlerInitDropzone(opts);
        },
        /**
         * 单个删除
         * @param url
         * @param id
         * @param msg
         */
        deleteSingle:function (url,id,msg) {
            handlerDeleteSingle(url,id,msg);
        }

    }
}();

$(document).ready(function () {
    App.init();
});