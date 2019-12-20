layui.config({
    base: './js/',
    version:new Date().getTime()
}).use(['table', 'layer', 'cookie','jquery'], function() {
    var

        $ = layui.jquery,
        layer = layui.layer,
        cookie=layui.cookie,
        table = layui.table;

    var job_number=$.cookie("cookieName");

    table.render({
        elem: '#test',
        url: '/apply/pageWithDepartSuggestAndResult?job_number='+job_number,
        cols: [
            [ {
                field: 'title',

                title: '会议名称'
            }, {
                field: 'startTime',

                title: '开始时间'
            }, {
                field: 'endTime',

                title: '结束时间'
            },
                {
                    field:'roomIdent',
                    title:'会议室名称'
                },
                 {
                field: 'depart_suggest',
                title: '申请部门负责人意见',
                templet: function (d) {
                    console.log(d);
                    if (d.departSuggest === 0) {
                        return '<a class="" href="javascript:void(0);" style="color: #1E9FFF">审核中 </a>';
                    } else if (d.departSuggest === 1) {
                        return '<a class="" href="javascript:void(0);" style="color: #4CAF50">同意</a>';
                        // return "<span style='color: #1b7e5a'>申请通过</span>";
                    } else if (d.departSuggest === 2) {
                        return '<a class="" href="javascript:void(0);" style="color: #FD482C">不同意 </a>';
                    }
                }
            }
                 ,
                {
                field:'result',
                title:'申请结果',
                templet: function(d){
                    console.log(d);
                    if(d.result  === 0){
                        return  '<a class="" href="javascript:void(0);" style="color: #1E9FFF">申请中 </a>';
                    }else if(d.result ===1){
                        return  '<a class="" href="javascript:void(0);" style="color: #4CAF50">申请通过 </a>';
                        // return "<span style='color: #1b7e5a'>申请通过</span>";
                    }else if(d.result  ===2){
                        return  '<a class="" href="javascript:void(0);" style="color: #FD482C">申请未通过 </a>';
                    }
                }
            }
            //     ,{
            //     fixed: 'right', width:178, align:'center', toolbar: '#barDemo'
            // }
            ]
        ]
        ,page: true
        ,limit:30
    });

    //监听工具条
    table.on('tool(demo)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            layer.msg('ID：'+ data.id + ' 的查看操作');
            location.href="/room/opk/examples/selectable.html?roomId="+data.id;
        } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            layer.alert('编辑行：<br>'+ JSON.stringify(data))
        }
    });
});