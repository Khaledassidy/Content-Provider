package com.example.contentproviders;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contentproviders.databinding.ActivityMainBinding;
import com.example.contentproviders.foodadmin.RecycleAddapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
/*
la7 ne7ke l youm 3an 3osnour rabe3 mn 3anser l android l asaseye le howe l content providers ya3ne mozawed l mo7tawa
7kena abl heek 3an l activity w servies ,broadcast la7 ne7ke hala2 3an l content providers

content providers:badna na3ref sho l fekra meno? ween byest5dam? keef momken est5dmo ? keef momken estafed meno b tare2a fa3ale?

contentProviders 2aw mozawed l mo7tawa:bts5dam lal wosoul lal data l m5azane b applications tenye keef ya3ne?

3ana applications mawjoude mosba2an btkoun 3ala l device applications 7ada 3emela 2aw ne7na 3emlena bekoun feha data m3yane msln metl application l contact fe data l asme2 w l phone number application l contact fe databse lal asme2 kel esm elo bayent m3yane w elo majmo3et 2ar2am elo soura..ect
hala2 ana law bade 2a3mel application metl l truecaller ,wattsap bado yet3emal ma3 hawde l asme2 bado yo3red l asme2 3ando bel application hal lezm erja3 3eed ketebet l asme2 mn sefr 2aw hal lezm le bado yest5dem l application yrou7 ye2ra l asme2 yadaweyan mn application l asme2  w yda5eloun yadaweyan b application le 3am 2a3mlo le howe metl l truecaller?
la2 ne7na mnla7ez l messages w l asme2 btente2l telk2yan la application l truecaller mojarad ma nazlo wefe2 3ala sala7eyet
l fekra btseer eno application  l truecaller byosal la asme2 l mawjoude b application l contact w l messages le mawjoude b application l messages le bokouno already mawjouden 3ala l device byosal lal data le fehoun w bye5edoun barmajeyan w be5zenoun 3ando
hal2 keef bye5edoun?
bye5edoun 3an tare2 l content provider
l example heda ya3ne byeshra7 eno ana bade 2osal la baynet mawjoude b applications w 2e5oda 3ande bel application ta3ele badel ma ana yadaweyan e5oda w sajela

momken l content provider ykoun bye7tewe 3ala wejhat metl ma mnla7ez b application l truecaller lama teje ta3mel edafet esem jdeed 2aw ya3ne conatct jdeed kamen metl watsaap fa be7wlak 3ala shshet l edafe le asln mawjoude b application l contact k2nak ro7 3ala application l asme2 fata7to w 3mlt edafe fa howe bye3tabar l contact provider waset baynak w been application l cotact,2aw l message , 2aw l gallary le mawjoude 3ala device
fa howe bshakel api wejha 2aw 7ala2et wasl ma benak w been l application le badak test7b meno data hala2 heda l contect provider bshakl mo5tasar eno howe 3ebra 3an 7ala2et wasl le mn 5elela ana be2dar 2osal la applications le nezle 3ala device w e5od mena l data le bade yeha bas l contact provider heda le 3emel l application nafso le fe data 3emel fe contant provider 3ashn 2e2dar e5od l data meno

tyeb lesh ana bade rou7 2a3mel kol hay w rou7 2a3ml contant provider ma ana mn l application ta3ele momken 2osal la databse mobasharatan le mawjoude b application l contact?

alak la2 l2no 3ashen mabede2 security 3ashen y7a2e2 security b application l contact msln 2aw application l messages ma be2dar 2osal lal databse mobashar 3ashn ma bade2 secrity 3ashn mskn ma trou7 enta te2dar te7zef table taba3 application l contact fa yrou7 yle2e application l asme2 mdamar w raye7 meno kel l contact kamen application l messages nafs she ma bte2dar trou7 te7zef databse kola
fa alak ne7na 3ashn mabda2 security fa ne7na mnkayed wosolak la databse mn 5elel waset le howe l contant provider fa enta ka programmer badak ta3mel application besm7lk tosal lal asme2 l contact le 3ande 2aw rase2el le 3ande 2aw la aye data tenye bas mesh tosal la database mobashar la2 tosal lal waset le howe l api l contant provider le mn5elelo totlob le badak fa yejblak yeh mn database 7a ykoun l application l howe l contact le mawjoud b device fe databse w content provider w l developer le 3emel application l contant 7at bel contant provider eno enta masmo7lak tosal la table le esmo kaza w kaza w kaza w masmo7lak te5od menou l data l folaneye w masmo7lak ta3mel l 3mlyet l folaneye  mesh masmo7lak aktar mn heek fa howe 7adadlak sala7eyt l 3amal ma3 l bayanet hay fekret mozawedet l mo7tawa 2aw l content provider
fa heye 3ebara 3an api 2aw waset local ma7ale 3ala l application mn 5elelo enta bte2dar ta3mel application yosal la data tenye w yes7ab l data tenye le enta masmo7lak tes7bha 2aw le sam7lak developer taba3 application l contact enak tosal la hay data
7kena eno akbar example howe application taba3 l contact,messages momken ykoun fe content provider fa bte2dar tet3emal ma3o mn 5elel l content provider
fa ana law bade 2a3mel application metl application l truecaller be2dar bkel basata barmajeyan 2osal lal content provider taba3 l application l contect w es7abb meno kel l asme2 w phone number w l ma3lomet ta3etha 3ala application taba3e w kamen bte2dar tes7ab kel l message le mawjoude 3a application l messge



hala2 3ashn net3emal ma3 l content provider le howe asln bery7na badel ma nrou7 nenshe2 mo7tawa mn sefr w metl ma 2olna momken ykoun bye7tewe 3ala wejhat b ma3na eno enta badal ma ta3mel edafet esm 3ala application ta3elak la2 bas badak ta3mel edafet esm betl3lak wejha ta3et application l contact w bseer mobashar bdeef 3ala database ta3et application l contact w bteje elak l data 3an tare2 l content provider la appliction ta3elak


keef momken et3emal ma3 l content provider?

3ala mostawayen:
1-eno enta ykoun 3andak application w enta badak tee7 bayanet ta3elo la application tenye fa enta badak tenshe2 mozawed mo7tawa 2aw content provider 3ashn te2dar test5dmo application tenye metl ma 3mlo developer ta3wlet application l contact

2-eno enta test5dem l content provider taba3 l applications tenye

ya3ne l conetnt provider howe 3onsour le betwer l application bya3mlo b7eto bel application enta momken ta3mel wa7ad 2aw test5dem wa7ad

fa ne7na 7a na3mel example eno nest5dem l content provider taba3 l asme2 w ne3red bel application ta3ele kel l asme2 kel l phone number b recycele view w heda nafs she byenteb2 3ala l messages w 3ala l dictionary kel l application le fehoun content provider


hala2 3ashn et3emal ma3 l content provider fe 3ande path:

||:ya3ne be2tejahen aysncronize ya3ne raw7a w raj3a first direction w second direction


1-2awl she bekoun 3ande Activity or Fragment feha recleview bade 2o3roud feha l asme2
                               ||
                               ||
2-tene she bade et3mela ma3she esmo l cursor loader heda 3ebra 3ashn l content provider te2ra meno asme2 bye7tej wa2t fa l wa2t heda 3ashn l content provider tet3emal ma3o w ma y3tel l thread le heye recyele view mawjoud feha lezm t7oto msln b thread monfsle ,2aw async task..etc fa l cursor loader 3ebra class mose3ed bese3ed 3ashn tjeeb l bayanet w ma t2ser 3ala l activity le enta mawjoud feha w t3tela tab3an l cursorloader momken test5dmo w momken ma test5dmo
                               ||
                               ||
3-telet she bade et3emal ma3 l contentresolver  howe l class le ana bet3emal ma3o 3ashn 2e2ra l data mn  content provider ya3ne ana 3ashn 2osal lal baynet l mawjoude fe l content provider 2aw 3ash 2e2dar 2a3mel etesal bel contentprovider lezm est5dem she esmo contentresolver le howe mn5elelo be2dar etwasal ma3 l content provider
                               ||
                               ||
4-rabe3 she 3ande content provider
                               ||
                               ||
5-5ames she   l conetnt provider byetwasal ma3 data stroge bjeb mena data


ya3ne badna nefterd 3ana activity b2lab application esmo khaled w datastorage heye datastorage ta3et application l contact fa ana 3ashn 2osal mn application esmo kahaled lal datastorage le mawjoude bel conatct app lezm 2osal mn 5elel selsle m3ayane databse ma7ada bye2dar yosala 8eer l contentprovider le bekoun m7aded fe l developer taba3 application l contact l sala7eyt le badak test5dema mn heda l application l conatact msln ya3ne enta masmo7lak ta3mel 3ard w ta3del mano masmo7lak ta3mel delete w add msln example w 3ashn est5dem l conetnt provider fe she lezm yetwasal ma3o le howe l contentresolver w ba3mel 3lee query metl ma kont 2a3mel beldatabse bel zabt bet3emal ma3 l content provider k2no howe databse bas howe mesh databse howe taba2 mawjoude fo2 databse be2dar ana mn 5elela 2osal lal baynet le masmo7le 2owsala  ya3ne l content resolver 3ebara 3an query w jomal ana bektobha metl ma kont tet3emal ma3 databse bel zabt laken be koyoud mo3yane
fa heda tasalsoul le mawjoud bade et3eml fe  3ashn et3emal ma3o w 2e2ra l databse  w 3adel 3lyha 2aw deef baynet ..ect mn 5elel application tene



hala2 bade 2a3mel immplement application byo3roud kel l asme2 l mawjoude bel device b2lab application l contact be3roudon b2lab recycleview kel esm elo soura ,ra2m l esm w email w tafasel tenye 7a yo3rouda:

1-2awal she bade 2a3mel recycele view w ladapter w model taba3a kel she taba3a hala2 3mlt l addapter w 3mlt l costume wkel she hala2 bel mainactivity w badna nshof keef badna njeeb data mn l cotentprovider
2-5alene 3aref Arraylist mno3 costume_cotact global
3-hala2 2awl sha8le badn nefhama 3ashn net3emal ma3 l content provider taba3 l asme2 l contact lezm ykoun 3andak permission esmo read contact heda mn 5elelo be2dar 2e2ra l conatct le bel application l conatct bas hyde senstive data so badna na3mel l permission kamen bel activity
4-hala2 b method readconatct ba3mel kel she8le code l contentprovider w 3abe l data be recycle view
5-fa ba3mel method a7san esma populatedatainrecycleview() bte5od arraylist mno3 conatactietm w yrou7 y3bele l data hay l method bdad testd3a b2lab method l readcontact

6-hala2 mnbalesh bel method readcontact w bel contentprovider
-fa le badoe 23mlo bade rou7 3ala l conetntprovider 2a3mel loop 3ala l data 2aw l asme2 l mawjoude bede5el application l contact be2ste5dem l contentprovider w 3abeha b recyele view ya3ne 3abeha bel arraylist w 2a3te heda l arraylist la method populatedataintoreccyleview fa byet3abo data bel recyelview

-2awl 5otwe lezm 3ashn etwasal ma3 l contentprovider taba3 aye application bel denye lezm ykoun ma3e l contract class fe she 3ande esmo contract class 2aw bel 3arabe class l 3akd le bado ykoun byne w been l content provider heda l class nafs sha5s developer le by3mel l content provider bya3mel heda l class be7otlna fe la heda l class asme2 l table esme2 l content providers tarteboun be7ot fe l tafasel ana masmo7le  2owsala mn l content provider be7otha b2lab heda l class fa ana msln bade rou7 jeeb l class tba3 l content provider fa 5alene 2awl she jeeb l arraylist le 3arfta fo2 le esma contact w 2a3mela intilize b2lab l method l read contact
-ba3den beje b3ref uri  taba3 l contacts fa beje b2olo Uri uri=ContractsContart heda 3ebra 3an class fe l ma3lomet le mn5elela be2dar 2osal lal content provider fa ContactsConract dot Contacts ya3ne bade l asme2 bade jeeb l uri taba3 l asme2 dot Content_Uri fa heda bejble l 3enwen taba3 l table l asme2 l contact le bde5el application l contact le bade jeb meno la heda table l data le heye l contact fa ana jebt l uri l 3enwen 3ash 2e2dar jeeb meno data la heda table ba3d heek
-ba3den beje ba3mel cursor metl ma ne7na 7kena men eyem databse l2no mn 5elelo bjeeb l data mn databse fa ba3mel Cursor cursor=getContentResolver() iza mnetzakar kona 3ashn netwasal ma3 l content provider mn7tej she esmo contentresolver howe l wa7ed le bye2dar yetwasal ma3 l contentprovider mne2dar mn 5elelo nektob query w jomal fa getcontentResolver() dot query() ba3mel query metl ma kent 2a3ml bel databse ba3teha l uri taba3e lal query tene parameter ba3teha l projection sho howe l projection asme2 l 2a3mede le bade 7adedha  fa ba3te null ya3ne 3ashn y7dedle kel l 2a3mede  kel l baynet selection ya3ne houn mn3mel where kaza kaza fa houn null ya3ne kel she bade ,telet parameter howe shorout le bade 7ota bel where ma bade 7ot shrout fa b5leha nullba3den e5er paramter l keyam taba3 shorout fa kamen b5leha null be ma2no ma 7atet shrout,e5er she paramter l order tarteboun msln ana bade ratehoun 7asab l contact name 2aw 7asb l asme2 tanazoleyan fa sho beje ba3mel beje b2olo rateboun 7asab l asme2 tanazolye  beje b2olo ContactsContaract dot Contact() dot le howe l asme2 ya3ne diplayname w plus  eno keef 7aykoun tarteb desnding 2aw asending b2olo DESC ,2aw ASC ya3ne ASC tasa3ode fa b2olo ASC
-heek ana jebt l ma3lomet l mawjoude bel contact w 7atyneha bel cursor hal2 bade 2a3mel loop 3ala l cursor  fa 2awl she bef7as hal l cursor fade wala la2 fa b2olo if cursor.movetofirst() iza kenet true ya3ne fe 3osnour bel first ya3ne 7awalo heek 3ala 2awal 3onsour metl ma 3mlna bel databse ba3den beje ba3mel do while , ya3ne 3ashn 2a3mel loop 3al cursor w jeeeb l ma3mlomet do{  haal2 houn bade jeeb ma3lomet w 5azena bel arraylist le esma contact fa 2awl she bade jeeb l id               while(cursor.movetonext());
2awl she bade jeeb l id fa Long id=cursor.getLong() ya3ne hetle l kemto le heye long l mawjoud bel cursor dot getcolumn index le esmo kaza le howe "_ID" fa heda l esm ma3mol lal id mawjoud bel table esmo _ID jebto ana mn documntation taba3etoun fa howe esmo _ID fa 2oltelo hatle hatle l index taba3o ba3den hatle nous le mawjoud fe fa houn Long id fa ana heek jebt l id taba3o
fa l id heda bade rou7 3ala l contact data taba3to w jeeb meno l data tab3to ya3ne mn 5elel heda l id bade jeeb kel data taba3 heda l id taba3o fa bade rou7 2a3mel kamen uri tene w query tenye 3ashn tjeeb baynet mn heda l uri fa bade 2a3mel uri tene w query tenye 3ashn tejble l data tab3 heda l id  l query le 2abl 3mlneha 3ashn tjeeb kel table ya3ne kel l asme2 hala2 l query le bade 2a3mela 3ashn tjble kel data le 5aso bheda l id fa hal2 bade emsek id id w jeeb mn 5elelo data  tab3 heda l id ta7t shart eno metl seled * from table kaza where id msln equll heda l id le jebto fa bade rou7 hala2 bas houn howe metl table inside table ya3ne ana 2awl query jebt kel l table l kbeer hala2 bade jeeb table l b2lbo bas howe mesh heek howe metl eno foregin key ya3ne ana jebt table le fe id hala2 bade jeeb data le be table tene le equll la heda l id
-2a3mel Uri  contacturi= howe 3ebra 3an ContactsContract.Data.ContentURI ana bade rou7 class data 2aw 3ala  table data w rou7 jeeb data tab3 heda l id
-w ba3den hala2 kamen ba3eml cursor equll la getcontentresolver().query() w b2olo 5od l uri taba3 heda table,tene paramter l 2a3mede le bado yjeble yeha null,3 paramter howe selection slection ana bade yeha bade 2olo where l id equll heda l id le jebto fa bade 2olo ContactsContract dot Data.Contact_ID+" =?" 3ashn security 2olna hay b7ded 2emto btene paramter ,new String[]{String.value(id)},l order null ma bade tarteb mo3yan
-hala2 sho data le bade jeba mn kel id bade jeeb l data metl l name,phone,image,email..details fa b3ref hala2 mot8yerat la kel wa7de
String displayName="";
String nickName="";
String homePhone="";
String workPhone="";
String photoPath="";
byte[] photoByte=null;
String homeEmail="";
String workEmail="";
String companyName="";
String title="";

bade jeeb diplayname,nickname,w ra2m l home,w ra2m l mobile w ra2m she8l,w l photopath ana bade 7ot soura ma3 l photo path by diffult eno bshakl eno iza ma keen fe soura yest5dem soura l deffult , w bade l photobyte heda soura btejena ka byte ka bitmap heye m5zane bel database ka blob ka binary large object fa ana 3ashn jeba bade jeba ka array of byte,w ba3den l home work company email, w title hay kel details le btl2eha b2lab application l contact fa ana bde jeba kola ba3den ana be5tar le bade yehoun
fa la 7ed hala2 ana jebt l id w jebna mn table mn jadwal table tafasel 2oltelo hatle kel l data le elo id equll kaza le howe le tal3ne mn jadwal l 2awal ba3den 3arft varible la data le bade jeboun mn l cursor
hala2 bade 2a3mel loop 3ala l cursor nafs ma 3mlt b 2awal cursor if w baden while loop
fa if (contactcursor.movetofirst) ya3ne fe 3anaser bel cursor fa rou7 3mele loop b as mesh lezm loop l2no hay data met3l2a b row wa7d bas fa ma bade 2omro2 3ala kel l rows fa bala loop
fa bade jeeb displayname fa b2olo displayname=l  cursor taba3 l contact dot getstring l2no name string() b2lobo contactcursor dot getcolumnindex sho esm l 3amoud taba3 display name contactcontract dot Contacts dot displayname

bas fe tare2a tenye eno 2a3ml loop 3ala heda l cursor 2a3ml check iza keen l cursor.getstring(contact.getcolumnindex("mimitype") hay l mimitype howe nou3 l 3onsour le 2e3ed blof 3lee .equls wala iza keen naw3o ContractsContarct.commonDatakinds heda 3ebra 3an table fe l anwa3 dot nickname wala law l 3amoud le ana we2ef 3ando equll la heda nickname dot content_item_type jeble nickname ya3ne iza keen nou3 l 3amoud nickname jeble nickname le howe esmo data1 fa wala law keen she tene 8eer nickname jeble msln l data2,dta3..etc msln le fe l email
 fa 5aznle yeh bel other details otherdetails+=nickname w nsal star \n
 nafs she ba3mel lal phone msln bas houn l mimitype equll lal commondatakind.phone.content_item_type
 bas houn fe kaza phone fe phone work,mobile,home..ect fa beje ba3mel switch baseta bshof hal heda l phone work,wala home,wala ...etc

howe kel phone wala dipaly name houn 3emlenoun ka class w b2lb heda l class fe 3na she esmo mimitype w b2lbo ve varible metl data1,data2 hawde la kel class msln bel email data 1 equll she data 2 email  she tene w bel company name l data1 she w data2 she tene

hala2 ne7na 5alsnamethod l readcontacts
hal2 ya3ne estd3eha bel else ta3el l permission mobasharatan heek momken tezbat ba3 la7 ya3melak pause 2aw y3le2 fa 5alene 2a3mel progress bar bnous shshe 2awal ma ye5tefe l progress bar yejo data

fa hala2 ma bade 2olo de8re read contacts bade 2a3mel thread 2aw handler 2aw async task


hal2 mn 5elel l contentresolver fena na3ml l crud operation 3ala data le mawjoude be app contact metl retrive,add,delete,edit kel haw haw bekono m3rafen sal7yet bel content provider eno feek testd5demoun le be3rfo l developer
w kamen fe ta3mel Batch operation 3mlyet mojma3a
hala2 l content provider le 2aktar she most3mlen houne l Contacts.Contact.Contacts,CalanderContact.Attendes,MediaStore.Audio.Albums

 StringBuilder stringBuilder=new StringBuilder();:heda 3ebra 3an class fena n7ot fe sting 2aw aye no3 object w feha na3mel repalce la character w delete caracter w howe more effince than + concatination bas badna nzeed mna3mel append() w bnzeed data w iza 3mlna appednd(khaled) appednad(assidi) w rja3na 3mlna system.out.println(stringbulder.string) natege 7a tetla3 khaledassidi

 ne7na 2olna eno iza kenet data kbere momken ye5od wa2t fa heda l wa2t y3tel l main thread fa mna3mel ya thread jdede ,async task,executor or she jded esmo loader

 request data mn l content provider bseer bel ui thread w ta3be2et data b recycle view kamen bseer bel ui thread fa heda bye5od wa2t w momken yseer exeption

 load the data from content provider take alot of time w btseer bel ui thread
 la n7el hay l meshkle mnest3mel loader,cursorloader hawde mne2dar nest5demoun la na3mel load la data men l content privider bedoun ma yet2sar l ui thread

 la nefham keef loader byeshte8l  badna na3ref eno kel activity 3anda single loader 2aw multiple loader
 3adatn btest5dam loader  le bel activity le bseer l activity tab3tna requst loader mn 5elel loadermanger class loader manger 3ando interface callbacks methods w l activity lezm ta3mel immplement la haw l methods fa bas ta3mel implemnt lal interface lezm ta3mel overide la haw l methods le houne oncreateLoader,onloaddinished,onloaderreset
 la nest5dmo l activity lezm ta3mel immplement la heda l interface LoaderManager.LoaderCallbacks fa bas na3mel heek l activity will intilize heda loader fa 7a yetla3o 3ande l methods l callbacks

 2awl method oncreateloader():hyde l method 3anda paramter esmo Contentprovider fa bhyde l method bt2ol eno aye content provider badak test3mel l contact,calander aye wa7d w hyde l method btesht8l be worker thread w bhay l method bt3mel load lal data w btjeboun w kel heda bseer bel worker thread internally

 ba3d hay l method data n3mloan load mn l content provider ya3ne jebnehoun 7a testd3a method esma
 OnLoadFinished():hyde l method btesd3a 3end ekteml ta7mel load mn l contnt provider  hay l method enta bt3be data ta3et l ui hayde  method bteshte8l bel ui thread

 w iza rj3na la documntation ta3et loader 7a nle2e eno heda l clas bte7tewe 3ala fe2et fer3eye mn l asynctask fa howe bas nest5dem l cursorloader bshakl 8ayr mobashar mnkoun 3am nest3mel l asynctask  fa 3ashn heek data bas na3mla load kelo bseer bel worker thread w ma bseer bel ui thread

 hala2 badna na3ml immplementation:
 2awl she badna na3ml immplemnt la interface loaderManager.Callbacks w bte5od geric type le howe l cursor naw3o cursor
 tene she mna3ml overide lal methods
 telet she lezm na3ml intilize lal loader bel activity fa le 7a na3mlo 7a nest3mel method esma getloaderManger() hyde btrj3le loader manger ba3den best3mel method b2lba emsa initloader() hyde bte5od 3 paramter 2awl wa7d integer data type ,tene wa7d howe l bundle ,w telet paramter l class le 3mel implent al loader
 2awl paramter:integer id unique le mn5elelo fena nel8e loader nsha8lo kel she l2no 2olna l activity momken ykoun feha kaza loader fa mnest3mel heda 3ashn nmayez loader tab3na iza keen fe kaza loader fa mn7ota 1 msln
 tene paramter heye bindle mn7ota null l2no ana ma bade mare2 aye data lal loader
 telet paramter howe l activity   this
 fa bas 2a3ml hay bekoun 3am 2a3ml trigger lal onreateloader method 3am sha8ela
 bhay l method lezm 2a3mlo  eno 2a3ml check iza keen l id equll 1 ma howe byeb3at l id w bundle 3ala l onreate method fa ne7na mnest2beloun w mna3ml chekck iza keen wa7d ya3ne heda l loader tab3na
 fa mn2olo return new CursorLoader() le be5od l context,uri taab3na w be2e l baynet ta3wlet l query l projection,order..ect fa hay l method btrou7 bt3mel load la data le bel uri w ba3d ektemel ta7mel mn l uri btestd3a method l onloadfinished ya3ne method retun cursorLoader btrou7 btjeble data mn l 3enwen l uri heda metl l query bas houn heye 3am tseer be worker thread
 fa ba3d ma tentehe mn load data btestd3a l onloadfinished w btb3tle l cursor taba3 data le jebneha mn l onreateloader fa b2lab mna3ml setuup lal ui w mnjeeb data bekl row mn7ota b array w mn3be l recycel view hay btestd3a b2kb l ui thread

 hala2 bel nesbe lal getloadermanger.initloader(1,null,this) hyde l a7san na3ml check flag iza kona 2awal mara mn3mel loaded nna3ela iza mesh 2awl mara mn3ml ba3detl intloader mn3ml restartloader(1,null,this);
 w loader howe 3atoul async ya3ne iza zedt data de8re bzed bel app iza 3mlt delete la data de8re byen2as mn  l app

 hala2 ne7na shofna keef na3mel retrive la data mn 5elel l getcontetresolver.query()
 haala2 fe kamen be2e l crud operation
 insert:insert() inset la data 3ala l content provider mn 5elel method insert()
 update:update() update la data m3yane mawjoude bel content provider mn 5elel method l update()
 delete:delet() delete la data m3yane mawjoude bel content provider  mn 5elel method esma delet()

 hala2 badna nshof keef badna na3meoloun :
 3ana msl method delete bte5od l uri metl query,w where cluase fa bt3mel delete la data 7asb l where clause

table le howe contact b2lbo kel rows ta3wlet different pepole based on aggregation of raw contact rows
table le howe RawContact contain summary of persons data ,specific to a user account type
table l Data conatin detils of raw contact sush as email adresses,or phone number
fa ne7na mnest3mel ya data,2aw rawcontacts

hal2 badna na3ml method l update la7 nes3mel l contentvalue class bade bas ekbous 3al 3onsour erja3 ektoub she bel edit text tet3dal kemet name

hala2 bel nesbe lal insert:
ne7na bel insert fena na3mel add la multiple operation ya3ne nzed 2 data ma3 ba3d
    best5dem she esmo applypatch ya3ne 3meel add la operation b def3a wa7de bte5od array list b2lba contentprovideroperation w bt3mel apply la hwde b def3a wa7de
    la na3mel contentprovideroperation mnest3ml l class heda w mn3mel inser bte5od 2awl she ween badak ta3mel add b 2aye table w ba3den t7ot l vlaues tab3 l columns le bel table


hala2 badna nblesh b jez2 l costume content provider
ya3ne hala2 ne7na badna na3mel content provider 5as bel application ta3elna bye2dar mn 5elelo application tene yest3mlo

hala2 abl ma nblesh badna nes2l hal mohem bkel application na3mel content provider?
la2 mesh darore kel application na3mlo content provider
aymta bseer l content provider mohem iza kouna b7aje la neb3at data ta3etna la application tene 2aw iza keen badna copy la data ta3etna
2aw iza kouna mnest5dem ba3d apis metl cursor loader,cursor addapter,abstarctthreadsyncAdapter

hala2 sho nou3 data l content provider ekoun b2alba:
Data from databse
files Data(Aundio,vedio,photo,json,xml)
2aw data le btkoun mawjoude 3ala remote cload w badak ta3mel sync la hay data NetworkData(AbstarctThreadedSyncAdapter)

hala2 keef badna nblesh bel implemnatation ta3et l content provider?

hala2 iza kouna 3am nest3mel database bel application ta3tna lezm tet2kad eno databse ta3etna 3anda primary key l2no iza ma keen fe primary key mesh 7a yeshte8l l content provider
fa lezm ta3mel class extend contentprovider
w lezm t7aded l uri l moneseb ta3et hayde tables le 3ande
sho howe l content uri sho azde fe?
bas kona 3am nest5dem l content resolver la nosal la table m3ayn bel application l contact fa kona 3am nshof eno 2awl paramter bte5oda l contentresolver heye l uri
l uri bshakl wade7 howe  howe 3enwen m3yan la nosal la data m3ayen bel content provider
bas hso howe l CONTENT_URI sho l hykaleye ta3eto,w keef l conetnt provider bjeble l data based on CONTENT_URI le b2sher 3ala table m3yan bel databse w iza ma ken table sho bekoun 3am y2sher?
hayde kol l 2as2ele le 7a njeweb 3lyha l youm
fa 5alona hala2 nefham sho howe l CONTENT_URI:
5alena neftered eno 3ana databse bel application ta3tna l food app w hyde databse le 3ande feha table 2aw tnen msln hala2 bas yeje request mn l conetnt resolver lel wosoul la aye data aye table mn hawde table le mwjouden 3ana bel app l request bekoun 3ebra 3an ya request la table m3yan ya request la kel tables 2aw momken ykoun request la column m3yan bas b2lab table m3yan select statement btotlob bas column m3yan ya3ne
complete request:ya3ne kel l databse kel tables
pariatl:tabel mn tables le 3ande
select:slect la column m3yan b table m3yan
fa 3ashn te2dar l contnt resolver ta3mel heda request lezm la kel wa7de mn haw n3ref 3anwen uri lal wosoul la haw l data howe mojarad varible sebet b3ber 3an 3enwen table 3enwen databse 3enwen column b databse
l uri byetshakl mn 3 mokwenet:
uri content:l content howe ma she bas howe slesle metl le btkoun mawjoude bel webistes metl content://
uri authority:l authority heye l package name ta3et l application le 3am njeeb mena data
uri path:user defined string constants ya3ne dataset
fa bel a7e2a majmo3 hawde momken yetla3 3ana heek she content://com.example.foodapp/Todo_List

le 7a na3mlo la nest5dem l application le 3mlo le howe food app le 7a na3mlo 7a na3mel implemntation lal contnt provider w b3den la7 nest5dem heda l app la 3ard data le bel app food app
-hal2 ba3d ma 5alsna implement lal costume content provider mneje 3ala heda l app w mnfez implemnation le howe badna na3ml request mn 5elel l contntresolver w l app taba3 l food 7a ya3mel respone la heda l app w n7na nestblo b cursor w n7ot data b recycle view

-2awl she lezm na3mlo howe na3ml create la instnce of contentresolver then using matching Cotent_uri as argumnt le ne7na 3arafnehoun le houne conetnt_uri_1,2,3
-ba3d heek la7 nest5dem l content resolver methods metl l query,insert,dekte,update

hala2 bel implemntation:
2awl she bade 2a3mel interface esmo foodproviderconstraint b2lbo kel l content uri le 3mlteloun define bel app1,ba3d heek bade 3aref varible houne l column le bel table
ne7na 3ana application heda b2lbo button getall foods w fe 3ana edittext fena n7ot feha ra2m l id taba3 l category btejble kel l food bheda l category ,ba3den 3ane kbset next btwadene 3ala ctivity tenye
mn 5ela hay l activity fene 2a3ml insert la food jdeed b category m3yane w iza rje3t 3al l app 7a la7ez eno nzeed food  w kamen iza badna na3ml update mne2dar badna na3ml delete mne2dar na3mel delete 7asb l id taba3 l food









 */












public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ActivityMainBinding binding;
    ArrayList<ContactItem> contact;
    ContactItem contact1;
    ActivityResultLauncher<String> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                binding.progressBar.setVisibility(View.VISIBLE);
                if(o){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            readContact();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    populateDataIntoRecycleView(contact);
                                    binding.progressBar.setVisibility(View.GONE);
                                }
                            });

                        }
                    }).start();

//                    getLoaderManager().initLoader(1,null,this);

                }
            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            activityResultLauncher.launch(Manifest.permission.WRITE_CONTACTS);
        }else{
            binding.progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    readContact();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateDataIntoRecycleView(contact);
                            binding.progressBar.setVisibility(View.GONE);

                        }
                    });
                }
            }).start();
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.READ_CONTACTS);
        }

    }

    private void populateDataIntoRecycleView(ArrayList<ContactItem> arrayList){
        RecycleAddapter recycleAddapter=new RecycleAddapter(this, arrayList, new onClick() {
            @Override
            public void click(ContactItem contactItem) {
                contact1=contactItem;
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.recycleView.setAdapter(recycleAddapter);
    }

    private void readContact() {
        contact=new ArrayList<>();
        Uri uri= ContactsContract.Contacts.CONTENT_URI;

        Cursor cursor=getContentResolver().query(uri,null,null,null,ContactsContract.Contacts.DISPLAY_NAME+" ASC");
        if(cursor.moveToFirst()){
            do{
                long id=cursor.getLong(cursor.getColumnIndexOrThrow("_ID"));
                Uri contactURI=ContactsContract.Data.CONTENT_URI;
                Cursor contactCursor=getContentResolver().query(contactURI,null,ContactsContract.Data.CONTACT_ID+"=?",new String[]{String.valueOf(id)},null);

                String displayName="";
                String nickName="";
                String homePhone="";
                String workPhone="";
                String mobilePhone="";
                String photoPath="";
                byte[] photoByte=null;
                String homeEmail="";
                String workEmail="";
                String companyName="";
                String title="";
                String contactEmailAddresses="";
                String contactotherDetails="";
                String CotactNumbers="";

                if(contactCursor.moveToFirst()) {
                    displayName = contactCursor.getString(contactCursor.getColumnIndexOrThrow(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY));
                    Log.d("khaledass",displayName+"");

                    do {
                        if (contactCursor.getString(contactCursor.getColumnIndexOrThrow("mimetype")).equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
                            nickName = contactCursor.getString(contactCursor.getColumnIndexOrThrow("data1"));
                            contactotherDetails += nickName + "\n";
                        }
                        if (contactCursor.getString(contactCursor.getColumnIndexOrThrow("mimetype")).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            switch (contactCursor.getInt(contactCursor.getColumnIndexOrThrow("data2"))) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    homePhone = contactCursor.getString(contactCursor.getColumnIndexOrThrow("data1"));
                                    CotactNumbers += "Home Phone: " + homePhone + "\n";
                                    break;

                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    workPhone = contactCursor.getString(contactCursor.getColumnIndexOrThrow("data1"));
                                    CotactNumbers += "Work Phone: " + workPhone + "\n";
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = contactCursor.getString(contactCursor.getColumnIndexOrThrow("data1"));
                                    CotactNumbers += "Mobile Phone: " + mobilePhone + "\n";
                                    break;


                            }
                        }

                        if (contactCursor.getString(contactCursor.getColumnIndexOrThrow("mimetype")).equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            switch (contactCursor.getInt(contactCursor.getColumnIndexOrThrow("data2"))) {
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    homeEmail = contactCursor.getString(contactCursor.getColumnIndexOrThrow("data1"));
                                    contactEmailAddresses += "Home Email: " + homeEmail + "\n";
                                    break;

                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    workEmail = contactCursor.getString(contactCursor.getColumnIndexOrThrow("data1"));
                                    contactEmailAddresses += "Work Email: " + workEmail + "\n";
                                    break;


                            }
                        }


                        if (contactCursor.getString(contactCursor.getColumnIndexOrThrow("mimetype")).equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                            companyName = contactCursor.getString(contactCursor.getColumnIndexOrThrow("data1"));
                            contactotherDetails += "Company Name: " + companyName + "\n";

                            title = contactCursor.getString(contactCursor.getColumnIndexOrThrow("data4"));
                            contactotherDetails += "Title: " + title + "\n";
                        }

                        if (contactCursor.getString(contactCursor.getColumnIndexOrThrow("mimetype")).equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            photoByte = contactCursor.getBlob(contactCursor.getColumnIndexOrThrow("data15"));
                            //houn btjeble l soura ka blob binray large object fa enta hala2 btsayeva ya bel cahse 2aw bshe matra7 heye bterja3lak bitmap

                            if (photoByte != null) {
                                //fa 2awl she bade 7awel l arrayof byte la object mno3 bitmap keef:
                                Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);

                                //ba3den rou7 nshe2 file mo3yan mawjoud bel cashe direcotry
                                File casheDirectory = getBaseContext().getCacheDir();
                                //hala2 7ot file b2lab heda l cashe b7ot l esm le bade yeh msln androidhub
                                File temp = new File(casheDirectory.getParent() + "/_androhub" + id + ".png");

                                //houn bt3mel try catch la fileoutputstrap la nektob 3lee
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(temp);

                                    //ba3den ba3mel copress lal image le heye hala2 object mno3 bitmap
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                //ba3den me5od l path taba3 l image
                                photoPath = temp.getPath();

                                //kel l code le 3mlto 3ashn sha8le wa7de bas 3ashn 5azen l photo bel cashe memory w ne5od rabet taba3a neb3at rabet tab3a lal object le esmo contentadapter
                            }
                        }


                    } while (contactCursor.moveToNext());
                    contact.add(new ContactItem(Long.toString(id),displayName,CotactNumbers,contactotherDetails,photoPath,contactEmailAddresses));

                }

            }while (cursor.moveToNext());






        }
        binding.add.setOnClickListener(v->{
            InsertContact(binding.edittext.getText().toString());
        });

        binding.update.setOnClickListener(v->{
            UpdateContact(binding.edittext.getText().toString(),contact1.getId());
        });

        binding.delete.setOnClickListener(v->{
            DeleteContact(binding.edittext.getText().toString());
        });
    }



    public void DeleteContact(String value){
        getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,ContactsContract.Data.DISPLAY_NAME+"=?",new String[]{value});
    }

    public void UpdateContact(String value,String id){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,value);
        getContentResolver().update(ContactsContract.RawContacts.CONTENT_URI,contentValues,ContactsContract.Data.CONTACT_ID+"=?",new String[]{id});

    }

    public void InsertContact(String value){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "com.google") // Example account type
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "your.account.name")
                .build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0) // Reference the raw contact
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, value)
                .build());

// Add more operations for phone numbers, emails, etc.

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            // Handle exceptions
        }
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,value);
//        contentValues.put(ContactsContract.RawContacts.CONTACT_ID,0);
//        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//           getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI,contentValues);
//
//       }
//        Log.d("khaledass",(String) contentValues.get(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY));
//        binding.recycleView.getAdapter().notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}