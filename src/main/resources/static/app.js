/* global Promise */

var stompClient = null;
var tinymce;
var docName = "";

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
    $("#textarea").append("<textarea></textarea>");
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

function loginUser(){
    
    
    $("#pantallaLogin").hide();
    $("#seleccionDocu").show();
    //alert("login");
}
 
function saveTextAsFile()
{      
    alert(document.getElementById("cuadro").value );
    
    var textToWrite = document.getElementById("cuadro").value;
    
//  crea un Blob que es Un objeto Blob representa un objeto tipo fichero de  datos planos inmutables
    var textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});
// nombre del archivo que se va guardar 
   var fileNameToSaveAs = "myNewFile.txt";
   
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
 
function destroyClickedElement(event)
{
// remuevo el elemento del documento 
    document.body.removeChild(event.target);
}
 


$(document).ready(
        
        function () {
            //connect();
            $("#seleccionDocu").hide();
        }
);
