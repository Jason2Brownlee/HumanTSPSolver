// Place your application-specific JavaScript functions and classes here
// This file is automatically included by javascript_include_tag :defaults

/**
*
*  Javascript cookies
*  http://www.webtoolkit.info/
*
**/
function CookieHandler() {

	this.setCookie = function (name, value, seconds) {

		if (typeof(seconds) != 'undefined') {
			var date = new Date();
			date.setTime(date.getTime() + (seconds*1000));
			var expires = "; expires=" + date.toGMTString();
		}
		else {
			var expires = "";
		}

		document.cookie = name+"="+value+expires+"; path=/";
	}

	this.getCookie = function (name) {

		name = name + "=";
		var carray = document.cookie.split(';');

		for(var i=0;i < carray.length;i++) {
			var c = carray[i];
			while (c.charAt(0)==' ') c = c.substring(1,c.length);
			if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
		}

		return null;
	}

	this.deleteCookie = function (name) {
		this.setCookie(name, "", -1);
	}

}

function getUserAppletSize()
{
	var ch = new CookieHandler();	
	var applet_size = ch.getCookie('applet_size');
	
	if(applet_size == null)
	{
		applet_size = "small";			
		ch.setCookie('applet_size', size_s, 60*60*24*30);
	}
	
	return applet_size;
}

function setAppletSize()
{
	// get the size
	var size = getUserAppletSize();
	// convert to a number
	var num = userAppletSizeToActualAppletSize(size);	
	resizeApplet(num);
}


function userAppletSizeToActualAppletSize(size_s)
{
	if(size_s == "small")
	{
		return 400;
	}
	else if(size_s == "medium")
	{
		return 550;
	}
	else if(size_s == "large")
	{
		return 700;
	}
	// default
	return 400;
}

function updateAppletSize(size_s)
{
	// store the cookie
	var ch = new CookieHandler();
	ch.setCookie('applet_size', size_s, 60*60*24*30);
	
	var num = userAppletSizeToActualAppletSize(size_s);
	resizeApplet(num);
}

function resizeApplet(num)
{
	document.applet.width = num;
	document.applet.height = num;
}

