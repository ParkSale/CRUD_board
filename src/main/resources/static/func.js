function logout(){
    $("#pop").modal();
    document.getElementById("y").onclick = function(){
        window.location.href="/logout";
    }
    document.getElementById("n").onclick = function(){
        $("#pop").modal("hide");
    }
}

function fileChk(input) {
    path = input.value.lastIndexOf('.');
    filePoint = input.value.substring(path+1,input.length);
    type = filePoint.toLowerCase();
    if(!(type == 'jpg' || type == 'gif' || type == 'png' || type == 'jpeg')){
        alert("jpg, gif, png, jpeg중 하나를 업로드해주세요!");
        e.value = '';
    }
    else{
        var reader = new FileReader();
        reader.onload = function(e){
            $('#imagePreview').attr('src',e.target.result);
            document.getElementById('imagePreview').style.display = "block";
        }
        reader.readAsDataURL(input.files[0]);
        console.log(input.files[0]);
    }

}
function nextGo(){
    param = document.location.href.split('?')[1];
    page = parseInt(document.location.href.split('/')[5]) + 1;
    location.replace("/posts/search/" + page + '?' + param);
}
function prevGo(){
    param = document.location.href.split('?')[1];
    page = parseInt(document.location.href.split('/')[5]) - 1;
    location.replace("/posts/search/" + page + '?' + param);
}