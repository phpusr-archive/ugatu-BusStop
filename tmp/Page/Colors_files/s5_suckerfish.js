//set active links for level 0
	var s5_fm_li2 = document.getElementById("s5_navv").getElementsByTagName("LI");
	var z2;
	var cc2 = 1;
	for (var z2=0; z2<s5_fm_li2.length; z2++) {
		if (s5_fm_li2[z2].className == "active") {
			s5_fm_li2[z2].parentNode.parentNode.className = "active";
			s5_fm_li2[z2].parentNode.parentNode.parentNode.parentNode.className = "active";
		}
	}
	
	

	
