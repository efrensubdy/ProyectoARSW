/* global Promise */

function Usuario(username,password){
    this.username = username;
    this.password = password;
}

function Documento(nombreDoc, autor, texto){
    this.nombreDoc = nombreDoc;
    this.autor = autor;
    this.texto = texto;
}

var usuarioActual;
var documentoActual;
var stompClient = null;
var tinymce;
var docName = "";
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
    url = "/texto/addDoc/";
    var nombreDoc = prompt("Digite el nombre del nuevo documento");
    
    if(nombreDoc !== null && nombreDoc !== ''){
        var jsPromise = Promise.resolve($.post(url + nombreDoc, {autor: usuarioActual.username}));

        jsPromise.then(function(response) {
            if(response){
                alert("Documento creado");
                docName = nombreDoc;
                crearPantallaTexto();
                console.log('Documento creado: ' + docName);
                documentoActual = new Documento(nombreDoc, usuarioActual.username , "");
            }else{
                alert("Nombre invalido o ya existente");
            }
        });
    }else if(nombreDoc === ''){
        alert("No se ingreso nada");
    }
}

function crearPantallaTexto(){
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
    url = "/user/listaDocumentos/";
    var jsPromise = Promise.resolve($.get( url + usuarioActual.username ));
        jsPromise.then(function(response) {
            if(parseInt(response[0]) >= 1){
                var size = parseInt(response[0]);
                $("#listaDocumentosDisponibles").append("<p></p>Documentos Disponibles <ul>");
                for(var pos = 1 ; pos <= size ; pos++){
                    $("#listaDocumentosDisponibles").append("<li>" +response[pos]+ "</li>");
                }
                $("#listaDocumentosDisponibles").append("</ul>");
            }
        },function() {
            alert("No hay documentos");
        });
} 
 

function abrirDoc(){
    var nombreDoc = $("#abrir").val();
    url = "/texto/";
    
    if(nombreDoc !== null && nombreDoc !== ''){
        var jsPromise = Promise.resolve($.get( url + nombreDoc, {username: usuarioActual.username}));
        jsPromise.then(function(response) {
            if(response){
                var obj = jQuery.parseJSON(response);   
                documentoActual = new Documento(obj.nombreDoc, obj.autor, obj.texto);
                $("#nombreDocu").html("<h1>"+ documentoActual.nombreDoc + " - " + documentoActual.autor +"</h1>");    
                docName = nombreDoc;
                crearPantallaTexto();
                tinymce.activeEditor.setContent(documentoActual.texto);
                //Se conecta
                connect();
                arreglarHTML();
                console.log('Documento abierto: ' + docName);
            }else{
                alert("Usted no tiene permisos para abrir el documento");
            }
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
    $("#documento").show();
    //Muestra el nombre del documento actual
    $("#nombreDocu").html("<h1>"+ docName + " - " + usuarioActual.username +"</h1>");
}

function devolverInicio(){
    $("#elementos").show();
    $("#seleccionDocu").show();
    $("#documento").hide();
    $("#nombreDocu").html("<h1>Bienvenido - " + usuarioActual.username +"</h1>");
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
                username: username,
                password: password
            },
            type: 'POST',
            success: function(response) {
                if(response){
                    alert("Bienvenido");
                    usuarioActual = new Usuario(username, password);
                    $("#pantallaLogin").hide();
                    $("#seleccionDocu").show();
                    $("#nombreDocu").html("<h1>Bienvenido - " + usuarioActual.username +"</h1>");
                    getDocs();
                    //window.location="docu.html";
                }else{
                    alert("Contraseña Invalida");
                }
            }
        }).fail( function(){
            alert("Datos Inválidos");
        });
    }
}

function registrerUser(){
    url = "/user/registrer";

    if(checkTextBoxes()){
        //post para registrar usuario
        $.ajax({
            url: url,
            data: {
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

function lineas(){       
    //Cuenta las lineas que haya en el documento
    var text = document.getElementById('test');
    var cnt = (text.cols);

    var lineCount = (text.value.length / cnt);
    var lineBreaksCount = (text.value.split('\r\n'));
    alert(lineBreaksCount.length);
    alert(Math.round(lineCount)+1);
}

function compartir(){
    url = "/texto/shareDoc/" + documentoActual.nombreDoc + "/";
    var username = prompt("Digite el nombre del usuario a compartir");
    
    if(username !== null && username !== ''){
        var jsPromise = Promise.resolve($.post(url + username, {autor: usuarioActual.username}));

        jsPromise.then(function(response) {
            if(response){
                alert("Documento compartido");
                console.log('Documento compartido: ' + docName + " -> " + username);
            }else{
                alert("Nombre invalido o ya existente");
            }
        },function() {
            alert("El usuario no existe");
        });
    }else if(username === ''){
        alert("No se ingreso nada");
    }
}

$(document).ready(
        
        function () {
            //connect();
            $("#seleccionDocu").hide();
            $("#documento").hide();
            $("#failName").hide(); 
            $("#failUser").hide(); 
            $("#failPass").hide();
        }
);
