/* global Promise */

function Usuario(nombre,username,password){
    this.nombre = nombre;
    this.username = username;
    this.password = password;
}

var stompClient = null;
var tinymce;
var docName = "";
var name = "";
var username = "";
var password = "";

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    
        stompClient.subscribe("/topic/textupdate/" + docName , function (data) {
           var bm = tinymce.activeEditor.selection.getBookmark(2,true);
           tinymce.activeEditor.setContent(data.body);
           tinymce.activeEditor.selection.moveToBookmark(bm);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function requestDocName() {
    url = "/texto";
    var nombreDoc = prompt("Digite el nombre del nuevo documento");
    
    if(nombreDoc !== null && nombreDoc !== ''){
        var jsPromise = Promise.resolve($.post(url, {nombreDoc: nombreDoc}));

        jsPromise.then(function(response) {
            if(response){
                alert("Documento creado");

                docName = nombreDoc;
                crearPantallaTexto();
                console.log('Documento creado: ' + docName);
            }else{
                alert("Nombre invalido o ya existente");
            }
        });
    }else if(nombreDoc === ''){
        alert("No se ingreso nada");
    }
}

function crearPantallaTexto(){
    $("#textarea").append("<textarea id="+"test"+"></textarea>");
    $("#textarea").append("<button onclick"+"=exportar()"+">Exportar Documento</button>");
    $("#textarea").append("<button onclick"+"=lines()"+">lineas</button>");
    tinymce.init({
    selector: "textarea",
    height: 300,
    skin: 'lightgray',
        setup: function (editor) {
            editor.on('change', function () {
                url = "/texto/" + docName;
                $.post(url, {texto: tinymce.activeEditor.getContent()}, 
                    function( data ) {
                        //actualizar los suscritos
                        stompClient.send("/app/textupdate/" + docName, {}, editor.getContent());
                }).fail(
                    function(data){
                        alert("ALGO MALO PASO :( " + data);
                    }
                ); 
            editor.save();
            });
        }
    });
    //Se conecta
    connect();
    arreglarHTML();
 };
 
function getDocs(){
    
} 
 

function abrirDoc(){
    var nombreDoc = $("#abrir").val();
    url = "/texto/";
    
    if(nombreDoc !== null && nombreDoc !== ''){
        var jsPromise = Promise.resolve($.get( url + nombreDoc));
        jsPromise.then(function(response) {
            alert("Documento abierto");

            docName = nombreDoc;
            crearPantallaTexto();
            tinymce.activeEditor.setContent(response);
            //Se conecta
            connect();
            arreglarHTML();
            console.log('Documento abierto: ' + docName);
        },function() {
            alert("El documento no existe");
        });
    }else if(nombreDoc === ''){
            alert("No se ingreso nada");
    };
}

function arreglarHTML(){
    //Oculta los botones y elementos iniciales
    $("#elementos").hide();
    $("#pantallaLogin").hide();
    //Muestra el nombre del documento actual
    $("#nombreDocu").html("<h1>"+ docName +"</h1>");
}

function checkTextBoxes(){
    username = $("#username").val();
    password = $("#password").val();
    
    var userGood = false;
    var passGood = false;
    
    if(username === ''){
        $("#failUser").show();
        userGood = false;
    }else{
        $("#failUser").hide(); 
        userGood = true;
    }
    
    if(password === ''){
        $("#failPass").show();
        passGood = false;
    }else{
        $("#failPass").hide();
        passGood = true;
    }
    
    if(userGood && passGood){
        return true;
    }
    
    return false;
}

function loginUser(){
    url = "/user/login";
    
    if(checkTextBoxes()){
        //post para comprobar usuario
        $.ajax({
            url: url,
            data: {
                nombre: username,
                username: username,
                password: password
            },
            type: 'POST',
            success: function() {
                alert("Bienvenido");
                $("#pantallaLogin").hide();
                $("#seleccionDocu").show();
                window.location="docu.html";
            }
        }).fail( function(){
            alert("Datos Inválidos");
        });
    }
}

function registrerUser(){
    url = "/user/registrer";
    name = $("#justname").val();
    var nomGood = false;
    
    if(name === ''){
        $("#failName").show();
        nomGood = false;
    }else{
        $("#failName").hide(); 
        nomGood = true;
    }
    
    if(checkTextBoxes() && nomGood){
        //post para registrar usuario
        $.ajax({
            url: url,
            data: {
                nombre: name,
                username: username,
                password: password
            },
            type: 'POST',
            success: function() {
                alert("Registro completo");
                window.location="index.html";
            }
        }).fail( function(data){
            alert(data.responseText);
        });
    }
}
 
function exportar(){      
    alert(tinymce.activeEditor.getContent());
    var textToWrite = tinymce.activeEditor.getContent();
    
//  crea un Blob que es Un objeto Blob representa un objeto tipo fichero de  datos planos inmutables
    var textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});
// nombre del archivo que se va guardar 
   var fileNameToSaveAs = prompt("Digite el nombre del  documento que va a exportar");
   
// link con el que se ejecuta la acciÃ³n
    var downloadLink = document.createElement("a");
    downloadLink.download = fileNameToSaveAs;
//texto para el link de descarga 
    downloadLink.innerHTML = "My Hidden Link";
    
// permite al codigo funcionar en varios navegadores con webkit o Gecko
    window.URL = window.URL || window.webkitURL;
          
// Crea el objeto con la descarga.
    downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
// Cuando se le da click se remueve el link del documento para que se puede descargar nuevamente .
    downloadLink.onclick = destroyClickedElement;
// Se asegura que el link este escondido.
    downloadLink.style.display = "none";
// agreaga el elemento  de descarga al documento
    document.body.appendChild(downloadLink);
    
// click en el nuevo link
    downloadLink.click();
}
 
function destroyClickedElement(event){
    // remuevo el elemento del documento 
    document.body.removeChild(event.target);
}

function lines(){       
    //Cuenta las lineas que haya en el documento
    var text = document.getElementById('test');
    var cnt = (text.cols);

    var lineCount = (text.value.length / cnt);
    var lineBreaksCount = (text.value.split('\r\n'));
    alert(lineBreaksCount.length);
    alert(Math.round(lineCount)+1);
}

$(document).ready(
        
        function () {
            //connect();
            $("#seleccionDocu").show();
            $("#failName").hide(); 
            $("#failUser").hide(); 
            $("#failPass").hide();
        }
);
