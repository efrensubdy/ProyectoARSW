var stompClient = null;
var tinymce;
var docName = null;

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
       console.log('Connected: ' + frame);
        
        stompClient.subscribe("/topic/textupdate/{docName}", function (data) {
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
    docName = prompt("Ingrese el nombre del nuevo documento", "");
}

function nuevoDoc(){
    requestDocName();
    crearDocumento();
}

function crearDocumento(){
        
        $("#textarea").append("<textarea></textarea>");
        tinymce.init({
        selector: "textarea",
        height: 300,
        skin: 'lightgray',
            setup: function (editor) {
                editor.on('change', function () {
                    
                    url = "http://localhost:8080/texto/" + docName;
                    $.post(url, {texto: tinymce.activeEditor.getContent()}, 
                        function( data ) {
                            //actualizar los suscritos
                            stompClient.send("/topic/textupdate/{docName}", {}, editor.getContent());
                    }).fail(
                        function(data){
                            alert("ALGO MALO PASO :( " + data);
                        }
                    ); 
                editor.save();
                });
            }
       });
       
       url = "http://localhost:8080/texto";
        $.post(url, {nombreDoc: docName}, 
            function( data ) {
                alert("Documento Creado");
        }).fail(
            function(data){
                alert("ALGO MALO PASO :( " + data);
            }
        ); 
       
       //stompClient.send("/texto/" + docName, {}, $("#textarea").val());
 };
function enviarTexto(){
    
    
    
};

function abrirDoc(){
    var nombreDoc = $("#abrir").val();
    docName = nombreDoc;
    
    if(docName !== null){
        $.get( "/texto/"+docName, function( data ) {
                crearDocumento();
                tinymce.activeEditor.setContent(data);
            }    
        ).fail(
            function(data){
                alert(data["responseText"]);
            }
        ); 
    }
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
            connect();
        }
);
