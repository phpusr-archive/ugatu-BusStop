// JavaScript Document

jQuery(document).ready(function(){
 if(jQuery('#s5_rightcolumn')[0]){
  jQuery('#s5_rightcolumn a.mainlevel:last').addClass('last_rht_menu_a');
 }


jQuery('#s5_rightcolumn .s5_mod_h3:first').click(function(){
 document.location.replace('index.php?option=com_content&view=category&layout=blog&id=8&Itemid=42');													
});

jQuery('#s5_rightcolumn .s5_mod_h3:eq(1)').click(function(){
 document.location.replace('index.php?option=com_content&view=category&layout=blog&id=7&Itemid=19');													
});

jQuery('#s5_leftcolumn .s5_mod_h3').click(function(){
 document.location.replace('index.php?option=com_content&view=category&layout=blog&id=3&Itemid=20');													
});

var pagenav_td = jQuery('.blog').find('.pagenav:first').parent().css({'font-size':'11px','font-family':'Tahoma'}).find('a, span').css({'font-size':'11px','font-family':'Tahoma'});

jQuery('.transMenu').css({'margin-top':-217, 'margin-left':-((jQuery(document).width()-944)/2-1)});
jQuery('#s5_leftcolumn').hover(function(){
 jQuery('.transMenu').css({'margin-left':-((jQuery(document).width()-944)/2-1)});
});

if(jQuery('#menu10').hasClass('mainlevel_active')){
 jQuery('#menu10').css({'background-image':'/templates/fusion/images/Izuminka_lft_menu_bottom_a_hover.png'});
}

jQuery('#menu a:eq(0), #menu a:eq(1), #menu a:eq(3), #menu a:eq(4), #menu a:eq(5), #menu a:eq(8)').css({'padding-top':'6px', 'height':'18px'});
});