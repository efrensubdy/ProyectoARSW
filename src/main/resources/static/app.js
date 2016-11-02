var stompClient = null;
var tinymce;

function connect() {
    var socket = new SockJS('/stompendproyecto');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/topic/TOPICOXX', function (data) {
           
            
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
function crearDocumento(){
       $("#texteditor").append("<textarea id="+"\"cuadro\""+"></textarea>");
       tinymce.init({
        selector: "textarea",
            setup: function (editor) {
                editor.on('change', function () {
                editor.save();
                });
            }
       });

 };

 
function saveTextAsFile()
{      
    alert(document.getElementById("cuadro").value );
    
    var textToWrite = document.getElementById("cuadro").value;
    
//  crea un Blob que es Un objeto Blob representa un objeto tipo fichero de  datos planos inmutables
    var textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});
// nombre del archivo que se va guardar 
   var fileNameToSaveAs = "myNewFile.txt";
   
// link con el que se ejecuta la acción
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
        }
);
