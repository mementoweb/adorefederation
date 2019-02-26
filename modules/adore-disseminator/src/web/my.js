<script language="javascript" type="text/javascript">

/* Create a new XMLHttpRequest object to talk to the Web server */
var xmlHttp = false;
/*@cc_on @*/
/*@if (@_jscript_version >= 5)
try {
  xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
} catch (e) {
  try {
    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
  } catch (e2) {
    xmlHttp = false;
  }
}
@end @*/

if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
  xmlHttp = new XMLHttpRequest();
}



function callServer() {
  // Get the city and state from the web form
  var id = document.getElementById("rft_id").value;
  var state = document.getElementById("state").value;
  // Only go on if there are values for both fields
  if ((id == null) ) return;

var url="/service?url_ver=Z39.88-2004&rft_val_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Aadore&rft_id="+escape(id)+
"&rft.version=&svc_id=info%3Alanl-repo%2Fsvc%2Ftoc.atom.srvlist";
 

  // Open a connection to the server
  xmlHttp.open("GET", url, true);

  // Setup a function for the server to run when it's done
  xmlHttp.onreadystatechange = updatePage;

  // Send the request
  xmlHttp.send(null);
}

function updatePage() {
  if (xmlHttp.readyState == 4) {
    var response = xmlHttp.responseXML;
    var srvlist = document.getElementById("srv_id");
    srvlist.innerHTML=response;
  }
}
</script>
