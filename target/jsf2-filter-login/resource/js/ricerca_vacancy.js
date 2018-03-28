//variabile globale di pagina che mi identifica se la mappa è mostrata o meno
var showMap = false;

minimizeMap = function() {
	fadeIn();
	document.getElementById("panelMapPlace").style.left = "235px";
	google.maps.event.trigger(maps.map_Vacancy, "resize");					
};

expandMap = function() {
	fadeOut();					      		
	document.getElementById("panelMapPlace").style.left = "10px";
	google.maps.event.trigger(maps.map_Vacancy, "resize");						
};

mrs 									= {}
mrs.setStreetPayMode 	= function(node) { mrs.pay 	= node.checked }
mrs.setPathMode				= function(node) { mrs.mode = node.value 	}

	mrs.setRadiusFilter	= function(node) { 
		var v = node.value
		var b
		var map = maps.map_Vacancy

		v = v.replace(/^\s+|\s$/g, "").replace(",", ".")
		b = !v || /^(0|[1-9][0-9]*|[0-9]+\.[0-9]+)$/.test(v) 
		
		if(b)	node.style.border = "1px solid #999999", node.style.backgroundColor = "transparent"
		else	node.style.border = "1px solid #AA0000", node.style.backgroundColor = "#ffc3ce"

		mrs.old_radius = mrs.radius							
		mrs.radius = v * 1000
	
	if(!mrs.circle) {
		var options = {
	      strokeColor: 	"#FF0000",
	      clickable:		false,
	      fillOpacity:		0, 
	      strokeOpacity:	0.8,
	      strokeWeight: 	2,
	      map: 						maps.map_Vacancy,
	      radius: 				mrs.radius
	    }
		  mrs.circle = new google.maps.Circle(options)
	}
						
	if(b && +v && mrs.address) 	mrs.circle.setOptions({ center: mrs.address, radius: mrs.radius })
	else 												mrs.circle.setOptions({ radius: 0 })
	
	if(mrs.address && mrs.circle.getBounds()) {							
		
		/*
		var lat1 = mrs.address.lat()
		var lon1 = mrs.address.lng()

		var lat2 = mrs.circle.getBounds().getNorthEast().lat()
		var lon2 = lon1 

		var R = 6371 // km
		var dLat = (lat2-lat1) * (Math.PI / 180)
		var dLon = (lon2-lon1) * (Math.PI / 180)
		lat1 = lat1 * (Math.PI / 180)
		lat2 = lat2 * (Math.PI / 180)
		
		var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2)
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
		var d = R * c		*/
		
		bbox = mrs.circle.getBounds().toUrlValue().split(",")
		var x1 = bbox[1]
		var y1 = bbox[0]
		var x2 = bbox[3]
		var y2 = bbox[2]
		/*
		filter = "CONTAINS(GEOMFROMWKT('POLYGON((" + x1 + " " + y1 + ","  
																	+ x2 + " " + y1 + "," 
																	+ x2 + " " + y2 + ","
																	+ x1 + " " + y2 + ","
																	+ x1 + " " + y1 + "))'),geometry)"*/				

		filter = "DISTANCE(geometry,GEOMFROMWKT('POINT(" + mrs.address.lng() + " " + mrs.address.lat() + ")')) < " + v / 111.12				
	}
	else 
		filter = "INCLUDE"

	if(+v && mrs.address)	map.radius_f = filter
	else									map.radius_f = ""
	
	updateFilter()														
	if(mrs.address !== mrs.old_address || mrs.radius !== mrs.old_radius) map.update()
}

generatePath = function(e, id) {

	if(!arguments.callee.__init) 
		directionsService = new google.maps.DirectionsService(),
		directionsDisplay = new google.maps.DirectionsRenderer({ map: maps.map_Vacancy }),
		arguments.callee.__init = true
	
	var r = {
		origin: 				mrs.address,
			destination: 		currentPoint,
			provideRouteAlternatives: true,      					
			travelMode: 		!mrs.mode || mrs.mode === "In auto" ? google.maps.TravelMode.DRIVING : google.maps.TravelMode.WALKING,
			avoidTolls: 		mrs.pay === true,
			avoidHighways:	false
	}

	directionsService.route(r, function(response, status) {							    
	    if(status == google.maps.DirectionsStatus.OK) 
	      directionsDisplay.setDirections(response)
				
				var ds = directionsDisplay.directions
				var rs = ds.routes[0]
				for(var d = 0, t = 0, i = 0, l = rs.legs.length; i < l; i++) 
			    d += rs.legs[i].distance.value,
			    t	+= rs.legs[i].duration.value
	      
	      d = (d / 1000)
				var mode 	= !mrs.mode || mrs.mode === "In auto" ? "in auto" : "a piedi"
				
				t = t / 60										
				t = ~~(t / 60) + "h." + (t % 60).toFixed(0) + "m"
				tt.close()
				tt.open(maps.map_Vacancy)
				tt.setPosition(currentPoint)
	      var div 			= document.getElementById("path_block" + id)
				div.innerHTML = "Tempo di percorrenza " + mode + ": " + t + "<br/>Distanza: " + d.toFixed(2).replace(".", ",") + "Km<br/><br/>"																							
	})							
}

blockSubmit = function(e) { 
	
	e 		= e || event
	var b = (e.event ? e.keyCode : e.which) !== 13 
	
	if(!b) 	e.preventDefault(), 
					e.stopPropagation()						

	return b 
}

initMap = function(map) {
	var tl 	= new google.maps.TrafficLayer()    					
var l 	= map.layersPanel

	var cb 					= document.createElement("div")
	cb.innerHTML		= "<input type=\"checkbox\">Traffico</input>"							
	cb.checked 			= false
	l.appendChild(cb) 
	
	cb.onclick = function(e) { 
		
		var node = e ? e.target : event.srcElement 
		
		if(node.checked)	tl.setMap(map)
		else							tl.setMap(null)
	}
}


updateGeoMask = function(map) {
	try {
		
		var div 		= map.getDiv()
  var w				= div.offsetWidth 
  var h 			= div.offsetHeight					      
  var bbox 		= map.getBounds() 
  var pj			= map.getProjection()					      
		var ne			= bbox.getNorthEast()
		var sw			= bbox.getSouthWest()

		var wn			= new google.maps.Point(sw.lng(), sw.lat())
		var es			= new google.maps.Point(ne.lng(), ne.lat())
		
		var deltaX	= 0.0013
		var deltaY 	= 0.00058
			
  var bbox 		= (wn.x + deltaX) + "," + (wn.y + deltaY) + "," + (es.x + deltaX) + "," + (es.y + deltaY) 	
  var url 		= "/" + contextName + "/secure/rest/geo/getPoiMask?LAYERS=Offerte&BBOX=" + bbox + "&WIDTH=" + w + "&HEIGHT=" + h 

		var offerte_f = map.radius_f || ""
		
		if(map.comuni_f && offerte_f)	offerte_f += " AND (" + map.comuni_f.replace(/denominazione/g, "comune") + ")"					
		else if(map.comuni_f)					offerte_f += map.comuni_f.replace(/denominazione/g, "comune")
		else if(!offerte_f)						offerte_f = "INCLUDE"				
  		
  url	+= "&cql_filter=" + encodeURIComponent(offerte_f.replace(/=/g, " like ").replace(/^\s+|\s+$/g, ""))	+ "&VIEWPARAMS=" + b32(map.solr_params) 	      
  $.ajax(url).complete(function(xhr) { 
		var dt, out, i
		
		//the encoding of the string is alwais utf8
		dt 	= xhr.responseText.split("")
		out = []
		
		for(var a = 0; dt[a] != null; a++)
			dt[a] = dt[a].charCodeAt(0)
			
		var b = 0
		for(a = 0; dt[a] != null; a++) {
			
			i = dt[a]
			if(i >= 128) 
				i = ((i & 31) << 6) | (dt[++a] & 63)
				
			
			out[b++] = i & 128
			out[b++] = i & 64
			out[b++] = i & 32
			out[b++] = i & 16
			out[b++] = i & 8
			out[b++] = i & 4
			out[b++] = i & 2
			out[b++] = i & 1
		}
			
  	map.geoMask = out
		/*
  //VISUAL TEST
		if(window.ps) for(a = 0; a < ps.length; a++) div.removeChild(ps[a])
		for(p = null, ps = [], y = 0; y < h; y++)
			for(x = 0; x < w; x++)	{
				if(map.geoMask[x + y * w]) {
					p = document.createElement("div")
					p.style.top = y + "px"
					p.style.left = x + "px"
					p.style.width="1px"
					p.style.height="1px"
					p.style.backgroundColor = "#880000"
					p.style.zIndex = "100"
					p.style.position="absolute"
					div.appendChild(p)
					ps.push(p)
				}
			}//END OF VISUAL TEST	*/					      
  })
	}
	catch(e) {
		console && console.error("Error(updateGeoMask): " + e)
	}
}

detectPOI = function(map, e) {
	var pt, i, gm, d, div
	
	gm 	= map.geoMask
div	= map.getDiv()

	if(gm) {
  	e.pixel.round()
		i = e.pixel.x + e.pixel.y * div.offsetWidth

		if(gm[i])	map.setOptions({ draggableCursor: "pointer" })	
		else			map.setOptions({ draggableCursor: "default" })							
	}
	else
		map.setOptions({ draggableCursor: "default" })		
}


function fadeOut() {
	$(jq("pulsante_riquadro_left")).css('display', 'inline');
	$(jq("pulsante_riquadro_right")).css('display', 'none');
	$(jq("riquadroGroup")).fadeOut('fast');
	$(jq("mapPlace")).css('width', '890px');
}			
function fadeIn() {
	$(jq("pulsante_riquadro_left")).css('display', 'none');
	$(jq("pulsante_riquadro_right")).fadeIn();
	$(jq("mapPlace")).css('width', '650px');
	$(jq("riquadroGroup")).fadeIn();
}			
function imageCandidato(id) {
	var src = id.src;
	src = src.substring(0,src.lastIndexOf('/'));
	id.src = src+"/candidato.png";
}			
function imageNonCandidato(id) {
	var src = id.src;
	src = src.substring(0,src.lastIndexOf('/'));
	id.src = src+"/nonCandidato.png";
}			


function updateFilter() {		
	var map				= maps.map_Vacancy
	var offerte_f = map.radius_f || ""
	
	if(map.comuni_f && offerte_f)	offerte_f += " AND (" + map.comuni_f.replace(/denominazione/g, "comune") + ")"					
	else if(map.comuni_f)					offerte_f += map.comuni_f.replace(/denominazione/g, "comune")
	else if(!offerte_f)						offerte_f = "INCLUDE"				
	
	map.params["Comuni_filtro"] = "cql_filter=" + (map.comuni_f || "denominazione='JS'")
	map.params["Offerte"]				= "cql_filter=" + offerte_f
}

function updateComuniFilter(ed, e) {
	var data = arguments.callee.__data
	var comuni
	var geocoder
	
	if(!data)
		data = arguments.callee.__data = { comuni:  {},  geocoder: new google.maps.Geocoder() }
	
	comuni 		= data.comuni
	geocoder 	= data.geocoder
	
	var pt = ed.responseHeader.params.pt
	geocoder.geocode({ latLng: new google.maps.LatLng(pt.split(",")[0], pt.split(",")[1]) }, function(results, status) {
  if(status == google.maps.GeocoderStatus.OK) {
    if(results[1]) 
      var r = results[1].address_components

      for(var a = 0, l = r.length; a < l; a++)
      	if(r[a].types[0] === "locality") {
						r = r[a].long_name			          		
      		break
      	}
			
			r = r	.replace(/'/g, "''")
						.replace(/à/g, "A''")
						.replace(/ì/g, "I''")
						.replace(/è/g, "E''")
						.replace(/ò/g, "O''")
						.replace(/ù/g, "U''").toUpperCase()
			
			//console && console.log("geocoder.geocode(results, status) >> found locality " + r)
			if(comuni[r])	delete comuni[r]
			else					comuni[r] = true
			
			var ps = e.map.params
			var fs = ""
			
			for(a in comuni)
				fs += "denominazione='" + a + "' OR "
				
			e.map.comuni_f = fs.slice(0, -4)
			updateFilter()
			e.map.update()			          	
  } 
  else 
    console && console.error("Error on reverse geocoding:\n" + status)
})
}
	
function callPOI(data, e) {
	
	e.pixel.round()
	if(e.map.geoMask && !e.map.geoMask[e.pixel.x + e.pixel.y * e.map.getDiv().offsetWidth]) 
		updateComuniFilter(data, e)	 
	
	else if(data.response.numFound && e.map.geoMask && e.map.geoMask[e.pixel.x + e.pixel.y * e.map.getDiv().offsetWidth])	{
		
		var n = data.response.numFound
		var s = ""
		
		for(var a = 0; a < (n < 3 ? n : 3); a++) {
			var id 				= data.response.docs[a].id_va_dati_vacancy
			var ragSoc 		= data.response.docs[a].ragione_sociale
			var attPrinc 	= data.response.docs[a].attivita_principale
			var comune 		= data.response.docs[a].comune
			var contratto = data.response.docs[a].contratto  
			
			contratto = contratto ? "<br/>Contratto:" + contratto : ""
			
			s += "<a href='/" + contextName + "/faces/secure/azienda/vacancies/visualizza.xhtml?id=" + id + "'> <b>" + attPrinc + 
					 "</b></a>&nbsp;&nbsp;&nbsp;<br/><b>" + ragSoc + "</b><br/><div class=\"testoFumetto\"><b>Località:</b>" + comune 	+ 
					 contratto + "</div><br/>"
			
 		  var dv 	= document.getElementById("address")
			var b 	= dv && dv.value === mrs.txt_addr

			s += "<div id ='path_block" + a + "'>"
											
			if(b)	s += "<a href='javascript:void(0)' onclick='generatePath(event, " + a + ")' style='text-decoration:underline;color:#40845c;float:right'>calcola percorso</a></br></br></br>"
			else	s += "<a href='javascript:void(0)' style='text-decoration:underline;color:#CCCCCC;float:right'>calcola percorso</a></br></br></br>"
			
			s += "</div>"
		}
										
		tt = new google.maps.InfoWindow({								
	        content: s + (n < 3 ? "" : "Sono stati rilevati altri risultati.</br>Aumenta il livello di zoom per migliorare il dettaglio.")
	    })

		var pt		= data.responseHeader.params.pt
		var latLng 	= new google.maps.LatLng(pt.split(",")[0], pt.split(",")[1])
		
		currentPoint = latLng
		tt.setPosition(latLng)																				
		tt.open(maps.map_Vacancy || maps.map_portlet)
		
		if(arguments.callee.__tt) arguments.callee.__tt.close()
		arguments.callee.__tt = tt
	}
}
		
/**
 * Sposto la mappa nel div iniziale per evitare che jsf sul render di ajax 
 * la cancelli dal DOM
*/		
function spostaMappa() {
}		
	
	
	
function removeFileAttach() {
    $('#confermaCandidatura\\:modal_form\\:original_file_name_dsplay').text('');
    $('#confermaCandidatura\\:modal_form\\:original_file_name').val('');
    $('#confermaCandidatura\\:modal_form\\:file_name').val('');
    $('#fileupload').val('');
    return false;
}
	
function showResponse(responseJson, statusText, xhr, $form)  {
	//console.log("responseJson:"+responseJson);
	//console.log("responseJson.statusCode:"+responseJson.statusCode);
	if(responseJson.statusCode==200){
		//console.log("responseJson:"+responseJson);
	    $('#confermaCandidatura\\:modal_form\\:original_file_name_dsplay').text(responseJson.original_file_name);
	    $('#confermaCandidatura\\:modal_form\\:original_file_name').val(responseJson.original_file_name);
	    $('#confermaCandidatura\\:modal_form\\:file_name').val(responseJson.file_name);
	} else {
		$.jnotify("Errore durante il caricamento dell'allegato:"+responseJson.error_code,"error",true);
	}			    
//    $( "#dialog_allegato" ).dialog('close');
}

var openConferma = function(params) {
	var itemId = params.id;
	var vacancyId = params.idVa;
	var clicLavoro = params.clicLavoro;
	console.log($('[id$="confermaCandidatura\:modal_form\:objectId"]'));
	$('[id$="confermaCandidatura\:modal_form\:objectId"]').val(itemId);
	$('[id$="confermaCandidatura\:modal_form\:vaId"]').val(vacancyId);
	$('[id$="confermaCandidatura\:modal_form\:clicLavoro"]').val(clicLavoro);
	if (clicLavoro) {
		$(jq("confermaCandidatura:modal_form:cv_cl_vacancy")).show();
		$(jq("confermaCandidatura:modal_form:cv_vacancy")).hide();
	}
	else {
		$(jq("confermaCandidatura:modal_form:cv_cl_vacancy")).hide();
		$(jq("confermaCandidatura:modal_form:cv_vacancy")).show();	
	}
	console.log('clicLavoro: ' + clicLavoro);
	removeFileAttach();
};

function mostraMappa() {
	showMap = true;
		
	var map = maps.map_Vacancy						
	
	if(!arguments.callee.__geo) {

		var adr = document.getElementById("address")
		var options = {
//					types: ["(cities)"],
				 	componentRestrictions: { country: "it" }
				}
	
		var auto 	= new google.maps.places.Autocomplete(adr, options)
		var mk 		= new google.maps.Marker()
		mk.setMap(map)							
		
		google.maps.event.addListener(auto, "place_changed", function() {
			var place = auto.getPlace()

			mrs.old_address = mrs.address
			mrs.address 		= place.geometry.location
			
			if(mrs.address !== mrs.old_address || mrs.radius !== mrs.old_radius) {
				if(mrs.radius && mrs.address)	map.radius_f = "DISTANCE(geometry,GEOMFROMWKT('POINT(" + mrs.address.lng() + " " + mrs.address.lat() + ")')) < " + mrs.radius / (111.12 * 1000)
				else													map.radius_f = ""
				
				updateFilter()
				map.update()					
			}

		if(window.tt) {
			var s, re, r, i

			s 	= tt.getContent()
			re 	= /style='text-decoration:underline;color:#CCCCCC;float:right'>calcola percorso<\/a><\/br><\/br><\/br>/g
			r 	= "onclick='generatePath(event, ?)' style='text-decoration:underline;color:#40845c;float:right'>calcola percorso</a></br></br></br>"
			i	= 0								
			s	= s.replace(re, function(s) { return r.replace("?", i++) })
			tt.setContent(s)
		}
			
			mrs.txt_addr 	= document.getElementById("address").value
			map.setCenter(mrs.address)
		  mk.setPosition(mrs.address)		
			mrs.circle &&	mrs.circle.setOptions({ center: mrs.address, radius: mrs.radius || 0 })							  								
		})
		
	
	}

	$(jq('form_lista_offerte:buttonMap')).hide();
	$(jq('form_lista_offerte:buttonTable')).show();
	$(jq('mapStartPlace')).css('height','');
	$(jq('riquadro_map')).fadeIn();
	$(jq('form_lista_offerte:riquadro_table')).css('display', 'none');
	$(jq('form_lista_offerte:buttonMoreBottom')).css('display', 'none');
	$(jq("form_lista_offerte:resultTop")).css('display', 'none');
	$(jq("form_lista_offerte:resultBottom")).css('display', 'none');
	
	$(jq('mapPlace')).append(riquadro_map)							
	param = $(jq("form_lista_offerte:paramSOLR")).val();
	
	if(!maps.map_Vacancy.params["Comuni_filtro"]) maps.map_Vacancy.params["Comuni_filtro"] = "cql_filter=denominazione='JS'"
	map.onCbClick = updateFilter			
	maps.map_Vacancy.solr_update(param);
	$(jq("mapPlace")).css("display", "")
	google.maps.event.trigger(map, "resize")
		
								
	if(!arguments.callee.__geo) {
			map.recenter()
			arguments.callee.__geo = true
	}

} //mostraMappa			

function mostraTabella() {
	showMap = false;

	$(jq("mapPlace")).css("display", "none")						
	
	$(jq("form_lista_offerte:riquadro_table")).fadeIn();
	$(jq("riquadro_map")).css('display', 'none');
	$(jq("form_lista_offerte:buttonTable")).css('display', 'none');
	$(jq("form_lista_offerte:buttonMap")).css('display', 'inline');
	spostaMappa();
	$(jq("form_lista_offerte:resultTop")).css('display', 'inline');
	$(jq("form_lista_offerte:resultBottom")).css('display', 'inline');
	$(jq('form_lista_offerte:buttonMoreBottom')).css('display', 'inline');
}


/**
 * Metodo agganciato all'evento ajax dei checkbox relativi ai filtri di 2° livello 
*/						
function reloadMappa(data) {
	if (data.status == 'begin') {
		if($(jq("form_lista_offerte:buttonTable")).css('display') == 'inline'){
			if(!showMap){
				showMap = true;
			}
		}else{
			if($(jq("form_lista_offerte:emptyTable")).css('display') == 'none'){
				showMap = false;
			}
		}
	}
	if (data.status == 'success') {
		window.disegnaBottoni && disegnaBottoni();
		if(showMap){
	
			if(window.tt) {
				tt.close()									
			}
			mostraMappa();							
		}
	}
}

function mostraLoaderOfferte(data) {
	if (data.status == 'begin') {
		$('#imgMore').show();
		$(jq('form_lista_offerte:buttonMoreBottom')).hide();
	}
	else if (data.status == 'success') {
		$('#imgMore').hide();
		$(jq('form_lista_offerte:buttonMoreBottom')).show();
	}
	
}

function setHiddenInputs() {
	$( "[id$=dove_hidden]" ).val($( "[id$=dove\\:inputText]" ).val());
	$( "[id$=cosa_hidden]" ).val($( "[id$=cosa\\:inputText]" ).val());
};


var riquadro_map = $(jq('riquadro_map'))
var tid = 0	//che cazzo è questa variabile?!?!?

$(function() {
	
	window.disegnaBottoni && disegnaBottoni();
	
	$(jq('pulsante_riquadro_left')).css('display', 'none');
	
	//il pannello loader parte nascosto
	$(jq('loader_lista_offerte')).css('display', 'none');
	
	$(jq("form_lista_offerte:emptyTable")).css('display', 'none');
		
/*	$('#dialog_allegato').dialog({
		width: 520,
		autoOpen: false,
		modal: true
	});*/

	var options = {
			success:       showResponse,  // post-submit callback 
			dataType:  'json'
	}; 
	
//	$('#allegatoUpload').ajaxForm(options);
});