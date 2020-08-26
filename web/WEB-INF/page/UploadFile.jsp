<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>文件上传</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>
 <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">文件上传</div>
          <div class="layui-card-body">
            <div class="layui-upload">
              <button type="button" class="layui-btn layui-btn-normal" id="btnGetFile">选择多文件</button> 
              <div class="layui-upload-list">
                <table class="layui-table">
                  <thead>
                    <tr><th>文件名</th>
                    <th>大小</th>
                    <th>状态</th>
                    <th>操作</th>
                  </tr></thead>
                  <tbody id="FileList"></tbody>
                </table>
              </div>
              <button type="button" class="layui-btn" id="test-upload-testListAction">开始上传</button>
            </div> 
          </div>
        </div>
      </div>
</body>
<script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>  
<script>
  layui.config({
    base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'upload'], function(){
    var $ = layui.jquery
    ,upload = layui.upload;
    //多文件列表示例
    var demoListView = $('#FileList')
    ,uploadListIns = upload.render({
      elem: '#btnGetFile'
      ,url: '${pageContext.request.contextPath}/File/UploadFiles'
      ,accept: 'file'
      ,multiple: true
      ,auto: false
      ,bindAction: '#test-upload-testListAction'
      ,choose: function(obj){   
        var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
        //读取本地文件
        obj.preview(function(index, file, result){
          var tr = $(['<tr id="upload-'+ index +'">'
            ,'<td>'+ file.name +'</td>'
            ,'<td>'+ (file.size/1024).toFixed(1) +'kb</td>'
            ,'<td>等待上传</td>'
            ,'<td>'
              ,'<button class="layui-btn layui-btn-mini test-upload-demo-reload layui-hide">重传</button>'
              ,'<button class="layui-btn layui-btn-mini layui-btn-danger test-upload-demo-delete">删除</button>'
            ,'</td>'
          ,'</tr>'].join(''));
          
          //单个重传
          tr.find('.test-upload-demo-reload').on('click', function(){
            obj.upload(index, file);
          });
          
          //删除
          tr.find('.test-upload-demo-delete').on('click', function(){
            delete files[index]; //删除对应的文件
            tr.remove();
            uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
          });
          
          demoListView.append(tr);
        });
      }
      ,done: function(res, index, upload){
        if(res.code == 0){ //上传成功
          var tr = demoListView.find('tr#upload-'+ index)
          ,tds = tr.children();
          tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
          tds.eq(3).html(''); //清空操作
          return delete this.files[index]; //删除文件队列已经上传成功的文件
        }
        this.error(index, upload);
      }
      ,error: function(index, upload){
        var tr = demoListView.find('tr#upload-'+ index)
        ,tds = tr.children();
        tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
        tds.eq(3).find('.test-upload-demo-reload').removeClass('layui-hide'); //显示重传
      }
    });
    });
   </script>
</html>