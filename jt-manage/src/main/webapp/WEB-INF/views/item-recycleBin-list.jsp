<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<table class="easyui-datagrid" id="itemList" title="商品列表"
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/item/queryDeletedItems',method:'post',pageSize:20,toolbar:toolbar">

    <thead>
    <div id="tb" style="padding:3px">
        <span>商品ID:</span>
        <input id="itemid" class="easyui-input" style="line-height:24.0208px;">
        <span>商品标题:</span>
        <input id="title" class="easyui-input" style="line-height:24.0208px;">
        <%--<span >状态:</span>
        <input id="status" class="easyui-combobox" style="width: 100px;height: 26px;" data-options="
		valueField: 'label',
		textField: 'value',
		data: [{
			label: '1',
			value: '正常'
		},{
			label: '2',
			value: '下架'
		},{
			label: '3',
			value: '删除'
		},{
			label: '4',
			value: '未知'
		}]" />--%>
        <span >删除时间:</span>
        <input id="startTime" class="easyui-datebox" sharedCalendar="#sc" style="width: 100px;height:26px;">
        --
        <input id="endTime" class="easyui-datebox" sharedCalendar="#sc" style="width: 100px;height:26px;">
        <div id="sc" class="easyui-calendar"></div>
        <button id="deletedItems" href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="doSearchDeletedItems()">查询</button>
    </div>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'id',width:60">商品ID</th>
        <th data-options="field:'title',width:200">商品标题</th>
        <th data-options="field:'cid',width:100,align:'center',formatter:KindEditorUtil.findItemName">叶子类目</th>
        <th data-options="field:'sellPoint',width:100">卖点</th>
        <th data-options="field:'price',width:70,align:'right',formatter:KindEditorUtil.formatPrice">价格</th>
        <th data-options="field:'num',width:70,align:'right'">库存数量</th>
        <th data-options="field:'barcode',width:100">条形码</th>
        <th data-options="field:'status',width:60,align:'center',formatter:KindEditorUtil.formatItemStatus">状态</th>
        <th data-options="field:'created',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">创建日期</th>
        <th data-options="field:'updated',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">删除日期</th>
    </tr>
    </thead>
</table>

<div id="itemEditWindow" class="easyui-window" title="编辑商品"
     data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/item-edit'"
     style="width:80%;height:80%;padding:10px;">
</div>
<script>

    //查询事件
    function doSearchDeletedItems() {
        $('#itemList').datagrid('load', {
            id: $('#itemid').val(),
            title: $('#title').val(),
            /*status: $('#status').textbox('getValue'),*/
            startTime: $('#startTime').textbox('getValue'),
            endTime: $('#endTime').textbox('getValue')
        });
    }

    function getSelectionsIds() {
        var itemList = $("#itemList");
        var sels = itemList.datagrid("getSelections");
        var ids = [];
        for (var i in sels) {
            ids.push(sels[i].id);
        }
        ids = ids.join(",");
        return ids;
    }

    var toolbar = [{
        text: '删除',
        iconCls: 'icon-cancel',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中商品!');
                return;
            }
            $.messager.confirm('确认', '删除后无法恢复，确定删除ID为 ' + ids + ' 的商品吗？', function (r) {
                if (r) {
                    var params = {"ids": ids};
                    $.post("/item/recycleBin/delete", params, function (data) {
                        if (data.status == 200) {
                            $.messager.alert('提示', '删除商品成功!', undefined, function () {
                                $("#itemList").datagrid("reload");
                            });
                        } else {
                            $.messager.alert("提示", data.msg);
                        }
                    });
                }
            });
        }
    }, '-', {
        text: '恢复',
        iconCls: 'icon-remove',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中商品!');
                return;
            }
            $.messager.confirm('确认', '确定恢复ID为 ' + ids + ' 的商品吗？', function (r) {
                if (r) {
                    var params = {"ids": ids};
                    $.post("/item/regain", params, function (data) {
                        if (data.status == 200) {
                            $.messager.alert('提示', '恢复商品成功!', undefined, function () {
                                $("#itemList").datagrid("reload");
                            });
                        } else {
                            $.messager.alert('提示', data.msg)
                        }
                    });
                }
            });
        }
    }];
</script>