/**
 * Created by 13 on 2017/2/22.
 */
// Tags Input
$('#tags').tagsInput({
    width: '100%',
    height: '35px',
    defaultText: '请输入文章标签'
});

$('.toggle').toggles({
    on: true,
    text: {
        on: '开启',
        off: '关闭'
    }
});

$(".select2").select2({
    width: '100%'
});

var tale = new $.tale();

/**
 * 保存城市
 * @param status
 */
function subCity(status) {
    var title = $('#articleForm input[name=name]').val();
    var content = $('#text').val();
    if (title === '') {
        tale.alertWarn('请输入城市名称');
        return;
    }
    if (content === '') {
        tale.alertWarn('请输入城市简介');
        return;
    }
    $('#content-editor').val(content);
    // $("#articleForm #status").val(status);
    $("#articleForm #categories").val($('#multiple-sel').val());
    var params = $("#articleForm").serialize();
    var url = $('#articleForm #cid').val() != '' ? '/admin/city/modify' : '/admin/city/publish';

    console.log(url);

    tale.post({
        url:url,
        data:params,
        success: function (result) {
            if (result && result.status === 200) {
                tale.alertOk({
                    text:'城市保存成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/admin/city';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '保存城市失败');
            }
        }
    });
}

/**
 * 保存省份
 * @param status
 */
function subProvince(status) {
    var title = $('#articleForm input[name=name]').val();
    var reason = $('#articleForm input[name=reason]').val();
    var content = $('#text').val();

    if (title === '') {
        tale.alertWarn('请输入省份名称');
        return;
    }
    if (reason === '') {
        tale.alertWarn('请输入省份名称由来');
        return;
    }
    if (content === '') {
        tale.alertWarn('请输入省份简介');
        return;
    }

    $('#content-editor').val(content);
    // $("#articleForm #status").val(status);
    $("#articleForm #categories").val($('#multiple-sel').val());
    var params = $("#articleForm").serialize();
    var url = $('#articleForm #cid').val() != '' ? '/admin/province/modify' : '/admin/province/publish';

    console.log(url);

    tale.post({
        url:url,
        data:params,
        success: function (result) {
            if (result && result.status === 200) {
                tale.alertOk({
                    text:'省份保存成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/admin/province';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '保存省份失败');
            }
        }
    });
}

var textarea = $('#text'),
    toolbar = $('<div class="markdown-editor" id="md-button-bar" />').insertBefore(textarea.parent())
preview = $('<div id="md-preview" class="md-hidetab" />').insertAfter('.markdown-editor');

markdown(textarea, toolbar, preview);


function allow_comment(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_comment').val(false);
    }
    if (off == 1) {
        $('#allow_comment').val(true);
    }
}

function allow_ping(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_ping').val(false);
    }
    if (off == 1) {
        $('#allow_ping').val(true);
    }
}


function allow_feed(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_feed').val(false);
    }
    if (off == 1) {
        $('#allow_feed').val(true);
    }
}

$('div.allow-false').toggles({
    off: true,
    text: {
        on: '开启',
        off: '关闭'
    }
});