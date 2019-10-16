function getChoseDb() {
    var choseDb = localStorage.getItem("tableSchema")
    if(isNull(choseDb)){
        layer.alert('未选中数据库',function(index){
            window.location.href='/htmls/dbs.html';
            layer.close(index);
        });

    }
    return choseDb;
}

function getChoseTable() {
    var choseTable = localStorage.getItem("table");
    if(isNull(choseTable)){
        layer.alert('未选中数据表',function (index) {
            window.location.href='/htmls/tableList.html'
            layer.close(index);
        });

    }
    return choseTable;
}

function choseTable(table) {
    localStorage.setItem("table",table);
}
function choseDb(db) {
    localStorage.setItem("tableSchema",db);
}
function isNull(value) {
    if(typeof  value=='undefined'){
        return true;
    }
    if(typeof  value=='string'&&value==""){
        return true;
    }
    return !value;
}

