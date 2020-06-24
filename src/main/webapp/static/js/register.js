$(function () {
    $('#loginBtn').click(function () {
        location.href = cwh.getProjectUrl()+'page/login.jsp';
    });
    //账号验证
    jQuery.validator.addMethod('account',function(value,element){
        var bol = true;
        //通过账号查询数据库中是否存在相同的账号名
        cwh.asyncGet('/power/user/byAccount', {account:value}, function(data) {
            if (data)bol=false;
        })
        return bol;
    },'用户已存在');
    //验证密码
    jQuery.validator.addMethod('password',function(value,element){
        var tel = /^[a-zA-Z]\w{5,16}$/;
        return this.optional (element) || (tel.test(value));
    },'密码格式错误');
    //验证手机号码
    jQuery.validator.addMethod("phone", function(value, element) {
        var tel = /^1([38]\d|5[0-35-9]|7[3678])\d{8}$/;
        return this.optional(element) || (tel.test(value));
        //校验失败提示信息
    }, "请输入正确的手机号");
    //表单验证
    $('#register').validate({
        rules:{
            account:{
                required:true,
                account:true,
            },
            password:{
                required:true,
                password:true,
            },
            email:{
                required:true,
                email:true,
            }, phone:{
                required:true,
                phone:true,
            }
        },
        messages:{
            account:{
                required:'请输入账号',
            },
            password:{
                required:'请输入密码',
            },
            email:{
                required:'请输入邮箱',
                email:'请输入正确的邮箱',
            },
            phone:{
                required:'请输入手机号',
                phone:'请输入正确的手机号',
            }
        },
        submitHandler:function(form){
            //saveOrUpdate(保存用户信息)
            var serializeForm = cwh.serializeForm('register');
            cwh.asyncPostOrPut('/power/user/register',"POST",serializeForm,function (data) {
                if (data.result == 0){
                    $('input[name="email"]').val();
                    cwh.clearForm('register');
                    cwh.cwhConfirm(function () {
                        location.href = cwh.getProjectUrl()+'/page/login.jsp';
                    },data.msg,"去登陆吗?",'Go!');

                }else{
                    cwh.error(data.msg);
                }
            })
        }
    },);
});