$wnd.showcase.runAsyncCallback24("function sUb(a){this.a=a}\nfunction uUb(a){this.a=a}\nfunction wUb(a){this.a=a}\nfunction BUb(a,b){this.a=a;this.b=b}\nfunction So(a,b){a.remove(b)}\nfunction $lc(a){return Gac(),a.hb}\nfunction cmc(a,b){Xlc(a,b);So((Gac(),a.hb),b)}\nfunction xac(){var a;if(!uac||Aac()){a=new yJc;zac(a);uac=a}return uac}\nfunction Aac(){var a=$doc.cookie;if(a!=vac){vac=a;return true}else{return false}}\nfunction Bac(a){wac&&(a=encodeURIComponent(a));$doc.cookie=a+'=;expires=Fri, 02-Jan-1970 00:00:00 GMT'}\nfunction pUb(a){var b,c,d,e;if($lc(a.c).options.length<1){hoc(a.a,'');hoc(a.b,'');return}e=$lc(a.c).selectedIndex;b=_lc(a.c,e);c=(d=xac(),ofb(b==null?GEc(QJc(d.d,null)):eKc(d.e,b)));hoc(a.a,b);hoc(a.b,c)}\nfunction oUb(a,b){var c,d,e,f,g,h;eh(a.c).options.length=0;h=0;e=new VFc(xac());for(d=(g=e.a.Wh().fc(),new $Fc(g));d.a.Og();){c=(f=kfb(d.a.Pg(),36),ofb(f.ai()));Wlc(a.c,c);kCc(c,b)&&(h=eh(a.c).options.length-1)}sm((lm(),km),new BUb(a,h))}\nfunction zac(b){var c=$doc.cookie;if(c&&c!=''){var d=c.split('; ');for(var e=d.length-1;e>=0;--e){var f,g;var h=d[e].indexOf('=');if(h==-1){f=d[e];g=''}else{f=d[e].substring(0,h);g=d[e].substring(h+1)}if(wac){try{f=decodeURIComponent(f)}catch(a){}try{g=decodeURIComponent(g)}catch(a){}}b.Yh(f,g)}}}\nfunction nUb(a){var b,c,d;c=new dkc(3,3);a.c=new emc;b=new Ldc('Delete');Dh((Gac(),b.hb),DTc,true);wjc(c,0,0,'<b><b>Existing Cookies:<\\/b><\\/b>');zjc(c,0,1,a.c);zjc(c,0,2,b);a.a=new qoc;wjc(c,1,0,'<b><b>Name:<\\/b><\\/b>');zjc(c,1,1,a.a);a.b=new qoc;d=new Ldc('Set Cookie');Dh(d.hb,DTc,true);wjc(c,2,0,'<b><b>Value:<\\/b><\\/b>');zjc(c,2,1,a.b);zjc(c,2,2,d);Kh(d,new sUb(a),(Gt(),Gt(),Ft));Kh(a.c,new uUb(a),(zt(),zt(),yt));Kh(b,new wUb(a),(null,Ft));oUb(a,null);return c}\ntCb(485,1,_Pc,sUb);_.Sc=function tUb(a){var b,c,d;c=doc(this.a.a);d=doc(this.a.b);b=new aeb(RBb(XBb((new $db).q.getTime()),GUc));if(c.length<1){$wnd.alert('You must specify a cookie name');return}Cac(c,d,b);oUb(this.a,c)};var Krb=sBc(oQc,'CwCookies/1',485);tCb(486,1,aQc,uUb);_.Rc=function vUb(a){pUb(this.a)};var Lrb=sBc(oQc,'CwCookies/2',486);tCb(487,1,_Pc,wUb);_.Sc=function xUb(a){var b,c;c=eh(this.a.c).selectedIndex;if(c>-1&&c<eh(this.a.c).options.length){b=_lc(this.a.c,c);Bac(b);cmc(this.a.c,c);pUb(this.a)}};var Mrb=sBc(oQc,'CwCookies/3',487);tCb(488,1,iQc);_.Bc=function AUb(){JEb(this.b,nUb(this.a))};tCb(489,1,{},BUb);_.Dc=function CUb(){this.b<eh(this.a.c).options.length&&dmc(this.a.c,this.b);pUb(this.a)};_.b=0;var Orb=sBc(oQc,'CwCookies/5',489);var uac=null,vac;KMc(zl)(24);\n//# sourceURL=showcase-24.js\n")
